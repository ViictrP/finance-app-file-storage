package com.victor.financeapp.file.upload.application.upload;

import com.victor.financeapp.file.upload.application.upload.dto.UploadChunkDTO;
import com.victor.financeapp.file.upload.application.upload.dto.UploadDTO;
import com.victor.financeapp.file.upload.application.upload.mapper.UploadChunkMapper;
import com.victor.financeapp.file.upload.application.upload.mapper.UploadMapper;
import com.victor.financeapp.file.upload.core.upload.entity.Upload;
import com.victor.financeapp.file.upload.core.upload.entity.UploadChunk;
import com.victor.financeapp.file.upload.core.upload.usecase.CreateUploadChunk;
import com.victor.financeapp.file.upload.core.upload.usecase.CreateUploadUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadApplication {

    private final CreateUploadUseCase createUploadUseCase;
    private final CreateUploadChunk createUploadChunk;

    public Mono<UploadDTO> createUpload(UploadDTO uploadDTO) {
        log.info("Creating upload for user {}", uploadDTO.userId());
        return createUploadUseCase.execute(Upload.builder()
                        .userId(uploadDTO.userId())
                        .totalParts(uploadDTO.totalParts())
                        .build())
                .switchIfEmpty(Mono.error(new RuntimeException("Upload creation failed")))
                .flatMap(upload -> Mono.just(UploadMapper.uploadtoDTO(upload)));
    }

    public Mono<UploadChunkDTO> createUploadChunk(UploadChunkDTO uploadChunkDTO) {
        log.info("Processing upload chunk for user {}, upload {}, part {}", uploadChunkDTO.userId(), uploadChunkDTO.uploadId(), uploadChunkDTO.partNumber());
        return createUploadChunk.execute(UploadChunk.builder()
                        .uploadId(uploadChunkDTO.uploadId())
                        .partNumber(uploadChunkDTO.partNumber())
                        .userId(uploadChunkDTO.userId())
                        .file(uploadChunkDTO.file())
                        .build())
                .switchIfEmpty(Mono.error(new RuntimeException("Upload chunk creation failed")))
                .flatMap(uploadChunk -> Mono.just(UploadChunkMapper.uploadChunktoDTO(uploadChunk)));
    }
}
