package com.victor.financeapp.file.storage.application.upload;

import com.victor.financeapp.file.storage.application.upload.dto.ChunkDTO;
import com.victor.financeapp.file.storage.application.upload.dto.SavedChunkDTO;
import com.victor.financeapp.file.storage.core.model.Chunk;
import com.victor.financeapp.file.storage.core.usecase.UploadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UploadApplication {
    private final UploadUseCase uploadUseCase;

    public Mono<SavedChunkDTO> uploadFilePart(ChunkDTO chunkDTO) {
        return uploadUseCase.execute(Chunk.builder()
                        .uploadId(chunkDTO.uploadId())
                        .partNumber(chunkDTO.partNumber())
                        .file(chunkDTO.file())
                        .userId(chunkDTO.userId())
                        .fileName(chunkDTO.fileName())
                        .fileSize(chunkDTO.fileSize())
                        .totalFileSize(chunkDTO.totalFileSize())
                        .mimeType(chunkDTO.mimeType())
                        .build())
                .flatMap(chunk -> Mono.just(SavedChunkDTO.builder()
                        .id(chunk.getId())
                        .path(chunk.getPath().toString())
                        .build()));
    }
}
