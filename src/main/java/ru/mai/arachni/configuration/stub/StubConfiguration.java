package ru.mai.arachni.configuration.stub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mai.arachni.converter.ArticleConverter;
import ru.mai.arachni.repository.ArticleRepository;
import ru.mai.arachni.repository.CategoryRepository;
import ru.mai.arachni.repository.CreatorRepository;
import ru.mai.arachni.service.stub.StubService;

@Configuration
public class StubConfiguration {

    @Bean
    public StubService stubService(
            ArticleConverter articleConverter,
            ArticleRepository articleRepository,
            CreatorRepository creatorRepository,
            CategoryRepository categoryRepository
    ) {
        return new StubService(
                articleConverter,
                articleRepository,
                creatorRepository,
                categoryRepository
        );
    }
}
