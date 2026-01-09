package com.ntt.banking.application.service;

import com.ntt.banking.application.port.out.AccountRepositoryPort;
import com.ntt.banking.domain.account.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepositoryPort accountRepository;

    public Mono<Account> createAccount(Account account) {
        return Mono.fromCallable(() -> {
            log.info("Creating account: {}", account.getAccountNumber());
            if (accountRepository.findByAccountNumber(account.getAccountNumber()).isPresent()) {
                throw new RuntimeException("Account with number " + account.getAccountNumber() + " already exists");
            }
            if (account.getId() == null) {
                // Ensure ID generation if not present
                // Ideally this should be handled in Domain or here before save
                // For now relying on Account constructor or Adapter logic, but let's be safe
                // Actually the Adapter converts String id to UUID, so we need a valid ID string
                // or handle null
                // Let's assume the Domain object should handle ID generation on creation
            }
            return accountRepository.save(account);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Account> getAccountById(String id) {
        return Mono.fromCallable(() -> accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"))).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Account> updateAccount(String id, Account updatedAccount) {
        return Mono.fromCallable(() -> {
            Account existing = accountRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            // Update fields
            // Assuming Account domain has setters or update methods
            // Re-creating for immutability often preferred but simplest approach here:
            Account toSave = new Account(
                    existing.getId(),
                    updatedAccount.getAccountNumber(), // Usually not updatable, but follows CRUD
                    updatedAccount.getAccountType(),
                    updatedAccount.getInitialBalance(),
                    updatedAccount.isActive(), // Status
                    existing.getCustomerId() // Usually customer doesn't change
            );

            return accountRepository.save(toSave);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> deleteAccount(String id) {
        return Mono.fromRunnable(() -> {
            accountRepository.deleteById(id);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    public reactor.core.publisher.Flux<Account> getAccountsByCustomer(String customerId) {
        return reactor.core.publisher.Flux.fromIterable(accountRepository.findByCustomerId(customerId))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
