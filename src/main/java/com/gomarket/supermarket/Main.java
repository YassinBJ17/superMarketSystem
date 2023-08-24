package com.gomarket.supermarket;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends javafx.application.Application {

    public static Scene scene;
    public static Stage currentStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginForm.fxml"));
        Parent root = fxmlLoader.load();
        currentStage=stage;
        scene = new Scene(root);
        stage.setTitle("Welcome to GO STORE");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}