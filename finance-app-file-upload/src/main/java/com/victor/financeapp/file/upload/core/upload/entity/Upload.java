package com.victor.financeapp.file.upload.core.upload.entity;

import com.victor.financeapp.file.upload.core.upload.entity.enums.Status;
import lombok.Builder;

@Builder
public record Upload(
        String id,
        Status status,
        String userId,
        String uploadId,
        Integer totalParts,
        Integer currentPart
) {
}
