package com.victor.financeapp.file.storage.application.upload.dto;

import lombok.Builder;

@Builder
public record SavedChunkDTO(
        String id,
        String path
) {
}
