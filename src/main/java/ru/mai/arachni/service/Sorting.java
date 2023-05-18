package ru.mai.arachni.service;


import ru.mai.arachni.domain.article.Article;
import ru.mai.arachni.dto.request.SortingCriterion;

import java.util.Comparator;


public class Sorting {

    public static Comparator<Article> getSortingArticlesLambda(
            SortingCriterion sortingCriterion
    ) {
        return (a1, a2) -> switch (sortingCriterion) {
            case TITLE -> a1.getTitle().toLowerCase()
                    .compareTo(a2.getTitle().toLowerCase());
            case CREATOR -> a1.getCreator().toLowerCase()
                    .compareTo(a2.getCreator().toLowerCase());
            case DATE -> a1.getCreationDate().compareTo(a2.getCreationDate());
        };
    }
}
