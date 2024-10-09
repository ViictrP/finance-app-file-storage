package com.victor.financeapp.file.upload.infra.upload.client;

import com.victor.financeapp.file.upload.core.upload.client.UploadClient;
import com.victor.financeapp.file.upload.core.upload.client.dto.UploadChunkClientDTO;
import com.victor.financeapp.file.upload.core.upload.client.response.UploadResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
class UploadClientImpl implements UploadClient {
    @Override
    public Mono<UploadResponse> send(UploadChunkClientDTO chunk) {
        var response = new UploadResponse();
        response.setUploadId("TEST");
        response.setFileId("TEST");
        response.setFilePath("/user/1/file_chunk_1.part");
        response.setSuccess(true);
        response.setCurrentPart(1);
        return Mono.just(response);
    }
}
