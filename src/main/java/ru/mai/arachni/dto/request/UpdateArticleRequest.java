package ru.mai.arachni.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleRequest {
    @NotBlank
    private String newTitle;

    @NotBlank
    private String newCategories;

    @NotBlank
    private String newText;
}
