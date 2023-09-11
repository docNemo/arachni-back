package ru.mai.arachni.article.dto.request.creator;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
@Builder
public class CreatorListRequest {
    private String searchString;
    private Integer skip;
    private Integer limit;
    private Sort.Direction order;
}
