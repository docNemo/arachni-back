package ru.mai.arachni.article.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListRequest {
    String searchString;
    Integer skip;
    Integer limit;
    Sort.Direction order;
    SortingParameter sortBy;
    String creator;
    List<String> categories;
    ZonedDateTime startDate;
    ZonedDateTime finishDate;
}
