package com.victor.financeapp.file.upload.core.upload.client;

import com.victor.financeapp.file.upload.core.upload.client.dto.UploadChunkClientDTO;
import com.victor.financeapp.file.upload.core.upload.client.response.UploadResponse;
import com.victor.financeapp.file.upload.core.upload.model.UploadChunk;
import reactor.core.publisher.Mono;

public interface UploadClient {
    Mono<UploadResponse> send(UploadChunkClientDTO chunk);
}
