package com.victor.financeapp.file.upload.core.upload.client.dto;

import org.springframework.http.codec.multipart.Part;

public record UploadChunkClientDTO(
        String uploadId,
        String userId,
        Integer partNumber,
        String fileName,
        String fileExtension,
        Long fileSize,
        Part file
) {
}
