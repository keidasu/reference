package ru.gpb.tech.pactrestprovider.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gpb.tech.pactrestprovider.dto.DocumentDto;
import ru.gpb.tech.pactrestprovider.service.DocumentService;

@RestController
@RequestMapping("/pact-rest-provider/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/{id}")
    public DocumentDto getDocument(@PathVariable Long id) {
        return documentService.getDocument(id);
    }
}
