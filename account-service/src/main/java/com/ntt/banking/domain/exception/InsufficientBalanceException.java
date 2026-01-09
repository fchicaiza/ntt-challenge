package com.ntt.banking.domain.exception;

public class InsufficientBalanceException extends MovementException {
    public InsufficientBalanceException() {
        super("Saldo no disponible");
    }
}
