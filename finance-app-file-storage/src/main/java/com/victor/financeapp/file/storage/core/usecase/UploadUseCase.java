package com.victor.financeapp.file.storage.core.usecase;

import com.victor.financeapp.file.storage.core.entity.Chunk;
import com.victor.financeapp.file.storage.core.exception.WrittingFileException;
import com.victor.financeapp.file.storage.core.repository.ChunkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadUseCase implements UseCase<Chunk, Boolean> {

    private final ChunkRepository chunkRepository;

    //TODO salvar tamanho dos chunks, tamanho total do arquivo, mime type, caminho do arquivo, etc.
    //TODO retornar o caminho do arquivo, file name e demais dados que foram salvos no banco de dados.
    @Override
    public Mono<Boolean> execute(Chunk chunk) {
        return Mono.fromCallable(() -> {
                    var directory = Paths.get("./" + chunk.userId() + "/" + chunk.uploadId());
                    Files.createDirectories(directory);
                    return directory.resolve("chunk_" + chunk.partNumber() + ".part");
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(path -> writeToFile(chunk, path))
                .flatMap(success -> chunkRepository.save(chunk, success))
                .doOnSuccess(success -> log.info("Completed processing chunk: {}", chunk.partNumber()))
                .doOnError(e -> log.error("Error processing chunk: {}", chunk.partNumber(), e));
    }

    private static Mono<Boolean> writeToFile(Chunk chunk, Path path) {
        return Mono.using(
                () -> new FileOutputStream(path.toFile()),
                outputStream -> chunk.file().content()
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
