package com.victor.financeapp.file.upload.infra.upload.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UploadChunkMongoRepository extends ReactiveMongoRepository<UploadChunkEntity, String> {
}
