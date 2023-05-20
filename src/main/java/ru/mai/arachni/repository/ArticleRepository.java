package ru.mai.arachni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mai.arachni.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
