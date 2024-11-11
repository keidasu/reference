package ru.gpb.tech.pactkafkaconsumer.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.gpb.tech.pactkafkaconsumer.dto.CreateDocumentRequest;
import ru.gpb.tech.pactkafkaconsumer.service.DocumentService;


@Service
@RequiredArgsConstructor
@KafkaListener(topics = "document_group")
public class GroupListener {

    private final DocumentService documentService;

    @KafkaHandler
    public void handleCreateDocument(CreateDocumentRequest request) {
        documentService.createDocument(request);
    }
}
