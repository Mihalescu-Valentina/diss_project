package com.example.diss.controller;

import com.example.diss.repository.CompletedDocumentRepository;
import com.example.diss.service.CompletedDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/completed")
public class CompletedDocumentController {

    @Autowired
    private CompletedDocumentService completedService;
    @Autowired
    private CompletedDocumentRepository completedDocumentRepository;

    @PostMapping("/mark")
    public boolean markAsCompleted(@RequestParam Long userId, @RequestParam Long documentId) {
        return completedService.markAsCompleted(userId, documentId);
    }

    @GetMapping("/isCompleted")
    public ResponseEntity<Boolean> isDocumentCompleted(
            @RequestParam Long userId,
            @RequestParam Long documentId) {
        boolean exists = completedDocumentRepository.existsByUserIdAndDocumentId(userId, documentId);
        return ResponseEntity.ok(exists);
    }
}