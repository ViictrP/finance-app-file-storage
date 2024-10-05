package com.victor.financeapp.file.upload.infra.upload.repository;

import com.victor.financeapp.file.upload.core.upload.entity.Upload;
import com.victor.financeapp.file.upload.core.upload.entity.enums.Status;
import com.victor.financeapp.file.upload.core.upload.repository.UploadRepository;
import com.victor.financeapp.file.upload.infra.upload.entity.UploadEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UploadRepositoryImpl implements UploadRepository {

    private final UploadMongoRepository repository;

    @Override
    public Mono<Upload> create(Upload upload) {
        log.info("Persisting upload {} for user {}", upload.uploadId(), upload.userId());
        return repository.save(UploadEntity.fromUpload(upload))
                .map(entity -> Upload.builder()
                        .id(entity.getId())
                        .userId(entity.getUserId())
                        .uploadId(entity.getUploadId())
                        .status(Status.fromName(entity.getStatus()))
                        .build())
                .doOnSuccess(entity -> log.info("Upload created: {}", entity.id()));
    }
}
