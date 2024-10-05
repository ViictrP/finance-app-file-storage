package com.victor.financeapp.file.upload.core.upload.usecase;

import com.victor.financeapp.file.upload.core.upload.entity.Upload;
import com.victor.financeapp.file.upload.core.upload.entity.enums.Status;
import com.victor.financeapp.file.upload.core.upload.exception.MissingRequiredFieldException;
import com.victor.financeapp.file.upload.core.upload.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUploadUseCase implements UseCase<Upload, Upload> {

    private final UploadRepository uploadRepository;

    @Override
    public Mono<Upload> execute(Upload payload) {
        log.info("Validating payload {}", payload);
        if (payload.userId() == null) {
            throw new MissingRequiredFieldException();
        }
        log.info("Creating upload for user {}", payload.userId());
        var upload = Upload.builder()
                .userId(payload.userId())
                .status(Status.CREATED)
                .build();
        return uploadRepository.create(upload);
    }
}
