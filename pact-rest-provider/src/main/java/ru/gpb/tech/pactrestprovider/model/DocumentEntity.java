package ru.gpb.tech.pactrestprovider.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import lombok.Data;

import java.util.Map;

@Data
@Entity
public class DocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private MetaDoc metaDoc;

    @Embedded
    private DocData docData;

    @ElementCollection
    @CollectionTable(name = "user_info_dict", joinColumns = @JoinColumn(name = "document_id"))
    @MapKeyColumn(name = "user_key")
    private Map<String, UserInfo> userInfoDict;

}


