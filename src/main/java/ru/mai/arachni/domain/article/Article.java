package ru.mai.arachni.domain.article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_article")
    private Long idArticle;

    @Column(name = "title")
    private String title;

    @Column(name = "categories")
    private String categories;

    @Column(name = "creator")
    private String creator;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "text")
    private String text;
}
