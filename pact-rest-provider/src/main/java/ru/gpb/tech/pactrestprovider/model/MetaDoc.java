package ru.gpb.tech.pactrestprovider.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaDoc {
    private UUID appId;
    private Long appSequence;
}
