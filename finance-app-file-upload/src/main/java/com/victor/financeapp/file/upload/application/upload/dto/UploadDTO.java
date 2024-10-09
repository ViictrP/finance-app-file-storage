package com.victor.financeapp.file.upload.application.upload.dto;

import lombok.Builder;

@Builder
public record UploadDTO(
        String id,
        String userId,
        String uploadId,
        String fileName,
        String fileExtension,
        String fileSize,
        String status,
        Integer totalParts
) {
}
