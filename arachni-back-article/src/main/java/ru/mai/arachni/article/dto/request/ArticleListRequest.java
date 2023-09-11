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
    private String searchString = "";
    private Integer skip = 0;
    private Integer limit = 25;
    private Sort.Direction order = Sort.Direction.ASC;
    private SortingParameter sortBy = SortingParameter.TITLE;
    private String creator;
    private List<String> categories;
    private ZonedDateTime startDate;
    private ZonedDateTime finishDate;
}
