package com.victor.financeapp.file.upload.entry.controller;

import com.victor.financeapp.file.upload.application.upload.UploadApplication;
import com.victor.financeapp.file.upload.application.upload.dto.UploadChunkDTO;
import com.victor.financeapp.file.upload.application.upload.dto.UploadDTO;
import com.victor.financeapp.file.upload.entry.controller.request.UploadRequest;
import com.victor.financeapp.file.upload.entry.controller.response.UploadResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
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
        return application.createUpload(UploadDTO.builder()
                .userId(request.getUserId())
                .totalParts(request.getTotalParts())
                .build()
        ).flatMap(uploadDTO ->
                Mono.just(ResponseEntity.ok(UploadResponse.builder()
                        .uploadId(uploadDTO.uploadId())
                        .id(uploadDTO.id())
                        .status(uploadDTO.status())
                        .userId(uploadDTO.userId())
                        .totalParts(uploadDTO.totalParts())
                        .build()))
        );
    }

    @PostMapping("/{uploadId}/chunks")
    public Mono<ResponseEntity<UploadResponse>> createUploadChunk(@PathVariable String uploadId,
                                                                  @RequestPart String partNumber,
                                                                  @RequestPart String userId,
                                                                  @RequestPart Part file) {
        log.info("Received upload chunk request for user {}, part {}", userId, partNumber);
        return application.createUploadChunk(UploadChunkDTO.builder()
                .uploadId(uploadId)
                .partNumber(Integer.parseInt(partNumber))
                .userId(userId)
                .file(file)
                .build()
        ).flatMap(chunk -> Mono.just(ResponseEntity.ok(UploadResponse.builder()
                .id(chunk.id())
                .userId(chunk.userId())
                .uploadId(chunk.uploadId())
                .status(chunk.status())
                .build())));
    }
}
