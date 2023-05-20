package ru.mai.arachni.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mai.arachni.converter.ArticleConverter;
import ru.mai.arachni.repository.ArticleRepository;
import ru.mai.arachni.repository.CategoryRepository;
import ru.mai.arachni.repository.CreatorRepository;
import ru.mai.arachni.service.ArticleService;

@Configuration
public class ArticleConfiguration {

    @Bean
    public ArticleService articleService(
            ArticleRepository articleRepository,
            CreatorRepository creatorRepository,
            CategoryRepository categoryRepository,
            ArticleConverter articleConverter
    ) {
        return new ArticleService(
                articleConverter,
                articleRepository,
                creatorRepository,
                categoryRepository
        );
    }

    @Bean
    public ArticleConverter articleConverter() {
        return new ArticleConverter();
    }
}
