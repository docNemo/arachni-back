package ru.mai.arachni.article.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
public class PaginationResponse<T> {
    private List<T> data;
    private Long count;
}
