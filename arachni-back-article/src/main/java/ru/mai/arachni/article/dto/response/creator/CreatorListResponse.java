package ru.mai.arachni.article.dto.response.creator;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class CreatorListResponse {
    private List<CreatorResponse> creators;
    private Long count;
}
