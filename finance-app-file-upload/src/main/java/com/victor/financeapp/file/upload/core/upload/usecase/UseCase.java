package com.victor.financeapp.file.upload.core.upload.usecase;

import reactor.core.publisher.Mono;

public interface UseCase<P,R> {

    Mono<R> execute(P payload);
}
