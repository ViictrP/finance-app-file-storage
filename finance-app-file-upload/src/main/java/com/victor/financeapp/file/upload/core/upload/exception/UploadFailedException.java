package com.victor.financeapp.file.upload.core.upload.exception;

import org.springframework.http.HttpStatus;

public class UploadFailedException extends CoreException {
    public UploadFailedException() {
        super("The upload failed for unknowm reasons, contact the support team.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
