package ru.mai.arachni.article.exception;

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
    ),
    INVALID_PARAMETER(
            "Получен некорректный параметр",
            HttpStatus.BAD_REQUEST
    ),
    DUPLICATE_ARTICLE_ATTRIBUTE(
            "Статья с таким названием уже существует",
            HttpStatus.BAD_REQUEST
    ),
    EMPTY_OR_NULL_PROPERTIES_ENCOUNTERED(
            "Невозможно сохранить статью с пустыми атрибутами",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_HTTP_MESSAGE(
            "Ошибка в HTTP-сообщении",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_JSON_PARAMETERS(
            "Невозможно принять json-файл с пустыми или NULL параметрами",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_PAGINATION_PARAMETER(
            "Некорректные параметры пагинации",
            HttpStatus.BAD_REQUEST
    );

    private final String errorMessage;
    private final HttpStatus statusCode;
}
