package ru.mai.arachni.article.dto.request.article;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ru.mai.arachni.article.dto.request.PaginationRequest;

import java.time.ZonedDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ArticleListRequest extends PaginationRequest {
    private ArticleSortingParameter sortBy;
    private String creator;
    private List<String> categories;
    private ZonedDateTime startDate;
    private ZonedDateTime finishDate;
}
