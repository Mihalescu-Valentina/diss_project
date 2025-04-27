package com.example.diss.service;



import com.example.diss.dto.DocumentCreateRequest;
import com.example.diss.model.DepartmentType;
import com.example.diss.model.Document;
import com.example.diss.model.Tag;
import com.example.diss.model.User;
import com.example.diss.repository.DocumentRepository;
import com.example.diss.repository.TagRepository;
import com.example.diss.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
    public class DocumentService {

        @Autowired
        private DocumentRepository documentRepository;

        @Autowired
        TagRepository tagRepository;

        @Autowired
        UserRepository userRepository;

        public Document getDocumentById(Long id) {
            return documentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Document not found"));
        }

        public List<Document> searchDocumentsByTitle(String title) {
            return documentRepository.findByTitleContainingIgnoreCase(title);
        }

        public String addDocument(Document document) {
            documentRepository.save(document);
            return "Document added successfully.";
        }
        @Transactional
        public String addDocumentTagNames(DocumentCreateRequest request) {
            Document document = new Document();
            document.setTitle(request.getTitle());
            document.setContent(request.getContent());
            document.setUploadedAt(LocalDateTime.now());

            // Attach uploader
            User uploader = userRepository.findByEmail(request.getUploadedBy())
                    .orElseThrow(() -> new RuntimeException("Uploader not found"));
            document.setUploadedBy(uploader);

            // Attach tags by name
            Set<Tag> tags = new HashSet<>();
            for (String tagName : request.getTagNames()) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseThrow(() -> new RuntimeException("Tag not found: " + tagName));
                tags.add(tag);
            }
            document.setTags(tags);

            documentRepository.save(document);
            return "Document created successfully.";
        }
        @Transactional
        public String removeDocument(Long documentId) {
            Optional<Document> documentOptional = documentRepository.findById(documentId);
            if (documentOptional.isEmpty()) {
                return "Document not found.";
            }
            documentRepository.deleteById(documentId);
            return "Document deleted successfully.";
        }

        public List<Document> getAllDocuments() {
            return documentRepository.findAll();
        }

        @Transactional
        public List<Document> getDocumentsByDepartment(String department) {
            return documentRepository.findByUploadedBy_DepartmentType(DepartmentType.valueOf(department.toUpperCase()));
        }
        public String editDocument(Long id, Document newDocumentData) {
            Optional<Document> documentOptional = documentRepository.findById(id);
            if (documentOptional.isEmpty()) {
                return "Document not found.";
            }

            Document document = documentOptional.get();
            document.setTitle(newDocumentData.getTitle());
            document.setContent(newDocumentData.getContent());
            document.setTags(newDocumentData.getTags());
            documentRepository.save(document);

            return "Document updated successfully.";
        }
    }

