package com.example.diss.service;

import com.example.diss.model.Document;
import com.example.diss.model.Tag;
import com.example.diss.repository.DocumentRepository;
import com.example.diss.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final DocumentRepository documentRepository;

    public TagService(TagRepository tagRepository, DocumentRepository documentRepository) {
        this.tagRepository = tagRepository;
        this.documentRepository = documentRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag addTag(Tag tag) {
        Optional<Tag> existingTag = tagRepository.findByNameIgnoreCase(tag.getName());
        if (existingTag.isPresent()) {
            throw new IllegalArgumentException("Tag with this name already exists.");
        }
        return tagRepository.save(tag);
    }

    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag with ID " + id + " not found."));

        // Remove this tag from all documents
        List<Document> documentsWithTag = documentRepository.findByTagsContaining(tag);
        for (Document doc : documentsWithTag) {
            doc.getTags().remove(tag);
        }
        tagRepository.deleteById(id);
    }
}
