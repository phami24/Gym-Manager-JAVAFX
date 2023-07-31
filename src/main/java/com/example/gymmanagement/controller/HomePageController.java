package com.example.gymmanagement.controller;

import javafx.event.ActionEvent;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    void closeHomePage() {
        stage.close();
    }

    @FXML
    void toDashBoard(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gymmanagement/view/home.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void toMembers(MouseEvent mouseEvent) {
    }

    public void toEmployees(MouseEvent mouseEvent) {
    }

    public void toClass(MouseEvent mouseEvent) {
    }
}
