package com.victor.financeapp.file.upload.core.upload.model;

import com.victor.financeapp.file.upload.core.upload.exception.MissingRequiredFieldException;
import com.victor.financeapp.file.upload.core.upload.model.enums.Status;
import lombok.Builder;
import org.springframework.http.codec.multipart.Part;

@Builder
public record UploadChunk(
        String id,
        Integer partNumber,
        String userId,
        String uploadId,
        Status status,
        String chunkPath,
        Part file
) {

    public void validate() {
        if (partNumber == null || userId == null || uploadId == null || file == null) {
            throw new MissingRequiredFieldException();
        }
    }
}
