package com.victor.financeapp.file.upload.core.upload.client;

import com.victor.financeapp.file.upload.core.upload.client.response.UploadResponse;
import com.victor.financeapp.file.upload.core.upload.model.Upload;
import reactor.core.publisher.Mono;

public interface UploadClient {
    Mono<UploadResponse> send(Upload upload);
}
