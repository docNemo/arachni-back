package ru.mai.arachni.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ArachniError {
    UNKNOWN_ERROR(
            "Неизвестная ошибка",
            HttpStatus.BAD_REQUEST
    ),
    ARTICLE_NOT_FOUND(
            "Статья с данным id не существует",
            HttpStatus.BAD_REQUEST
    );

    private final String errorMessage;
    private final HttpStatus statusCode;
}
