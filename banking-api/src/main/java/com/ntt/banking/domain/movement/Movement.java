package com.ntt.banking.domain.movement;

import com.ntt.banking.domain.account.AccountId;

import java.time.LocalDateTime;
import java.util.UUID;

public class Movement {

    private final UUID id;
    private final AccountId accountId;
    private final MovementType type;
    private final MovementAmount amount;
    private final LocalDateTime date;

    public Movement(
            UUID id,
            AccountId accountId,
            MovementType type,
            MovementAmount amount,
            LocalDateTime date
    ) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public static Movement createDeposit(AccountId accountId, MovementAmount amount) {
        return new Movement(
                UUID.randomUUID(),
                accountId,
                MovementType.DEPOSIT,
                amount,
                LocalDateTime.now()
        );
    }

    public static Movement createWithdrawal(AccountId accountId, MovementAmount amount) {
        return new Movement(
                UUID.randomUUID(),
                accountId,
                MovementType.WITHDRAWAL,
                amount,
                LocalDateTime.now()
        );
    }

    public UUID getId() { return id; }
    public AccountId getAccountId() { return accountId; }
    public MovementType getType() { return type; }
    public MovementAmount getAmount() { return amount; }
    public LocalDateTime getDate() { return date; }
}
