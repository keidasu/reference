package ru.gazprombank.automation.akitagpb.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import io.cucumber.java.Scenario;
import io.cucumber.java.ru.И;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class RabbitMQSteps {

    private Connection connection;
    private Channel channel;
    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost;
    private Scenario scenario;

    // ObjectMapper для работы с JSON
    private final ObjectMapper objectMapper = new ObjectMapper();

    private void loadRabbitMQConfig() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("rabbitmq.properties")) {
            if (input == null) {
                throw new IOException("Unable to find rabbitmq.properties");
            }
            properties.load(input);
            this.host = properties.getProperty("rabbitmq.host");
            this.port = Integer.parseInt(properties.getProperty("rabbitmq.port"));
            this.username = properties.getProperty("rabbitmq.username");
            this.password = properties.getProperty("rabbitmq.password");
            this.virtualHost = properties.getProperty("rabbitmq.virtualHost");
        }
    }

    @И("настроить и подключиться к RabbitMQ")
    public void configureAndConnectToRabbitMQ() throws Exception {
        loadRabbitMQConfig();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);

        connection = factory.newConnection();
        channel = connection.createChannel();
        Assert.assertNotNull("Не удалось подключиться к RabbitMQ", connection);
        System.out.println("Успешное подключение к RabbitMQ: host=" + host + ", port=" + port + ", username=" + username + ", virtualHost=" + virtualHost);
    }

    @И("отправить содержимое файла {string} в очередь {string} как JSON")
    public void sendFileContentToQueueAsJSON(String filePath, String queueName) throws Exception {
        Assert.assertNotNull("Канал не инициализирован", channel);

        String resolvedFilePath = resolveFilePath(filePath);

        String fileContent = readFileContent(resolvedFilePath);

        Object jsonObject = objectMapper.readValue(fileContent, Object.class);

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .contentType("application/json")
                .build();

        channel.queueDeclare(queueName, true, false, false, null);

        String jsonMessage = objectMapper.writeValueAsString(jsonObject);
        channel.basicPublish("", queueName, properties, jsonMessage.getBytes());
        System.out.println("Содержимое файла отправлено в очередь " + queueName + " с типом JSON.");
    }

    @И("получить последнее сообщение из очереди {string} и сохранить его в переменную {string}")
    public void getLastMessageFromQueueAndSaveToVariable(String queueName, String variableName) throws IOException {
        Assert.assertNotNull("Канал не инициализирован", channel);

        GetResponse response = channel.basicGet(queueName, true); // true для автоподтверждения

        if (response != null) {
            byte[] body = response.getBody();
            String messageContent = new String(body);
            System.out.println("Получено сообщение из очереди " + queueName + ": " + messageContent);

            scenario.attach(messageContent, "application/json", "Сообщение из очереди: " + variableName);
        } else {
            System.out.println("Сообщений в очереди " + queueName + " нет.");
            scenario.attach("Сообщений нет", "text/plain", "Сообщение из очереди: " + variableName); // Сохраняем уведомление, если сообщений нет
        }
    }


    private String readFileContent(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    private String resolveFilePath(String filePath) {
        String basePath = System.getProperty("data.base.path", "src/test/resources/data");
        return filePath.replace("${data.base.path}", basePath);
    }

    @И("закрыть соединение с RabbitMQ")
    public void closeRabbitMQConnection() throws Exception {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
        if (connection != null && connection.isOpen()) {
            connection.close();
            System.out.println("Соединение с RabbitMQ закрыто");
        }
    }
}
