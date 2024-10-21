package com.victor.financeapp.file.upload.infra.upload.persistence;

import com.victor.financeapp.file.upload.core.upload.model.Upload;
import com.victor.financeapp.file.upload.core.upload.model.UploadChunk;
import com.victor.financeapp.file.upload.core.upload.model.enums.Status;
import com.victor.financeapp.file.upload.core.upload.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
class UploadRepositoryImpl implements UploadRepository {

    private final UploadMongoRepository repository;
    private final UploadChunkMongoRepository chunkRepository;
    private final ToUploadMapper mapper;

    @Override
    public Mono<Upload> save(Upload upload) {
        return repository.save(UploadEntity.fromUpload(upload))
                .flatMap(entity -> {
                    upload.setId(entity.getId());
                    return Mono.just(upload);
                });
    }

    @Override
    public Mono<UploadChunk> saveChunk(UploadChunk chunk) {
        return chunkRepository.save(UploadChunkEntity.fromUpload(chunk))
                .flatMap(entity -> {
                    chunk.setId(entity.getId());
                    return Mono.just(chunk);
                })
                .doOnSuccess(c -> log.info("Upload chunk created: {}", c.getId()));
    }

    @Override
    public Mono<Upload> findByUploadIdAndStatusIn(String id, List<Status> statuses) {
        return repository.findByUploadIdAndStatusIn(id, statuses.stream().map(Status::name).toList())
                .flatMap(entity -> Mono.just(mapper.toModel(entity)));
    }
}
