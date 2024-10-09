package com.victor.financeapp.file.upload.core.upload.client.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UploadResponse {
    private String uploadId;
    private String fileId;
    private String filePath;
    private Boolean success;
    private Integer currentPart;

    public boolean wasSuccessful() {
        return success != null && success;
    }
}
