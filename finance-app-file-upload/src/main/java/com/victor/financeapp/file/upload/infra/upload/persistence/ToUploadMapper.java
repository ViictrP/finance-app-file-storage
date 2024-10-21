package com.victor.financeapp.file.upload.infra.upload.persistence;

import com.victor.financeapp.file.upload.core.upload.model.Upload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface ToUploadMapper {

    Upload toModel(UploadEntity entity);
}
