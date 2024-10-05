package com.victor.financeapp.file.upload.application.upload.dto;

import lombok.Builder;
import org.springframework.http.codec.multipart.Part;

@Builder
public record UploadChunkDTO(
        String id,
        String uploadId,
        Integer partNumber,
        String userId,
        String status,
        Part file
) {
}
