package ru.mai.arachni.article.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import ru.mai.arachni.article.dto.request.article.ArticleListRequest;
import ru.mai.arachni.article.dto.request.article.ArticleSortingParameter;
import ru.mai.arachni.article.dto.response.PaginationResponse;
import ru.mai.arachni.core.domain.Creator;
import ru.mai.arachni.core.repository.CreatorRepository;
import ru.mai.arachni.core.repository.pagerequest.OffsetBasedPageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreatorServiceTest {
    private static final Long ID_CREATOR_1 = 10L;
    private static final Long ID_CREATOR_2 = 20L;
    private static final String CREATOR_1 = "creator1";
    private static final String CREATOR_2 = "creator2";

    private CreatorRepository creatorRepository;
    private CreatorService creatorService;

    private Page<Creator> creatorPage;
    private PaginationResponse<String> expectedCreatorListResponse;

    private void initCreatorService() {
        creatorRepository = mock(CreatorRepository.class);
        creatorService = new CreatorService(
                creatorRepository
        );
    }

    private void initCreatorPage() {
        creatorPage = new PageImpl<>(List.of(
                new Creator(
                        ID_CREATOR_1,
                        CREATOR_1
                ),
                new Creator(
                        ID_CREATOR_2,
                        CREATOR_2
                )
        ));
    }

    private void initExpectedResponses() {

        List<String> creatorList
                = List.of(
                CREATOR_1,
                CREATOR_2
        );

        expectedCreatorListResponse = new PaginationResponse<>(
                creatorList,
                (long) creatorList.size()
        );
    }


    @BeforeEach
    void setup() {
        initCreatorService();
        initCreatorPage();
        initExpectedResponses();
    }

    @Test
    void testGetArticlePreviewList() {
        final String searchString = "другой";
        final Integer skip = 0;
        final Integer limit = 25;

        when(creatorRepository.findByCreatorContainingIgnoreCase(
                eq(searchString),
                any(OffsetBasedPageRequest.class)
        )).thenReturn(creatorPage);

        ArticleListRequest request = ArticleListRequest
                .builder()
                .searchString(searchString)
                .skip(skip)
                .limit(limit)
                .order(Sort.Direction.DESC)
                .sortBy(ArticleSortingParameter.DATE)
                .build();

        PaginationResponse<String> actualCreatorListResponse = creatorService
                .getCreatorList(request);

        assertEquals(expectedCreatorListResponse, actualCreatorListResponse);
        verify(creatorRepository, times(1))
                .findByCreatorContainingIgnoreCase(
                        eq(searchString),
                        any(OffsetBasedPageRequest.class)
                );
    }
}
