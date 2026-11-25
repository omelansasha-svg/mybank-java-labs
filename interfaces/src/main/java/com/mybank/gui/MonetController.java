package com.mybank.gui;

import com.mybank.data.DataSource;
import com.mybank.domain.Bank;
import com.mybank.domain.Customer;
import com.mybank.report.CustomerReport;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public final class MonetController {
    @FXML private ComboBox<Customer> customers;
    @FXML private Button showButton;
    @FXML private TextArea output;
    private Bank bank;

    @FXML
    private void initialize() {
        bank = DataSource.open();
        customers.getItems().setAll(bank.customers());
        customers.getSelectionModel().selectFirst();
        showButton.disableProperty().bind(customers.valueProperty().isNull());
    }

    @FXML
    private void showCustomer() {
        output.setText(CustomerReport.details(customers.getValue()));
        output.positionCaret(0);
    }

    @FXML
    private void showReport() {
        output.setText(CustomerReport.all(bank));
        output.positionCaret(0);
    }

    @FXML
    private void showAbout() {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION, "Banking | Lab 6 | JavaFX Scene Builder");
        dialog.initOwner(output.getScene().getWindow());
        dialog.setHeaderText("About Banking");
        dialog.showAndWait();
    }
}
