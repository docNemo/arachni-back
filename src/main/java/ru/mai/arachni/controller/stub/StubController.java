package ru.mai.arachni.controller.stub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.arachni.dto.response.stub.ArticleResponse;
import ru.mai.arachni.service.stub.StubService;

@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class StubController {
    private final StubService stubService;

//    @GetMapping("/get")
//    public StubResponse helloGet(@RequestParam String name) {
//        return stubService.helloForGet(name);
//    }
//
//    @GetMapping("/get/{name}")
//    public StubResponse helloGetWithPathVariable(@PathVariable String name) {
//        return stubService.helloForGet(name);
//    }
//
//    @PostMapping("/post")
//    public StubResponse helloPost(@RequestBody StubRequest stubRequest) {
//        return stubService.helloForPost(stubRequest);
//    }

    @PostMapping("/article")
    public void createArticle(@RequestParam String title, @RequestParam String text) {
        stubService.createArticle(title, text);
    }

    @GetMapping("/article/{idArticle}")
    public ArticleResponse getArticle(@PathVariable Long idArticle) {
        return stubService.getArticle(idArticle);
    }

    @PutMapping("/article/{idArticle}")
    public ArticleResponse updateArticle(@PathVariable Long idArticle, final String newText) {
        return stubService.updateArticle(idArticle, newText);
    }

    @DeleteMapping("/article/{idArticle}")
    public void deleteArticle(@PathVariable Long idArticle) {
        stubService.deleteArticle(idArticle);
    }

}
