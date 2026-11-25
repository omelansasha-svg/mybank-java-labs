package com.mybank.domain;

import java.math.BigDecimal;

public final class SavingsAccount extends Account {
    private final BigDecimal interestRate;

    public SavingsAccount(BigDecimal balance, BigDecimal interestRate) {
        super(balance);
        if (interestRate.signum() < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        this.interestRate = interestRate;
    }

    public BigDecimal interestRate() {
        return interestRate;
    }

    @Override
    public String type() {
        return "Savings";
    }
}
