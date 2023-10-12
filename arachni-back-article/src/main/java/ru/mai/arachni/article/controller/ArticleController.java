package ru.mai.arachni.article.controller;

import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.arachni.article.dto.request.article.ArticleListRequest;
import ru.mai.arachni.article.dto.request.article.CreateArticleRequest;
import ru.mai.arachni.article.dto.request.article.ArticleSortingParameter;
import ru.mai.arachni.article.dto.request.article.UpdateArticleRequest;
import ru.mai.arachni.article.dto.response.PaginationResponse;
import ru.mai.arachni.article.dto.response.article.ArticlePreviewResponse;
import ru.mai.arachni.article.dto.response.article.ArticleResponse;
import ru.mai.arachni.article.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;


    @PutMapping("/{idArticle}")
    public ArticleResponse updateArticle(
            @PathVariable Long idArticle,
            @RequestBody @Valid UpdateArticleRequest updateArticleRequest
    ) {
        return articleService.updateArticle(idArticle, updateArticleRequest);
    }

    @GetMapping("/{idArticle}")
    public ArticleResponse getArticle(@PathVariable Long idArticle) {
        return articleService.getArticle(idArticle);
    }

    @DeleteMapping("/{idArticle}")
    public void deleteArticle(@PathVariable Long idArticle) {
        articleService.deleteArticle(idArticle);
    }

    @GetMapping("/list")
    public PaginationResponse<ArticlePreviewResponse> getArticlePreviewList(
            @RequestParam(defaultValue = "") String searchString,
            @RequestParam(defaultValue = "0") Integer skip,
            @RequestParam(defaultValue = "25") Integer limit,
            @RequestParam(defaultValue = "DESC") Sort.Direction order,
            @RequestParam(defaultValue = "DATE") ArticleSortingParameter sortBy,
            @RequestParam(required = false) String creator,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) ZonedDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) ZonedDateTime finishDate
    ) {

        ArticleListRequest articleListRequest = ArticleListRequest.builder()
                .searchString(searchString)
                .skip(skip)
                .limit(limit)
                .order(order)
                .sortBy(sortBy)
                .creator(creator)
                .categories(categories)
                .startDate(startDate)
                .finishDate(finishDate)
                .build();

        return articleService.getArticlePreviewList(
                articleListRequest
        );
    }

    @PostMapping
    public ArticleResponse createArticle(
            @RequestBody @Valid CreateArticleRequest createArticleRequest
    ) {
        return articleService.createArticle(createArticleRequest);
    }
}
