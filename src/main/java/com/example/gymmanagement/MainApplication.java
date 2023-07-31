package com.example.gymmanagement;

import com.example.gymmanagement.model.service.MembersService;
import com.example.gymmanagement.model.service.impl.MembersServiceImpl;
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

        MembersService membersService = new MembersServiceImpl();
        System.out.println(membersService.getTotalMembers());
    }

    public static void main(String[] args) {
        launch();
    }


}