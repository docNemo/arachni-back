package ru.mai.arachni.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mai.arachni.core.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findOneCategoryByCategory(String category);
}
