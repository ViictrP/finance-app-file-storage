package com.victor.financeapp.file.upload.application.upload.mapper;

import com.victor.financeapp.file.upload.application.upload.dto.UploadChunkDTO;
import com.victor.financeapp.file.upload.core.upload.model.UploadChunk;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UploadChunkMapper {

    public static UploadChunkDTO uploadChunktoDTO(UploadChunk uploadChunk) {
        if (uploadChunk != null) {
            return UploadChunkDTO.builder()
                    .id(uploadChunk.getId())
                    .uploadId(uploadChunk.getUploadId())
                    .partNumber(uploadChunk.getPartNumber())
                    .userId(uploadChunk.getUserId())
                    .status(uploadChunk.getStatus().name())
                    .build();
        }
        return null;
    }
}
