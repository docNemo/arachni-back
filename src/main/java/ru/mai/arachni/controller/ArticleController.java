package ru.mai.arachni.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.arachni.dto.response.ArticleResponse;
import ru.mai.arachni.service.ArticleService;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/{idArticle}")
    public ArticleResponse getArticle(@PathVariable Long idArticle) {
        return articleService.getArticle(idArticle);
    }

    @DeleteMapping("/{idArticle}")
    public void deleteArticle(@PathVariable Long idArticle) {
        articleService.deleteArticle(idArticle);
    }
}
