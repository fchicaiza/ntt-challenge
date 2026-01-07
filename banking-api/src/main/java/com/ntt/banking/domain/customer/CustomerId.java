package com.ntt.banking.domain.customer;

import java.util.UUID;

public record CustomerId(UUID value) {

    public static CustomerId generate() {
        return new CustomerId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
