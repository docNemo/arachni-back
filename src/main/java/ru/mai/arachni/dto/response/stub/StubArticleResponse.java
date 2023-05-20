package ru.mai.arachni.dto.response.stub;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mai.arachni.domain.Article;

@Data
@AllArgsConstructor
public class StubArticleResponse {
    private Article article;
}
