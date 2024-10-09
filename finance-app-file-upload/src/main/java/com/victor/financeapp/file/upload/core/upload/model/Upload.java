package com.victor.financeapp.file.upload.core.upload.model;

import com.victor.financeapp.file.upload.core.upload.model.enums.Status;
import lombok.Builder;

@Builder
public record Upload(
        String id,
        Status status,
        String userId,
        String uploadId,
        String fileName,
        String fileExtension,
        Long fileSize,
        String filePath,
        Integer totalParts,
        Integer currentPart
) {
}
