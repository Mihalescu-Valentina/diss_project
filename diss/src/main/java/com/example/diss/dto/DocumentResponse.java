package com.example.diss.dto;


import com.example.diss.model.Document;
import com.example.diss.model.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.yaml.snakeyaml.tokens.Token.ID.Tag;

public class DocumentResponse {
    private Long id;
    private String title;
    private List<String> tags;
    private String uploadedBy;
    private LocalDateTime uploadedAt;

    public DocumentResponse(Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.tags = document.getTags().stream()
                .map(com.example.diss.model.Tag::getName)
                .collect(Collectors.toList());
        this.uploadedBy = document.getUploadedBy().getEmail();
        this.uploadedAt = document.getUploadedAt();
    }

}

