package com.mybank.tui;

import com.mybank.data.DataSource;
import com.mybank.domain.Bank;
import com.mybank.report.CustomerReport;
import java.io.IOException;
import java.io.PrintWriter;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public final class CLIdemo {
    private CLIdemo() {
    }

    public static void main(String[] args) throws IOException {
        Bank bank = DataSource.open();
        try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {
            LineReader reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new ArgumentCompleter(
                            new StringsCompleter("help", "customers", "customer", "report", "exit")))
                    .build();
            PrintWriter output = terminal.writer();
            output.println("Banking. Type help or press Tab to see the commands.");
            output.flush();
            run(bank, reader, output);
        }
    }

    private static void run(Bank bank, LineReader reader, PrintWriter output) {
        try {
            while (true) {
                String line = reader.readLine("bank> ").strip();
                if (line.isEmpty()) {
                    continue;
                }
                String[] words = line.split("\\s+", 2);
                String argument = words.length == 2 ? words[1] : "";
                try {
                    switch (words[0]) {
                        case "help" -> output.println("help | customers | customer <number> | report | exit");
                        case "customers" -> {
                            for (int i = 0; i < bank.customers().size(); i++) {
                                output.printf("%d  %s%n", i, bank.customers().get(i));
                            }
                        }
                        case "customer" -> output.print(CustomerReport.details(bank.customer(argument)));
                        case "report" -> output.print(CustomerReport.all(bank));
                        case "exit" -> {
                            return;
                        }
                        default -> output.println("Unknown command. Type help.");
                    }
                } catch (IllegalArgumentException e) {
                    output.println(e.getMessage());
                }
                output.flush();
            }
        } catch (EndOfFileException | UserInterruptException e) {
            output.println();
            output.flush();
        }
    }
}
