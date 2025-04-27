package com.example.diss.repository;

import com.example.diss.model.DepartmentType;
import com.example.diss.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByTitleContainingIgnoreCase(String title);
    List<Document> findByUploadedBy_DepartmentType(DepartmentType department);
}

