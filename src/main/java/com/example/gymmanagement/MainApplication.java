package com.example.gymmanagement;

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
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/gymmanagement/view/member.fxml"));
        Scene scene = new Scene(root);

//        MouseDragHandler mouseDragHandler = new MouseDragHandler(stage);
//        root.setOnMousePressed(mouseDragHandler);
//        root.setOnMouseDragged(mouseDragHandler);
//        root.setOnMouseReleased(mouseDragHandler);


        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}