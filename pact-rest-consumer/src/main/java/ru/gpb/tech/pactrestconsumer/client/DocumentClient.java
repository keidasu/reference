package ru.gpb.tech.pactrestconsumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.gpb.tech.pactrestconsumer.dto.DocumentDto;

@FeignClient(name = "documentClient", url = "${gpb.service-location.pact-rest-provider.url}")
public interface DocumentClient {

    @GetMapping("/documents/{id}")
    DocumentDto getDocumentById(@PathVariable("id") Long id);
}
