package ru.mai.arachni.service.stub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mai.arachni.dto.request.stub.StubRequest;
import ru.mai.arachni.dto.response.stub.StubResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StubServiceTest {
    private final static String NAME = "Ivan";
    private final static String HELLO_PATTERN = "Hello, %s!";

    private StubService stubService;

    private StubRequest stubRequest;
    private StubResponse expectedStubResponse;

    @BeforeEach
    void setup() {
        stubRequest = new StubRequest(NAME);
        expectedStubResponse = new StubResponse(HELLO_PATTERN.formatted(NAME));
        stubService = new StubService(HELLO_PATTERN);
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
