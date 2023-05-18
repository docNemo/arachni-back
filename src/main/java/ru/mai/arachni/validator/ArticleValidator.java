package ru.mai.arachni.validator;

import org.apache.commons.lang3.ObjectUtils;
import ru.mai.arachni.dto.request.UpdateArticleRequest;

public class ArticleValidator {

    public Boolean isValidUpdateArticleRequest(UpdateArticleRequest updateArticleRequest) {
        return ObjectUtils.allNotNull(
                updateArticleRequest.getNewTitle(),
                updateArticleRequest.getNewCategories(),
                updateArticleRequest.getNewText()
        )
        && !updateArticleRequest.getNewTitle().isBlank()
        && !updateArticleRequest.getNewCategories().isBlank()
        && !updateArticleRequest.getNewText().isBlank();
    }
}
