package ru.gpb.tech.pactrestprovider.mapper;

import org.mapstruct.Mapper;
import ru.gpb.tech.pactrestprovider.dto.DocumentDto;
import ru.gpb.tech.pactrestprovider.model.DocumentEntity;

@Mapper(componentModel = "spring")
public interface DocumentConverter {

    DocumentEntity toDocumentEntity(DocumentDto documentDto);

    DocumentDto toDocumentDto(DocumentEntity documentEntity);
}
