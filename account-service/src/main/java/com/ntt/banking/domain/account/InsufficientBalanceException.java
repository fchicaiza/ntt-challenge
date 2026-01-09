package com.ntt.banking.domain.account;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException() {
        super("Saldo no disponible");
    }
}
