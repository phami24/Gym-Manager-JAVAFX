package com.example.gymmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private double x = 0, y = 0;

    @FXML
    private AnchorPane sideBar;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sideBar.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        sideBar.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void closeProgram(MouseEvent event) {
        stage.close();
    }

    @FXML
    void authenticateLogin(MouseEvent event) throws SQLException, IOException {
//        JDBCConnect jdbcConnect = new JDBCConnect();
//        Connection connection = jdbcConnect.getJDBCConnection();
//        Statement statement = connection.createStatement();
//        String query = "SELECT * FROM admin";
//        ResultSet resultSet = statement.executeQuery(query);

//        while (resultSet.next()) {
            // Lấy giá trị từ các cột trong ResultSet
            if (username.getText().equals("admin") && password.getText().equals("1234")) {
                Stage mainStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gymmanagement/view/home.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
                stage.close();


//            }

        }
//        resultSet.close();
//        statement.close();
//        connection.close();
    }
}