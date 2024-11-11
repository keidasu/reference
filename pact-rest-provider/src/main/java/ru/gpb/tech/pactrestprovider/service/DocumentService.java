package ru.gpb.tech.pactrestprovider.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.gpb.tech.pactrestprovider.dto.DocumentDto;
import ru.gpb.tech.pactrestprovider.mapper.DocumentConverter;
import ru.gpb.tech.pactrestprovider.model.DocumentEntity;
import ru.gpb.tech.pactrestprovider.repository.DocumentRepository;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentConverter documentConverter;

    public DocumentDto getDocument(Long id) {
        DocumentEntity documentEntity = documentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Документ не найден"));
        return documentConverter.toDocumentDto(documentEntity);
    }

}
