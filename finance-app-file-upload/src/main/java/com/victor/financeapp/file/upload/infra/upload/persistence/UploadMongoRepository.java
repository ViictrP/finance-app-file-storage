package com.victor.financeapp.file.upload.infra.upload.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
interface UploadMongoRepository extends ReactiveMongoRepository<UploadEntity, String> {

    Mono<UploadEntity> findByUploadIdAndStatus(String uploadId, String status);
}
