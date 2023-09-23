package com.sims.ppob.exception;

import com.sims.ppob.model.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class HandleException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<?>> handleException(Exception ex) {
        WebResponse<?> response = WebResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> apiException(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(WebResponse.<String>builder()
                        .status(mapToCustomStatusCode((HttpStatus) exception.getStatusCode()))
                        .message(exception.getReason()).build());
    }

    public static int mapToCustomStatusCode(HttpStatus status) {

        return switch (status) {
            case BAD_REQUEST -> 102;
            case UNAUTHORIZED -> 108;
            case NOT_FOUND -> 114;
            case CONFLICT -> 120;
            case INTERNAL_SERVER_ERROR -> 126;
            default -> -1;
        };
    }
}
