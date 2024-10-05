package com.victor.financeapp.file.upload.core.upload.entity.enums;

public enum Status {
    CREATED;

    public static Status fromName(String name) {
        return Status.valueOf(name.toUpperCase());
    }
}
