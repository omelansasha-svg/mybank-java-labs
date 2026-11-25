package com.mybank.report;

import com.mybank.domain.Account;
import com.mybank.domain.Bank;
import com.mybank.domain.Customer;

public final class CustomerReport {
    private CustomerReport() {
    }

    public static String all(Bank bank) {
        StringBuilder report = new StringBuilder("CUSTOMERS REPORT\n================\n");
        for (Customer customer : bank.customers()) {
            report.append('\n').append(details(customer));
        }
        return report.toString();
    }

    public static String details(Customer customer) {
        return accountDetails(customer, customer.accounts().size());
    }

    public static String firstAccount(Customer customer) {
        return accountDetails(customer, Math.min(customer.accounts().size(), 1));
    }

    private static String accountDetails(Customer customer, int accountCount) {
        StringBuilder text = new StringBuilder("Customer: ")
                .append(customer.lastName()).append(", ").append(customer.firstName()).append('\n');
        for (int i = 0; i < accountCount; i++) {
            Account account = customer.accounts().get(i);
            text.append("  ").append(account.type()).append(" Account: current balance is ")
                    .append(Account.dollars(account.balance())).append('\n');
        }
        if (accountCount == 0) {
            text.append("  No accounts\n");
        }
        return text.toString();
    }
}
