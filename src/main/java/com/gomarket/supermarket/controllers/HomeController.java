package com.gomarket.supermarket.controllers;

import com.gomarket.supermarket.CalendarView;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.io.IOException;
import java.time.LocalDate;

import static com.gomarket.supermarket.Main.currentStage;
public class HomeController {

    @FXML
    Button btnProducts , btnServices , btnEmployees, btnStatistics;
    @FXML
    Pane homePane=new Pane();


    public void initialize() {

        CalendarView calendarView = new CalendarView();
        calendarView.getSelectionModel().setSelectedDate(LocalDate.now().minusWeeks(1));
        HBox.setHgrow(calendarView, Priority.ALWAYS);

        VBox options1 = new VBox(10);

        options1.getChildren().add(createOption("Show today", calendarView.showTodayProperty()));
        options1.getChildren().add(createOption("Show today button", calendarView.showTodayButtonProperty()));
        options1.getChildren().add(createOption("Show month", calendarView.showMonthProperty()));
        options1.getChildren().add(createOption("Show year", calendarView.showYearProperty()));
        options1.getChildren().add(createOption("Show year spinner", calendarView.showYearSpinnerProperty()));
        options1.getChildren().add(createOption("Show month arrows", calendarView.showMonthArrowsProperty()));
        options1.getChildren().add(createOption("Show week numbers", calendarView.showWeekNumbersProperty()));
        options1.getChildren().add(createOption("Show days of other months", calendarView.showDaysOfPreviousOrNextMonthProperty()));

        ComboBox<CalendarView.SelectionModel.SelectionMode> selectionModeComboBox = new ComboBox<>();
        selectionModeComboBox.getItems().setAll(CalendarView.SelectionModel.SelectionMode.values());
        selectionModeComboBox.valueProperty().bindBidirectional(calendarView.getSelectionModel().selectionModeProperty());
        options1.getChildren().add(selectionModeComboBox);

        VBox options2 = new VBox(10);

        HBox box = new HBox(50, calendarView, options1, options2);
        box.setPadding(new Insets(50));
        box.setLayoutX(10);
        box.setLayoutY(85);


        homePane.getChildren().add(box);
    }
    private static Node createOption(String name, BooleanProperty property) {
        CheckBox box = new CheckBox(name);
        box.selectedProperty().bindBidirectional(property);
        return box;
    }
    public void openProducts(ActionEvent event) throws IOException {
        Utility.moveTo(event,"Products");
    }
    public void openHome(ActionEvent event) throws IOException {

        Utility.moveTo(event,"Home");
    }
    public void openLogin(MouseEvent event) throws IOException {

        Utility.moveToLogin(event);
    }
    public void fullScreen(){

        currentStage.setFullScreen(!currentStage.isFullScreen());
    }
    public void openEmployees(ActionEvent event) throws IOException {
        Utility.moveTo(event,"Employees");
    }
    public void openServices(ActionEvent event) throws IOException {
        Utility.moveTo(event,"Services");
    }
    public void openStatistics(ActionEvent event) throws IOException {
        Utility.moveTo(event,"Statistics");
    }

}
