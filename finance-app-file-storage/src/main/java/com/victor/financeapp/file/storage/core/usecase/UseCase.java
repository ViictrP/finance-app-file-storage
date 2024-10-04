package com.victor.financeapp.file.storage.core.usecase;

import reactor.core.publisher.Mono;

public interface UseCase<P, R> {
    Mono<R> execute(P params);
}
