package com.ntt.banking.application.exception;

public class CustomerAlreadyExistsException extends RuntimeException {

    public CustomerAlreadyExistsException(String identification) {
        super("Customer already exists with identification: " + identification);
    }
}
