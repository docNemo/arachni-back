package ru.mai.arachni.article.dto.response.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryListResponse {
    private List<CategoryResponse> categories;
    private Long count;
}
