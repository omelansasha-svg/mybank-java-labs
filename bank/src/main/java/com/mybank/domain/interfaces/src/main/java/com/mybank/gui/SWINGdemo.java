package com.mybank.gui;

import com.mybank.data.DataSource;
import com.mybank.domain.Bank;
import com.mybank.domain.Customer;
import com.mybank.report.CustomerReport;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public final class SWINGdemo {
    private SWINGdemo() {
    }

    private static void open(Bank bank) {
        JFrame frame = new JFrame("Banking | Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComboBox<Customer> customers = new JComboBox<>(bank.customers().toArray(Customer[]::new));
        JButton show = new JButton("Show");
        JButton report = new JButton("Report");
        JTextArea output = new JTextArea(14, 48);
        output.setEditable(false);

        Runnable showDetails = () -> {
            Customer selected = (Customer) customers.getSelectedItem();
            if (selected != null) {
                output.setText(CustomerReport.details(selected));
                output.setCaretPosition(0);
            }
        };
        show.addActionListener(event -> showDetails.run());
        customers.addActionListener(event -> showDetails.run());
        report.addActionListener(event -> {
            output.setText(CustomerReport.all(bank));
            output.setCaretPosition(0);
        });
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEADING));
        controls.add(new JLabel("Customer"));
        controls.add(customers);
        controls.add(show);
        controls.add(report);
        frame.add(controls, BorderLayout.NORTH);
        frame.add(new JScrollPane(output), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        showDetails.run();
    }

    public static void main(String[] args) {
        Bank bank = DataSource.open();
        SwingUtilities.invokeLater(() -> open(bank));
    }
}
