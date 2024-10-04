package com.victor.financeapp.file.storage.core.exception;

import org.springframework.http.HttpStatus;

public class CoreException extends RuntimeException  {

    public CoreException(String message, HttpStatus status) {
        super(message + status.getReasonPhrase());
    }
}
