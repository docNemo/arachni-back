package ru.mai.arachni.converter;

import ru.mai.arachni.domain.Article;
import ru.mai.arachni.domain.Category;
import ru.mai.arachni.dto.response.ArticleResponse;

import java.util.stream.Collectors;

public class ArticleConverter {
    public ArticleResponse convertArticleToArticleResponse(Article article) {
        return new ArticleResponse(
                article.getIdArticle(),
                article.getTitle(),
                article
                        .getCategories()
                        .stream()
                        .map(Category::getCategory)
                        .collect(Collectors.toList()),
                article.getCreator().getCreator(),
                article.getCreationDate(),
                article.getText()
        );
    }
}
