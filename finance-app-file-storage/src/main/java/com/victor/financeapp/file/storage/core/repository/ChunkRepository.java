package com.victor.financeapp.file.storage.core.repository;

import com.victor.financeapp.file.storage.core.model.Chunk;
import reactor.core.publisher.Mono;

public interface ChunkRepository {

    Mono<Chunk> save(Chunk chunk, Boolean success);
}
