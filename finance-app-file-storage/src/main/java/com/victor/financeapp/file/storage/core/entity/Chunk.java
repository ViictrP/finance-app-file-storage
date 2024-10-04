package com.victor.financeapp.file.storage.core.entity;

import lombok.Builder;

@Builder
public record Chunk(
        String id,
        String uploadId,
        Integer partNumber,
        byte[] data
) {
}
