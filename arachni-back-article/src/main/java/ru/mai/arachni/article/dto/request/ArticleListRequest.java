package ru.mai.arachni.article.dto.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class ArticleListRequest {
    private String searchString;
    private Integer skip;
    private Integer limit;
    private Sort.Direction order;
    private SortingParameter sortBy;
    private String creator;
    private List<String> categories;
    private ZonedDateTime startDate;
    private ZonedDateTime finishDate;
}
