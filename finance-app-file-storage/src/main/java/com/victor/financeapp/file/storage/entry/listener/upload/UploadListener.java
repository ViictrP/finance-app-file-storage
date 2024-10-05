package com.victor.financeapp.file.storage.entry.listener.upload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.financeapp.file.storage.entry.listener.upload.message.UploadMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UploadListener extends Listener<UploadMessage> {

    protected UploadListener(
            @Qualifier("UPLOAD_COMPLETED_RECEIVED") ReactiveKafkaConsumerTemplate<String, String> consumer,
            ObjectMapper objectMapper) {
        super(consumer, objectMapper);
    }

    @Override
    Mono<?> processMessage(UploadMessage message) {
        System.out.println(message);
        return Mono.empty();
    }
}
