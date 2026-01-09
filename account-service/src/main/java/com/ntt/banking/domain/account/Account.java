package com.ntt.banking.domain.account;

import com.ntt.banking.domain.customer.CustomerId;
import com.ntt.banking.domain.movement.Movement;

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

    /**
     * Apply a movement to the account enforcing business rules (F2, F3)
     */
    public void apply(Movement movement) {

        Objects.requireNonNull(movement);

        if (movement.isWithdrawal() &&
                balance.isLessThan(movement.getAmount().getValue())) {
            throw new InsufficientBalanceException();
        }

        if (movement.isDeposit()) {
            this.balance = balance.add(movement.getAmount().getValue());
        }

        if (movement.isWithdrawal()) {
            this.balance = balance.subtract(movement.getAmount().getValue());
        }
    }

    public AccountId getId() { return id; }
    public CustomerId getCustomerId() { return customerId; }
    public AccountType getType() { return type; }
    public Balance getBalance() { return balance; }
    public boolean isActive() { return active; }
}
