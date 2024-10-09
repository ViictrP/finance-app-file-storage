package com.victor.financeapp.file.upload.core.upload.model.enums;

public enum Status {
    CREATED,
    IN_PROGRESS,
    COMPLETED,
    FAILED;

    public static Status fromName(String name) {
        return Status.valueOf(name.toUpperCase());
    }
}
