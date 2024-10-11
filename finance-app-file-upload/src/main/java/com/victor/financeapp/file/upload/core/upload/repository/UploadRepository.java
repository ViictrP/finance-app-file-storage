package com.victor.financeapp.file.upload.core.upload.repository;

import com.victor.financeapp.file.upload.core.upload.model.Upload;
import com.victor.financeapp.file.upload.core.upload.model.UploadChunk;
import com.victor.financeapp.file.upload.core.upload.model.enums.Status;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UploadRepository {
    Mono<Upload> save(Upload upload);
    Mono<UploadChunk> saveChunk(UploadChunk chunk);
    Mono<Upload> findByUploadIdAndStatusIn(String id, List<Status> status);
}
