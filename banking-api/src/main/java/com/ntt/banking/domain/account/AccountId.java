package com.ntt.banking.domain.account;

import java.util.UUID;

public record AccountId(UUID value) {

    public static AccountId generate() {
        return new AccountId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}