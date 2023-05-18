package ru.mai.arachni.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ArticlePreviewResponse {
    private Long idArticle;
    private String titleArticle;
    private String categoriesArticle;
    private String creatorArticle;
    private ZonedDateTime creationDate;
}
