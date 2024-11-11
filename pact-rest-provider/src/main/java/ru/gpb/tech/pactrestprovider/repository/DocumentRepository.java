package ru.gpb.tech.pactrestprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gpb.tech.pactrestprovider.model.DocumentEntity;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
}
