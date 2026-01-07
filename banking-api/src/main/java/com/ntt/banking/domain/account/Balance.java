package com.ntt.banking.domain.account;

import java.math.BigDecimal;
import java.util.Objects;

public class Balance {

    private final BigDecimal amount;

    public Balance(BigDecimal amount) {
        Objects.requireNonNull(amount);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.amount = amount;
    }

    public static Balance zero() {
        return new Balance(BigDecimal.ZERO);
    }

    public Balance add(BigDecimal value) {
        return new Balance(this.amount.add(value));
    }

    public Balance subtract(BigDecimal value) {
        BigDecimal result = this.amount.subtract(value);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        return new Balance(result);
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
