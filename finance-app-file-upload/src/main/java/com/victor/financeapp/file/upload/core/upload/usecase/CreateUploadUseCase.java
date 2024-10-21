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
        log.info("Validating payload from user {}", payload.getUserId());
        if (payload.getUserId() == null) {
            throw new MissingRequiredFieldException();
        }

        var upload = Upload.startNewUpload(payload.getUserId(),
                payload.getTotalParts(),
                payload.getFileName(),
                payload.getFileExtension(),
                payload.getFileSize()
        );

        log.info("Creating upload {}", upload.getUploadId());
        return uploadRepository.save(upload);
    }
}
