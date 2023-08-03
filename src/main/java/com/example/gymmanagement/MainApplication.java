package com.example.gymmanagement;

import com.example.gymmanagement.controller.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        StageManager stageManager = new StageManager();
        stageManager.setCurrentStage(stage);
        stageManager.loadLoginStage();


    }

    public static void main(String[] args) {
        launch();
    }


}