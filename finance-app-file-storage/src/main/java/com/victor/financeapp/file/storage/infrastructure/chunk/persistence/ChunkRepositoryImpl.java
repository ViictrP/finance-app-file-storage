package com.victor.financeapp.file.storage.infrastructure.chunk.persistence;

import com.victor.financeapp.file.storage.core.model.Chunk;
import com.victor.financeapp.file.storage.core.repository.ChunkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
class ChunkRepositoryImpl implements ChunkRepository {

    private final ChunkMongoRepository chunkMongoRepository;

    @Override
    public Mono<Chunk> save(Chunk chunk, Boolean success) {
        var newChunk = ChunkEntity.builder()
                .success(success)
                .createdAt(LocalDateTime.now())
                .uploadId(chunk.getUploadId())
                .partNumber(chunk.getPartNumber())
                .userId(chunk.getUserId())
                .fileName(chunk.getFileName())
                .fileSize(chunk.getFileSize())
                .mimeType(chunk.getMimeType())
                .totalFileSize(chunk.getTotalFileSize())
                .build();
        return chunkMongoRepository.save(newChunk)
                .flatMap(chunkEntity -> Mono.just(Chunk.builder()
                        .id(chunkEntity.getId())
                        .fileSize(chunkEntity.getFileSize())
                        .totalFileSize(chunkEntity.getTotalFileSize())
                        .path(Path.of(chunkEntity.getPath()))
                        .partNumber(chunkEntity.getPartNumber())
                        .userId(chunkEntity.getUserId())
                        .uploadId(chunkEntity.getUploadId())
                        .fileName(chunkEntity.getFileName())
                        .mimeType(chunkEntity.getMimeType())
                        .build()));
    }
}
