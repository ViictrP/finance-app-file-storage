package com.victor.financeapp.file.upload.core.upload.exception;

import org.springframework.http.HttpStatus;

public class UploadNotFoundException extends CoreException{
    public UploadNotFoundException(String uploadId) {
        super("The upload " + uploadId + " was not found.", HttpStatus.NOT_FOUND);
    }
}
