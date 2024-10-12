package com.victor.financeapp.file.upload.core.upload.usecase;

import com.victor.financeapp.file.upload.core.upload.client.UploadClient;
import com.victor.financeapp.file.upload.core.upload.client.dto.UploadChunkClientDTO;
import com.victor.financeapp.file.upload.core.upload.client.response.UploadResponse;
import com.victor.financeapp.file.upload.core.upload.exception.UploadFailedException;
import com.victor.financeapp.file.upload.core.upload.exception.UploadNotFoundException;
import com.victor.financeapp.file.upload.core.upload.model.Upload;
import com.victor.financeapp.file.upload.core.upload.model.UploadChunk;
import com.victor.financeapp.file.upload.core.upload.model.enums.Status;
import com.victor.financeapp.file.upload.core.upload.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUploadChunkUseCase implements UseCase<UploadChunk> {

    private final UploadClient uploadClient;
    private final UploadRepository uploadRepository;

    @Override
    public Mono<UploadChunk> execute(UploadChunk payload) {
        log.info("Validating the upload {} with part {}", payload.uploadId(), payload.partNumber());
        payload.validate();

        log.info("Fetching the upload {} details", payload.uploadId());
        return uploadRepository
                .findByUploadIdAndStatusIn(payload.uploadId(), List.of(Status.CREATED, Status.FAILED))
                .switchIfEmpty(Mono.error(new UploadNotFoundException(payload.uploadId())))
                .flatMap(upload -> {
                    log.info("Saving chunk {} for upload {} with initial status", payload.partNumber(), payload.uploadId());
                    return uploadRepository.saveChunk(
                                    UploadChunk.builder()
                                            .uploadId(payload.uploadId())
                                            .partNumber(payload.partNumber())
                                            .userId(payload.userId())
                                            .status(Status.IN_PROGRESS)
                                            .file(payload.file())
                                            .build()
                            )
                            .flatMap(chunk -> sendTheUploadAndUpdateStatus(chunk, upload));
                });
    }

    private Mono<UploadChunk> sendTheUploadAndUpdateStatus(UploadChunk chunk, Upload upload) {
        log.info("Sending chunk {} for upload {}", chunk.partNumber(), chunk.uploadId());
        return uploadClient.send(new UploadChunkClientDTO(
                        chunk.uploadId(),
                        chunk.userId(),
                        chunk.partNumber(),
                        upload.fileName(),
                        upload.fileExtension(),
                        upload.fileSize(),
                        chunk.file()
                ))
                .flatMap(response ->
                        updateTheUploadStatus(upload, response)
                                .flatMap(u -> updateChunkStatus(chunk, response))
                                .flatMap(c -> response.wasSuccessful() ? Mono.just(c) : Mono.error(new UploadFailedException()))
                )
                .doOnError(throwable -> log.error("Failed to upload chunk {} for upload {}, message: {}", chunk.partNumber(), chunk.uploadId(), throwable.getMessage()));
    }

    //TODO apply DDD
    private Mono<Upload> updateTheUploadStatus(Upload upload, UploadResponse uploadResponse) {
        log.info("Successfully uploaded chunk {} for upload {}", upload.currentPart(), upload.uploadId());

        var builder = Upload.builder()
                .id(upload.id())
                .status(Status.FAILED)
                .currentPart(uploadResponse.getCurrentPart())
                .userId(upload.userId())
                .uploadId(upload.uploadId())
                .fileName(upload.fileName())
                .fileExtension(upload.fileExtension())
                .fileSize(upload.fileSize())
                .totalParts(upload.totalParts());

        if (uploadResponse.wasSuccessful()) {
            builder.filePath(uploadResponse.getFilePath().replace("chunk_" + uploadResponse.getCurrentPart() + ".part", upload.fileName()))
                    .status(upload.totalParts().equals(uploadResponse.getCurrentPart()) ? Status.COMPLETED : Status.IN_PROGRESS);
        }


        log.info("Updating the payload {} progress ", upload.uploadId());
        //TODO send upload completed event after saving.
        return uploadRepository.save(builder.build());
    }

    //TODO apply DDD
    private Mono<UploadChunk> updateChunkStatus(UploadChunk payload, UploadResponse response) {
        log.info("Updating the chunk {} status to {}", payload.partNumber(), response.wasSuccessful() ? "completed" : "failed");
        var chunk = UploadChunk.builder()
                .partNumber(payload.partNumber())
                .chunkPath(response.getFilePath())
                .uploadId(payload.uploadId())
                .id(payload.id())
                .userId(payload.userId())
                .status(response.wasSuccessful() ? Status.COMPLETED : Status.FAILED)
                .build();

        return uploadRepository.saveChunk(chunk);
    }
}
