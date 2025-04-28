package com.example.diss.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class DocumentDTO {
    private String title;
    private String content;
    private Set<String> tags;
    private String uploadedBy;
    private LocalDateTime uploadedAt;
}