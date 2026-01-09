package com.ntt.banking.application.port.out;

import com.ntt.banking.domain.account.Account;
import com.ntt.banking.domain.account.AccountId;

import java.util.Optional;

public interface AccountRepositoryPort {

    Account save(Account account);

    Optional<Account> findById(String id);

    Optional<Account> findByAccountNumber(String accountNumber);

    java.util.List<Account> findByCustomerId(String customerId);

    void deleteById(String id);
}
