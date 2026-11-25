package com.mybank.domain;

import java.util.List;

public record Customer(String firstName, String lastName, List<Account> accounts) {
    public Customer {
        accounts = List.copyOf(accounts);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
