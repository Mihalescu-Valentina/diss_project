package com.example.diss.repository;

import com.example.diss.model.Document;
import com.example.diss.model.Tag;
import com.example.diss.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findDistinctByTags_NameContainingIgnoreCase(String tagName);
    List<Document> findByUploadedBy(User user);
    List<Document> findByContentContaining(String keyword);

    @Query(value = "SELECT * FROM document WHERE content_tsv @@ plainto_tsquery('english', :keyword)", nativeQuery = true)
    List<Document> searchByContent(@Param("keyword") String keyword);

    List<Document> findByTagsContaining(Tag tag);

    @Query("SELECT DISTINCT d FROM Document d JOIN d.tags t WHERE t.name IN :tagNames GROUP BY d.id HAVING COUNT(DISTINCT t.name) = :tagCount")
    List<Document> findByTagNames(@Param("tagNames") List<String> tagNames, @Param("tagCount") int tagCount);
}
