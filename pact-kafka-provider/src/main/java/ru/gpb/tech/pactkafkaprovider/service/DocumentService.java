package ru.gpb.tech.pactkafkaprovider.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.gpb.tech.pactkafkaprovider.dto.CreateDocumentRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final KafkaTemplate<String, CreateDocumentRequest> kafkaTemplate;

    public void processCreateDocumentRequest(Long id) {
        kafkaTemplate.send("document_group", new EasyRandom().nextObject(CreateDocumentRequest.class).setId(id));
    }
}
