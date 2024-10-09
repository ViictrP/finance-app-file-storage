package com.victor.financeapp.file.upload.entry.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestPart;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadRequest {

    @NotBlank
    private String userId;

    @NotNull
    private Integer totalParts;

    @NotBlank
    private String fileName;

    @NotBlank
    private String fileExtension;

    @NotBlank
    private String fileSize;
}
