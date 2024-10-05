package com.victor.financeapp.file.upload.application.upload.mapper;

import com.victor.financeapp.file.upload.application.upload.dto.UploadDTO;
import com.victor.financeapp.file.upload.core.upload.entity.Upload;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UploadMapper {

    public static UploadDTO uploadtoDTO(Upload upload) {
        if (upload != null) {
            return UploadDTO.builder()
                    .userId(upload.userId())
                    .build();
        }
        return null;
    }
}
