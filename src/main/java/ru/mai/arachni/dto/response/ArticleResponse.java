package ru.mai.arachni.dto.response;

import lombok.experimental.Delegate;
import ru.mai.arachni.domain.article.Article;


public class ArticleResponse {
    @Delegate
    private Article article;
}
