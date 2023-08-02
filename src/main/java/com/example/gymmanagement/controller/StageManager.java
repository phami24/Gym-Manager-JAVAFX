package com.example.gymmanagement.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The StageManager class is used for managing JavaFX stages.
 * It provides methods to set scenes on the current stage, show the stage, and load different stages.
 */
public class StageManager {

    // The current stage to manage
    private Stage currentStage;

    /**
     * Set the scene for the current stage.
     * @param scene The scene to set on the current stage.
     */
    public void setSceneCurrentStage(Scene scene) {
        currentStage.setScene(scene);
    }

    /**
     * Set the current stage.
     * @param currentStage The stage to set as the current stage.
     */
    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    /**
     * Show the current stage.
     */
    public void showStage() {
        currentStage.show();
    }


    // Write additional methods here .....

    /**
     * Example method to load the login stage.
     * You can call this method to load the login stage in your application.
     */
    public void loadLoginStage() {
        // Implementation for loading the login stage goes here
        // For example: You can create a new scene for login and set it as the current scene.
    }

    /**
     * Example method to load the home stage.
     * You can call this method to load the home stage in your application.
     */
    public void loadHomeStage() {
        // Implementation for loading the home stage goes here
        // For example: You can create a new scene for the home screen and set it as the current scene.
    }

}



