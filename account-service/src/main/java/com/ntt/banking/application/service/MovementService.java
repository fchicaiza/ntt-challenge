package com.ntt.banking.application.service;

import com.ntt.banking.application.port.out.AccountRepositoryPort;
import com.ntt.banking.application.port.out.MovementRepositoryPort;
import com.ntt.banking.domain.account.Account;
import com.ntt.banking.domain.exception.InsufficientBalanceException;
import com.ntt.banking.domain.movement.Movement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@lombok.extern.slf4j.Slf4j
public class MovementService {

    private final MovementRepositoryPort movementRepository;
    private final AccountRepositoryPort accountRepository;
    private final com.ntt.banking.infrastructure.messaging.MovementEventPublisher eventPublisher;

    @Transactional
    public Mono<Movement> createMovement(String accountId, Movement movement) {
        log.info("Processing movement creation for account: {} - Type: {}, Amount: {}", accountId, movement.getType(),
                movement.getAmount());
        return Mono.fromCallable(() -> {
            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            BigDecimal amount = movement.getAmount();
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Amount must be greater than zero");
            }
            BigDecimal currentBalance = account.getBalance();
            BigDecimal newBalance;

            if ("DEBIT".equalsIgnoreCase(movement.getType())) {
                if (currentBalance.compareTo(amount) < 0) {
                    throw new InsufficientBalanceException();
                }
                newBalance = currentBalance.subtract(amount);
            } else if ("CREDIT".equalsIgnoreCase(movement.getType())) {
                newBalance = currentBalance.add(amount);
            } else {
                throw new RuntimeException("Invalid movement type");
            }

            // Update Account Balance
            Account updatedAccount = new Account(
                    account.getId(),
                    account.getAccountNumber(),
                    account.getAccountType(),
                    account.getInitialBalance(),
                    newBalance,
                    account.isActive(),
                    account.getCustomerId());
            accountRepository.save(updatedAccount);

            // Create and Save Movement
            Movement movementToSave = Movement.create(
                    movement.getType().toUpperCase(),
                    amount,
                    newBalance,
                    movement.getDescription(),
                    accountId);

            Movement savedMovement = movementRepository.save(movementToSave);

            // Publish Event
            eventPublisher.publishMovementCreated(com.ntt.banking.application.dto.MovementEvent.builder()
                    .id(savedMovement.getId())
                    .accountId(savedMovement.getAccountId())
                    .type(savedMovement.getType())
                    .amount(savedMovement.getAmount())
                    .balanceAfter(savedMovement.getBalance())
                    .description(savedMovement.getDescription())
                    .dateTime(savedMovement.getDateTime())
                    .build());

            return savedMovement;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Flux<Movement> getMovementsByAccount(String accountId) {
        return movementRepository.findByAccountId(accountId);
    }

    public Flux<Movement> getMovementsByAccountAndDate(String accountId, LocalDateTime start, LocalDateTime end) {
        return movementRepository.findByAccountIdAndDateBetween(accountId, start, end);
    }

    @Transactional
    public Mono<Movement> updateMovement(String movementId, String description) {
        log.info("Updating movement description for ID: {}", movementId);
        return Mono.fromCallable(() -> {
            Movement movement = movementRepository.findById(movementId)
                    .orElseThrow(() -> new RuntimeException("Movement not found"));

            Movement updatedMovement = new Movement(
                    movement.getId(),
                    movement.getDateTime(),
                    movement.getType(),
                    movement.getAmount(),
                    movement.getBalance(),
                    description,
                    movement.getAccountId());
            return movementRepository.save(updatedMovement);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Transactional
    public Mono<Void> deleteMovement(String movementId) {
        log.info("Deleting movement with ID: {}", movementId);
        return Mono.fromRunnable(() -> {
            movementRepository.delete(movementId);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
}
