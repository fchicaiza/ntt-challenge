package com.ntt.banking.domain.movement;

import java.math.BigDecimal;
import java.util.Objects;

public class MovementAmount {
    private final BigDecimal value;

    public MovementAmount(BigDecimal value) {
        Objects.requireNonNull(value);
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}
