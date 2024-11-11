package ru.gpb.tech.mqrestadapter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Класс представляет ответ клиенту с информацией о балансе кошелька.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ClientResponse {
    /**
     * Баланс кошелька.
     */
    private BigDecimal balance;
    
    /**
     * Валюта баланса.
     */
    private String currency;
    
    /**
     * Время последнего обновления баланса.
     */
    private OffsetDateTime lastUpdated;
}
