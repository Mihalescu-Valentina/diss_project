package com.example.diss.controller;

import com.example.diss.model.Document;
import com.example.diss.service.DocumentService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // Upload document
    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(@RequestParam Long userId,
                                                   @RequestParam String title,
                                                   @RequestParam String description,
                                                   @RequestParam MultipartFile file,
                                                   @RequestParam List<String> tags) throws IOException {
        Document document = documentService.uploadDocument(userId, title, description, file, tags);
        return new ResponseEntity<>(document, HttpStatus.CREATED);
    }

    // List all documents
    @GetMapping("/")
    @JsonView(Document.Views.Summary.class)
    public ResponseEntity<List<Document>> listDocuments() {
        List<Document> documents = documentService.listDocuments();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    // Filter documents by tags
    @PostMapping("/filter")
    @JsonView(Document.Views.Summary.class)
    public ResponseEntity<List<Document>> filterDocumentsByTags(@RequestBody List<String> tags) {
        List<Document> documents = documentService.filterDocumentsByTags(tags);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/download/{documentId}")
    public ResponseEntity<?> downloadDocument(@PathVariable Long documentId) throws IOException {
        try {
            byte[] fileBytes = documentService.downloadFileByDocumentId(documentId);

            Document doc = documentService.getDocumentById(documentId);

            String fileName = Paths.get(doc.getFilePath()).getFileName().toString();
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));

            Resource resource = new ByteArrayResource(fileBytes);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IllegalArgumentException e) {
            // This will catch if documentId is invalid (thrown from your service)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            // This will catch IO errors (e.g., problem reading the file)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error reading the file."));
        }
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long documentId,
                                                   @RequestParam(required = false) String title,
                                                   @RequestParam(required = false) String description,
                                                   @RequestParam(required = false) MultipartFile file,
                                                   @RequestParam(required = false) List<String> tags) throws IOException {
        Document updated = documentService.updateDocument(documentId, title, description, file, tags);
        return ResponseEntity.ok(updated);
    }


    // Edit document tags
    @PutMapping("/{documentId}/tags")
    public ResponseEntity<Document> editDocumentTags(@PathVariable Long documentId, @RequestParam List<String> tags) {
        Document updatedDocument = documentService.editDocumentTags(documentId, tags);
        return new ResponseEntity<>(updatedDocument, HttpStatus.OK);
    }

    // Delete document
    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) throws IOException {
        documentService.deleteDocument(documentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    @JsonView(Document.Views.Summary.class)
    public ResponseEntity<?> listDocumentsByUser(@PathVariable Long userId) {
        try {
            List<Document> documents = documentService.listDocumentsByUserId(userId);
            return ResponseEntity.ok(documents);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());  // Return the "User with ID X not found." message
        }
    }

    @GetMapping("/{id}/content")
    @JsonView(Document.Views.Detail.class)
    public ResponseEntity<?> getDocumentContent(@PathVariable Long id) {
        try {
            String content = documentService.getDocumentContent(id);
            return ResponseEntity.ok(Map.of("content", content));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @JsonView(Document.Views.Detail.class)

    public ResponseEntity<?> getDocument(@PathVariable Long id) {
        try {
            var doc = documentService.getDocumentById(id);
            return ResponseEntity.ok(doc);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/search")
    @JsonView(Document.Views.Summary.class)
    public ResponseEntity<List<Document>> searchDocuments(@RequestParam String keyword) {
        List<Document> documents = documentService.searchDocuments(keyword);
        return ResponseEntity.ok(documents);
    }
}
