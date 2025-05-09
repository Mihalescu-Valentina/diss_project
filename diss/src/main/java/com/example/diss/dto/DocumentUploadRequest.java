package com.example.diss.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class DocumentUploadRequest {

    private String title;
    private String description;
    private List<String> tags;
    private String email;
    private MultipartFile file;
}
