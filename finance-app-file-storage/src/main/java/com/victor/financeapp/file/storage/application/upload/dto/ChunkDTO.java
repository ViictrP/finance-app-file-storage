package com.victor.financeapp.file.storage.application.upload.dto;

import lombok.Builder;

@Builder
public record ChunkDTO(
        String id,
        String uploadId,
        Integer partNumber,
        byte[] data
) {
}
