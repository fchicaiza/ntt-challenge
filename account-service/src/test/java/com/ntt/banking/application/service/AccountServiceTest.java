package com.ntt.banking.application.service;

import com.ntt.banking.application.port.out.AccountRepositoryPort;
import com.ntt.banking.domain.account.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    private AccountRepositoryPort accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateAccount() {
        Account account = new Account("1", "478758", "Ahorros", BigDecimal.valueOf(100), true, "1");

        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        StepVerifier.create(accountService.createAccount(account))
                .expectNext(account)
                .verifyComplete();
    }

    @Test
    void shouldGetAccountById() {
        Account account = new Account("1", "478758", "Ahorros", BigDecimal.valueOf(100), true, "1");
        when(accountRepository.findById("1")).thenReturn(Optional.of(account));

        StepVerifier.create(accountService.getAccountById("1"))
                .expectNext(account)
                .verifyComplete();
    }

    @Test
    void shouldUpdateAccount() {
        Account existing = new Account("1", "478758", "Ahorros", BigDecimal.valueOf(100), true, "cust-1");
        Account updated = new Account("1", "478758", "Ahorros", BigDecimal.valueOf(100), false, "cust-1");

        when(accountRepository.findById("1")).thenReturn(Optional.of(existing));
        when(accountRepository.save(any(Account.class))).thenReturn(updated);

        StepVerifier.create(accountService.updateAccount("1", updated))
                .expectNextMatches(acc -> !acc.isActive())
                .verifyComplete();
    }

    @Test
    void shouldGetAccountsByCustomer() {
        Account account1 = new Account("1", "478758", "Ahorros", BigDecimal.valueOf(100), true, "cust-1");
        Account account2 = new Account("2", "225487", "Corriente", BigDecimal.valueOf(500), true, "cust-1");

        when(accountRepository.findByCustomerId("cust-1")).thenReturn(java.util.List.of(account1, account2));

        StepVerifier.create(accountService.getAccountsByCustomer("cust-1"))
                .expectNext(account1)
                .expectNext(account2)
                .verifyComplete();
    }
}
