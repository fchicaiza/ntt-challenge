package com.ntt.banking.infrastructure.persistence.adapter;

import com.ntt.banking.application.port.out.AccountRepositoryPort;
import com.ntt.banking.domain.account.*;
import com.ntt.banking.domain.customer.CustomerId;
import com.ntt.banking.infrastructure.persistence.entity.AccountEntity;
import com.ntt.banking.infrastructure.persistence.repository.JpaAccountRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final JpaAccountRepository repository;

    public AccountRepositoryAdapter(JpaAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Account account) {
        AccountEntity entity = new AccountEntity();
        entity.setId(account.getId().value());
        entity.setCustomerId(account.getCustomerId().value());
        entity.setType(account.getType().name());
        entity.setBalance(account.getBalance().getAmount());
        entity.setActive(account.isActive());

        repository.save(entity);
    }

    @Override
    public Optional<Account> findById(AccountId accountId) {
        return repository.findById(accountId.value())
                .map(entity -> new Account(
                        new AccountId(entity.getId()),
                        new CustomerId(entity.getCustomerId()),
                        AccountType.valueOf(entity.getType()),
                        new Balance(entity.getBalance()),
                        entity.isActive()
                ));
    }
}
