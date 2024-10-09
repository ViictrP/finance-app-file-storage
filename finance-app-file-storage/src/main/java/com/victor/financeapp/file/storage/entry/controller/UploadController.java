package com.victor.financeapp.file.storage.entry.controller;

import com.victor.financeapp.file.storage.application.upload.UploadApplication;
import com.victor.financeapp.file.storage.application.upload.dto.ChunkDTO;
import com.victor.financeapp.file.storage.entry.response.UploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/v1/chunks")
@RequiredArgsConstructor
public class UploadController {
    private final UploadApplication uploadApplication;

    @PostMapping
    public Mono<ResponseEntity<UploadResponse>> uploadPart(@RequestPart String uploadId,
                                                           @RequestPart String userId,
                                                           @RequestPart String partNumber,
                                                           @RequestPart String fileName,
                                                           @RequestPart String fileExtension,
                                                           @RequestPart Part file) {
        log.info("Received chunk {} for upload {} and file {}", partNumber, uploadId, file.name());
        return uploadApplication.uploadFilePart(ChunkDTO.builder()
                        .partNumber(Integer.valueOf(partNumber))
                        .userId(userId)
                        .uploadId(uploadId)
                        .fileName(fileName + "." + fileExtension)
                        .mimeType(fileExtension)
                        .build())
                .map(savedChunkDTO -> ResponseEntity.ok(UploadResponse.builder()
                        .uploadId(uploadId)
                        .success(true)
                        .fileId(savedChunkDTO.id())
                        .filePath(savedChunkDTO.path())
                        .build()));
    }
}
