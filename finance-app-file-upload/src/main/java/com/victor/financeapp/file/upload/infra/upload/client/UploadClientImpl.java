package com.victor.financeapp.file.upload.infra.upload.client;

import com.victor.financeapp.file.upload.core.upload.client.UploadClient;
import com.victor.financeapp.file.upload.core.upload.client.response.UploadResponse;
import com.victor.financeapp.file.upload.core.upload.model.Upload;
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
    public Mono<UploadResponse> send(Upload upload) {
        var chunk = upload.getCurrentChunk();
        return storageWebClient.post()
                .uri("/v1/chunks")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("partNumber", chunk.getPartNumber().toString())
                        .with("userId", chunk.getUserId())
                        .with("fileName", upload.getFileName())
                        .with("fileExtension", upload.getFileExtension())
                        .with("fileSize", upload.getFileSize().toString())
                        .with("uploadId", upload.getUploadId())
                        .with("file", chunk.getFile())
                )
                .retrieve()
                .bodyToMono(UploadResponse.class)
                .doOnSuccess(response -> log.info("Upload chunk successful: {}", response.getFilePath()))
                .doOnError(error -> log.info("Error uploading chunk: {}", error.getMessage()))
                .onErrorReturn(UploadResponse.unsuccessful());
    }
}
