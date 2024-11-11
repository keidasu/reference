package ru.gpb.tech.pactrestconsumer.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gpb.tech.pactrestconsumer.dto.DocumentDto;
import ru.gpb.tech.pactrestconsumer.service.DocumentService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/documents/{id}")
    public DocumentDto getDocument(@PathVariable Long id) {
        return documentService.getDocument(id);
    }
}
