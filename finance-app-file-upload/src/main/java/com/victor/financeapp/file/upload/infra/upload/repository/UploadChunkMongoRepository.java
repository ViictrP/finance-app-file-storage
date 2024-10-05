package com.victor.financeapp.file.upload.infra.upload.repository;

import com.victor.financeapp.file.upload.infra.upload.entity.UploadChunkEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadChunkMongoRepository extends ReactiveMongoRepository<UploadChunkEntity, String> {
}
