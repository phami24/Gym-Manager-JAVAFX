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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    public Label logo;
    public Label setting;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Button dashBoardButton;
    @FXML
    private Button employeeButton;
    @FXML
    private Button membersButton;
    @FXML
    private Button classButton;

    private StageManager stageManager = new StageManager();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    void closeHomePage() {
        stage.close();
    }

    @FXML
    void toDashBoard(MouseEvent event) throws IOException {
        stageManager.loadMemberControlStage(); // loadDashBoard
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
        closeHomePage();
    }
}