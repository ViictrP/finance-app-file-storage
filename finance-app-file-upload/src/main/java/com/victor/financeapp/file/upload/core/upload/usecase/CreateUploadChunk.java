package com.victor.financeapp.file.upload.core.upload.usecase;

import com.victor.financeapp.file.upload.core.upload.client.UploadClient;
import com.victor.financeapp.file.upload.core.upload.client.response.UploadResponse;
import com.victor.financeapp.file.upload.core.upload.entity.Upload;
import com.victor.financeapp.file.upload.core.upload.entity.UploadChunk;
import com.victor.financeapp.file.upload.core.upload.entity.enums.Status;
import com.victor.financeapp.file.upload.core.upload.exception.UploadFailedException;
import com.victor.financeapp.file.upload.core.upload.exception.UploadNotFoundException;
import com.victor.financeapp.file.upload.core.upload.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUploadChunk implements UseCase<UploadChunk> {

    private final UploadClient uploadClient;
    private final UploadRepository uploadRepository;

    @Override
    public Mono<UploadChunk> execute(UploadChunk payload) {
        log.info("Validating the upload {} with part {}", payload.uploadId(), payload.partNumber());
        payload.validate();

        log.info("Fetching the upload {} details", payload.uploadId());
        return uploadRepository
                .findByUploadIdAndStatus(payload.uploadId(), Status.CREATED.name())
                .switchIfEmpty(Mono.error(new UploadNotFoundException(payload.uploadId())))
                .flatMap(upload -> {
                    log.info("Saving chunk {} for upload {} with initial status", payload.partNumber(), payload.uploadId());
                    return uploadRepository.saveChunk(
                                    UploadChunk.builder()
                                            .uploadId(payload.uploadId())
                                            .partNumber(payload.partNumber())
                                            .userId(payload.userId())
                                            .status(Status.IN_PROGRESS)
                                            .build()
                            )
                            .flatMap(chunk -> sendTheUploadAndUpdateStatus(chunk, upload)
                                    .flatMap(response -> updateChunkStatus(chunk, response.wasSuccessful()))
                            );
                });
    }

    private Mono<UploadResponse> sendTheUploadAndUpdateStatus(UploadChunk chunk, Upload upload) {
        log.info("Sending chunk {} for upload {}", chunk.partNumber(), chunk.uploadId());
        return uploadClient.send(chunk)
                .flatMap(response -> {
                    if (response.wasSuccessful()) {
                        return updateTheUploadStatus(chunk, upload)
                                .then(Mono.just(response));
                    } else {
                        return Mono.error(new UploadFailedException());
                    }
                })
                .doOnError(throwable -> log.error("Failed to upload chunk {} for upload {}, message: {}", chunk.partNumber(), chunk.uploadId(), throwable.getMessage()))
                .onErrorStop();
    }

    private Mono<Upload> updateTheUploadStatus(UploadChunk payload, Upload upload) {
        log.info("Successfully uploaded chunk {} for upload {}", payload.partNumber(), payload.uploadId());

        var updatedUpload = Upload.builder()
                .id(upload.id())
                .userId(upload.userId())
                .uploadId(upload.uploadId())
                .status(upload.totalParts().equals(payload.partNumber()) ? Status.COMPLETED : Status.IN_PROGRESS)
                .totalParts(upload.totalParts())
                .currentPart(payload.partNumber())
                .build();

        log.info("Updating the payload {} progress ", payload.uploadId());
        //TODO send upload completed event after saving.
        return uploadRepository.save(updatedUpload);
    }

    private Mono<UploadChunk> updateChunkStatus(UploadChunk payload, boolean success) {
        log.info("Updating the chunk {} status to {}", payload.partNumber(), success ? "completed" : "failed");
        var chunk = UploadChunk.builder()
                .partNumber(payload.partNumber())
                .uploadId(payload.uploadId())
                .id(payload.id())
                .userId(payload.userId())
                .status(success ? Status.COMPLETED : Status.FAILED)
                .build();

        return uploadRepository.saveChunk(chunk);
    }
}
