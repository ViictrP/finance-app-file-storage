package com.victor.financeapp.file.storage.application.upload.mapper;

public interface Mapper<P, R> {
    R map(P payload) throws NoSuchFieldException;
}
