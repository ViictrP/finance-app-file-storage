package com.victor.financeapp.file.storage.entry.controller;

import com.victor.financeapp.file.storage.application.upload.UploadApplication;
import com.victor.financeapp.file.storage.entry.response.UploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/chunks")
@RequiredArgsConstructor
public class UploadController {
    private final UploadApplication uploadApplication;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<UploadResponse>> uploadChunk(@RequestPart String userId,
                                                            @RequestPart String partNumber,
                                                            @RequestPart Part file) {
        var uploadId = UUID.randomUUID().toString(); // Generate a unique upload ID for each file upload request
        log.info("Received chunk {} for upload {} and file {}", partNumber, uploadId, file.name());
        return uploadApplication.uploadFile(userId, Integer.parseInt(partNumber), file)
                .map(success -> ResponseEntity.ok(UploadResponse.builder()
                        .uploadId(uploadId)
                        .success(success)
                        .build()));
    }

    @PostMapping("/{uploadId}")
    public Mono<ResponseEntity<UploadResponse>> uploadPart(@PathVariable String uploadId,
                                                  @RequestPart String userId,
                                                  @RequestPart String partNumber,
                                                  @RequestPart Part file) {
        log.info("Received chunk {} for upload {} and file {}", partNumber, uploadId, file.name());
        return uploadApplication.uploadFilePart(userId, uploadId, Integer.parseInt(partNumber), file)
                .map(success -> ResponseEntity.ok(UploadResponse.builder()
                        .uploadId(uploadId)
                        .success(success)
                        .build()));
    }
}
