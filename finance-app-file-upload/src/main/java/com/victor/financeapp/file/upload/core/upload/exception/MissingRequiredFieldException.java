package com.victor.financeapp.file.upload.core.upload.exception;

import org.springframework.http.HttpStatus;

public class MissingRequiredFieldException extends CoreException {

    public MissingRequiredFieldException() {
        super("The upload is missing some required fields", HttpStatus.BAD_REQUEST);
    }
}
