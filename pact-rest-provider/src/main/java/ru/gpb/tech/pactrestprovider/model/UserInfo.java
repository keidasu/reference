package ru.gpb.tech.pactrestprovider.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String name;
    private BigInteger accNumber;
}
