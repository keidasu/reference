package ru.gpb.tech.pactrestprovider.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocData {
    private Integer year;
    private LocalDate issueDate;
    private BigDecimal taxRate;

    @ElementCollection
    private List<Income> incomeList;
}
