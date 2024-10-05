# Etapa de construção usando a imagem do Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /usr/src/app

# Copia o pom.xml para a pasta de trabalho atual
COPY ./finance-app-file-upload/pom.xml ./

# Baixa as dependências do Maven (ajuda no cache para builds futuros)
RUN mvn dependency:go-offline

# Copia o restante dos arquivos da aplicação para a pasta de trabalho
COPY ./finance-app-file-upload/src ./src

# Compila a aplicação
RUN mvn clean install -DskipTests

# Etapa de execução usando uma imagem mais enxuta do OpenJDK
FROM openjdk:21-slim AS runtime
WORKDIR /usr/src/app

# Copia o JAR gerado pela etapa de build
COPY --from=build /usr/src/app/target/finance-app-file-upload-0.0.1-SNAPSHOT.jar ./app.jar

# Definindo variáveis de ambiente
ENV PORT=$PORT
ENV DATABASE_URL=$DATABASE_URL
ENV DB_USER=$DB_USER
ENV DB_PASSWORD=$DB_PASSWORD
ENV DB_PORT=$DB_PORT

# Expõe a porta da aplicação
EXPOSE $PORT

# Inicia a aplicação Java
ENTRYPOINT ["java", "-jar", "./app.jar"]