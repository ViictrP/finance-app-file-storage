package com.victor.financeapp.file.upload.entry.controller.response;

import lombok.Builder;

@Builder
public record UploadResponse(
        String id,
        String status,
        String userId,
        String uploadId,
        Integer totalParts
) {
}
