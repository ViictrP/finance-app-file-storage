package com.victor.financeapp.file.storage.infrastructure.chunk;

import com.victor.financeapp.file.storage.core.entity.Chunk;
import com.victor.financeapp.file.storage.core.repository.ChunkRepository;
import com.victor.financeapp.file.storage.infrastructure.chunk.entity.ChunkEntity;
import com.victor.financeapp.file.storage.infrastructure.chunk.repository.ChunkMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class ChunkRepositoryImpl implements ChunkRepository {

    private final ChunkMongoRepository chunkMongoRepository;

    @Override
    public Mono<Boolean> save(Chunk chunk, Boolean success) {
        var newChunk = ChunkEntity.builder()
                .success(success)
                .createdAt(LocalDateTime.now())
                .uploadId(chunk.uploadId())
                .partNumber(chunk.partNumber())
                .userId(chunk.userId())
                .build();
        return chunkMongoRepository.save(newChunk)
                .then(Mono.just(success));
    }
}
