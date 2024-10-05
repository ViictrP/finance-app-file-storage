package com.victor.financeapp.file.upload.core.upload.repository;

import com.victor.financeapp.file.upload.core.upload.entity.Upload;
import reactor.core.publisher.Mono;

public interface UploadRepository {
    Mono<Upload> create(Upload upload);
}
