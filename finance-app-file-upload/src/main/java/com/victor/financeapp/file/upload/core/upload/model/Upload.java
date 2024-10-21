package com.victor.financeapp.file.upload.core.upload.model;

import com.victor.financeapp.file.upload.core.upload.model.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.codec.multipart.Part;

import java.util.UUID;

@Builder
@Getter
public class Upload {

    @Setter
    private String id;

    private Status status;
    private String userId;
    private String uploadId;
    private String fileName;
    private String fileExtension;
    private Long fileSize;
    private String filePath;
    private Integer totalParts;
    private Integer currentPart;

    private UploadChunk currentChunk;

    public void failed() {
        this.status = Status.FAILED;
    }

    public void succeeded() {
        this.status = Status.COMPLETED;
        this.currentChunk.setStatus(Status.COMPLETED);
    }

    public void buildFilePath(String filePath) {
        this.filePath = filePath.replace("chunk_" + this.currentPart + ".part", this.fileName);
    }

    public void createNewChunk(Integer partNumber, Part file) {
        this.currentChunk = UploadChunk.createNewChunk(partNumber, userId, uploadId, file);
    }

    public static Upload startNewUpload(String userId, Integer totalParts, String fileName, String fileExtension, Long fileSize) {
        return Upload.builder()
                .userId(userId)
                .uploadId(UUID.randomUUID().toString())
                .status(Status.CREATED)
                .totalParts(totalParts)
                .fileName(fileName)
                .fileSize(fileSize)
                .fileExtension(fileExtension)
                .build();
    }
}
