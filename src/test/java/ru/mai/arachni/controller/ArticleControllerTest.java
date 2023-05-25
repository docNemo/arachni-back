package ru.mai.arachni.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.mai.arachni.dto.request.CreateArticleRequest;
import ru.mai.arachni.dto.request.SortingParameter;
import ru.mai.arachni.dto.request.UpdateArticleRequest;
import ru.mai.arachni.dto.response.ArticleListResponse;
import ru.mai.arachni.dto.response.ArticlePreviewResponse;
import ru.mai.arachni.dto.response.ArticleResponse;
import ru.mai.arachni.service.ArticleService;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArticleController.class)
public class ArticleControllerTest {
    private final Long ID_ARTICLE = 10L;
    private final String STR_CREATOR = "Автор";
    private final String TITLE = "Название";
    private final String TEXT = "Это какой-то случайный текст";
    private final List<String> STR_CATEGORIES = List.of("Категория 1", "Категория 2");
    private final ZonedDateTime DATE_TIME = ZonedDateTime.now();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ArticleService articleService;
    private UpdateArticleRequest updateArticleRequest;
    private CreateArticleRequest createArticleRequest;
    private ObjectMapper objectMapper;
    private ArticleResponse expectedArticleResponse;
    private ArticleListResponse expectedArticleListResponse;

    private void initMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSz"
        ));
    }

    private void initRequests() {
        updateArticleRequest = new UpdateArticleRequest(
                TITLE,
                STR_CATEGORIES,
                TEXT
        );

        createArticleRequest = new CreateArticleRequest(
                TITLE,
                STR_CATEGORIES,
                TEXT,
                STR_CREATOR
        );
    }

    private void initExpectedResponses() {
        expectedArticleResponse = new ArticleResponse(
                ID_ARTICLE,
                TITLE,
                STR_CATEGORIES,
                STR_CREATOR,
                DATE_TIME,
                TEXT
        );

        Long otherIdArticle = 11L;
        String otherStrCreator = "Другой автор";
        String otherTitle = "Другое название";
        List<String> otherStrCategories = List.of("Категория 2", "Категория 3");
        List<ArticlePreviewResponse> articlePreviewResponseList
                = List.of(
                new ArticlePreviewResponse(
                        ID_ARTICLE,
                        TITLE,
                        STR_CATEGORIES,
                        STR_CREATOR,
                        DATE_TIME
                ),
                new ArticlePreviewResponse(
                        otherIdArticle,
                        otherTitle,
                        otherStrCategories,
                        otherStrCreator,
                        DATE_TIME
                )
        );

        expectedArticleListResponse = new ArticleListResponse(
                articlePreviewResponseList,
                (long) articlePreviewResponseList.size()
        );
    }


    @BeforeEach
    void setup() {
        initMapper();
        initRequests();
        initExpectedResponses();
    }


    @Test
    void testGetArticle() throws Exception {
        when(articleService.getArticle(ID_ARTICLE)).thenReturn(expectedArticleResponse);
        mockMvc
                .perform(get("/article/%s".formatted(ID_ARTICLE)))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(expectedArticleResponse)
                ));
    }

    @Test
    void testGetArticleListResponse() throws Exception {
        when(articleService.getArticlePreviewList(
                "",
                0,
                25,
                Sort.Direction.DESC,
                SortingParameter.DATE
        )).thenReturn(expectedArticleListResponse);
        mockMvc
                .perform(get("/article/list"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(expectedArticleListResponse)
                ));
    }

    @Test
    void testCreateArticle() throws Exception {
        when(articleService.createArticle(createArticleRequest))
                .thenReturn(expectedArticleResponse);
        mockMvc
                .perform(post("/article")
                        .content(objectMapper.writeValueAsString(createArticleRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(expectedArticleResponse))
                );
    }

    @Test
    void testUpdateArticle() throws Exception {
        when(articleService.updateArticle(ID_ARTICLE, updateArticleRequest))
                .thenReturn(expectedArticleResponse);
        mockMvc
                .perform(put("/article/%s".formatted(ID_ARTICLE))
                        .content(objectMapper.writeValueAsString(updateArticleRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(expectedArticleResponse))
                );
    }

    @Test
    void testDeleteArticle() throws Exception {
        mockMvc
                .perform(delete("/article/%s".formatted(ID_ARTICLE)))
                .andExpect(status().isOk());
    }
}
