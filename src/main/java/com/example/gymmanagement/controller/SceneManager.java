package com.example.gymmanagement.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * The SceneManager class is used for loading FXML scenes in the JavaFX application.
 * It provides methods to load an FXML scene and associate it with a controller if needed.
 */
public class SceneManager {

    /**
     * Load an FXML scene and associate it with a specific controller.
     * @param fxmlFileName The name of the FXML file to load (including the path).
     * @param controller The controller object that will handle events and actions in the scene.
     * @return The Scene object representing the loaded FXML scene.
     */
    public Scene loadScene(String fxmlFileName, Object controller) {
        Scene scene = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();
            scene = new Scene(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scene;
    }

    /**
     * Load an FXML scene without associating it with a specific controller.
     * @param fxmlFileName The name of the FXML file to load (including the path).
     * @return The Scene object representing the loaded FXML scene.
     */
    public Scene loadScene(String fxmlFileName) {
        Scene scene = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = fxmlLoader.load();
            scene = new Scene(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scene;
    }
}
