package com.victor.financeapp.file.upload.core.upload.model;

import com.victor.financeapp.file.upload.core.upload.exception.MissingRequiredFieldException;
import com.victor.financeapp.file.upload.core.upload.model.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.codec.multipart.Part;

@Builder
@Getter
public class UploadChunk {

    @Setter
    private String id;

    @Setter
    private Status status;

    private Integer partNumber;
    private String userId;
    private String uploadId;
    private String chunkPath;
    private Part file;

    public void validate() {
        if (partNumber == null || userId == null || uploadId == null || file == null) {
            throw new MissingRequiredFieldException();
        }
    }

    public static UploadChunk createNewChunk(Integer partNumber, String userId, String uploadId, Part file) {
        return UploadChunk.builder()
                .partNumber(partNumber)
                .userId(userId)
                .uploadId(uploadId)
                .status(Status.IN_PROGRESS)
                .chunkPath(null)
                .file(file)
                .build();
    }

    public static UploadChunk createUploadedChunk(String id, Integer partNumber, String filePath, String uploadId, String userId, boolean sucessfulyUploaded) {
        return UploadChunk.builder()
                .id(id)
                .partNumber(partNumber)
                .chunkPath(filePath)
                .uploadId(uploadId)
                .userId(userId)
                .status(sucessfulyUploaded ? Status.COMPLETED : Status.FAILED)
                .build();
    }
}
