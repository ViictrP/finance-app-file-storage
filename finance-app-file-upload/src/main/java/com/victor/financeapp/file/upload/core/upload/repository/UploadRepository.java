package com.victor.financeapp.file.upload.core.upload.repository;

import com.victor.financeapp.file.upload.core.upload.entity.Upload;
import com.victor.financeapp.file.upload.core.upload.entity.UploadChunk;
import reactor.core.publisher.Mono;

public interface UploadRepository {
    Mono<Upload> save(Upload upload);
    Mono<UploadChunk> saveChunk(UploadChunk chunk);
    Mono<Upload> findByUploadIdAndStatus(String id, String status);
}
