package ru.gpb.tech.mqrestadapter.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.gpb.tech.mqrestadapter.model.ClientRequest;
import ru.gpb.tech.mqrestadapter.service.AdapterService;

@Slf4j
@Service
@RequiredArgsConstructor
@KafkaListener(topics = "kafka-service-request")
public class GroupListener {

    private final ObjectMapper objectMapper;

    private final AdapterService adapterService;

    @KafkaHandler
    public void processMessage(ClientRequest request) throws JsonProcessingException {
        log.info("Получено сообщение клиента для предоставления актуальной информации. Запрос {} ", objectMapper.writeValueAsString(request));
        adapterService.processRequest(request);
    }

}
