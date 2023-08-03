package com.example.gymmanagement.controller;

import com.example.gymmanagement.database.JDBCConnect;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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

            stage.close();
            stageManager.loadAnimationChangeStage();

            // Tạo một Task để chờ 4 giây
            Task<Void> waitTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(5000); // Đợi 4 giây
                    return null;
                }
            };

            // Gán hành động khi Task hoàn thành (sau 4 giây)
            waitTask.setOnSucceeded(eventTask -> {
//                successAlert.showAndWait();
                stageManager.closeStage();
                stageManager.loadHomeStage(); // Load home page sau khi chờ 4 giây
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
