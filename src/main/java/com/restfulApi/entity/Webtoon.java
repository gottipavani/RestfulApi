package com.restfulApi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data @Getter
@Setter
@Entity
@Table(name = "webtoon")
public class Webtoon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;
    @ElementCollection
    @Column(name = "characters", nullable = false, length = 150)
    private List<String> characters;
}