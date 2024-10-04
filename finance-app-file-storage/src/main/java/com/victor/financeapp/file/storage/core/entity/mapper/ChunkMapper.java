package com.victor.financeapp.file.storage.core.entity.mapper;

import com.victor.financeapp.file.storage.core.entity.Chunk;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class ChunkMapper implements Mapper {
    @Override
    public Chunk map(Object chunkDTO) throws NoSuchFieldException, IllegalAccessException {
        if (chunkDTO == null) {
            return null;
        }
        Field idField = chunkDTO.getClass().getDeclaredField("id");
        Field uploadIdField = chunkDTO.getClass().getDeclaredField("uploadId");
        Field partNumberField = chunkDTO.getClass().getDeclaredField("partNumber");
        Field dataField = chunkDTO.getClass().getDeclaredField("data");
        return Chunk.builder()
                .id(idField.get(chunkDTO).toString())
                .uploadId(uploadIdField.get(chunkDTO).toString())
                .partNumber(partNumberField.getInt(chunkDTO))
                .data((byte[]) dataField.get(chunkDTO))
                .build();
    }
}
