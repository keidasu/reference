# MQ Rest Adapter

## Обзор

kafka-service - это микросервис, который принимает запросы через Kafka, валидирует их и вызывает внешний сервис
wallet для получения баланса кошелька. Этот сервис построен с использованием Java 17, Spring Boot, Spring OpenFeign и
Kafka.

## Функции

- Прием запросов через Kafka.
- Валидация входящих запросов.
- Вызов внешнего сервиса wallet для получения баланса кошелька.

## Требования

- Java 17
- Docker (для запуска Testcontainers)
- Gradle

## Сборка и запуск

Настройка окружения:

В корне приложения выполните команду для запуска контейнера kafka:

```bash
docker compose up
```

В топик kafka-service-request добавьте сообщение:

```bash
заголовок 
__TypeId__ = ru.gpb.tech.mqrestadapter.model.ClientRequest

сообщение
{
    "clientId": "b9f00d96-c282-4d8e-8ce6-681e5f1f9b21",
    "dateFrom": "2024-06-01T12:00:00Z",
    "dateTo": "2024-06-01T13:00:00Z"
}
```

Соберите проект:

```bash
./gradlew clean build
```
Запустите приложение:

```bash
./gradlew bootRun
```

## Тестирование

Для запуска интеграционных тестов, убедитесь, что у вас установлен Docker. Тесты используют Testcontainers для запуска
Kafka и WireMock.

```bash
./gradlew test
```