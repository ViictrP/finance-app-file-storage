# FINANCE APP BACKEND
API for Finance App

[![Build - finance-app-backend-java](https://github.com/ViictrP/finance-app-backend-java/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/ViictrP/finance-app-backend-java/actions/workflows/build.yml)

## Building the application
    docker build --tag file-storage -f ./Dockerfile .

## Running the application
>Before running the application in Docker, you must run docker compose on `docker-compose.yml`.
This will run all the dependencies for this application to start.

Now you can run the docker image you have built

    docker run -d -e DATABASE_URL=DATABASE_URL -e DB_USER=DB_USER -e DB_PASSWORD=DB_PASSWORD -e PORT=PORT -p PORT:PORT tag:version

To verify if the API is UP go to `http://localhost:8080/actuator/health`

## API Documentation
The Swagger of this application can be found at <br>
#### `http://localhost:8080/swagger-ui.html`

