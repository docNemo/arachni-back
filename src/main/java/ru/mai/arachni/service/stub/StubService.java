package ru.mai.arachni.service.stub;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.mai.arachni.domain.Article;
import ru.mai.arachni.dto.response.stub.StubArticleResponse;
import ru.mai.arachni.exception.ArachniError;
import ru.mai.arachni.exception.ArachniException;
import ru.mai.arachni.repository.ArticleRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StubService {
    private final ArticleRepository articleRepository;

    @Transactional()
    public void createArticle(final String title, final String text) {
        Article article = new Article();
        article.setTitle(title);
        article.setCreator("creator");
        article.setCategories(List.of("Catego"));
        article.setCreationDate(ZonedDateTime.now());
        article.setText(text);

        articleRepository.save(article);
    }

    @Transactional()
    public StubArticleResponse updateArticle(final Long idArticle, final String newText) {
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
        return new StubArticleResponse(article);
    }

}
