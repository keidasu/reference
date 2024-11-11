package ru.gpb.tech.pactrestconsumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.LambdaDsl;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.hc.client5.http.fluent.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ru.gpb.tech.pactrestconsumer.dto.DocumentDto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(PactConsumerTestExt.class)
@MockServerConfig(hostInterface = "localhost", port = "8888")
@PactTestFor(providerName = "pact-rest-provider")
public class DocumentConsumerPactTest {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String REQUEST_URL = "/pact-rest-provider/api/documents/1";

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Описание контракта для взаимодействия pact-rest-provider - pact-rest-consumer по HTTP через
     * endpoint [GET] /pact-rest-provider/api/documents/{id} для состояния провайдера, когда у него есть запрашиваемый
     * документ. По этому описанию будет настроен Pact MockServer.
     */
    @Pact(consumer = "pact-rest-consumer")
    RequestResponsePact createPactForGetDocument(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // Описываем ожидаемое тело ответа с помощью LambdaDSL
        DslPart dslPartDocumentDto = LambdaDsl.newJsonBody((body) -> body
                        .integerType("id")
                        .object("metaDoc", (metaDoc) -> metaDoc
                                .uuid("appId")
                                .integerType("appSequence"))
                        .object("docData", (docData) -> docData
                                .integerType("year")
                                .date("issueDate", DATE_FORMAT)
                                .decimalType("taxRate")
                                .array("incomeList", (element) -> element.object((income) ->
                                        income
                                                .booleanType("active")
                                                .integerType("incomeSum"))))
                        .object("userInfoDict", (userInfoDict) -> userInfoDict
                                .eachKeyLike("exampleKey", (userInfo) -> userInfo
                                        .stringType("name")
                                        .integerType("accNumber"))))
                .build();

        return builder
                .given("[GET] /pact-rest-provider/api/documents/{id} OK") // состояние провайдера
                .uponReceiving("Get document by id") // описание запроса, который ожидается получить
                .method("GET")
                .matchPath("/pact-rest-provider/api/documents/[0-9]+")
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .headers(headers)
                .body(dslPartDocumentDto)
                .toPact();
    }

    /**
     * Описание контракта pact-rest-provider - pact-rest-consumer для состояния провайдера, когда у него нет запрашиваемого документа
     */
    @Pact(consumer = "pact-rest-consumer")
    RequestResponsePact createPactForGetDocumentNotFound(PactDslWithProvider builder) {
        return builder
                .given("[GET] /pact-rest-provider/api/documents/{id} Not Found")
                .uponReceiving("Get document by id")
                .method("GET")
                .matchPath("/pact-rest-provider/api/documents/[0-9]+")
                .willRespondWith()
                .status(HttpStatus.NOT_FOUND.value())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createPactForGetDocument")
    void testCreatePactForGetDocument(MockServer mockServer, RequestResponsePact requestResponsePact) throws IOException {
        var httpResponse = Request.get(mockServer.getUrl() + REQUEST_URL).execute().returnResponse();
        var requestResponseFromPact = requestResponsePact.getInteractions().get(0).asSynchronousRequestResponse();
        assertThat(requestResponseFromPact).isNotNull();

        var responseFromPact =  requestResponseFromPact.getResponse().getBody().valueAsString();
        var responseItem = objectMapper.readValue(responseFromPact, DocumentDto.class);

        assertThat(httpResponse.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseItem).usingRecursiveAssertion().hasNoNullFields();
    }

    @Test
    @PactTestFor(pactMethod = "createPactForGetDocumentNotFound")
    void testCreatePactForGetDocumentNotFound(MockServer mockServer) throws IOException {
        var httpResponse = Request.get(mockServer.getUrl() + REQUEST_URL).execute().returnResponse();

        assertThat(httpResponse.getCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
