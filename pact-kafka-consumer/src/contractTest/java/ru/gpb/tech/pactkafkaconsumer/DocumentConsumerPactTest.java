package ru.gpb.tech.pactkafkaconsumer;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.LambdaDsl;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gpb.tech.pactkafkaconsumer.dto.CreateDocumentRequest;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "pact-kafka-provider", providerType = ProviderType.SYNCH_MESSAGE, pactVersion = PactSpecVersion.V4)
public class DocumentConsumerPactTest {

    @Pact(consumer = "pact-kafka-consumer")
    V4Pact createDocumentRequestPact(MessagePactBuilder builder) {

        var dslPartCreateDocumentRequest = LambdaDsl.newJsonBody((body) -> body
                        .integerType("id")
                        .object("metaDoc", (metaDoc) -> metaDoc
                                .uuid("appId")
                                .integerType("appSequence"))
                        .object("docData", (docData) -> docData
                                .integerType("year")
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
                .expectsToReceive("createDocumentRequest")
                .withContent(dslPartCreateDocumentRequest)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createDocumentRequestPact", providerType = ProviderType.ASYNCH, pactVersion = PactSpecVersion.V4)
    void testCreateDocumentRequestPact(V4Interaction.AsynchronousMessage message) throws JsonProcessingException {
        // arrange
        var objectMapper = new ObjectMapper();

        // act
        var createDocumentRequest = objectMapper.readValue(
                message.getContents().getContents().valueAsString(), CreateDocumentRequest.class);

        // assert
        assertThat(createDocumentRequest).usingRecursiveAssertion().hasNoNullFields();
    }
}
