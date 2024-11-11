package ru.gpb.tech.pactrestconsumer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gpb.tech.pactrestconsumer.client.DocumentClient;
import ru.gpb.tech.pactrestconsumer.dto.DocumentDto;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentClient documentClient;

    public DocumentDto getDocument(Long id) {
        return documentClient.getDocumentById(id);
    }
}
