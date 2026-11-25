package com.mybank.domain;

import java.util.List;

public record Bank(List<Customer> customers) {
    public Bank {
        customers = List.copyOf(customers);
    }

    public Customer customer(String idText) {
        int id;
        try {
            id = Integer.parseInt(idText.strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Enter a whole customer number.", e);
        }
        if (id < 0 || id >= customers.size()) {
            throw new IllegalArgumentException("Customer number must be between 0 and "
                    + (customers.size() - 1) + ".");
        }
        return customers.get(id);
    }
}
