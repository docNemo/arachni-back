package ru.mai.arachni.article.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.arachni.article.dto.request.PaginationRequest;
import ru.mai.arachni.article.dto.response.PaginationResponse;
import ru.mai.arachni.article.service.CreatorService;

@RestController
@RequestMapping("/creator")
@RequiredArgsConstructor
public class CreatorController {
    private final CreatorService creatorService;

    @GetMapping("/list")
    public PaginationResponse<String> getCreators(
            @RequestParam(defaultValue = "") String searchString,
            @RequestParam(defaultValue = "0") Integer skip,
            @RequestParam(defaultValue = "25") Integer limit,
            @RequestParam(defaultValue = "DESC") Sort.Direction order
    ) {
        PaginationRequest paginationRequest = PaginationRequest
                .builder()
                .searchString(searchString)
                .skip(skip)
                .limit(limit)
                .order(order).build();
        return creatorService.getCreatorList(paginationRequest);
    }
}
