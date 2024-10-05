package com.victor.financeapp.file.upload.application.upload.mapper;

import com.victor.financeapp.file.upload.application.upload.dto.UploadChunkDTO;
import com.victor.financeapp.file.upload.core.upload.entity.UploadChunk;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UploadChunkMapper {

    public static UploadChunkDTO uploadChunktoDTO(UploadChunk uploadChunk) {
        if (uploadChunk != null) {
            return UploadChunkDTO.builder()
                    .id(uploadChunk.id())
                    .uploadId(uploadChunk.uploadId())
                    .partNumber(uploadChunk.partNumber())
                    .userId(uploadChunk.userId())
                    .status(uploadChunk.status().name())
                    .build();
        }
        return null;
    }
}
