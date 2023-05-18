package ru.mai.arachni.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.arachni.dto.request.Order;
import ru.mai.arachni.dto.request.SortingCriterion;
import ru.mai.arachni.dto.response.ArticleResponse;
import ru.mai.arachni.dto.response.ArticleListResponse;
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

    @GetMapping("/list")
    public ArticleListResponse getArticlePreviewList(
            @RequestParam(defaultValue = "0") Integer skip,
            @RequestParam(defaultValue = "25") Integer limit,
            @RequestParam(defaultValue = "DESC") Order order,
            @RequestParam(defaultValue = "DATE") SortingCriterion sortBy
    ) {
        return articleService.getArticlePreviewList(skip, limit, order, sortBy);
    }
}
