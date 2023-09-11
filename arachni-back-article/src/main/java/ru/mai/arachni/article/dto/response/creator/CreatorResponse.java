package ru.mai.arachni.article.dto.response.creator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatorResponse {
    private Long idCreator;
    private String creator;
}
