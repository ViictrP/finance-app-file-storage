package com.victor.financeapp.file.storage.core.usecase;

public interface UseCase<P, R> {
    R execute(P params);
}
