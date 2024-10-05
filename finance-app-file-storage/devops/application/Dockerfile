FROM maven:3.9.9-eclipse-temurin-21 AS mvn
COPY .. ./usr/src/app
WORKDIR ./usr/src/app

RUN mvn clean install -DskipTests

FROM openjdk:21-slim AS java
WORKDIR ./usr/src/app
COPY --from=mvn /usr/src/app/target/finance-app-file-storage-0.0.1-SNAPSHOT.jar ./app.jar

ENV PORT=$PORT
ENV DATABASE_URL=$DATABASE_URL
ENV DB_USER=$DB_USER
ENV DB_PASSWORD=$DB_PASSWORD
ENV DB_PORT=$DB_PORT

ENTRYPOINT ["java", "-jar", "./app.jar"]
EXPOSE $PORT
