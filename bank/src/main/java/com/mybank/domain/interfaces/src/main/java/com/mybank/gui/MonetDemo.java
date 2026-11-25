package com.mybank.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class MonetDemo extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MonetDemo.class.getResource("MonetDemo.fxml"));
        stage.setTitle("Banking | Monet");
        stage.setScene(new Scene(loader.load(), 570, 330));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
