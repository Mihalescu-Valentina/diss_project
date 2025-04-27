package com.example.diss.controller;

import com.example.diss.dto.DocumentCreateRequest;
import com.example.diss.model.DepartmentType;
import com.example.diss.model.Document;
import com.example.diss.model.Tag;
import com.example.diss.model.User;
import com.example.diss.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id);
        return ResponseEntity.ok(document);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Document>> searchDocumentsByTitle(@RequestParam String title) {
        List<Document> documents = documentService.searchDocumentsByTitle(title);
        return ResponseEntity.ok(documents);
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {
        String result = documentService.removeDocument(id);
        if (result.equals("Document not found.")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editDocument(@PathVariable Long id, @RequestBody Document document) {
        String result = documentService.editDocument(id, document);
        if (result.equals("Document not found.")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> addDocument(@RequestBody DocumentCreateRequest request) {
        String result = documentService.addDocumentTagNames(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Document>> getDocumentsByDepartment(@PathVariable String department) {
        List<Document> documents = documentService.getDocumentsByDepartment(department);
        if (documents.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no documents are found
        }
        return ResponseEntity.ok(documents); // Return 200 and the list of documents
    }
}

