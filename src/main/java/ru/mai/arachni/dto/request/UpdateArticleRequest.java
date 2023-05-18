package ru.mai.arachni.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleRequest {
    private String newTitle;
    private String newCategories;
    private String newText;
}
