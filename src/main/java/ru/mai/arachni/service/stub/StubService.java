package ru.mai.arachni.service.stub;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.mai.arachni.converter.ArticleConverter;
import ru.mai.arachni.domain.Article;
import ru.mai.arachni.domain.Category;
import ru.mai.arachni.domain.Creator;
import ru.mai.arachni.dto.response.ArticleResponse;
import ru.mai.arachni.exception.ArachniError;
import ru.mai.arachni.exception.ArachniException;
import ru.mai.arachni.repository.ArticleRepository;
import ru.mai.arachni.repository.CategoryRepository;
import ru.mai.arachni.repository.CreatorRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StubService {
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
                categories
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
    public void createArticle(final String title, final String text) {
        Article article = new Article();
        article.setTitle(title);

        setCreatorToArticle(article, "creator");
        setCategoriesToArticle(article, List.of("Cat1", "Cat2"));

        article.setCreationDate(ZonedDateTime.now());
        article.setText(text);

        articleRepository.save(article);
    }

    @Transactional()
    public ArticleResponse updateArticle(final Long idArticle, final String newText) {
        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new ArachniException(
                    ArachniError.ARTICLE_NOT_FOUND,
                    "id_article: " + idArticle
            );
        }

        Article article = articleOptional.get();
        article.setText(newText);
        articleRepository.save(article);
        return articleConverter.convertArticleToArticleResponse(article);
    }

}
