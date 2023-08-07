package com.example.gymmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.css.SizeUnits.S;

public class HomePageController implements Initializable {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private StageManager stageManager = new StageManager();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void toDashBoard(MouseEvent event) throws IOException {
        stageManager.loadDoardBoard();
    }


    public void toMembers(MouseEvent mouseEvent) {
        stageManager.loadMemberControlStage();
    }

    public void toEmployees(MouseEvent mouseEvent) {
        stageManager.loadInstructorControlStage();
    }

    public void toClass(MouseEvent mouseEvent) {
        stageManager.loadClassesControlStage();
    }

    public void toEquipment(MouseEvent mouseEvent) {
        stageManager.loadEquipmentControlStage();
    }

    public void exit(MouseEvent mouseEvent) {
        stage.close();
        Stage loginStage = new Stage();
        stageManager.setCurrentStage(loginStage);
        stageManager.loadLoginStage();
    }

    public void close() {
        stage.close();
    }
}