package ru.mai.arachni.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleRequest {
    @NotBlank
    private String title;

    @NotEmpty
    private List<@NotBlank String> categories;

    @NotBlank
    private String text;
}
