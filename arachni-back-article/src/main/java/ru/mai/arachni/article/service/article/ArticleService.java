package ru.mai.arachni.article.service.article;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.mai.arachni.article.converter.ArticleConverter;
import ru.mai.arachni.article.dto.request.article.ArticleListRequest;
import ru.mai.arachni.article.dto.request.article.CreateArticleRequest;
import ru.mai.arachni.article.dto.request.article.UpdateArticleRequest;
import ru.mai.arachni.article.exception.ArachniError;
import ru.mai.arachni.article.exception.ArachniException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.mai.arachni.article.dto.response.article.ArticleListResponse;
import ru.mai.arachni.article.dto.response.article.ArticlePreviewResponse;
import ru.mai.arachni.article.dto.response.article.ArticleResponse;
import ru.mai.arachni.core.domain.Article;
import ru.mai.arachni.core.domain.Category;
import ru.mai.arachni.core.domain.Creator;
import ru.mai.arachni.core.repository.ArticleRepository;
import ru.mai.arachni.core.repository.CategoryRepository;
import ru.mai.arachni.core.repository.CreatorRepository;
import ru.mai.arachni.core.repository.pagerequest.OffsetBasedPageRequest;
import ru.mai.arachni.core.specification.ArticleSpecification;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ArticleService {
    private final ArticleConverter articleConverter;
    private final ArticleRepository articleRepository;
    private final CreatorRepository creatorRepository;
    private final CategoryRepository categoryRepository;

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

        Article recordedArticle = articleRepository.save(article);

        return articleConverter.convertArticleToArticleResponse(recordedArticle);
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
            ArticleListRequest articleListRequest
    ) {
        Specification<Article> articleSpecification = ArticleSpecification.hasTitle(
                articleListRequest.getSearchString()
        );

        if (StringUtils.hasText(articleListRequest.getCreator())) {
            articleSpecification = ArticleSpecification.hasCreator(articleListRequest.getCreator());
        }

        if (
                Objects.nonNull(articleListRequest.getCategories())
                        && articleListRequest
                        .getCategories()
                        .stream()
                        .anyMatch(StringUtils::hasText)
        ) {
            articleSpecification = articleSpecification.and(
                    ArticleSpecification.hasCategories(
                            articleListRequest
                                    .getCategories()
                                    .stream()
                                    .filter(StringUtils::hasText)
                                    .toList()
                    )
            );
        }

        if (Objects.nonNull(articleListRequest.getStartDate())) {
            articleSpecification = articleSpecification.and(
                    ArticleSpecification.isLaterThan(articleListRequest.getStartDate())
            );
        }

        if (Objects.nonNull(articleListRequest.getFinishDate())) {
            articleSpecification = articleSpecification.and(
                    ArticleSpecification.isEarlierThan(articleListRequest.getFinishDate())
            );
        }

        Page<Article> articles = articleRepository
                .findAll(
                        articleSpecification,
                        new OffsetBasedPageRequest(
                                articleListRequest.getSkip(),
                                articleListRequest.getLimit(),
                                Sort.by(
                                        articleListRequest.getOrder(),
                                        articleListRequest.getSortBy().getPropertyName()
                                )
                        )
                );

        List<ArticlePreviewResponse> articlePreviewResponseList = articles
                .map(articleConverter::convertArticleToArticlePreviewResponse)
                .toList();

        return new ArticleListResponse(articlePreviewResponseList, articles.getTotalElements());
    }

    @Transactional
    public ArticleResponse createArticle(CreateArticleRequest createArticleRequest) {
        Article article = new Article();
        article.setTitle(createArticleRequest.getTitle());

        setCategoriesToArticle(article, createArticleRequest.getCategories());
        setCreatorToArticle(article, createArticleRequest.getCreator());

        article.setText(createArticleRequest.getText());
        article.setCreationDate(ZonedDateTime.now());

        Article recordedArticle = articleRepository.save(article);

        return articleConverter.convertArticleToArticleResponse(recordedArticle);
    }
}
