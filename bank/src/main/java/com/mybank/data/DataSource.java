package com.mybank.data;

import com.mybank.domain.Account;
import com.mybank.domain.Bank;
import com.mybank.domain.CheckingAccount;
import com.mybank.domain.Customer;
import com.mybank.domain.SavingsAccount;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class DataSource {
    private DataSource() {
    }

    public static Bank open() {
        String file = System.getProperty("bank.data");
        try (InputStream stream = file == null
                ? DataSource.class.getResourceAsStream("/test.dat")
                : Files.newInputStream(Path.of(file))) {
            if (stream == null) {
                throw new IOException("Cannot find test.dat.");
            }
            return read(stream);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not load bank data.", e);
        }
    }

    public static Bank read(InputStream stream) throws IOException {
        try (Scanner input = new Scanner(stream, StandardCharsets.UTF_8).useLocale(Locale.ROOT)) {
            int size = count(input);
            List<Customer> customers = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                String firstName = input.next();
                String lastName = input.next();
                int accountCount = count(input);
                List<Account> accounts = new ArrayList<>();
                for (int j = 0; j < accountCount; j++) {
                    accounts.add(readAccount(input));
                }
                customers.add(new Customer(firstName, lastName, accounts));
            }
            if (input.hasNext()) {
                throw new IllegalArgumentException("Unexpected data after the customer records.");
            }
            if (input.ioException() != null) {
                throw input.ioException();
            }
            return new Bank(customers);
        } catch (NoSuchElementException | IllegalArgumentException | ArithmeticException e) {
            throw new IOException("Invalid test.dat: " + e.getMessage(), e);
        }
    }

    private static int count(Scanner input) {
        int count = input.nextInt();
        if (count < 0) {
            throw new IllegalArgumentException("A record count cannot be negative.");
        }
        return count;
    }

    private static Account readAccount(Scanner input) {
        String type = input.next();
        BigDecimal balance = input.nextBigDecimal();
        BigDecimal parameter = input.nextBigDecimal();
        return switch (type) {
            case "S" -> new SavingsAccount(balance, parameter);
            case "C" -> new CheckingAccount(balance, parameter);
            default -> throw new IllegalArgumentException("Unknown account type: " + type);
        };
    }
}
