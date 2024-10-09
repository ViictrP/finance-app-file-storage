package com.victor.financeapp.file.upload.core.upload.repository;

import com.victor.financeapp.file.upload.core.upload.model.Upload;
import com.victor.financeapp.file.upload.core.upload.model.UploadChunk;
import reactor.core.publisher.Mono;

public interface UploadRepository {
    Mono<Upload> save(Upload upload);
    Mono<UploadChunk> saveChunk(UploadChunk chunk);
    Mono<Upload> findByUploadIdAndStatus(String id, String status);
}
