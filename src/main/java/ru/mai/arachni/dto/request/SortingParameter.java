package ru.mai.arachni.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SortingParameter {
    DATE("creationDate"),
    TITLE("title"),
    CREATOR("creator");

    private final String propertyName;
}
