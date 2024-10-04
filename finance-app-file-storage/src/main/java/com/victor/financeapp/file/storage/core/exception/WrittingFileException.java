package com.victor.financeapp.file.storage.core.exception;

import org.springframework.http.HttpStatus;

public class WrittingFileException extends CoreException  {
    public WrittingFileException() {
        super("Error while writting the file", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
