package ru.mai.arachni.service.stub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mai.arachni.dto.request.stub.StubRequest;
import ru.mai.arachni.dto.response.stub.StubResponse;
import ru.mai.arachni.repository.ArticleRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class StubServiceTest {
    private static final String NAME = "Ivan";
    private static final String HELLO_PATTERN = "Hello, %s!";

    private StubService stubService;

    private StubRequest stubRequest;
    private StubResponse expectedStubResponse;

    @BeforeEach
    void setup() {
        stubRequest = new StubRequest(NAME);
        expectedStubResponse = new StubResponse(HELLO_PATTERN.formatted(NAME));
        stubService = new StubService(HELLO_PATTERN, mock(ArticleRepository.class));
    }

    @Test
    void testHelloForGet() {
        StubResponse actualStubResponse = stubService.helloForGet(NAME);
        assertEquals(expectedStubResponse, actualStubResponse);
    }

    @Test
    void testHelloForPost() {
        StubResponse actualStubResponse = stubService.helloForPost(stubRequest);
        assertEquals(expectedStubResponse, actualStubResponse);
    }
}
