package com.example.gymmanagement;

import com.example.gymmanagement.controller.HomePageController;
import com.example.gymmanagement.controller.LoginController;
import com.example.gymmanagement.controller.StageManager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
//        Parent root = loader.load();
//        LoginController controller = loader.getController();
//
//        Scene scene = new Scene(root);
//        scene.setFill(Color.TRANSPARENT);
//        stage.initStyle(StageStyle.TRANSPARENT);
//
//        stage.setScene(scene);
//        controller.setStage(stage);
//        stage.show();
        StageManager stageManager = new StageManager();
        stageManager.setCurrentStage(stage);
        stageManager.loadHomePage();
    }

    public static void main(String[] args) {
        launch();
    }
}
