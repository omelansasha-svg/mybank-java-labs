package com.mybank.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Account {
    private BigDecimal balance;

    protected Account(BigDecimal openingBalance) {
        balance = money(openingBalance);
        if (balance.signum() < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative.");
        }
    }

    public abstract String type();

    public BigDecimal balance() {
        return balance;
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(positiveAmount(amount));
    }

    public void withdraw(BigDecimal amount) {
        BigDecimal updated = balance.subtract(positiveAmount(amount));
        if (updated.compareTo(overdraft().negate()) < 0) {
            BigDecimal deficit = updated.negate().subtract(overdraft());
            throw new IllegalArgumentException("Insufficient funds. Deficit: " + dollars(deficit));
        }
        balance = updated;
    }

    protected BigDecimal overdraft() {
        return BigDecimal.ZERO;
    }

    private static BigDecimal positiveAmount(BigDecimal value) {
        BigDecimal amount = money(value);
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Enter an amount greater than zero.");
        }
        return amount;
    }

    private static BigDecimal money(BigDecimal value) {
        try {
            return value.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("Enter an amount with at most two decimal places.", e);
        }
    }

    public static String dollars(BigDecimal amount) {
        return "$" + money(amount).toPlainString();
    }
}
