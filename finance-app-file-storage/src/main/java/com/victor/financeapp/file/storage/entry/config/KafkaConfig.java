package com.victor.financeapp.file.storage.entry.config;

import com.victor.financeapp.file.storage.entry.listener.upload.message.UploadMessage;
import io.github.springwolf.bindings.kafka.annotations.KafkaAsyncOperationBinding;
import io.github.springwolf.core.asyncapi.annotations.AsyncListener;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

@Slf4j
@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    @Bean
    NewTopic uploadTopic(
            @Value("${kafka.topic.upload.partitions:1}") Integer partitions,
            @Value("${kafka.topic.upload.replicas:1}") Integer replicas,
            @Value("${kafka.topic.upload.name:UploadCompleted}") String topicName
    ) {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    @Bean
    @Qualifier("UPLOAD_COMPLETED_RECEIVED_OPTION")
    ReceiverOptions<String, String> uploadCompletedReceivedOptions(KafkaProperties kafkaProperties) {
        return ReceiverOptions.<String, String>create(kafkaProperties.buildConsumerProperties(null))
                .subscription(Collections.singletonList("UploadCompletedReceived"));
    }

    @AsyncListener(operation = @AsyncOperation(
            channelName = "UploadCompletedReceived",
            description = "Upload Completed has been created.",
            payloadType = UploadMessage.class
    ))
    @KafkaAsyncOperationBinding(groupId = "storage")
    @Bean
    @Qualifier("UPLOAD_COMPLETED_RECEIVED")
    ReactiveKafkaConsumerTemplate<String, String> uploadCompletedReceivedConsumerTemplate(
            @Qualifier("UPLOAD_COMPLETED_RECEIVED_OPTION") ReceiverOptions<String, String> receiverOptions
    ) {
        return new ReactiveKafkaConsumerTemplate<>(receiverOptions);
    }
}
