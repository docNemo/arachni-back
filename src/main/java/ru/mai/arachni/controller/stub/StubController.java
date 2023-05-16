package ru.mai.arachni.controller.stub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.arachni.dto.response.stub.StubArticleResponse;
import ru.mai.arachni.service.stub.StubService;

@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class StubController {
    private final StubService stubService;

    @PostMapping("/article")
    public void createArticle(@RequestParam String title, @RequestParam String text) {
        stubService.createArticle(title, text);
    }

    @PutMapping("/article/{idArticle}")
    public StubArticleResponse updateArticle(@PathVariable Long idArticle, final String newText) {
        return stubService.updateArticle(idArticle, newText);
    }

}
