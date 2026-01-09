package com.ntt.banking.infrastructure.web;

import com.ntt.banking.application.exception.CustomerAlreadyExistsException;
import com.ntt.banking.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleCustomerAlreadyExists(CustomerAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setCode("CUSTOMER_ALREADY_EXISTS");
        error.setMessage(ex.getMessage());
        error.setTimestamp(OffsetDateTime.now());
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(error));
    }

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleRuntimeException(RuntimeException ex) {
        // Basic handler for "Customer not found" generic runtime exception for now
        ErrorResponse error = new ErrorResponse();
        if (ex.getMessage().contains("not found")) {
            error.setCode("RESOURCE_NOT_FOUND");
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
        }
        error.setCode("INTERNAL_SERVER_ERROR");
        error.setMessage(ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error));
    }
}
