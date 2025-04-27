package com.example.diss.controller;

import com.example.diss.model.Document;
import com.example.diss.model.Tag;
import com.example.diss.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
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

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // Upload document
    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(@RequestParam Long userId,
                                                   @RequestParam String title,
                                                   @RequestParam MultipartFile file,
                                                   @RequestParam List<String> tags) throws IOException {
        Document document = documentService.uploadDocument(userId, title, file, tags);
        return new ResponseEntity<>(document, HttpStatus.CREATED);
    }

    // List all documents
    @GetMapping("/")
    public ResponseEntity<List<Document>> listDocuments() {
        List<Document> documents = documentService.listDocuments();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/download/{documentId}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long documentId) throws IOException {
        byte[] fileBytes = documentService.downloadFileByDocumentId(documentId);

        // Get the document object to retrieve the filename and extension
        Document doc = documentService.getDocumentById(documentId);  // Ensure you have a method to get the Document by ID

        // Extract the file extension from the filePath (ensure to get the file name)
        String fileName = Paths.get(doc.getFilePath()).getFileName().toString();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

        // Create a resource from the file bytes
        Resource resource = new ByteArrayResource(fileBytes);

        // Set the content-disposition header to indicate it's a file download with the correct extension
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
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
    public ResponseEntity<List<Document>> listDocumentsByUser(@PathVariable Long userId) {
        List<Document> documents = documentService.listDocumentsByUserId(userId);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

//    @GetMapping("/{id}/content")
//    public ResponseEntity<String> getDocumentContent(@PathVariable Long id) {
//        String content = documentService.getDocumentContent(id);
//        return ResponseEntity.ok(content);
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<List<Document>> searchDocuments(@RequestParam String keyword) {
//        List<Document> documents = documentService.searchDocuments(keyword);
//        return ResponseEntity.ok(documents);
//    }
}
