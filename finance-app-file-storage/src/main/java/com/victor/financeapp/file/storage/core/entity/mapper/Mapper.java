package com.victor.financeapp.file.storage.core.entity.mapper;

import com.victor.financeapp.file.storage.core.entity.Chunk;

public interface Mapper {
    Chunk map(Object chunkDTO) throws NoSuchFieldException, IllegalAccessException;
}
