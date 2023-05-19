package ru.mai.arachni.dto.response;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import ru.mai.arachni.domain.Article;

@AllArgsConstructor
public class ArticleResponse {
    @Delegate
    private Article article;
}
