package com.victor.financeapp.file.storage.infrastructure.chunk.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ChunkMongoRepository extends ReactiveMongoRepository<ChunkEntity, String> {
}
