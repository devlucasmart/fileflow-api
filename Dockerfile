# Etapa 1: Construção da aplicação
FROM maven:3.8.5-openjdk-17 AS build

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie o arquivo pom.xml para o container
COPY pom.xml .

# Baixe as dependências do Maven
RUN mvn dependency:go-offline

# Copie o código-fonte para o container
COPY src ./src

# Compile a aplicação (gerar o JAR)
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final com o JAR pronto
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie o JAR gerado do build anterior
COPY --from=build /app/target/*.jar app.jar

# Exponha a porta que o Spring Boot usará
EXPOSE 8080

# Execute o JAR gerado
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
