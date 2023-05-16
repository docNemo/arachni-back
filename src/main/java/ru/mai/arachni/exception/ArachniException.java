package ru.mai.arachni.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ArachniException extends RuntimeException {
    private final ArachniError error;
    private final String extraInformation;

    @Override
    public String getMessage() {
        return "%s: %s: %s".formatted(error, error.getErrorMessage(), extraInformation);
    }
}
