package com.victor.financeapp.file.upload.application.upload;

import com.victor.financeapp.file.upload.application.upload.dto.UploadDTO;
import com.victor.financeapp.file.upload.application.upload.mapper.UploadMapper;
import com.victor.financeapp.file.upload.core.upload.entity.Upload;
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

    public Mono<UploadDTO> createUpload(UploadDTO uploadDTO) {
        log.info("Creating upload for user {}", uploadDTO.userId());
        return createUploadUseCase.execute(Upload.builder()
                        .userId(uploadDTO.userId())
                        .build())
                .switchIfEmpty(Mono.error(new RuntimeException("Upload creation failed")))
                .flatMap(upload -> Mono.just(UploadMapper.uploadtoDTO(upload)));
    }
}
