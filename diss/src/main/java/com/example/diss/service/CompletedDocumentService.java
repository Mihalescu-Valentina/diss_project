package com.example.diss.service;

import com.example.diss.model.*;
import com.example.diss.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompletedDocumentService {

    @Autowired
    private CompletedDocumentRepository completedRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private DocumentRepository documentRepo;

    public boolean markAsCompleted(Long userId, Long documentId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Document document = documentRepo.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found"));

        if (completedRepo.existsByUserAndDocument(user, document)) {
            return false; // Already completed
        }

        CompletedDocument completed = new CompletedDocument();
        completed.setUser(user);
        completed.setDocument(document);
        completedRepo.save(completed);
        return true;

    }
}
