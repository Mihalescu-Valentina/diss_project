package com.example.diss.service;

import com.example.diss.model.Tag;
import com.example.diss.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

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
        if (!tagRepository.existsById(Math.toIntExact(id))) {
            throw new IllegalArgumentException("Tag with this ID does not exist.");
        }
        tagRepository.deleteById(Math.toIntExact(id));
    }
}
