package com.victor.financeapp.file.upload.infra.upload.client;

import com.victor.financeapp.file.upload.core.upload.client.UploadClient;
import com.victor.financeapp.file.upload.core.upload.client.dto.UploadChunkClientDTO;
import com.victor.financeapp.file.upload.core.upload.client.response.UploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
class UploadClientImpl implements UploadClient {

    private final WebClient storageWebClient;

    @Override
    public Mono<UploadResponse> send(UploadChunkClientDTO chunk) {
        return storageWebClient.post()
                .uri("/v1/chunks")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("partNumber", chunk.partNumber().toString())
                        .with("userId", chunk.userId())
                        .with("fileName", chunk.fileName())
                        .with("fileExtension", chunk.fileExtension())
                        .with("fileSize", chunk.fileSize().toString())
                        .with("uploadId", chunk.uploadId())
                        .with("file", chunk.file())
                )
                .retrieve()
                .bodyToMono(UploadResponse.class)
                .doOnSuccess(response -> log.info("Upload chunk successful: {}", response.getFilePath()))
                .doOnError(error -> log.info("Error uploading chunk: {}", error.getMessage()))
                .onErrorReturn(UploadResponse.unsuccessful());
    }
}
