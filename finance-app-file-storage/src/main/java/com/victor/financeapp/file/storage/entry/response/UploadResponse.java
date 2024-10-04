package com.victor.financeapp.file.storage.entry.response;

import lombok.Builder;

@Builder
public record UploadResponse(
        String uploadId,
        Boolean success
) {
}