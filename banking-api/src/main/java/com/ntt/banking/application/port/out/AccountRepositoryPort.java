package com.ntt.banking.application.port.out;

import com.ntt.banking.domain.account.Account;
import com.ntt.banking.domain.account.AccountId;

import java.util.Optional;

public interface AccountRepositoryPort {

    void save(Account account);

    Optional<Account> findById(AccountId accountId);
}
