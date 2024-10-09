package com.victor.financeapp.file.storage.infrastructure.chunk.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "files")
@Getter
@Setter
@Builder
public class ChunkEntity {
    @Id
    private String id;
    private String uploadId;
    private Integer partNumber;
    private Boolean success;
    private String userId;
    private String fileName;
    private String path;
    private String mimeType;
    private Long fileSize;
    private Long totalFileSize;
    private LocalDateTime createdAt;
}
