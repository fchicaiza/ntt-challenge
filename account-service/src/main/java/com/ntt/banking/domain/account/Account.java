package com.ntt.banking.domain.account;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    private final String id;
    private final String accountNumber;
    private final String accountType;
    private final BigDecimal initialBalance;
    private final Boolean active;
    private final String customerId;

    public Account(
            String id,
            String accountNumber,
            String accountType,
            BigDecimal initialBalance,
            Boolean active,
            String customerId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.active = active;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public Boolean isActive() {
        return active;
    }

    public String getCustomerId() {
        return customerId;
    }
}
