package ru.mai.arachni.article.dto.response.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryResponse {
    private Long idCategory;
    private String category;
}
