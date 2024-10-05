package com.victor.financeapp.file.upload.infra.upload.entity;

import com.victor.financeapp.file.upload.core.upload.entity.Upload;
import com.victor.financeapp.file.upload.core.upload.entity.UploadChunk;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chunks")
@Builder
@Getter
@Setter
public class UploadChunkEntity {

    @Id
    private String id;
    private String status;
    private String userId;
    private String uploadId;
    private Integer partNumber;

    public static UploadChunkEntity fromUpload(UploadChunk chunk) {
        return UploadChunkEntity.builder()
                .id(chunk.id())
                .status(chunk.status().name())
                .userId(chunk.userId())
                .uploadId(chunk.uploadId())
                .partNumber(chunk.partNumber())
                .build();
    }
}
