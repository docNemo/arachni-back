package ru.mai.arachni.service.stub;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.mai.arachni.domain.article.Article;
import ru.mai.arachni.dto.request.stub.StubRequest;
import ru.mai.arachni.dto.response.stub.ArticleResponse;
import ru.mai.arachni.dto.response.stub.StubResponse;
import ru.mai.arachni.exception.ArachniError;
import ru.mai.arachni.exception.ArachniException;
import ru.mai.arachni.repository.ArticleRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class StubService {
    private final String helloPattern;
    private final ArticleRepository articleRepository;

    public StubResponse helloForGet(final String name) {
        return new StubResponse(helloPattern.formatted(name));
    }

    public StubResponse helloForPost(final StubRequest stubRequest) {
        return new StubResponse(helloPattern.formatted(stubRequest.getName()));
    }

    @Transactional()
    public void createArticle(final String title, final String text) {
        Article article = new Article();
        article.setTitle(title);
        article.setCreator("creator");
        article.setCategories("Catego");
        article.setCreationDate(LocalDateTime.now());
        article.setText(text);

        articleRepository.save(article);
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

    @Transactional()
    public ArticleResponse updateArticle(final Long idArticle, final String newText) {
        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new ArachniException(
                    ArachniError.ARTICLE_NOT_FOUND,
                    "id_article: " + idArticle
            );
        }

        Article article = articleOptional.get();
        article.setText(newText);
        articleRepository.save(article);
        return new ArticleResponse(article);
    }

    @Transactional()
    public void deleteArticle(final Long idArticle) {
        articleRepository.deleteById(idArticle);
    }
}
