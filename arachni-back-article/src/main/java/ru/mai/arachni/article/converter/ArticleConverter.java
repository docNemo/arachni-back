package ru.mai.arachni.article.converter;

import ru.mai.arachni.article.dto.response.article.ArticlePreviewResponse;
import ru.mai.arachni.article.dto.response.article.ArticleResponse;
import ru.mai.arachni.core.domain.Article;
import ru.mai.arachni.core.domain.Category;

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

    public ArticlePreviewResponse convertArticleToArticlePreviewResponse(Article article) {
        return new ArticlePreviewResponse(
                article.getIdArticle(),
                article.getTitle(),
                article
                        .getCategories()
                        .stream()
                        .map(Category::getCategory)
                        .collect(Collectors.toList()),
                article.getCreator().getCreator(),
                article.getCreationDate()
        );
    }
}
