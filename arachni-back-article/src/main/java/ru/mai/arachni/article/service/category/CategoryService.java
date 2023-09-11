package ru.mai.arachni.article.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.mai.arachni.article.dto.request.PaginationRequest;
import ru.mai.arachni.article.dto.response.PaginationResponse;
import ru.mai.arachni.core.domain.Category;
import ru.mai.arachni.core.repository.CategoryRepository;
import ru.mai.arachni.core.repository.pagerequest.OffsetBasedPageRequest;

import java.util.List;

@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public PaginationResponse<String> getCategories(
            PaginationRequest paginationRequest
    ) {
        Page<Category> categoryPage = categoryRepository.findByCategoryContainingIgnoreCase(
                paginationRequest.getSearchString(),
                new OffsetBasedPageRequest(
                        paginationRequest.getSkip(),
                        paginationRequest.getLimit(),
                        Sort.by(
                                paginationRequest.getOrder(),
                                "category"
                        )
                )
        );

        List<String> categoryResponses = categoryPage
                .getContent()
                .stream()
                .map(Category::getCategory)
                .toList();

        return new PaginationResponse<>(
                categoryResponses,
                categoryPage.getTotalElements()
        );
    }
}
