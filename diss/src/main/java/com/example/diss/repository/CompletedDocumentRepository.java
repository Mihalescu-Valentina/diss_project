package com.example.diss.repository;
import com.example.diss.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedDocumentRepository extends JpaRepository<CompletedDocument, Long> {
    boolean existsByUserAndDocument(User user, Document document);

    boolean existsByUserIdAndDocumentId(Long userId, Long documentId);
}