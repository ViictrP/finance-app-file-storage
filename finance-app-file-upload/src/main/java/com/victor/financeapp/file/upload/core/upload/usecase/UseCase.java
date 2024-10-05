package com.victor.financeapp.file.upload.core.upload.usecase;

import reactor.core.publisher.Mono;

public interface UseCase<T> {

    Mono<T> execute(T payload);
}
