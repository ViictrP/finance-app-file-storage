package com.victor.financeapp.file.storage.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MimeType {
    PDF("application/pdf"),
    JPEG("image/jpeg"),
    PNG("image/png");

    private final String value;
}
