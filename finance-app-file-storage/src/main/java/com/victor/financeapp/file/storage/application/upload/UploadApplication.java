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

    public Mono<Boolean> uploadFile(Integer partNumber, Part file) {
        var uploadId = UUID.randomUUID().toString(); // Generate a unique upload ID for each file upload request
        return uploadUseCase.execute(Chunk.builder()
                        .uploadId(uploadId)
                        .partNumber(partNumber)
                        .file(file)
                        .build());
    }
}
