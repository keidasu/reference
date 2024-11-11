package ru.gpb.tech.pactrestconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {

    private Long id;
    private MetaDoc metaDoc;
    private DocData docData;
    private Map<String, UserInfo> userInfoDict;

    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MetaDoc {
        private UUID appId;
        private Long appSequence;
    }

    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DocData {
        private Integer year;
        private LocalDate issueDate;
        private BigDecimal taxRate;
        private List<Income> incomeList;

        @Data
        @Accessors(chain = true)
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Income {
            private Boolean active;
            private Integer incomeSum;
        }
    }

    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfo {
        private String name;
        private BigInteger accNumber;
    }
}
