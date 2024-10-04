package com.victor.financeapp.file.storage.application.upload;

import com.victor.financeapp.file.storage.application.upload.dto.ChunkDTO;
import com.victor.financeapp.file.storage.application.upload.mapper.ChunkDTOMapper;
import com.victor.financeapp.file.storage.core.entity.mapper.ChunkMapper;
import com.victor.financeapp.file.storage.core.usecase.UploadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UploadApplication {
    private final UploadUseCase uploadUseCase;
    private final ChunkMapper chunkMapper;
    private final ChunkDTOMapper mapper;

    public ChunkDTO map(Object request) throws NoSuchFieldException {
        return mapper.map(request);
    }

    public Boolean uploadFile(ChunkDTO chunkDTO) throws NoSuchFieldException, IllegalAccessException {
        return uploadUseCase.execute(chunkMapper.map(chunkDTO));
    }
}
