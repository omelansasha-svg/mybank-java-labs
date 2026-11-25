package com.mybank.gui;

import com.mybank.data.DataSource;
import com.mybank.domain.Bank;
import com.mybank.domain.Customer;
import com.mybank.report.CustomerReport;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public final class MatisseDemo extends JFrame {
    private final Bank bank;

    public MatisseDemo() {
        bank = DataSource.open();
        initComponents();
        customers.setModel(new DefaultComboBoxModel<>(bank.customers().toArray(Customer[]::new)));
        showButton.setEnabled(!bank.customers().isEmpty());
    }

    //GEN-BEGIN:initComponents
    private void initComponents() {
        customerLabel = new JLabel("Customer");
        customers = new JComboBox<>();
        showButton = new JButton("Show");
        reportButton = new JButton("Report");
        aboutButton = new JButton("About");
        scroll = new JScrollPane();
        output = new JTextArea(12, 45);

        setTitle("Banking | Matisse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        output.setEditable(false);
        scroll.setViewportView(output);
        showButton.addActionListener(this::showCustomer);
        reportButton.addActionListener(this::showReport);
        aboutButton.addActionListener(this::showAbout);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(customerLabel)
                        .addComponent(customers, 155, 180, 220)
                        .addComponent(showButton)
                        .addComponent(reportButton)
                        .addComponent(aboutButton))
                .addComponent(scroll));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(customerLabel)
                        .addComponent(customers)
                        .addComponent(showButton)
                        .addComponent(reportButton)
                        .addComponent(aboutButton))
                .addComponent(scroll));
        pack();
    }
    //GEN-END:initComponents

    private void showCustomer(java.awt.event.ActionEvent event) {//GEN-FIRST:event_showCustomer
        Customer selected = (Customer) customers.getSelectedItem();
        if (selected != null) {
            output.setText(CustomerReport.details(selected));
            output.setCaretPosition(0);
        }
    }//GEN-LAST:event_showCustomer

    private void showReport(java.awt.event.ActionEvent event) {//GEN-FIRST:event_showReport
        output.setText(CustomerReport.all(bank));
        output.setCaretPosition(0);
    }//GEN-LAST:event_showReport

    private void showAbout(java.awt.event.ActionEvent event) {//GEN-FIRST:event_showAbout
        JOptionPane.showMessageDialog(this, "Banking | Lab 4 | NetBeans Matisse",
                "About Banking", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_showAbout

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MatisseDemo form = new MatisseDemo();
            form.setLocationRelativeTo(null);
            form.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton aboutButton;
    private JLabel customerLabel;
    private JComboBox<Customer> customers;
    private JTextArea output;
    private JButton reportButton;
    private JScrollPane scroll;
    private JButton showButton;
    // End of variables declaration//GEN-END:variables
}
