package com.ntt.banking.domain.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ntt.banking.domain.account.AccountId;

public class MovementTest {
    @Test
    void shouldCreateDepositMovement() {
        Movement movement = Movement.createDeposit(
                new AccountId(UUID.randomUUID()),
                new MovementAmount(BigDecimal.TEN));

        assertEquals(MovementType.DEPOSIT, movement.getType());
    }

}
