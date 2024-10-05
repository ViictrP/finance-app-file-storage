package com.victor.financeapp.file.storage.application.upload;

import com.victor.financeapp.file.storage.core.entity.Chunk;
import com.victor.financeapp.file.storage.core.usecase.UploadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadApplication {
    private final UploadUseCase uploadUseCase;

    public Mono<Boolean> uploadFile(String userId, Integer partNumber, Part file) {
        var uploadId = UUID.randomUUID().toString();
        return this.uploadFilePart(userId, uploadId, partNumber, file);
    }

    public Mono<Boolean> uploadFilePart(String userId, String uploadId, Integer partNumber, Part file) {
        return uploadUseCase.execute(Chunk.builder()
                .uploadId(uploadId)
                .partNumber(partNumber)
                .file(file)
                .userId(userId)
                .build());
    }
}
