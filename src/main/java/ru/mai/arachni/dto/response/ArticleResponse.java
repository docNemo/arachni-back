package ru.mai.arachni.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ArticleResponse {
    private Long idArticle;

    private String title;

    private List<String> categories;

    private String creator;

    private ZonedDateTime creationDate;

    private String text;
}
