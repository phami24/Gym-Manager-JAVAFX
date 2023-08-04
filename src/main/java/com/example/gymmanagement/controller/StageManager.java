package com.example.gymmanagement.controller;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The StageManager class is used for managing JavaFX stages.
 * It provides methods to set scenes on the current stage, show the stage, and load different stages.
 */
public class StageManager {

    // The current stage to manage
    private Stage currentStage;
    private final SceneManager sceneManager = new SceneManager();

    /**
     * Set the scene for the current stage.
     *
     * @param scene The scene to set on the current stage.
     */
    public void setSceneCurrentStage(Scene scene) {
        currentStage.setScene(scene);
    }

    /**
     * Set the current stage.
     *
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
        LoginController loginController = new LoginController();
        Scene loginScene = sceneManager.loadScene("login.fxml", loginController);
        loginController.setStage(currentStage);

        setSceneCurrentStage(loginScene);
        loginScene.setFill(Color.TRANSPARENT);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        showStage();
    }

    /**
     * Example method to load the home stage.
     * You can call this method to load the home stage in your application.
     */
    public void loadHomeStage() {
        // Implementation for loading the home stage goes here
        // For example: You can create a new scene for the home screen and set it as the current scene.
        Stage mainStage = new Stage();
//        HomePageController homeStageController = new HomePageController();
        Scene homeStage = sceneManager.loadScene("member.fxml");
//        homeStageController.setStage(currentStage);
        mainStage.setScene(homeStage);
        setCurrentStage(mainStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        showStage();
    }

    public void loadAnimationChangeStage() {
        Stage animationStage = new Stage();
        animationStage.initStyle(StageStyle.TRANSPARENT);

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Mã HTML để nhúng hoạt ảnh Lottie
        String htmlContent = "<html>\n" +
                "<head>\n" +
                "    <title>Lottie Animation</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            display: flex;\n" +
                "            flex-direction: column; /* Hiển thị theo chiều dọc */\n" +
                "            justify-content: center; /* Căn giữa theo chiều dọc */\n" +
                "            align-items: center; /* Căn giữa theo chiều ngang */\n" +
                "            height: 100vh;\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "        lottie-player {\n" +
                "            width: 300px;\n" +
                "            height: 300px;\n" +
                "        }\n" +
                "        .login-success {\n" +
                "            display: none; /* Ẩn nội dung ban đầu */\n" +
                "            margin-top: 20px; /* Khoảng cách từ hoạt ảnh đến văn bản */\n" +
                "            font-size: 24px;\n" +
                "            color: green; /* Màu xanh */\n" +
                "            opacity: 0; /* Ẩn đi ban đầu */\n" +
                "            transform: translateY(20px); /* Hiệu ứng dịch lên 20px */\n" +
                "            transition: opacity 0.5s, transform 0.5s; /* Hiệu ứng trong 0.5s */\n" +
                "        }\n" +
                "    </style>\n" +
                "    <script src=\"https://unpkg.com/@lottiefiles/lottie-player@latest/dist/lottie-player.js\"></script>\n" +
                "    <script>\n" +
                "        document.addEventListener('DOMContentLoaded', function() {\n" +
                "            setTimeout(function() {\n" +
                "                var loginSuccessDiv = document.querySelector('.login-success');\n" +
                "                loginSuccessDiv.style.display = 'block';\n" +
                "                loginSuccessDiv.style.opacity = '1';\n" +
                "                loginSuccessDiv.style.transform = 'translateY(0)';\n" +
                "            }, 3000); // Hiển thị sau 2 giây\n" +
                "        });\n" +
                "    </script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <lottie-player src=\"https://lottie.host/d30489d4-7899-420e-a2d8-cdc14e011c59/P8MGqKVZum.json\" background=\"#fff\" speed=\"1\" loop autoplay direction=\"1\" mode=\"normal\"></lottie-player>\n" +
                "    <div class=\"login-success\">Login Success</div>\n" +
                "</body>\n" +
                "</html>";




        webEngine.loadContent(htmlContent);

        Scene scene = new Scene(webView, 800, 550);
        setCurrentStage(animationStage);
        setSceneCurrentStage(scene);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        showStage();
    }

    public void closeStage() {
        currentStage.close();
    }

}



