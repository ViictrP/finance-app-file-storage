package com.victor.financeapp.file.storage.application.upload.mapper;

import com.victor.financeapp.file.storage.application.upload.dto.ChunkDTO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class ChunkDTOMapper implements Mapper<Object, ChunkDTO> {
    @SneakyThrows
    @Override
    public ChunkDTO map(Object payload) throws NoSuchFieldException {
        if (payload == null) {
            return null;
        }

        Field id = payload.getClass().getDeclaredField("id");
        Field uploadIdField = payload.getClass().getDeclaredField("uploadId");
        Field partNumberField = payload.getClass().getDeclaredField("partNumber");
        Field dataField = payload.getClass().getDeclaredField("data");

        return ChunkDTO.builder()
                .id(id.get(payload).toString())
                .uploadId(uploadIdField.get(payload).toString())
                .partNumber(partNumberField.getInt(payload))
                .data((byte[]) dataField.get(payload))
                .build();
    }
}
