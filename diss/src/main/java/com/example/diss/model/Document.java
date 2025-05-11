package com.example.diss.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "documents")
@Data
@JsonView(Document.Views.Summary.class)
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String filePath;
    private String fileType;

    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "TEXT")
    @JsonView(Views.Detail.class)
    private String content;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "documents_tags",
            joinColumns = @JoinColumn(name = "documents_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    @JsonView(Views.Summary.class)
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User uploadedBy;

    private LocalDateTime uploadedAt;

    public static class Views {
        public interface Summary {}
        public interface Detail extends Summary {}
    }
}

