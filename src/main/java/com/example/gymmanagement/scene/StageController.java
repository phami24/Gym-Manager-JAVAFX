package com.example.gymmanagement.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class StageController {
    //method to
    public Scene loadStage(String fxmlFileName, Object controller) {
        Scene scene = null;
        try {
            //load file FXML from specified file name
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/gymmanagement/view" + fxmlFileName));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();
            //add new scene from root and specified size
            scene = new Scene(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scene;
    }
    public Scene loadStage(String fxmlFileName) {
        Scene scene = null;
        try {
            //load file FXML from specified file name
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/gymmanagement/view" + fxmlFileName));
            Parent root = fxmlLoader.load();
            //add new scene from root and specified size
            scene = new Scene(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scene;
    }
}
