package com.ntt.banking.domain.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ntt.banking.domain.account.Account;
import com.ntt.banking.domain.account.AccountType;
import com.ntt.banking.domain.customer.CustomerId;

public class AccountTest {
    @Test
void shouldOpenAccountWithZeroBalance() {
    Account account = Account.open(
        new CustomerId(UUID.randomUUID()),
        AccountType.SAVINGS
    );

    assertEquals(BigDecimal.ZERO, account.getBalance().getAmount());
}
}
