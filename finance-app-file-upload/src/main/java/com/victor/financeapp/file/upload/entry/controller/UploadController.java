package com.victor.financeapp.file.upload.entry.controller;

import com.victor.financeapp.file.upload.application.upload.UploadApplication;
import com.victor.financeapp.file.upload.application.upload.dto.UploadDTO;
import com.victor.financeapp.file.upload.entry.controller.request.UploadRequest;
import com.victor.financeapp.file.upload.entry.controller.response.UploadResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/v1/uploads")
@RequiredArgsConstructor
public class UploadController {

    private final UploadApplication application;

    @PostMapping
    public Mono<ResponseEntity<UploadResponse>> createUpload(@RequestBody @Valid UploadRequest request) {
        log.info("Received upload request for user {}", request.getUserId());
        return application.createUpload(UploadDTO.builder().userId(request.getUserId()).build())
                .flatMap(uploadDTO ->
                    Mono.just(ResponseEntity.ok(UploadResponse.builder()
                        .uploadId(uploadDTO.uploadId())
                        .id(uploadDTO.id())
                        .status(uploadDTO.status())
                        .userId(uploadDTO.userId())
                        .build()))
                );
    }
}
