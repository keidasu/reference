package ru.gpb.tech.pactkafkaconsumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gpb.tech.pactkafkaconsumer.dto.CreateDocumentRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    public void createDocument(CreateDocumentRequest createDocumentRequest) {
        log.info("Создание документа по запросу {}", createDocumentRequest);
    }
}
