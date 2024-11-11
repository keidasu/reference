package ru.gpb.tech.pactkafkaprovider.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gpb.tech.pactkafkaprovider.service.DocumentService;

@RestController
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/createDocument")
    public void createDocument(Long id) {
        documentService.processCreateDocumentRequest(id);
    }
}
