package com.victor.financeapp.file.storage.core.entity;

import lombok.Builder;
import org.springframework.http.codec.multipart.Part;

@Builder
public record Chunk(
        String id,
        String uploadId,
        Integer partNumber,
        String userId,
        Part file
) {
}
