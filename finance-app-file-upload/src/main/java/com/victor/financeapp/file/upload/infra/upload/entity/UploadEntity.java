package com.victor.financeapp.file.upload.infra.upload.entity;

import com.victor.financeapp.file.upload.core.upload.entity.Upload;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "uploads")
@Builder
@Getter
@Setter
public class UploadEntity {

    @Id
    private String id;
    private String status;
    private String userId;
    private String uploadId;
    private Integer totalParts;
    private Integer currentPart;

    public static UploadEntity fromUpload(Upload upload) {
        return UploadEntity.builder()
                .id(upload.id())
                .status(upload.status().name())
                .userId(upload.userId())
                .uploadId(upload.uploadId())
                .totalParts(upload.totalParts())
                .currentPart(upload.currentPart())
                .build();
    }
}
