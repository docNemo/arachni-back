package ru.mai.arachni.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.mai.arachni.exception.ArachniErrorRepresentation;
import ru.mai.arachni.exception.ArachniException;

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
