package com.victor.financeapp.file.storage.application.upload.response;

import lombok.Builder;

@Builder
public record UploadResponse(
        String uploadId,
        Boolean success
) {
}
