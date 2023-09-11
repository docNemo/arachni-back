package ru.mai.arachni.article.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.mai.arachni.article.dto.request.PaginationRequest;
import ru.mai.arachni.article.dto.response.PaginationResponse;
import ru.mai.arachni.article.service.CategoryService;
import ru.mai.arachni.configuration.CategoryConfiguration;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
@ContextConfiguration(classes = {CategoryController.class, CategoryConfiguration.class})
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    private ObjectMapper objectMapper;
    private PaginationResponse<String> expectedCategoryListResponse;

    private void initMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSz"
        ));
    }

    private void initExpectedResponses() {
        final List<String> categoryList
                = List.of(
                "Category1",
                "Category2"
        );

        expectedCategoryListResponse = new PaginationResponse<>(
                categoryList,
                (long) categoryList.size()
        );
    }


    @BeforeEach
    void setup() {
        initMapper();
        initExpectedResponses();
    }

    @Test
    void testGetCreatorListResponse() throws Exception {
        final String searchString = "";
        final Integer skip = 0;
        final Integer limit = 25;
        PaginationRequest request = PaginationRequest
                .builder()
                .searchString(searchString)
                .skip(skip)
                .limit(limit)
                .order(Sort.Direction.DESC)
                .build();
        when(categoryService.getCategoryList(request))
                .thenReturn(expectedCategoryListResponse);
        mockMvc
                .perform(get("/category/list"))
                .andExpect(status().isOk())
                .andExpect(
                        content().json(
                                objectMapper.writeValueAsString(expectedCategoryListResponse)
                        )
                );
    }
}
