package com.example.gymmanagement.controller;

import com.example.gymmanagement.database.JDBCConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Label errorMess;

    @FXML
    private CheckBox keepLoggedInCheckbox;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private AnchorPane sideBar;

    private Stage stage;

    private JDBCConnect jdbcConnect;

    private StageManager stageManager;
    public LoginController() {
        this.jdbcConnect = new JDBCConnect();
        this.stageManager = new StageManager();
    }


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
    void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidCredentials(username, password)) {
            // Tạo thông báo khi đăng nhập thành công
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Login Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Welcome, " + username + "!");
            successAlert.setX(stage.getWidth() - successAlert.getWidth());
            successAlert.setY(stage.getHeight() - successAlert.getHeight());
            stage.close();
            stageManager.loadHomeStage();

            successAlert.showAndWait();
        } else {
            errorMess.setText("Invalid credentials. Please try again.");
        }
    }


    private boolean isValidCredentials(String username, String password) {
        boolean isValid = false;

        // Truy vấn cơ sở dữ liệu để kiểm tra cặp username và password
        try {
            Connection connection = jdbcConnect.getJDBCConnection();
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            isValid = resultSet.next(); // Kiểm tra xem có kết quả trả về từ cơ sở dữ liệu hay không

            // Đóng các tài nguyên
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }



}
