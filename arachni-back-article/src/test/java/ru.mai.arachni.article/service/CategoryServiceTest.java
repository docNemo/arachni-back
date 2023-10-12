package ru.mai.arachni.article.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import ru.mai.arachni.article.dto.request.article.ArticleListRequest;
import ru.mai.arachni.article.dto.request.article.ArticleSortingParameter;
import ru.mai.arachni.article.dto.response.PaginationResponse;
import ru.mai.arachni.core.domain.Category;
import ru.mai.arachni.core.repository.CategoryRepository;
import ru.mai.arachni.core.repository.pagerequest.OffsetBasedPageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {
    private static final Long ID_CATEGORY_1 = 10L;
    private static final Long ID_CATEGORY_2 = 20L;
    private static final String CATEGORY_1 = "category1";
    private static final String CATEGORY_2 = "category2";

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    private Page<Category> categoryPage;
    private PaginationResponse<String> expectedCategoryListResponse;

    private void initCategoryService() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(
                categoryRepository
        );
    }

    private void initCategoryPage() {
        categoryPage = new PageImpl<>(List.of(
                new Category(
                        ID_CATEGORY_1,
                        CATEGORY_1
                ),
                new Category(
                        ID_CATEGORY_2,
                        CATEGORY_2
                )
        ));
    }

    private void initExpectedResponses() {

        List<String> categoryList
                = List.of(
                CATEGORY_1,
                CATEGORY_2
        );

        expectedCategoryListResponse = new PaginationResponse<>(
                categoryList,
                (long) categoryList.size()
        );
    }


    @BeforeEach
    void setup() {
        initCategoryService();
        initCategoryPage();
        initExpectedResponses();
    }

    @Test
    void testGetArticlePreviewList() {
        final String searchString = "другой";
        final Integer skip = 0;
        final Integer limit = 25;

        when(categoryRepository.findByCategoryContainingIgnoreCase(
                eq(searchString),
                any(OffsetBasedPageRequest.class)
        )).thenReturn(categoryPage);

        ArticleListRequest request = ArticleListRequest
                .builder()
                .searchString(searchString)
                .skip(skip)
                .limit(limit)
                .order(Sort.Direction.DESC)
                .sortBy(ArticleSortingParameter.DATE)
                .build();

        PaginationResponse<String> actualCategoryListResponse = categoryService
                .getCategoryList(request);

        assertEquals(expectedCategoryListResponse, actualCategoryListResponse);
        verify(categoryRepository, times(1))
                .findByCategoryContainingIgnoreCase(
                        eq(searchString),
                        any(OffsetBasedPageRequest.class)
                );
    }
}
