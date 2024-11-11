package ru.gpb.tech.pactrestprovider.provider;

import au.com.dius.pact.core.model.Interaction;
import au.com.dius.pact.core.model.SynchronousRequestResponse;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.gpb.tech.pactrestprovider.dto.DocumentDto;
import ru.gpb.tech.pactrestprovider.mapper.DocumentConverter;
import ru.gpb.tech.pactrestprovider.repository.DocumentRepository;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@PactBroker// связывание с Pact Broker для верификации контрактов
//@PactFolder("pacts") //для локальной верификации без Pact Broker
@Provider("pact-rest-provider") //имя поставщика
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentProviderPactTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DocumentConverter documentConverter;

    @MockBean
    private DocumentRepository documentRepository;

    @LocalServerPort
    private int port;

    private String responseText;

    @BeforeEach
    void setUp(Interaction interaction, PactVerificationContext context) {
        assumeThat(context).isNotNull();
        context.setTarget(new HttpTestTarget("localhost", port));
        if (interaction instanceof SynchronousRequestResponse) {
            responseText = Objects.requireNonNull(interaction.asSynchronousRequestResponse()).getResponse()
                    .getBody().valueAsString();
        }
    }

    /**
     * Запуск верификации контрактов
     */
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    /**
     * Состояние провайдера. В данном случае задаем, что в БД поставщика существуют документы по запрашиваемым id.
     */
    @State("[GET] /pact-rest-provider/api/documents/{id} OK")
    void toDocumentsExistState() throws JsonProcessingException {
        var responseBody = documentConverter.toDocumentEntity(objectMapper.readValue(responseText, DocumentDto.class));
        when(documentRepository.findById(any())).thenReturn(Optional.of(responseBody));
    }

    /**
     * Состояние провайдера. В данном случае задаем, что в БД поставщика не существуют документов по запрашиваемым id.
     */
    @State("[GET] /pact-rest-provider/api/documents/{id} Not Found")
    void toNoDocumentExistState() {
        when(documentRepository.findById(any())).thenReturn(Optional.empty());
    }
}
