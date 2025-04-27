package com.example.diss.dto;

import com.example.diss.model.Document;
import lombok.Data;

import java.util.Set;

@Data
public class TagDTO {
    private String name;
    private Set<Document> documents;
}