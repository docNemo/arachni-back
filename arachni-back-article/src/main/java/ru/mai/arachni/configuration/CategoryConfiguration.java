package ru.mai.arachni.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mai.arachni.article.service.category.CategoryService;
import ru.mai.arachni.core.repository.CategoryRepository;

@Configuration
public class CategoryConfiguration {

    @Bean
    public CategoryService categoryService(
            CategoryRepository categoryRepository
    ) {
        return new CategoryService(
                categoryRepository
        );
    }
}
