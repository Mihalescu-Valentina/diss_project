package com.example.diss.dto;

import lombok.Data;
import java.util.List;

@Data
public class DocumentCreateRequest {
    private String title;
    private String description;
    private String content;
    private String uploadedBy;
    private List<String> tagNames;
}

