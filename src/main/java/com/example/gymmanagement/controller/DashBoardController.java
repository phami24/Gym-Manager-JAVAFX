package com.example.gymmanagement.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DashBoardController {
    private Stage stage;

    @FXML
    public void exit(MouseEvent event){
        stage.close();
    }
}
