package com.example.diss.service;

import com.example.diss.dto.TagRequest;
import com.example.diss.model.Tag;
import com.example.diss.repository.DocumentRepository;
import com.example.diss.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private DocumentRepository documentRepository;

    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTagByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public String addTag(Tag tag) {
        Optional<Tag> existingTag = tagRepository.findByName(tag.getName());
        if (existingTag.isPresent()) {
            return "Tag already exists.";
        }
        String name = tag.getName().toLowerCase();
        tagRepository.save(tag);
        return "Tag added successfully.";
    }


    public String removeTag(Long id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isEmpty()) {
            return "Tag not found.";
        }
        tagRepository.deleteById(id);
        return "Tag deleted successfully.";
    }

    public String editTag(Long id, TagRequest request) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isEmpty()) {
            return "Tag not found.";
        }
        Tag tag = tagOptional.get();
        tag.setName(request.getName());
        tagRepository.save(tag);
        return "Tag updated successfully.";
    }
}

