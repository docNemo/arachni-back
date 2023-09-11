//package ru.mai.arachni.article.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Sort;
//import ru.mai.arachni.article.converter.ArticleConverter;
//import ru.mai.arachni.article.dto.request.article.CreateArticleRequest;
//import ru.mai.arachni.article.dto.request.article.SortingParameter;
//import ru.mai.arachni.article.dto.request.article.UpdateArticleRequest;
//import ru.mai.arachni.article.dto.response.article.ArticleListResponse;
//import ru.mai.arachni.article.dto.response.article.ArticlePreviewResponse;
//import ru.mai.arachni.article.dto.response.article.ArticleResponse;
//import ru.mai.arachni.article.exception.ArachniError;
//import ru.mai.arachni.article.exception.ArachniException;
//import ru.mai.arachni.core.domain.Article;
//import ru.mai.arachni.core.domain.Category;
//import ru.mai.arachni.core.domain.Creator;
//import ru.mai.arachni.core.repository.ArticleRepository;
//import ru.mai.arachni.core.repository.CategoryRepository;
//import ru.mai.arachni.core.repository.CreatorRepository;
//import ru.mai.arachni.core.repository.pagerequest.OffsetBasedPageRequest;
//
//import java.time.ZonedDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class ArticleServiceTest {
//    private static final Long ID_ARTICLE_1 = 10L;
//    private static final Long ID_ARTICLE_2 = 11L;
//    private static final Long ID_CREATOR = 5L;
//    private static final String STR_CREATOR_1 = "Автор";
//    private static final String STR_CREATOR_2 = "Другой автор";
//    private static final String TITLE_1 = "Название";
//    private static final String TITLE_2 = "Другое название";
//    private static final String TEXT = "Это какой-то случайный текст";
//    private static final List<Long> ID_CATEGORIES = List.of(0L, 1L);
//    private static final List<String> STR_CATEGORIES_1 = List.of("Категория 1", "Категория 2");
//    private static final List<String> STR_CATEGORIES_2 = List.of("Категория 2", "Категория 3");
//    private static final ZonedDateTime DATE_TIME = ZonedDateTime.now();
//
//    private ArticleRepository articleRepository;
//    private CreatorRepository creatorRepository;
//    private CategoryRepository categoryRepository;
//    private ArticleService articleService;
//    private Article article;
//    private Creator creator;
//    private List<Category> categories;
//    private Article articleWithoutCreator;
//    private Article articleWithoutCategories;
//    private Creator creatorWithId;
//    private Creator creatorWithoutId;
//    private List<String> sameStrCategories;
//    private List<String> differentStrCategories;
//    private List<Category> sameCategories;
//    private List<Category> differentCategories;
//    private Category categoryWithId;
//    private Category categoryWithoutId;
//    private Page<Article> pageArticles;
//    private UpdateArticleRequest updateArticleRequest;
//    private CreateArticleRequest createArticleRequest;
//    private Category expectedCategoryWithId;
//    private Category expectedCategoryWithoutId;
//    private ArticleResponse expectedArticleResponse;
//    private ArticleListResponse expectedArticleListResponse;
//    private ArachniException expectedArticleNotFoundException;
//    private Article expectedArticleWithSameCategories;
//    private Article expectedArticleWithDifferentCategories;
//    private Article expectedArticleWithCreatorWithId;
//    private Article expectedArticleWithCreatorWithoutId;
//
//    private void initArticleService() {
//        articleRepository = mock(ArticleRepository.class);
//        creatorRepository = mock(CreatorRepository.class);
//        categoryRepository = mock(CategoryRepository.class);
//        articleService = new ArticleService(
//                new ArticleConverter(),
//                articleRepository,
//                creatorRepository,
//                categoryRepository
//        );
//    }
//
//    private void initRequests() {
//        updateArticleRequest = new UpdateArticleRequest(
//                TITLE_1,
//                STR_CATEGORIES_1,
//                TEXT
//        );
//
//        createArticleRequest = new CreateArticleRequest(
//                TITLE_1,
//                STR_CATEGORIES_1,
//                TEXT,
//                STR_CREATOR_1
//        );
//    }
//
//    private void initArticles() {
//        article = new Article(
//                ID_ARTICLE_1,
//                TITLE_1,
//                categories,
//                creator,
//                DATE_TIME,
//                TEXT
//        );
//        articleWithoutCreator = new Article();
//        articleWithoutCreator.setIdArticle(ID_ARTICLE_1);
//        articleWithoutCreator.setTitle(TITLE_1);
//        articleWithoutCreator.setCategories(categories);
//        articleWithoutCreator.setCreationDate(DATE_TIME);
//        articleWithoutCreator.setText(TEXT);
//        articleWithoutCategories = new Article();
//        articleWithoutCategories.setIdArticle(ID_ARTICLE_1);
//        articleWithoutCategories.setTitle(TITLE_1);
//        articleWithoutCategories.setCreator(creator);
//        articleWithoutCategories.setCreationDate(DATE_TIME);
//        articleWithoutCategories.setText(TEXT);
//    }
//
//    private void initPageArticles() {
//        final List<Long> otherIdCategories = List.of(1L, 2L);
//        final Long otherIdCreator = 6L;
//        final String otherText = "Ещё один случайный текст";
//        pageArticles = new PageImpl<>(List.of(
//                new Article(
//                        ID_ARTICLE_1,
//                        TITLE_1,
//                        List.of(
//                                new Category(ID_CATEGORIES.get(0), STR_CATEGORIES_1.get(0)),
//                                new Category(ID_CATEGORIES.get(1), STR_CATEGORIES_1.get(1))
//                        ),
//                        new Creator(ID_CREATOR, STR_CREATOR_1),
//                        DATE_TIME,
//                        TEXT
//                ),
//                new Article(
//                        ID_ARTICLE_2,
//                        TITLE_2,
//                        List.of(
//                                new Category(otherIdCategories.get(0), STR_CATEGORIES_2.get(0)),
//                                new Category(otherIdCategories.get(1), STR_CATEGORIES_2.get(1))
//                        ),
//                        new Creator(otherIdCreator, STR_CREATOR_2),
//                        DATE_TIME,
//                        otherText
//                )
//        ));
//    }
//
//    private void initCategories() {
//        categoryWithId = new Category(ID_CATEGORIES.get(0), STR_CATEGORIES_1.get(0));
//        categoryWithoutId = new Category(null, STR_CATEGORIES_1.get(0));
//        sameStrCategories = List.of(STR_CATEGORIES_1.get(0), STR_CATEGORIES_1.get(0));
//        differentStrCategories = List.of(STR_CATEGORIES_1.get(0), STR_CATEGORIES_1.get(1));
//        categories = List.of(
//                new Category(ID_CATEGORIES.get(0), STR_CATEGORIES_1.get(0)),
//                new Category(ID_CATEGORIES.get(1), STR_CATEGORIES_1.get(1))
//        );
//        sameCategories = List.of(
//                new Category(null, STR_CATEGORIES_1.get(0))
//        );
//        differentCategories = List.of(
//                new Category(null, STR_CATEGORIES_1.get(0)),
//                new Category(null, STR_CATEGORIES_1.get(1))
//        );
//    }
//
//    private void initCreators() {
//        creatorWithId = new Creator(ID_CREATOR, STR_CREATOR_1);
//        creatorWithoutId = new Creator(null, STR_CREATOR_1);
//        creator = new Creator(ID_CREATOR, STR_CREATOR_1);
//    }
//
//    private void initExpectedArticles() {
//        expectedArticleWithCreatorWithId = new Article(
//                ID_ARTICLE_1,
//                TITLE_1,
//                categories,
//                creatorWithId,
//                DATE_TIME,
//                TEXT
//        );
//        expectedArticleWithCreatorWithoutId = new Article(
//                ID_ARTICLE_1,
//                TITLE_1,
//                categories,
//                creatorWithoutId,
//                DATE_TIME,
//                TEXT
//        );
//        expectedArticleWithSameCategories = new Article(
//                ID_ARTICLE_1,
//                TITLE_1,
//                sameCategories,
//                creator,
//                DATE_TIME,
//                TEXT
//        );
//        expectedArticleWithDifferentCategories = new Article(
//                ID_ARTICLE_1,
//                TITLE_1,
//                differentCategories,
//                creator,
//                DATE_TIME,
//                TEXT
//        );
//    }
//
//    private void initExpectedResponses() {
//        expectedArticleResponse = new ArticleResponse(
//                ID_ARTICLE_1,
//                TITLE_1,
//                STR_CATEGORIES_1,
//                STR_CREATOR_1,
//                DATE_TIME,
//                TEXT
//        );
//
//        List<ArticlePreviewResponse> articlePreviewResponseList
//                = List.of(
//                new ArticlePreviewResponse(
//                        ID_ARTICLE_1,
//                        TITLE_1,
//                        STR_CATEGORIES_1,
//                        STR_CREATOR_1,
//                        DATE_TIME
//                ),
//                new ArticlePreviewResponse(
//                        ID_ARTICLE_2,
//                        TITLE_2,
//                        STR_CATEGORIES_2,
//                        STR_CREATOR_2,
//                        DATE_TIME
//                )
//        );
//
//        expectedArticleListResponse = new ArticleListResponse(
//                articlePreviewResponseList,
//                (long) articlePreviewResponseList.size()
//        );
//    }
//
//    private void initExpectedCategories() {
//        expectedCategoryWithId = new Category(ID_CATEGORIES.get(0), STR_CATEGORIES_1.get(0));
//        expectedCategoryWithoutId = new Category(null, STR_CATEGORIES_1.get(0));
//    }
//
//    private void initExpectedExceptions() {
//        expectedArticleNotFoundException = new ArachniException(
//                ArachniError.ARTICLE_NOT_FOUND,
//                "id_article: %s".formatted(ID_ARTICLE_1)
//        );
//    }
//
//
//    @BeforeEach
//    void setup() {
//        initArticleService();
//        initRequests();
//        initCategories();
//        initCreators();
//        initArticles();
//        initPageArticles();
//
//        initExpectedCategories();
//        initExpectedArticles();
//        initExpectedResponses();
//        initExpectedExceptions();
//    }
//
//    @Test
//    void testSetCreatorWithIdToArticle() {
//        when(creatorRepository.findOneCreatorsByCreator(STR_CREATOR_1))
//                .thenReturn(Optional.of(creatorWithId));
//        articleService.setCreatorToArticle(articleWithoutCreator, STR_CREATOR_1);
//        assertEquals(expectedArticleWithCreatorWithId, articleWithoutCreator);
//        verify(creatorRepository, times(1))
//                .findOneCreatorsByCreator(STR_CREATOR_1);
//    }
//
//    @Test
//    void testSetCreatorWithoutIdToArticle() {
//        when(creatorRepository.findOneCreatorsByCreator(STR_CREATOR_1))
//                .thenReturn(Optional.of(creatorWithoutId));
//        articleService.setCreatorToArticle(articleWithoutCreator, STR_CREATOR_1);
//        assertEquals(expectedArticleWithCreatorWithoutId, articleWithoutCreator);
//        verify(creatorRepository, times(1))
//                .findOneCreatorsByCreator(STR_CREATOR_1);
//    }
//
//    @Test
//    void testSetSameCategoriesToArticle() {
//        articleService.setCategoriesToArticle(articleWithoutCategories, sameStrCategories);
//        assertEquals(expectedArticleWithSameCategories, articleWithoutCategories);
//    }
//
//    @Test
//    void testSetDifferentCategoriesToArticle() {
//        articleService.setCategoriesToArticle(articleWithoutCategories, differentStrCategories);
//        assertEquals(expectedArticleWithDifferentCategories, articleWithoutCategories);
//    }
//
//    @Test
//    void testChooseCategoryWithId() {
//        when(categoryRepository.findOneCategoryByCategory(STR_CATEGORIES_1.get(0)))
//                .thenReturn(Optional.of(categoryWithId));
//        Category actualCategory = articleService.chooseCategory(STR_CATEGORIES_1.get(0));
//        assertEquals(expectedCategoryWithId, actualCategory);
//        verify(categoryRepository, times(1))
//                .findOneCategoryByCategory(STR_CATEGORIES_1.get(0));
//    }
//
//    @Test
//    void testChooseCategoryWithoutId() {
//        when(categoryRepository.findOneCategoryByCategory(STR_CATEGORIES_1.get(0)))
//                .thenReturn(Optional.of(categoryWithoutId));
//        Category actualCategory = articleService.chooseCategory(STR_CATEGORIES_1.get(0));
//        assertEquals(expectedCategoryWithoutId, actualCategory);
//        verify(categoryRepository, times(1))
//                .findOneCategoryByCategory(STR_CATEGORIES_1.get(0));
//    }
//
//    @Test
//    void testCreateArticle() {
//        when(articleRepository.save(any(Article.class))).thenReturn(article);
//        ArticleResponse actualArticleResponse
//                = articleService.createArticle(createArticleRequest);
//        assertEquals(expectedArticleResponse, actualArticleResponse);
//        verify(articleRepository, times(1)).save(any(Article.class));
//    }
//
//    @Test
//    void testUpdateArticle() {
//        when(articleRepository.findById(ID_ARTICLE_1)).thenReturn(Optional.of(article));
//        when(articleRepository.save(any(Article.class))).thenReturn(article);
//        ArticleResponse actualArticleResponse
//                = articleService.updateArticle(ID_ARTICLE_1, updateArticleRequest);
//        assertEquals(expectedArticleResponse, actualArticleResponse);
//        verify(articleRepository, times(1)).save(any(Article.class));
//    }
//
//    @Test
//    void testUpdateArticleArachniException() {
//        when(articleRepository.findById(ID_ARTICLE_1)).thenReturn(Optional.empty());
//        ArachniException actualArachniException = assertThrows(
//                ArachniException.class,
//                () -> articleService.updateArticle(ID_ARTICLE_1, updateArticleRequest)
//        );
//        assertEquals(expectedArticleNotFoundException, actualArachniException);
//        verify(articleRepository, times(1)).findById(ID_ARTICLE_1);
//    }
//
//    @Test
//    void testGetArticlePreviewList() {
//        final String searchString = "другой";
//        final Integer skip = 0;
//        final Integer limit = 25;
//        when(articleRepository.findByTitleContainingIgnoreCase(
//                eq(searchString),
//                any(OffsetBasedPageRequest.class)
//        )).thenReturn(pageArticles);
//        ArticleListResponse actualArticleListResponse = articleService.getArticlePreviewList(
//                searchString,
//                skip,
//                limit,
//                Sort.Direction.DESC,
//                SortingParameter.DATE
//        );
//        assertEquals(expectedArticleListResponse, actualArticleListResponse);
//        verify(articleRepository, times(1))
//                .findByTitleContainingIgnoreCase(
//                        eq(searchString),
//                        any(OffsetBasedPageRequest.class)
//                );
//    }
//
//    @Test
//    void testGetArticle() {
//        when(articleRepository.findById(ID_ARTICLE_1)).thenReturn(Optional.of(article));
//        ArticleResponse actualArticleResponse = articleService.getArticle(ID_ARTICLE_1);
//        assertEquals(expectedArticleResponse, actualArticleResponse);
//        verify(articleRepository, times(1)).findById(ID_ARTICLE_1);
//    }
//
//    @Test
//    void testGetArticleArachniException() {
//        when(articleRepository.findById(ID_ARTICLE_1)).thenReturn(Optional.empty());
//        ArachniException actualArachniException = assertThrows(
//                ArachniException.class,
//                () -> articleService.getArticle(ID_ARTICLE_1)
//        );
//        assertEquals(expectedArticleNotFoundException, actualArachniException);
//        verify(articleRepository, times(1)).findById(ID_ARTICLE_1);
//    }
//
//    @Test
//    void testDeleteArticle() {
//        articleService.deleteArticle(ID_ARTICLE_1);
//        verify(articleRepository, times(1)).deleteById(ID_ARTICLE_1);
//    }
//}
