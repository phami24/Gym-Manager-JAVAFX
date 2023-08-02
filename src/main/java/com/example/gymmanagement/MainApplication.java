package com.example.gymmanagement;

import com.example.gymmanagement.controller.LoginController;
import com.example.gymmanagement.controller.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        StageManager stageManager = new StageManager();
        stageManager.setCurrentStage(stage);
        stageManager.loadHomeStage();
    }

    public static void main(String[] args) {
        launch();
    }
}