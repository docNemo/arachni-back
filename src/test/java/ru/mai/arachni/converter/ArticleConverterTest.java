package ru.mai.arachni.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mai.arachni.domain.Article;
import ru.mai.arachni.domain.Category;
import ru.mai.arachni.domain.Creator;
import ru.mai.arachni.dto.response.ArticlePreviewResponse;
import ru.mai.arachni.dto.response.ArticleResponse;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleConverterTest {
    private Article article;
    private ArticleResponse expectedArticleResponse;
    private ArticlePreviewResponse expectedArticlePreviewResponse;
    private ArticleConverter articleConverter;

    @BeforeEach
    void setup() {
        Long idArticle = 10L;
        String strCreator = "Автор";
        String title = "Название";
        String text = "Это какой-то случайный текст";
        List<String> strCategories = List.of("Категория 1", "Категория 2");
        ZonedDateTime dateTime = ZonedDateTime.now();

        articleConverter = new ArticleConverter();

        Creator creator = new Creator(5L, strCreator);

        List<Category> categories = List.of(
                new Category(0L, strCategories.get(0)),
                new Category(1L, strCategories.get(1))
        );

        article = new Article(
                idArticle,
                title,
                categories,
                creator,
                dateTime,
                text
        );

        expectedArticleResponse = new ArticleResponse(
                idArticle,
                title,
                strCategories,
                strCreator,
                dateTime,
                text
        );

        expectedArticlePreviewResponse = new ArticlePreviewResponse(
                idArticle,
                title,
                strCategories,
                strCreator,
                dateTime
        );
    }

    @Test
    void testConvertArticleToArticleResponse() {
        ArticleResponse actualArticleResponse =
                articleConverter.convertArticleToArticleResponse(article);
        assertEquals(expectedArticleResponse, actualArticleResponse);
    }

    @Test
    void testConvertArticleToArticlePreviewResponse() {
        ArticlePreviewResponse actualArticlePreviewResponse =
                articleConverter.convertArticleToArticlePreviewResponse(article);
        assertEquals(expectedArticlePreviewResponse, actualArticlePreviewResponse);
    }
}
