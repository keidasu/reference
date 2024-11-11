package ru.gpb.tech.pactrestprovider.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Income {
    private Boolean active;
    private Integer incomeSum;
}
