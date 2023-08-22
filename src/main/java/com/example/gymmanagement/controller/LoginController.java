package com.example.gymmanagement.controller;

import com.example.gymmanagement.database.JDBCConnect;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    @FXML
    private TextField password_show;

    @FXML
    private Button hidden;
    @FXML
    private Button show;


    @FXML

    private Stage stage;

    private JDBCConnect jdbcConnect;

    private StageManager stageManager;

    public LoginController() {
        this.jdbcConnect = new JDBCConnect();
        this.stageManager = new StageManager();
    }

    private boolean isPasswordVisible = false;
    private Timeline hidePasswordTimeline;

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

        password_show.setVisible(false);

        hidden.setOnAction(event -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                // If the checkbox is selected, show the password
                showPassword();
            } else {
                // If the checkbox is not selected, hide the password
                hidePassword();
            }
        });

        // Initialize the Timeline to hide the password after 5 seconds (5000 milliseconds)
        hidePasswordTimeline = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hidePassword();
            }
        }));
        hidePasswordTimeline.setCycleCount(1); // Only run once
    }

    private void showPassword() {
        passwordField.setVisible(false);
        password_show.setText(passwordField.getText());
        password_show.setVisible(true);
        hidden.setVisible(false);
        show.setVisible(true);
        hidePasswordTimeline.stop(); // Stop any previous timeline
        hidePasswordTimeline.play(); // Start the timeline to hide the password after 5 seconds
    }

    private void hidePassword() {
        password_show.setVisible(false);
        passwordField.setVisible(true);
        hidden.setVisible(true);
        show.setVisible(false);
        hidePasswordTimeline.stop(); // Stop any previous timeline
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void close() {
        javafx.application.Platform.exit();
    }

    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidCredentials(username, password)) {
            // Tạo thông báo khi đăng nhập thành công
//            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
//            successAlert.setTitle("Login Successful");
//            successAlert.setHeaderText(null);
//            successAlert.setContentText("Welcome, " + username + "!");
            // Lấy kích thước của màn hình
//            Screen screen = Screen.getPrimary();
//            Rectangle2D bounds = screen.getBounds();

            // Thiết lập vị trí của Alert ở góc dưới cùng bên phải của màn hình
//            double xPosition = bounds.getMaxX() - successAlert.getHeight();
//            double yPosition = bounds.getMaxY() - successAlert.getWidth();
//            successAlert.setX(xPosition);
//            successAlert.setY(yPosition);

//            stageManager.loadHomeStage();
            stageManager.loadAnimationChangeStage();
            stage.close();

            // Tạo một Task để chờ 4 giây
            Task<Void> waitTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(6000); // Đợi 5 giây
                    return null;
                }
            };

            // Gán hành động khi Task hoàn thành (sau 4 giây)
            waitTask.setOnSucceeded(eventTask -> {
//                successAlert.showAndWait();
                stageManager.closeStage();
                stageManager.loadHomeStage(); // Load home page sau khi chờ 5 giây
            });

            // Bắt đầu Task
            new Thread(waitTask).start();
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
