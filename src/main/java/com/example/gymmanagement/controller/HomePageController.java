package com.example.gymmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    private Button classButton;

    @FXML
    private Button dashBoardButton;

    @FXML
    private Button employeeButton;

    @FXML
    private Label exit;

    @FXML
    private Button gymEquipbt;

    @FXML
    private Label logOutbt;

    @FXML
    private Button membersButton;

    @FXML
    private Button emailButton;

    @FXML
    void logOut(MouseEvent event) {
        stage.close();
        Stage loginStage = new Stage();
        stageManager.setCurrentStage(loginStage);
        stageManager.loadLoginStage();
    }
    @FXML
    public void close() {
        javafx.application.Platform.exit();
    }

    @FXML
    void toClass(MouseEvent event) {
        stageManager.loadClassesControlStage();
    }

    @FXML
    void toDashBoard(MouseEvent event) {
        stageManager.loadDashBoard();
    }

    @FXML
    void toEmployees(MouseEvent event) {
        stageManager.loadInstructorControlStage();
    }

    @FXML
    void toEquipment(MouseEvent event) {
        stageManager.loadEquipmentControlStage();
    }

    @FXML
    void toMembers(MouseEvent event) {
        stageManager.loadMemberControlStage();
    }

    @FXML
    void toEvent(MouseEvent event){stageManager.loadEventManage();}

    public void exit(MouseEvent mouseEvent) {
        stage.close();
//        Stage loginStage = new Stage();
//        stageManager.setCurrentStage(loginStage);
//        stageManager.loadLoginStage();
    }

}