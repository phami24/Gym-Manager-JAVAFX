package com.example.gymmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private Button gymEquipbt;

    @FXML
    private Button logOutbt;

    @FXML
    private Button membersButton;

    @FXML
    private Button emailButton;

    @FXML
    void logOut(MouseEvent event) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Are you sure you want to log out?");
        confirmationDialog.setContentText("Press OK to log out or Cancel to stay logged in.");

        // Show the dialog and wait for a result
        ButtonType result = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);

        // If the user clicks OK, proceed with the logout
        if (result == ButtonType.OK) {
            // Close the current stage
            stage.close();

            // Create a new login stage
            Stage loginStage = new Stage();

            // Set the current stage in the stageManager
            stageManager.setCurrentStage(loginStage);

            // Load and display the login stage
            stageManager.loadLoginStage();
        }
    }
    @FXML
    public void close() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Are you sure you want to CLOSE?");
        confirmationDialog.setContentText("Press OK to close or Cancel.");

        // Show the dialog and wait for a result
        ButtonType result = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);

        // If the user clicks OK, proceed with the logout
        if (result == ButtonType.OK) {
            // Close the current stage
            stage.close();

        }
    }

    @FXML
    void toClass(MouseEvent event) {
        stageManager.loadClassesControlStage();
        stage.close();
    }

    @FXML
    void toDashBoard(MouseEvent event) {
        stageManager.loadDashBoard();
        stage.close();
    }

    @FXML
    void toEmployees(MouseEvent event) {
        stageManager.loadInstructorControlStage();
        stage.close();
    }

    @FXML
    void toEquipment(MouseEvent event) {
        stageManager.loadEquipmentControlStage();
        stage.close();
    }

    @FXML
    void toMembers(MouseEvent event) {
        stageManager.loadMemberControlStage();
        stage.close();
    }

    @FXML
    void toEvent(MouseEvent event){
        stageManager.loadEventManage();
        stage.close();
    }

    public void exit(MouseEvent mouseEvent) {
        stage.close();
//        Stage loginStage = new Stage();
//        stageManager.setCurrentStage(loginStage);
//        stageManager.loadLoginStage();
    }

}