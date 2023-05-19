package ru.mai.arachni.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.mai.arachni.domain.article.Article;
import ru.mai.arachni.dto.request.UpdateArticleRequest;
import ru.mai.arachni.dto.response.ArticleResponse;
import ru.mai.arachni.exception.ArachniError;
import ru.mai.arachni.exception.ArachniException;
import ru.mai.arachni.repository.ArticleRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponse updateArticle(
            final Long idArticle,
            final UpdateArticleRequest updateArticleRequest
    ) {
        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new ArachniException(
                    ArachniError.ARTICLE_NOT_FOUND,
                    "id_article: " + idArticle
            );
        }

        Article article = articleOptional.get();
        article.setTitle(updateArticleRequest.getNewTitle());
        article.setCategories(updateArticleRequest.getNewCategories());
        article.setText(updateArticleRequest.getNewText());

        articleRepository.save(article);

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
