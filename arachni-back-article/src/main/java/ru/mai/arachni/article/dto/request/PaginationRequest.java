package ru.mai.arachni.article.dto.request;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

@Data
@SuperBuilder
public class PaginationRequest {
    private String searchString;
    private Integer skip;
    private Integer limit;
    private Sort.Direction order;
}
