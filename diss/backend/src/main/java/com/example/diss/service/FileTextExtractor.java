package com.example.diss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class FileTextExtractor {

    public String extractText(MultipartFile file) {
        try (InputStream input = file.getInputStream()) {
            org.apache.tika.Tika tika = new org.apache.tika.Tika();
            return tika.parseToString(input);
        } catch (Exception e) {
            return "";
        }
    }
}
