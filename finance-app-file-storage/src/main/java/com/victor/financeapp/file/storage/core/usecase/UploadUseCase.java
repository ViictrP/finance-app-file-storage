package com.victor.financeapp.file.storage.core.usecase;

import com.victor.financeapp.file.storage.core.entity.Chunk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UploadUseCase implements UseCase<Chunk, Boolean> {

    @Override
    public Boolean execute(Chunk chunk) {
        try {
            log.info("Uploading chunk: {}", chunk);
            var data = chunk.data();
            log.debug("Data {}", data);
            return true;
        } catch (Exception e) {
            log.error("Error uploading chunk: {}", chunk, e);
            return false;
        }
    }
}
