package ru.mai.arachni.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mai.arachni.domain.Article;
import ru.mai.arachni.dto.response.ArticlePreviewResponse;
import ru.mai.arachni.dto.response.ArticleResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleConverterTest {
    private Article article;
    private ArticleResponse expectedArticleResponse;
    private ArticlePreviewResponse expectedArticlePreviewResponse;
    private ArticleConverter articleConverter;

    @BeforeEach
    void setup() {

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
