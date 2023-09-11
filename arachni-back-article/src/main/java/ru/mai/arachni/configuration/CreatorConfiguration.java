package ru.mai.arachni.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mai.arachni.article.service.creator.CreatorService;
import ru.mai.arachni.core.repository.CreatorRepository;

@Configuration
public class CreatorConfiguration {

    @Bean
    public CreatorService creatorService(
            CreatorRepository creatorRepository
    ) {
        return new CreatorService(
                creatorRepository
        );
    }
}
