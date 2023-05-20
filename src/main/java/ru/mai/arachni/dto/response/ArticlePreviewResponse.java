package ru.mai.arachni.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ArticlePreviewResponse {
    private Long idArticle;
    private String title;
    private String categories;
    private String creator;
    private ZonedDateTime creationDate;
}
