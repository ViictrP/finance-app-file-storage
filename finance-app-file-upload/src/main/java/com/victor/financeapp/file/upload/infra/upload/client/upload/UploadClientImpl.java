package com.victor.financeapp.file.upload.infra.upload.client.upload;

import com.victor.financeapp.file.upload.core.upload.client.UploadClient;
import com.victor.financeapp.file.upload.core.upload.client.response.UploadResponse;
import com.victor.financeapp.file.upload.core.upload.entity.UploadChunk;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UploadClientImpl implements UploadClient {
    @Override
    public Mono<UploadResponse> send(UploadChunk chunk) {
        var response = new UploadResponse();
        response.setUploadId("TEST");
        response.setSuccess(true);
        return Mono.just(response);
    }
}
