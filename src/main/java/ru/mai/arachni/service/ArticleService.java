package ru.mai.arachni.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.mai.arachni.domain.article.Article;
import ru.mai.arachni.dto.request.Order;
import ru.mai.arachni.dto.request.SortingCriterion;
import ru.mai.arachni.dto.response.ArticleListResponse;
import ru.mai.arachni.dto.response.ArticlePreviewResponse;
import ru.mai.arachni.converter.ArticleConverter;
import ru.mai.arachni.domain.Article;
import ru.mai.arachni.domain.Category;
import ru.mai.arachni.domain.Creator;
import ru.mai.arachni.dto.request.UpdateArticleRequest;
import ru.mai.arachni.dto.response.ArticleResponse;
import ru.mai.arachni.exception.ArachniError;
import ru.mai.arachni.exception.ArachniException;
import ru.mai.arachni.repository.ArticleRepository;
import ru.mai.arachni.repository.CategoryRepository;
import ru.mai.arachni.repository.CreatorRepository;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ArticleService {
    private final ArticleConverter articleConverter;
    private final ArticleRepository articleRepository;
    private final CreatorRepository creatorRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    void setCreatorToArticle(final Article article, final String creatorName) {
        Optional<Creator> existCreator = creatorRepository.findOneCreatorsByCreator(creatorName);
        if (existCreator.isPresent()) {
            article.setCreator(existCreator.get());
        } else {
            Creator creator = new Creator();
            creator.setCreator(creatorName);
            article.setCreator(creator);
        }
    }

    void setCategoriesToArticle(final Article article, final List<String> categories) {
        article.setCategories(
                new HashSet<>(categories)
                        .stream()
                        .map(this::chooseCategory)
                        .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    Category chooseCategory(final String categoryName) {
        Optional<Category> existCategory
                = categoryRepository.findOneCategoryByCategory(categoryName);
        if (existCategory.isPresent()) {
            return existCategory.get();
        } else {
            Category category = new Category();
            category.setCategory(categoryName);
            return category;
        }
    }

    List<Article> sortArticles(
            List<Article> articles,
            SortingCriterion sortingCriterionArticles
    ) {
        return articles.stream()
                .sorted(Sorting.getSortingArticlesLambda(
                        sortingCriterionArticles
                ))
                .collect(Collectors.toList());
    }

    List<Article> extractSubArticleList(
            List<Article> articles,
            Integer skipArticles,
            Integer limitArticles
    ) {
        if (skipArticles >= articles.size()) {
            return List.of();
        }
        if (skipArticles + limitArticles > articles.size()) {
            return articles.subList(skipArticles, articles.size());
        }
        return articles.subList(skipArticles, skipArticles + limitArticles);
    }

    List<ArticlePreviewResponse> collectArticlePreviewResponseList(
            List<Article> articles
    ) {
        List<ArticlePreviewResponse> articlePreviewResponseList = new ArrayList<>();
        for (Article article : articles) {
            articlePreviewResponseList.add(
                    new ArticlePreviewResponse(
                            article.getIdArticle(),
                            article.getTitle(),
                            article.getCategories(),
                            article.getCreator(),
                            article.getCreationDate()
                    )
            );
        }
        return articlePreviewResponseList;
    }

    @Transactional
    public ArticleResponse updateArticle(
            final Long idArticle,
            final UpdateArticleRequest updateArticleRequest
    ) {
        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new ArachniException(
                    ArachniError.ARTICLE_NOT_FOUND,
                    "id_article: " + idArticle
            );
        }

        Article article = articleOptional.get();
        article.setTitle(updateArticleRequest.getNewTitle());
        setCategoriesToArticle(article, updateArticleRequest.getNewCategories());
        article.setText(updateArticleRequest.getNewText());

        articleRepository.save(article);

        return articleConverter.convertArticleToArticleResponse(article);
    }

    @Transactional(readOnly = true)
    public ArticleResponse getArticle(final Long idArticle) {
        Optional<Article> article = articleRepository.findById(idArticle);
        if (article.isEmpty()) {
            throw new ArachniException(
                    ArachniError.ARTICLE_NOT_FOUND,
                    "id_article: " + idArticle
            );
        }
        return articleConverter.convertArticleToArticleResponse(article.get());
    }

    @Transactional
    public void deleteArticle(final Long idArticle) {
        articleRepository.deleteById(idArticle);
    }

    @Transactional(readOnly = true)
    public ArticleListResponse getArticlePreviewList(
            Integer skipArticles,
            Integer limitArticles,
            Order orderArticles,
            SortingCriterion sortingCriterionArticles
    ) {
        List<Article> articles = articleRepository.findAll();
        List<Article> sortedArticles = sortArticles(articles, sortingCriterionArticles);

        if (orderArticles == Order.DESC) {
            Collections.reverse(sortedArticles);
        }

        List<Article> subListArticles
                = extractSubArticleList(sortedArticles, skipArticles, limitArticles);
        List<ArticlePreviewResponse> articlePreviewResponseList
                = collectArticlePreviewResponseList(subListArticles);

        return new ArticleListResponse(articlePreviewResponseList);
    }
}
