package ru.mai.arachni.article.handler;

import ru.mai.arachni.article.exception.ArachniError;
import ru.mai.arachni.article.exception.ArachniException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.mai.arachni.article.exception.ArachniErrorRepresentation;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalRuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ArachniErrorRepresentation> handleRuntimeException(
            final RuntimeException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(ArachniError.UNKNOWN_ERROR.getStatusCode())
                .body(
                        new ArachniErrorRepresentation(
                                ArachniError.UNKNOWN_ERROR.name(),
                                ArachniError.UNKNOWN_ERROR.getErrorMessage()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ArachniErrorRepresentation> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(ArachniError.INVALID_PARAMETER.getStatusCode())
                .body(
                        new ArachniErrorRepresentation(
                                ArachniError.INVALID_PARAMETER.name(),
                                "%s: %s".formatted(
                                        ArachniError.INVALID_PARAMETER.getErrorMessage(),
                                        e.getMessage()
                                )
                        )
                );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ArachniErrorRepresentation> handleDataIntegrityViolationException(
            final DataIntegrityViolationException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(ArachniError.DUPLICATE_ARTICLE_ATTRIBUTE.getStatusCode())
                .body(
                        new ArachniErrorRepresentation(
                                ArachniError.DUPLICATE_ARTICLE_ATTRIBUTE.name(),
                                ArachniError.DUPLICATE_ARTICLE_ATTRIBUTE.getErrorMessage()
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
                .status(ArachniError.INVALID_JSON_PARAMETERS.getStatusCode())
                .body(
                        new ArachniErrorRepresentation(
                                ArachniError.INVALID_JSON_PARAMETERS.name(),
                                "%s: %s".formatted(
                                        ArachniError.INVALID_JSON_PARAMETERS.getErrorMessage(),
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
                .status(ArachniError.INVALID_HTTP_MESSAGE.getStatusCode())
                .body(
                        new ArachniErrorRepresentation(
                                ArachniError.INVALID_HTTP_MESSAGE.name(),
                                "%s: %s".formatted(
                                        ArachniError.INVALID_HTTP_MESSAGE.getErrorMessage(),
                                        e.getMessage()
                                )
                        )
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ArachniErrorRepresentation> handleIllegalArgumentException(
            final IllegalArgumentException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(ArachniError.INVALID_PAGINATION_PARAMETER.getStatusCode())
                .body(
                        new ArachniErrorRepresentation(
                                ArachniError.INVALID_PAGINATION_PARAMETER.name(),
                                "%s: %s".formatted(
                                        ArachniError.INVALID_PAGINATION_PARAMETER.getErrorMessage(),
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
