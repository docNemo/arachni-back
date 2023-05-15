package ru.mai.arachni.configuration.stub;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mai.arachni.repository.ArticleRepository;
import ru.mai.arachni.service.stub.StubService;

@Configuration
public class StubConfiguration {

    @Bean
    public StubService stubService(
            @Value("${helloPattern}") String helloPattern,
            ArticleRepository articleRepository
    ) {
        return new StubService(helloPattern, articleRepository);
    }
}
