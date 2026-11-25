package com.mybank.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class CheckingAccount extends Account {
    private final BigDecimal limit;

    public CheckingAccount(BigDecimal balance, BigDecimal limit) {
        super(balance);
        this.limit = limit.setScale(2, RoundingMode.UNNECESSARY);
        if (this.limit.signum() < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative.");
        }
    }

    @Override
    public String type() {
        return "Checking";
    }

    @Override
    protected BigDecimal overdraft() {
        return limit;
    }
}
