package ru.gpb.tech.mqrestadapter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Класс представляет запрос клиента для получения баланса кошелька.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ClientRequest {
    /**
     * Идентификатор клиента.
     */
    private UUID clientId;
    
    /**
     * Начальная дата запроса.
     */
    private OffsetDateTime dateFrom;
    
    /**
     * Конечная дата запроса.
     */
    private OffsetDateTime dateTo;
}
