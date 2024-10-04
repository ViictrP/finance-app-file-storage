package com.victor.financeapp.file.storage.entry.controller;

import com.victor.financeapp.file.storage.application.upload.UploadApplication;
import com.victor.financeapp.file.storage.entry.request.UploadRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/chunks")
@RequiredArgsConstructor
public class UploadController {
    private final UploadApplication uploadApplication;

    @PostMapping
    public Boolean uploadChunk(@RequestBody @Valid UploadRequest request) throws NoSuchFieldException, IllegalAccessException {
        return uploadApplication.uploadFile(uploadApplication.map(request));
    }
}
