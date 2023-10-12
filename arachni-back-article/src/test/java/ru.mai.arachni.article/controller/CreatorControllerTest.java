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
import ru.mai.arachni.article.service.CreatorService;
import ru.mai.arachni.configuration.CreatorConfiguration;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CreatorController.class)
@ContextConfiguration(classes = {CreatorController.class, CreatorConfiguration.class})
public class CreatorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CreatorService creatorService;
    private ObjectMapper objectMapper;
    private PaginationResponse<String> expectedCreatorListResponse;

    private void initMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSz"
        ));
    }

    private void initExpectedResponses() {
        final List<String> creatorList
                = List.of(
                "Author1",
                "Author2"
        );

        expectedCreatorListResponse = new PaginationResponse<>(
                creatorList,
                (long) creatorList.size()
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
        when(creatorService.getCreatorList(request))
                .thenReturn(expectedCreatorListResponse);
        mockMvc
                .perform(get("/creator/list"))
                .andExpect(status().isOk())
                .andExpect(
                        content().json(
                                objectMapper.writeValueAsString(expectedCreatorListResponse)
                        )
                );
    }
}
