package com.ntt.banking.infrastructure.web;

import com.ntt.banking.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getMessage();

        if (message != null) {
            if (message.contains("not found")) {
                status = HttpStatus.NOT_FOUND;
            } else if (message.contains("already exists")) {
                status = HttpStatus.CONFLICT;
            }
        }

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(com.ntt.banking.domain.exception.MovementException.class)
    public ResponseEntity<ErrorResponse> handleMovementException(
            com.ntt.banking.domain.exception.MovementException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
