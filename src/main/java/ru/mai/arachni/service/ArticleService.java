package ru.mai.arachni.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import ru.mai.arachni.domain.article.Article;
import ru.mai.arachni.dto.request.UpdateArticleRequest;
import ru.mai.arachni.dto.response.ArticleResponse;
import ru.mai.arachni.exception.ArachniError;
import ru.mai.arachni.exception.ArachniException;
import ru.mai.arachni.repository.ArticleRepository;
import ru.mai.arachni.validator.ArticleValidator;

import java.util.Optional;

@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    //private final ArticleValidator articleValidator;

    @Transactional
    public ArticleResponse updateArticle(
            final Long idArticle,
            UpdateArticleRequest updateArticleRequest
    ) {
        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new ArachniException(
                    ArachniError.ARTICLE_NOT_FOUND,
                    "id_article: " + idArticle
            );
        }
//        if (!articleValidator.isValidUpdateArticleRequest(updateArticleRequest)) {
//            throw new ArachniException(
//                    ArachniError.EMPTY_OR_NULL_PROPERTIES_ENCOUNTERED,
//                    "newTitle: %s, newCategories: %s, newText: %s".formatted(
//                            updateArticleRequest.getNewTitle(),
//                            updateArticleRequest.getNewCategories(),
//                            updateArticleRequest.getNewText()
//                    )
//            );
//        }

        Article article = articleOptional.get();
        article.setTitle(updateArticleRequest.getNewTitle());
        article.setCategories(updateArticleRequest.getNewCategories());
        article.setText(updateArticleRequest.getNewText());

        try {
            articleRepository.save(article);
        } catch (DataIntegrityViolationException e) {
            throw new ArachniException(
                    ArachniError.DUPLICATE_TITLE_ERROR,
                    e.getMessage()
            );
        }

        return new ArticleResponse(article);
    }

    @Transactional(readOnly = true)
    public ArticleResponse getArticle(final Long idArticle) {
        Optional<Article> article = articleRepository.findById(idArticle);
        if (article.isEmpty()) {
            throw new ArachniException(
                    ArachniError.ARTICLE_NOT_FOUND,
                    "id_article: " + idArticle
            );
        }
        return new ArticleResponse(article.get());
    }

    @Transactional
    public void deleteArticle(final Long idArticle) {
        articleRepository.deleteById(idArticle);
    }
}
