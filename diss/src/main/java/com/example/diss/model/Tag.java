package com.example.diss.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Entity
@Table(name = "tags")
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @JsonView(Document.Views.Summary.class)
    private String name;
}




