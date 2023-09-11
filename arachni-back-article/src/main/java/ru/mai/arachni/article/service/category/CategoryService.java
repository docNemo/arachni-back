package ru.mai.arachni.article.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.mai.arachni.article.dto.request.category.CategoryListRequest;
import ru.mai.arachni.article.dto.response.category.CategoryListResponse;
import ru.mai.arachni.article.dto.response.category.CategoryResponse;
import ru.mai.arachni.core.domain.Category;
import ru.mai.arachni.core.repository.CategoryRepository;
import ru.mai.arachni.core.repository.pagerequest.OffsetBasedPageRequest;

import java.util.List;

@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryListResponse getCategories(CategoryListRequest creatorListRequest) {
        Page<Category> categoryPage = categoryRepository.findByCategoryContainingIgnoreCase(
                creatorListRequest.getSearchString(),
                new OffsetBasedPageRequest(
                        creatorListRequest.getSkip(),
                        creatorListRequest.getLimit(),
                        Sort.by(
                                creatorListRequest.getOrder(),
                                "creator"
                        )
                )
        );

        List<CategoryResponse> categoryResponses = categoryPage
                .getContent()
                .stream()
                .map(creator -> new CategoryResponse(
                        creator.getIdCategory(), creator.getCategory())
                )
                .toList();

        return new CategoryListResponse(
                categoryResponses,
                categoryPage.getTotalElements()
        );
    }
}