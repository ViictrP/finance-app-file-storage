package com.victor.financeapp.file.upload.core.upload.entity;

import com.victor.financeapp.file.upload.core.upload.entity.enums.Status;
import com.victor.financeapp.file.upload.core.upload.exception.MissingRequiredFieldException;
import lombok.Builder;
import org.springframework.http.codec.multipart.Part;

@Builder
public record UploadChunk(
        String id,
        Integer partNumber,
        String userId,
        String uploadId,
        Status status,
        Part file
) {

    public void validate() {
        if (partNumber == null || userId == null || uploadId == null || file == null) {
            throw new MissingRequiredFieldException();
        }
    }
}
