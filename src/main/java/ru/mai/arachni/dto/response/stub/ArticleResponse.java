package ru.mai.arachni.dto.response.stub;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mai.arachni.domain.article.Article;

@Data
@AllArgsConstructor
public class ArticleResponse {
    private Article article;
}
