package ferramong.toolsmanager.config;

import ferramong.toolsmanager.dto.ErrorResponse;
import ferramong.toolsmanager.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // TODO: Enhance error message returned as response body for the client.

    @ExceptionHandler({
            ToolNotAvailableException.class,
            DwellerNotFoundException.class,
            RentalNotFoundException.class,
            ToolNotFoundException.class,
            WebClientResponseException.NotFound.class
    })
    public ResponseEntity<ErrorResponse> notFound(Exception exception) {
        var responseBody = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        log.error(exception.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(responseBody);
    }

    @ExceptionHandler(NotEnoughCreditoolsException.class)
    public ResponseEntity<ErrorResponse> forbidden(Exception exception) {
        var responseBody = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        log.error(exception.getMessage());
        return ResponseEntity.status(FORBIDDEN).body(responseBody);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            MissingRequestHeaderException.class,
            CannotUpdateRentedToolException.class,
            CannotDeleteRentedToolException.class
    })
    public ResponseEntity<ErrorResponse> badRequest(Exception exception) {
        var responseBody = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        log.error(exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(responseBody);
    }

    @ExceptionHandler({
            InvalidDataAccessApiUsageException.class,
            DataIntegrityViolationException.class,
            IllegalArgumentException.class,
            NullPointerException.class,
            Exception.class
    })
    public ResponseEntity<ErrorResponse> internalServerError(Exception exception) {
        var responseBody = ErrorResponse.builder()
                .message(
                        "An unexpected error has occurred: please try again. " +
                        "If the error persists, contact the development team."
                ).build();

        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
