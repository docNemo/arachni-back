package ru.mai.arachni.configuration.stub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mai.arachni.repository.ArticleRepository;
import ru.mai.arachni.service.stub.StubService;

@Configuration
public class StubConfiguration {

    @Bean
    public StubService stubService(ArticleRepository articleRepository) {
        return new StubService(articleRepository);
    }
}
