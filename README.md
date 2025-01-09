# File Server Service

![Java](https://img.shields.io/badge/Java-17-red.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.10-brightgreen.svg)
![Maven](https://img.shields.io/badge/Maven-3.8.5-yellow.svg)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1.14-orange.svg)

Este é um serviço de **File Server** para upload e download de arquivos, desenvolvido utilizando **Spring Boot**, **Java 17**, **Maven 3.8.5**, e **Thymeleaf** para o frontend. A aplicação permite o upload de arquivos para o servidor e fornece a opção de download através de uma interface web simples.

## Funcionalidades Principais

- Upload de arquivos para o servidor.
- Download de arquivos já armazenados.
- Interface de usuário utilizando Thymeleaf.

## Pré-requisitos

- **JDK 17 ou superior**
- **Maven 3.8.5**
- **Banco de Dados** (opcional, para armazenamento de metadados dos arquivos)
- **Docker** (para execução de containers, se necessário)

## Instalação e Configuração

1. Clone este repositório: `git clone https://github.com/devlucasmart/fileflow-api.git`
2. Navegue até o diretório do projeto: `cd fileflow-api`
3. Configure as propriedades de armazenamento de arquivos em `src/main/resources/application.yml`.
4. Execute o comando para levantar o container da aplicação (somente se tiver o docker instalado): `docker-compose up -d`.
5. Compile o projeto utilizando Maven: `mvn clean install`.
6. Execute o projeto: `mvn spring-boot:run`.
7. Abra o navegador e acesse a interface na URL: `http://localhost:8080`.

## Tela de Upload e Download

A aplicação oferece uma tela simples onde os usuários podem fazer o upload de arquivos e visualizar a lista de arquivos disponíveis para download. A interface é gerada dinamicamente usando **Thymeleaf** e exibe os arquivos armazenados no servidor.

- **Página Inicial**: 
  - Formulário de upload de arquivos.
  - Lista de arquivos com botões para download.