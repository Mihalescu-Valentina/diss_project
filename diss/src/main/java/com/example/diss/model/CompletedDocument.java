package com.example.diss.model;

import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "completed_documents", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "document_id"}))
public class CompletedDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "document_id")
    private Document document;

    // Getters and Setters
    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Document getDocument() { return document; }
    public void setDocument(Document document) { this.document = document; }
}