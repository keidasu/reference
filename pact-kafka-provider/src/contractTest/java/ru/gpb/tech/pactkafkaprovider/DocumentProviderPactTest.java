package ru.gpb.tech.pactkafkaprovider;

import au.com.dius.pact.core.model.Interaction;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.provider.MessageAndMetadata;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gpb.tech.pactkafkaprovider.dto.CreateDocumentRequest;

import java.util.List;
import java.util.Map;

@Provider("pact-kafka-provider")
@PactBroker
//@PactFolder("pacts") //для локальной верификации без Pact Broker
@ExtendWith(SpringExtension.class)
public class DocumentProviderPactTest {

    private String requestText;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void testTemplate(Interaction interaction, PactVerificationContext context) {
        requestText = ((V4Interaction.AsynchronousMessage) interaction).getContents().getContents().valueAsString();
        context.verifyInteraction();
    }

    @BeforeEach
    void setUp(PactVerificationContext context) {
        context.setTarget(new MessageTestTarget(List.of(this.getClass().getPackageName()), this.getClass().getClassLoader()));
    }

    @PactVerifyProvider("createDocumentRequest")
    MessageAndMetadata testCreateDocumentRequest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        var request = objectMapper.readValue(requestText, CreateDocumentRequest.class);
        return new MessageAndMetadata(objectMapper.writeValueAsString(request).getBytes(), Map.of());
    }
}
