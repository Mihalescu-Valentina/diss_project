package com.example.diss.repository;

import com.example.diss.model.Document;
import com.example.diss.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findDistinctByTags_NameContainingIgnoreCase(String tagName);
    List<Document> findByUploadedBy(User user);
    List<Document> findByContentContaining(String keyword);

}
