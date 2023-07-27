package com.example.gymmanagement.stage;

import com.example.gymmanagement.controller.HomeStageController;
import com.example.gymmanagement.controller.LoginController;
import com.example.gymmanagement.scene.StageController;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageManagement {
    private Stage currentStage;
    private final StageController stageController = new StageController();

    public void setSceneCurrentStage(Scene scene) {
        currentStage.setScene(scene);
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void showStage() {
        currentStage.show();
    }

    public void loadLoginStage() {
        LoginController loginController = new LoginController();
        Scene loginScene = stageController.loadStage("/com/example/gymmanagement/view/login.fxml" + loginController);
        loginController.setStage(currentStage);

        setSceneCurrentStage(loginScene);
        loginScene.setFill(Color.TRANSPARENT);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        showStage();
    }

    public void loadHomeStage() {
        Stage mainStage = new Stage();
        HomeStageController homeStageController = new HomeStageController();
        Scene homeStage = stageController.loadStage("/com/example/gymmanagement/view/home-stage.fxml" + homeStageController);
        homeStageController.setStage(currentStage);
        mainStage.setScene(homeStage);
        setCurrentStage(mainStage);
        showStage();
    }
}
