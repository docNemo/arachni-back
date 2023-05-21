package ru.mai.arachni.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.mai.arachni.dto.request.CreateArticleRequest;
import ru.mai.arachni.dto.request.Order;
import ru.mai.arachni.dto.request.SortingParameter;
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

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
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

    Comparator<Article> getArticleComparatorByParameter(
            SortingParameter sortingParameter,
            Order order
    ) {
        Comparator<Article> comparator = switch (sortingParameter) {
            case TITLE -> Comparator.comparing(a -> a.getTitle().toLowerCase());
            case CREATOR -> Comparator.comparing(a -> a.getCreator().getCreator().toLowerCase());
            case DATE -> Comparator.comparing(Article::getCreationDate);
        };
        if (order == Order.DESC) {
            return Collections.reverseOrder(comparator);
        }
        return comparator;
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
                    "id_article: %s".formatted(idArticle)
            );
        }

        Article article = articleOptional.get();
        article.setTitle(updateArticleRequest.getTitle());
        setCategoriesToArticle(article, updateArticleRequest.getCategories());
        article.setText(updateArticleRequest.getText());

        articleRepository.save(article);

        return articleConverter.convertArticleToArticleResponse(article);
    }

    @Transactional(readOnly = true)
    public ArticleResponse getArticle(final Long idArticle) {
        Optional<Article> article = articleRepository.findById(idArticle);
        if (article.isEmpty()) {
            throw new ArachniException(
                    ArachniError.ARTICLE_NOT_FOUND,
                    "id_article: %s".formatted(idArticle)
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
            String searchString,
            Integer skipArticles,
            Integer limitArticles,
            Order order,
            SortingParameter sortingParameterArticles
    ) {
        List<Article> articles = articleRepository
                .findAll()
                .stream()
                .filter(s -> searchString.isBlank() || s.getTitle()
                        .toLowerCase()
                        .contains(searchString.toLowerCase()))
                .toList();
        List<ArticlePreviewResponse> articlePreviewResponseList = articles
                .stream()
                .sorted(getArticleComparatorByParameter(sortingParameterArticles, order))
                .skip(skipArticles)
                .limit(limitArticles)
                .map(articleConverter::convertArticleToArticlePreviewResponse)
                .toList();

        return new ArticleListResponse(articlePreviewResponseList, articles.size());
    }

    @Transactional
    public ArticleResponse createArticle(CreateArticleRequest createArticleRequest) {
        Article article = new Article();
        article.setTitle(createArticleRequest.getTitle());

        setCategoriesToArticle(article, createArticleRequest.getCategories());
        setCreatorToArticle(article, createArticleRequest.getCreator());

        article.setText(createArticleRequest.getText());
        article.setCreationDate(ZonedDateTime.now());

        articleRepository.save(article);

        return articleConverter.convertArticleToArticleResponse(article);
    }
}
