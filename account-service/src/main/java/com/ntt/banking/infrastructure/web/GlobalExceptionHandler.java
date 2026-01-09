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
    public Mono<ResponseEntity<ErrorResponse>> handleRuntimeException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setCode("INTERNAL_SERVER_ERROR");
        error.setMessage(ex.getMessage());
        error.setTimestamp(OffsetDateTime.now());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex.getMessage().contains("not found")) {
            status = HttpStatus.NOT_FOUND;
            error.setCode("RESOURCE_NOT_FOUND");
        } else if (ex.getMessage().contains("already exists")) {
            status = HttpStatus.CONFLICT;
            error.setCode("RESOURCE_ALREADY_EXISTS");
        }

        return Mono.just(ResponseEntity.status(status).body(error));
    }
}
