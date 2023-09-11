package ru.mai.arachni.core.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.mai.arachni.core.domain.Article;

import java.time.ZonedDateTime;
import java.util.List;

public class ArticleSpecification {
    public static Specification<Article> hasCreator(String creator) {
        return (article, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(
                        article
                                .join("creator")
                                .get("creator"),
                        "%" + creator + "%"
                );
    }

    public static Specification<Article> hasTitle(String title) {
        return (article, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(
                        article.get("title"),
                        "%" + title + "%"
                );
    }

    public static Specification<Article> hasCategories(List<String> categories) {
        return (article, criteriaQuery, criteriaBuilder) ->
                article.
                        join("categories")
                        .get("category")
                        .in(categories);
    }

    public static Specification<Article> isLaterThan(ZonedDateTime startDateTime) {
        return (article, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(
                        article.get("creationDate"),
                        startDateTime
                );

    }

    public static Specification<Article> isEarlierThan(ZonedDateTime finishDateTime) {
        return (article, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(
                        article.get("creationDate"),
                        finishDateTime
                );

    }
}
