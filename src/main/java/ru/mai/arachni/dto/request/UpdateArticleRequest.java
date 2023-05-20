package ru.mai.arachni.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleRequest {
    @NotBlank
    private String newTitle;

    @NotBlank
    private List<String> newCategories;

    @NotBlank
    private String newText;
}
