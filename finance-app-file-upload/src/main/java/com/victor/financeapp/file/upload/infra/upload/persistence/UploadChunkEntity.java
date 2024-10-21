package com.victor.financeapp.file.upload.infra.upload.persistence;

import com.victor.financeapp.file.upload.core.upload.model.UploadChunk;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chunks")
@Builder
@Getter
@Setter
class UploadChunkEntity {

    @Id
    private String id;
    private String status;
    private String userId;
    private String uploadId;
    private Integer partNumber;
    private String chunkPath;

    public static UploadChunkEntity fromUpload(UploadChunk chunk) {
        return UploadChunkEntity.builder()
                .id(chunk.getId())
                .status(chunk.getStatus().name())
                .userId(chunk.getUserId())
                .uploadId(chunk.getUploadId())
                .partNumber(chunk.getPartNumber())
                .chunkPath(chunk.getChunkPath())
                .build();
    }
}
