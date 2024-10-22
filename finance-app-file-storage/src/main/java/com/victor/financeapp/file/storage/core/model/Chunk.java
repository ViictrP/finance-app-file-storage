package com.victor.financeapp.file.storage.core.model;

import com.victor.financeapp.file.storage.core.exception.WrittingFileException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class Chunk {

    @Setter
    private String id;

    private Path path;
    private String uploadId;
    private Integer partNumber;
    private String userId;
    private String fileName;
    private Long fileSize;
    private Long totalFileSize;
    private String mimeType;
    private Part file;

    public Path createFilePath() throws IOException {
        var directory = Paths.get("./" + this.userId + "/" + this.uploadId);
        Files.createDirectories(directory);
        this.path = directory.resolve("chunk_" + this.partNumber + ".part");
        return this.path;
    }

    public Mono<Boolean> writeBytes() {
        return Mono.using(
                () -> new FileOutputStream(path.toFile()),
                outputStream -> this.file.content()
                        .publishOn(Schedulers.boundedElastic())
                        .doOnNext(dataBuffer -> writeBytes(dataBuffer, outputStream))
                        .doOnComplete(() -> log.info("Completed writing to file: {}", path))
                        .then(Mono.just(true)),
                outputStream -> {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        log.error("Error closing file output stream", e);
                    }
                }
        );
    }

    private static void writeBytes(DataBuffer dataBuffer, FileOutputStream outputStream) {
        try {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            throw new WrittingFileException();
        }
    }
}
