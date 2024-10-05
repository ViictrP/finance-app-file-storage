package com.victor.financeapp.file.upload.entry.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UploadRequest {
    private String userId;
}
