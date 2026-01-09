package com.ntt.banking.application.service;

import com.ntt.banking.application.port.out.AccountRepositoryPort;
import com.ntt.banking.application.port.out.MovementRepositoryPort;
import com.ntt.banking.domain.account.Account;
import com.ntt.banking.domain.exception.InsufficientBalanceException;
import com.ntt.banking.domain.movement.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MovementServiceTest {

    @Mock
    private MovementRepositoryPort movementRepository;

    @Mock
    private AccountRepositoryPort accountRepository;

    @InjectMocks
    private MovementService movementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateCreditMovement() {
        Account account = new Account("1", "478758", "Ahorros", BigDecimal.valueOf(100), BigDecimal.valueOf(100), true,
                "1");
        Movement movementReq = Movement.create("CREDIT", BigDecimal.valueOf(50), BigDecimal.ZERO, "Deposit", "1");
        Movement movementSaved = new Movement("m1", java.time.LocalDateTime.now(), "CREDIT", BigDecimal.valueOf(50),
                BigDecimal.valueOf(150),
                "Deposit", "1");

        when(accountRepository.findById("1")).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(movementRepository.save(any(Movement.class))).thenReturn(movementSaved);

        StepVerifier.create(movementService.createMovement("1", movementReq))
                .expectNext(movementSaved)
                .verifyComplete();
    }

    @Test
    void shouldCreateDebitMovement() {
        Account account = new Account("1", "478758", "Ahorros", BigDecimal.valueOf(100), BigDecimal.valueOf(100), true,
                "1");
        Movement movementReq = Movement.create("DEBIT", BigDecimal.valueOf(50), BigDecimal.ZERO, "Withdrawal", "1");
        Movement movementSaved = new Movement("m1", java.time.LocalDateTime.now(), "DEBIT", BigDecimal.valueOf(50),
                BigDecimal.valueOf(50),
                "Withdrawal", "1");

        when(accountRepository.findById("1")).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(movementRepository.save(any(Movement.class))).thenReturn(movementSaved);

        StepVerifier.create(movementService.createMovement("1", movementReq))
                .expectNext(movementSaved)
                .verifyComplete();
    }

    @Test
    void shouldThrowInsufficientBalanceException() {
        Account account = new Account("1", "478758", "Ahorros", BigDecimal.valueOf(100), BigDecimal.valueOf(40), true,
                "1");
        Movement movementReq = Movement.create("DEBIT", BigDecimal.valueOf(50), BigDecimal.ZERO, "Withdrawal", "1");

        when(accountRepository.findById("1")).thenReturn(Optional.of(account));

        StepVerifier.create(movementService.createMovement("1", movementReq))
                .expectError(InsufficientBalanceException.class)
                .verify();
    }
}
