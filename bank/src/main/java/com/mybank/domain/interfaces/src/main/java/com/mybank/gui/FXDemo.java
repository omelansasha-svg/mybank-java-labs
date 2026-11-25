package com.mybank.gui;

import com.mybank.data.DataSource;
import com.mybank.domain.Bank;
import com.mybank.domain.Customer;
import com.mybank.report.CustomerReport;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public final class FXDemo extends Application {
    @Override
    public void start(Stage stage) {
        Bank bank = DataSource.open();
        ComboBox<Customer> customers = new ComboBox<>();
        customers.getItems().setAll(bank.customers());
        customers.getSelectionModel().selectFirst();
        TextArea output = new TextArea();
        output.setEditable(false);
        Button show = new Button("Show");
        show.disableProperty().bind(customers.valueProperty().isNull());
        show.setOnAction(event -> {
            output.setText(CustomerReport.details(customers.getValue()));
            output.positionCaret(0);
        });
        Button report = new Button("Report");
        report.setOnAction(event -> {
            output.setText(CustomerReport.all(bank));
            output.positionCaret(0);
        });
        Button about = new Button("About");
        about.setOnAction(event -> {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION, "Banking | Lab 5 | JavaFX");
            dialog.initOwner(stage);
            dialog.setHeaderText("About Banking");
            dialog.showAndWait();
        });

        VBox content = new VBox(10, new HBox(8, new Label("Customer"), customers, show, report, about), output);
        VBox.setVgrow(output, Priority.ALWAYS);
        content.setPadding(new Insets(12));
        stage.setTitle("Banking | JavaFX");
        stage.setScene(new Scene(content, 570, 330));
        stage.show();
        Customer first = customers.getValue();
        if (first != null) {
            output.setText(CustomerReport.firstAccount(first));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
