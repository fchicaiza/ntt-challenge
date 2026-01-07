package com.ntt.banking.domain.account;

import com.ntt.banking.domain.customer.CustomerId;

import java.util.Objects;

public class Account {

    private final AccountId id;
    private final CustomerId customerId;
    private final AccountType type;
    private Balance balance;
    private boolean active;

    public Account(
            AccountId id,
            CustomerId customerId,
            AccountType type,
            Balance balance,
            boolean active
    ) {
        this.id = Objects.requireNonNull(id);
        this.customerId = Objects.requireNonNull(customerId);
        this.type = Objects.requireNonNull(type);
        this.balance = Objects.requireNonNull(balance);
        this.active = active;
    }

    public static Account open(CustomerId customerId, AccountType type) {
        return new Account(
                AccountId.generate(),
                customerId,
                type,
                Balance.zero(),
                true
        );
    }

    public void deposit(Balance newBalance) {
        this.balance = newBalance;
    }

    public void withdraw(Balance newBalance) {
        this.balance = newBalance;
    }

    public AccountId getId() { return id; }
    public CustomerId getCustomerId() { return customerId; }
    public AccountType getType() { return type; }
    public Balance getBalance() { return balance; }
    public boolean isActive() { return active; }
}