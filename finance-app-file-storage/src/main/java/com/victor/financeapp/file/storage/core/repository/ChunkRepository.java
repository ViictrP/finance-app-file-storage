package com.victor.financeapp.file.storage.core.repository;

import com.victor.financeapp.file.storage.core.entity.Chunk;
import reactor.core.publisher.Mono;

public interface ChunkRepository {

    Mono<Boolean> save(Chunk chunk, Boolean success);
}
