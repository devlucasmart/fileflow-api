package com.devlucasmart.fileflow.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devlucasmart.fileflow.FileStorageProperties;

import jakarta.servlet.http.HttpServletRequest;



@Controller
@RequestMapping("/api/file")
public class FileStorageController {
    private final Path fileStorageLocation;

    public FileStorageController(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
        .toAbsolutePath().normalize();
    }

    @PostMapping("upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        String fileName = StringUtils.cleanPath((Objects.requireNonNull(file.getOriginalFilename())));
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation.toFile());

            redirectAttributes.addFlashAttribute("message", "Upload realizado com sucesso: " + fileName);
            return "redirect:/";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao realizar upload do arquivo.");
            return "redirect:/";
        }
    }


    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        try {
            Resource resource =  new UrlResource(filePath.toUri());

            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            if (contentType == null) {
                contentType = "application/octet-stream";
                
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("list")
    public ResponseEntity<List<String>> listAllFiles() throws IOException {
        List<String> fileNames = Files.list(fileStorageLocation)
            .map(Path::getFileName)
            .map(Path::toString)
            .collect(Collectors.toList());

        return ResponseEntity.ok(fileNames);
    }
}
