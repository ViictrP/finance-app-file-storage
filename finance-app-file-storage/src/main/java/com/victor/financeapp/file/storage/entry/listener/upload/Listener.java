package com.victor.financeapp.file.storage.entry.listener.upload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.financeapp.file.storage.entry.listener.upload.message.UploadMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.util.retry.Retry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Slf4j
@Service
public abstract class Listener<T> implements CommandLineRunner {
    private final ReactiveKafkaConsumerTemplate<String, String> consumer;
    private final ObjectMapper objectMapper;
    private final Scheduler scheduler = Schedulers.newBoundedElastic(
            16,
            128,
            "schedulers"
    );

    protected Listener(ReactiveKafkaConsumerTemplate<String, String> consumer, ObjectMapper objectMapper) {
        this.consumer = consumer;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        this.consume();
    }

    public void consume() {
        consumer
                .receive()
                .doOnError(throwable -> log.error("Error receiving event {}", throwable.getMessage()))
                .retryWhen(Retry.max(3).transientErrors(true))
                .repeat()
                .doOnNext(this::logReceivedMessage)
                .concatMap(receiverRecord -> handleMessage(receiverRecord)
                        .doOnError(throwable -> logError(receiverRecord, throwable))
                        .onErrorResume(throwable -> Mono.empty()))
                .doOnNext(record -> {
                    if (record.receiverOffset() != null) {
                        record.receiverOffset().acknowledge();
                    }
                })
                .doOnNext(this::logProcessedMessage)
                .publishOn(scheduler)
                .subscribe();
    }

    private void logReceivedMessage(ReceiverRecord<String, String> event) {
        log.info(
                "event received from topic={}, key={}, value={}, offset={}",
                event.topic(),
                event.key(),
                event.value(),
                event.offset()
        );
    }

    private void logProcessedMessage(ReceiverRecord<String, String> event) {
        log.info(
                "event processed from topic={}, key={}, value={}, offset={}",
                event.topic(),
                event.key(),
                event.value(),
                event.offset()
        );
    }

    private static void logError(ReceiverRecord<String, String> receiverRecord, Throwable throwable) {
        log.error(
                "Error while while processing event from topic={} key={}, value={}, offset={}, error={}",
                receiverRecord.topic(),
                receiverRecord.key(),
                receiverRecord.value(),
                receiverRecord.offset(),
                throwable.getMessage());
    }

    private Mono<ReceiverRecord<String, String>> handleMessage(ReceiverRecord<String, String> event) {
        try {

            ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
            Type type = genericSuperclass.getActualTypeArguments()[0];

            T message = null;
            Class<T> clazz = null;
            if (type instanceof Class) {
                clazz = (Class<T>) type;
                message = objectMapper.readValue(event.value(), clazz);
            } else if (type instanceof ParameterizedType) {
                var rawClass = (Class<T>) ((ParameterizedType) type).getRawType();
                var classType = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];

                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(rawClass, classType);
                message = objectMapper.readValue(event.value(), javaType);
            }

            return processMessage(message).then(Mono.just(event));

        } catch (JsonProcessingException e) {
            log.error(String.format("Problem deserializing an instance of [%s] with the following json: %s ",
                    UploadMessage.class.getSimpleName(),
                    event.value()), e);
            return Mono.error(new RuntimeException(e));
        }
    }

    abstract Mono<?> processMessage(T message);
}