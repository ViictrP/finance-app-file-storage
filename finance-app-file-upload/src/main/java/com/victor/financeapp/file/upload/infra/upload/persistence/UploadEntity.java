package com.victor.financeapp.file.upload.infra.upload.persistence;

import com.victor.financeapp.file.upload.core.upload.model.Upload;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "uploads")
@Builder
@Getter
@Setter
class UploadEntity {

    @Id
    private String id;
    private String status;
    private String userId;
    private String uploadId;
    private String fileName;
    private String fileExtension;
    private Long fileSize;
    private Integer totalParts;
    private Integer currentPart;
    private String filePath;

    public static UploadEntity fromUpload(Upload upload) {
        return UploadEntity.builder()
                .id(upload.id())
                .status(upload.status().name())
                .userId(upload.userId())
                .uploadId(upload.uploadId())
                .fileName(upload.fileName())
                .fileExtension(upload.fileExtension())
                .fileSize(upload.fileSize())
                .totalParts(upload.totalParts())
                .currentPart(upload.currentPart())
                .filePath(upload.filePath())
                .build();
    }
}
