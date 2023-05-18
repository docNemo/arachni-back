package ru.mai.arachni.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mai.arachni.repository.ArticleRepository;
import ru.mai.arachni.service.ArticleService;
import ru.mai.arachni.validator.ArticleValidator;

@Configuration
public class ArticleConfiguration {

    @Bean
    public ArticleService articleService(
            ArticleRepository articleRepository,
            ArticleValidator articleValidator
    ) {
        return new ArticleService(articleRepository, articleValidator);
    }
}
