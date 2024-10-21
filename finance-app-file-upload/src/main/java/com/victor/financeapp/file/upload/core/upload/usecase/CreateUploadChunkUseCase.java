package com.victor.financeapp.file.upload.core.upload.usecase;

import com.victor.financeapp.file.upload.core.upload.client.UploadClient;
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
        log.info("Validating the upload {} with part {}", payload.getUploadId(), payload.getPartNumber());
        payload.validate();

        log.info("Fetching the upload {} details", payload.getUploadId());
        return uploadRepository
                .findByUploadIdAndStatusIn(payload.getUploadId(), List.of(Status.CREATED, Status.FAILED)) //TODO fix when it has multiple parts not finding the upload
                .switchIfEmpty(Mono.error(new UploadNotFoundException(payload.getUploadId())))
                .flatMap(upload -> {
                    log.info("Saving chunk {} for upload {} with initial status", payload.getPartNumber(), payload.getUploadId());
                    upload.createNewChunk(payload.getPartNumber(), payload.getFile());
                    return uploadRepository.saveChunk(upload.getCurrentChunk())
                            .flatMap(chunk -> sendTheUploadAndUpdateStatus(upload));
                });
    }

    private Mono<UploadChunk> sendTheUploadAndUpdateStatus(Upload upload) {
        log.info("Sending chunk {} for upload {}", upload.getCurrentChunk().getPartNumber(), upload.getUploadId());
        return uploadClient.send(upload)
                .flatMap(response ->
                        updateTheUploadStatus(upload, response)
                                .flatMap(u -> updateChunkStatus(upload, response))
                                .flatMap(c -> response.wasSuccessful() ? Mono.just(c) : Mono.error(new UploadFailedException()))
                )
                .doOnError(throwable -> log.error("Failed to upload chunk {} for upload {}, message: {}", upload.getCurrentChunk().getPartNumber(), upload.getCurrentChunk().getUploadId(), throwable.getMessage()));
    }

    private Mono<Upload> updateTheUploadStatus(Upload upload, UploadResponse uploadResponse) {
        log.info("Successfully uploaded chunk {} for upload {}", upload.getCurrentPart(), upload.getUploadId());
        upload.failed();

        if (uploadResponse.wasSuccessful()) {
            upload.buildFilePath(uploadResponse.getFilePath());
            upload.succeeded();
        }

        log.info("Updating the payload {} progress ", upload.getUploadId());
        //TODO send upload completed event after saving.
        return uploadRepository.save(upload);
    }

    private Mono<UploadChunk> updateChunkStatus(Upload upload, UploadResponse response) {
        var chunk = upload.getCurrentChunk();
        log.info("Updating the chunk {} status to {}", chunk.getPartNumber(), response.wasSuccessful() ? "completed" : "failed");
        return uploadRepository.saveChunk(chunk);
    }
}
