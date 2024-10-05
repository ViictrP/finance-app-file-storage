package com.victor.financeapp.file.upload.infra.upload.repository;

import com.victor.financeapp.file.upload.infra.upload.entity.UploadEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadMongoRepository extends ReactiveMongoRepository<UploadEntity, String> {
}
