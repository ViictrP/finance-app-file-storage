package com.victor.financeapp.file.upload.infra.upload.repository;

import com.victor.financeapp.file.upload.core.upload.model.Upload;
import com.victor.financeapp.file.upload.core.upload.model.UploadChunk;
import com.victor.financeapp.file.upload.core.upload.model.enums.Status;
import com.victor.financeapp.file.upload.core.upload.repository.UploadRepository;
import com.victor.financeapp.file.upload.infra.upload.entity.UploadChunkEntity;
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
    private final UploadChunkMongoRepository chunkRepository;

    @Override
    public Mono<Upload> save(Upload upload) {
        return repository.save(UploadEntity.fromUpload(upload))
                .flatMap(entity -> Mono.just(Upload.builder()
                        .id(entity.getId())
                        .userId(entity.getUserId())
                        .uploadId(entity.getUploadId())
                        .fileName(entity.getFileName())
                        .fileExtension(entity.getFileExtension())
                        .fileSize(entity.getFileSize())
                        .filePath(entity.getFilePath())
                        .status(Status.fromName(entity.getStatus()))
                        .totalParts(entity.getTotalParts())
                        .build()));
    }

    @Override
    public Mono<UploadChunk> saveChunk(UploadChunk chunk) {
        return chunkRepository.save(UploadChunkEntity.fromUpload(chunk))
                .flatMap(entity -> Mono.just(UploadChunk.builder()
                        .status(Status.fromName(entity.getStatus()))
                        .partNumber(entity.getPartNumber())
                        .userId(entity.getUserId())
                        .uploadId(entity.getUploadId())
                        .chunkPath(chunk.chunkPath())
                        .id(entity.getId())
                        .build())
                )
                .doOnSuccess(c -> log.info("Upload chunk created: {}", c.id()));
    }

    @Override
    public Mono<Upload> findByUploadIdAndStatus(String id, String status) {
        return repository.findByUploadIdAndStatus(id, status)
                .flatMap(entity -> Mono.just(Upload.builder()
                        .id(entity.getId())
                        .userId(entity.getUserId())
                        .uploadId(entity.getUploadId())
                        .status(Status.fromName(entity.getStatus()))
                        .filePath(entity.getFilePath())
                        .fileName(entity.getFileName())
                        .fileExtension(entity.getFileExtension())
                        .fileSize(entity.getFileSize())
                        .totalParts(entity.getTotalParts())
                        .currentPart(entity.getCurrentPart())
                        .build()));
    }
}
