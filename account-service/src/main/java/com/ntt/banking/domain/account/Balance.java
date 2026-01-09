package com.ntt.banking.domain.account;

import java.math.BigDecimal;
import java.util.Objects;

public class Balance {

    private final BigDecimal amount;

    private Balance(BigDecimal amount) {
        Objects.requireNonNull(amount);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.amount = amount;
    }

    public static Balance zero() {
        return new Balance(BigDecimal.ZERO);
    }

    public static Balance of(BigDecimal amount) {
        return new Balance(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Balance add(BigDecimal value) {
        return new Balance(this.amount.add(value));
    }

    public Balance subtract(BigDecimal value) {
        return new Balance(this.amount.subtract(value));
    }

    public boolean isLessThan(BigDecimal value) {
        return this.amount.compareTo(value) < 0;
    }
}
