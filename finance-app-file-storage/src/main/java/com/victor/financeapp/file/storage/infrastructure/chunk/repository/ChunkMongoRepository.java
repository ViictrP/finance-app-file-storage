package com.victor.financeapp.file.storage.infrastructure.chunk.repository;

import com.victor.financeapp.file.storage.infrastructure.chunk.entity.ChunkEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChunkMongoRepository extends ReactiveMongoRepository<ChunkEntity, String> {
}
