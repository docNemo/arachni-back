package ru.mai.arachni.service.stub;

import lombok.RequiredArgsConstructor;
import ru.mai.arachni.dto.request.stub.StubRequest;
import ru.mai.arachni.dto.response.stub.StubResponse;

@RequiredArgsConstructor
public class StubService {
    private final String helloPattern;

    public StubResponse helloForGet(final String name) {
        return new StubResponse(helloPattern.formatted(name));
    }

    public StubResponse helloForPost(final StubRequest stubRequest) {
        return new StubResponse(helloPattern.formatted(stubRequest.getName()));
    }
}
