package com.victor.financeapp.file.storage.application.upload.dto;

import lombok.Builder;
import org.springframework.http.codec.multipart.Part;

@Builder
public record ChunkDTO(
        String userId,
        String uploadId,
        Integer partNumber,
        Part file,
        String fileName,
        Long fileSize,
        Long totalFileSize,
        String mimeType,
        String path
) {
}
