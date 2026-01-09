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

    private Movement(
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

    public static Movement deposit(AccountId accountId, MovementAmount amount) {
        return new Movement(
                UUID.randomUUID(),
                accountId,
                MovementType.DEPOSIT,
                amount,
                LocalDateTime.now()
        );
    }

    public static Movement withdrawal(AccountId accountId, MovementAmount amount) {
        return new Movement(
                UUID.randomUUID(),
                accountId,
                MovementType.WITHDRAWAL,
                amount,
                LocalDateTime.now()
        );
    }

    public boolean isDeposit() {
        return type == MovementType.DEPOSIT;
    }

    public boolean isWithdrawal() {
        return type == MovementType.WITHDRAWAL;
    }

    public MovementAmount getAmount() {
        return amount;
    }

    public MovementType getType() {
        return type;
    }
}
