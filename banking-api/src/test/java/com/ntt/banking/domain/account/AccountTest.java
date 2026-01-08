package com.ntt.banking.domain.account;

import com.ntt.banking.domain.customer.CustomerId; // Importa tu Value Object
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
void shouldOpenAccountWithZeroBalance() {
    Account account = Account.open(
        new CustomerId(UUID.randomUUID()),
        AccountType.SAVINGS
    );

    assertEquals(BigDecimal.ZERO, account.getBalance().getAmount());
}
}