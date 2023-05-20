package ru.mai.arachni.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.mai.arachni.exception.ArachniErrorRepresentation;
import ru.mai.arachni.exception.ArachniException;

import java.util.stream.Collectors;

import static ru.mai.arachni.exception.ArachniError.DUPLICATE_ARTICLE_ATTRIBUTE;
import static ru.mai.arachni.exception.ArachniError.INVALID_JSON_PARAMETERS;
import static ru.mai.arachni.exception.ArachniError.INVALID_HTTP_MESSAGE;
import static ru.mai.arachni.exception.ArachniError.UNKNOWN_ERROR;

@ControllerAdvice
@Slf4j
public class GlobalRuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ArachniErrorRepresentation> handleRuntimeException(
            final RuntimeException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(UNKNOWN_ERROR.getStatusCode())
                .body(
                        new ArachniErrorRepresentation(
                                UNKNOWN_ERROR.name(),
                                UNKNOWN_ERROR.getErrorMessage()
                        )
                );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ArachniErrorRepresentation> handleDataIntegrityViolationException(
            final DataIntegrityViolationException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(DUPLICATE_ARTICLE_ATTRIBUTE.getStatusCode())
                .body(
                        new ArachniErrorRepresentation(
                                DUPLICATE_ARTICLE_ATTRIBUTE.name(),
                                DUPLICATE_ARTICLE_ATTRIBUTE.getErrorMessage()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ArachniErrorRepresentation> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e
    ) {
        LOGGER.error("Handling: ", e);

        String issues = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(INVALID_JSON_PARAMETERS.getStatusCode())
                .body(
                        new ArachniErrorRepresentation(
                                INVALID_JSON_PARAMETERS.name(),
                                "%s: %s".formatted(
                                        INVALID_JSON_PARAMETERS.getErrorMessage(),
                                        issues
                                )
                        )
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ArachniErrorRepresentation> handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(INVALID_HTTP_MESSAGE.getStatusCode())
                .body(
                        new ArachniErrorRepresentation(
                                INVALID_HTTP_MESSAGE.name(),
                                "%s: %s".formatted(
                                        INVALID_HTTP_MESSAGE.getErrorMessage(),
                                        e.getMessage()
                                )
                        )
                );
    }

    @ExceptionHandler(ArachniException.class)
    public ResponseEntity<ArachniErrorRepresentation> handleArachniException(
            final ArachniException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(e.getError().getStatusCode())
                .body(
                        new ArachniErrorRepresentation(
                                e.getError().name(),
                                e.getError().getErrorMessage()
                        )
                );
    }
}
