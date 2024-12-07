package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WeatherApp.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Weather Station Manager");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}