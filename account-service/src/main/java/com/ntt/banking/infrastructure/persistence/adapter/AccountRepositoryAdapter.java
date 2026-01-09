package com.ntt.banking.infrastructure.persistence.adapter;

import com.ntt.banking.application.port.out.AccountRepositoryPort;
import com.ntt.banking.domain.account.Account;
import com.ntt.banking.infrastructure.persistence.entity.AccountEntity;
import com.ntt.banking.infrastructure.persistence.repository.JpaAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final JpaAccountRepository jpaAccountRepository;

    @Override
    public Account save(Account account) {
        AccountEntity entity = mapToEntity(account);
        AccountEntity savedEntity = jpaAccountRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<Account> findById(String id) {
        return jpaAccountRepository.findById(UUID.fromString(id))
                .map(this::mapToDomain);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return jpaAccountRepository.findByAccountNumber(accountNumber)
                .map(this::mapToDomain);
    }

    @Override
    public void deleteById(String id) {
        jpaAccountRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public java.util.List<Account> findByCustomerId(String customerId) {
        return jpaAccountRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    private AccountEntity mapToEntity(Account account) {
        return AccountEntity.builder()
                .id(account.getId() != null ? UUID.fromString(account.getId()) : null)
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .initialBalance(account.getInitialBalance())
                .status(account.isActive())
                .customerId(account.getCustomerId())
                .build();
    }

    private Account mapToDomain(AccountEntity entity) {
        // Assuming Account domain object matches necessary fields
        // Since we are creating AccountService from scratch, we might need to adjust
        // Domain object too
        return new Account(
                entity.getId().toString(),
                entity.getAccountNumber(),
                entity.getAccountType(),
                entity.getInitialBalance(),
                entity.getStatus(),
                entity.getCustomerId());
    }
}
