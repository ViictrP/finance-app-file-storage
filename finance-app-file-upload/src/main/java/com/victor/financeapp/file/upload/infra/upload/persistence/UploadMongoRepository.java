package com.victor.financeapp.file.upload.infra.upload.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
interface UploadMongoRepository extends ReactiveMongoRepository<UploadEntity, String> {

    Mono<UploadEntity> findByUploadIdAndStatusIn(String uploadId, List<String> statuses);
}
