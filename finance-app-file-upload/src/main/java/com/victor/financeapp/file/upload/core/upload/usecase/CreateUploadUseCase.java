package com.victor.financeapp.file.upload.core.upload.usecase;

import com.victor.financeapp.file.upload.core.upload.model.Upload;
import com.victor.financeapp.file.upload.core.upload.model.enums.Status;
import com.victor.financeapp.file.upload.core.upload.exception.MissingRequiredFieldException;
import com.victor.financeapp.file.upload.core.upload.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUploadUseCase implements UseCase<Upload> {

    private final UploadRepository uploadRepository;

    @Override
    public Mono<Upload> execute(Upload payload) {
        log.info("Validating payload from user {}", payload.userId());
        if (payload.userId() == null) {
            throw new MissingRequiredFieldException();
        }

        var upload = Upload.builder()
                .userId(payload.userId())
                .uploadId(UUID.randomUUID().toString())
                .status(Status.CREATED)
                .totalParts(payload.totalParts())
                .fileName(payload.fileName())
                .fileSize(payload.fileSize())
                .fileExtension(payload.fileExtension())
                .build();

        log.info("Creating upload {}", upload.uploadId());
        return uploadRepository.save(upload);
    }
}
