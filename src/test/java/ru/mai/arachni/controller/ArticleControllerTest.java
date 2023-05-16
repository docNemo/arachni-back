package ru.mai.arachni.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.mai.arachni.service.ArticleService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArticleController.class)
public class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;
    private static final long ID = 1234567890;

    @Test
    public void testDeleteArticle() throws Exception {
        mockMvc
                .perform(delete("/article/" + ID))
                .andExpect(status().isOk());
    }
}
