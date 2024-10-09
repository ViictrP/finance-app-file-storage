package com.victor.financeapp.file.storage.core.usecase;

import com.victor.financeapp.file.storage.core.model.Chunk;
import com.victor.financeapp.file.storage.core.repository.ChunkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadUseCase implements UseCase<Chunk, Chunk> {

    private final ChunkRepository chunkRepository;

    @Override
    public Mono<Chunk> execute(Chunk chunk) {
        return Mono.fromCallable(chunk::createFilePath)
                .subscribeOn(Schedulers.boundedElastic())
                .then(chunk.writeBytes())
                .flatMap(success -> chunkRepository.save(chunk, success))
                .doOnSuccess(success -> log.info("Completed processing chunk: {}", chunk.getPartNumber()))
                .doOnError(e -> log.error("Error processing chunk: {}", chunk.getPartNumber(), e));
    }
}
