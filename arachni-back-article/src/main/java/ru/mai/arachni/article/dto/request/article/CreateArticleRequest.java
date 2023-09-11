package ru.mai.arachni.article.dto.request.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleRequest {
    @NotBlank
    private String title;

    @NotEmpty
    private List<@NotBlank String> categories;

    @NotBlank
    private String text;

    @NotBlank
    private String creator;
}
