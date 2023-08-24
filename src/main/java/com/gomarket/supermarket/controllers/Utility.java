package com.gomarket.supermarket.controllers;

import com.gomarket.supermarket.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.gomarket.supermarket.Main.*;

public class Utility {
    public static void moveTo(ActionEvent event , String page) throws IOException {

        boolean isFullScreen = currentStage.isFullScreen();


        Node node = (Node) event.getSource();
        Stage stage =(Stage) node.getScene().getWindow();

        Parent root =  FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(page + ".fxml")));
         scene.setRoot(root);
         stage.setScene(scene);

        if(!isFullScreen) {
            stage.setWidth(1100);
            stage.setHeight(680);
            stage.centerOnScreen();
        }else
            stage.setFullScreen(true);

        if(Objects.equals(currentStage.getTitle(), "Welcome to go market"))
        {
            currentStage.close();
            stage.show();
        }
        stage.setTitle(page);
        currentStage=stage;
    }



    public static void moveToLogin(MouseEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage =(Stage) node.getScene().getWindow();


        Parent root =  FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("loginForm.fxml")));
        scene.setRoot(root);
        stage.setFullScreen(false);
        stage.setWidth(660);
        stage.setHeight(535);
        stage.centerOnScreen();
        stage.setTitle("Welcome to GO STORE");
        stage.setScene(scene);
        stage.setResizable(false);

    }


    public static void clearForm(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setText("");
        }
    }

}
