package com.example.gymmanagement.controller;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
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
        HomePageController homeStageController = new HomePageController();
        Scene homeStage = sceneManager.loadScene("home-page.fxml", homeStageController);
        mainStage.setScene(homeStage);
        setCurrentStage(mainStage);
        homeStageController.setStage(currentStage);
        homeStage.setFill(Color.TRANSPARENT);
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
                "            color: black; /* Màu xanh */\n" +
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
                "            }, 5000); // Hiển thị sau 5 giây\n" +
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

    public void loadEventManage() {
        Stage eventStage = new Stage();
        EventController eventController = new EventController();
        Scene eventScene = sceneManager.loadScene("event.fxml",eventController);
        eventStage.setScene(eventScene);
        setCurrentStage(eventStage);
        eventController.setStage(currentStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        eventScene.setFill(Color.TRANSPARENT);
        showStage();
    }

    public void loadMemberControlStage() {
        Stage memberStage = new Stage();
        MembersController membersController = new MembersController();
        Scene memberScene = sceneManager.loadScene("member-control.fxml",membersController);
        memberStage.setScene(memberScene);
        setCurrentStage(memberStage);
        membersController.setStage(currentStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        memberScene.setFill(Color.TRANSPARENT);
        showStage();
    }

    public void loadClassesControlStage() {
        Stage classStage = new Stage();
        GymClassController gymClassController = new GymClassController();
        Scene classScenne = sceneManager.loadScene("gymclass-control.fxml", gymClassController);
        classStage.setScene(classScenne);
        setCurrentStage(classStage);
        gymClassController.setStage(classStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        classScenne.setFill(Color.TRANSPARENT);
        showStage();
    }

    public void loadInstructorControlStage() {
        Stage instructorStage = new Stage();
        InstructorController instructorController = new InstructorController();
        Scene homeStage = sceneManager.loadScene("instructor-control.fxml",instructorController);
        instructorStage.setScene(homeStage);
        setCurrentStage(instructorStage);
        instructorController.setStage(instructorStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        homeStage.setFill(Color.TRANSPARENT);
        showStage();
    }

    public void loadEquipmentControlStage() {
        Stage equipmentStage = new Stage();
        EquipmentController equipmentController = new EquipmentController();
        Scene homeStage = sceneManager.loadScene("equipment-control.fxml",equipmentController);
        equipmentStage.setScene(homeStage);
        setCurrentStage(equipmentStage);
        equipmentController.setStage(equipmentStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        homeStage.setFill(Color.TRANSPARENT);
        showStage();
    }

    public void loadMemberAddFormDialog(MemberAddFormController addFormController) {
        Stage memberAddFormDialogStage = new Stage();
        memberAddFormDialogStage.initModality(Modality.APPLICATION_MODAL);
        Scene memberAddFormScene = sceneManager.loadScene("member-add-form.fxml", addFormController);
        memberAddFormDialogStage.setScene(memberAddFormScene);
        setCurrentStage(memberAddFormDialogStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        memberAddFormScene.setFill(Color.TRANSPARENT);
        showStage();
    }

    public void loadMemberUpdateFormDialog(MemberUpdateFormController updateFormController) {
        Stage memberUpdateFormDialogStage = new Stage();
        memberUpdateFormDialogStage.initModality(Modality.APPLICATION_MODAL);
        Scene memberAddFormScene = sceneManager.loadScene("member-data-form.fxml", updateFormController);
        memberUpdateFormDialogStage.setScene(memberAddFormScene);
        setCurrentStage(memberUpdateFormDialogStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        memberAddFormScene.setFill(Color.TRANSPARENT);
        showStage();
    }

    public void closeStage() {
        currentStage.close();
    }

    public void loadInstructorAddFormDialog(InstructorAddFormController addFormController) {
        Stage instructorAddFormDialogStage = new Stage();
        instructorAddFormDialogStage.initModality(Modality.APPLICATION_MODAL);
        Scene instructorAddFormScene = sceneManager.loadScene("instructor-add-form.fxml", addFormController);
        instructorAddFormDialogStage.setScene(instructorAddFormScene);
        setCurrentStage(instructorAddFormDialogStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        instructorAddFormScene.setFill(Color.TRANSPARENT);
        showStage();
    }

    public void loadInstructorUpdateFormDialog(InstructorUpdateFormController updateFormController) {
        Stage instructorUpdateFormDialogStage = new Stage();
        instructorUpdateFormDialogStage.initModality(Modality.APPLICATION_MODAL);
        Scene instructorUpdateFormScene = sceneManager.loadScene("instructor-data-form.fxml", updateFormController);
        instructorUpdateFormDialogStage.setScene(instructorUpdateFormScene);
        setCurrentStage(instructorUpdateFormDialogStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        instructorUpdateFormScene.setFill(Color.TRANSPARENT);
        showStage();
    }

    public void loadGymClassAddFormDialog(GymClassAddFormController addFormController) {
        Stage classAddFormDialogStage = new Stage();
        classAddFormDialogStage.initModality(Modality.APPLICATION_MODAL);
        Scene classAddFormScene = sceneManager.loadScene("class-add-form.fxml", addFormController);
        classAddFormDialogStage.setScene(classAddFormScene);
        setCurrentStage(classAddFormDialogStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        classAddFormScene.setFill(Color.TRANSPARENT);
        showStage();
    }

    public void loadClassUpdateFormDialog(GymClassUpdateController updateFormController) {
        Stage classUpdateFormDialogStage = new Stage();
        classUpdateFormDialogStage.initModality(Modality.APPLICATION_MODAL);
        Scene classUpdateFormScene = sceneManager.loadScene("class-data-form.fxml", updateFormController);
        classUpdateFormDialogStage.setScene(classUpdateFormScene);
        setCurrentStage(classUpdateFormDialogStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        classUpdateFormScene.setFill(Color.TRANSPARENT);
        showStage();
    }

    public void loadEquipmentAddFormDialog(EquipmentAddFormController addFormController) {
        Stage equipmentAddFormDialogStage = new Stage();
        equipmentAddFormDialogStage.initModality(Modality.APPLICATION_MODAL);
        Scene equipmentAddFormScene = sceneManager.loadScene("equipment-add-form.fxml", addFormController);
        equipmentAddFormDialogStage.setScene(equipmentAddFormScene);
        setCurrentStage(equipmentAddFormDialogStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        equipmentAddFormScene.setFill(Color.TRANSPARENT);
        showStage();
    }

    public void loadEquipmentUpdateFormDialog(EquipmentUpdateController updateFormController) {
        Stage equipmentUpdateFormDialogStage = new Stage();
        equipmentUpdateFormDialogStage.initModality(Modality.APPLICATION_MODAL);
        Scene equipmentUpdateFormScene = sceneManager.loadScene("equipment-data-form.fxml", updateFormController);
        equipmentUpdateFormDialogStage.setScene(equipmentUpdateFormScene);
        setCurrentStage(equipmentUpdateFormDialogStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        equipmentUpdateFormScene.setFill(Color.TRANSPARENT);
        showStage();
    }

    public void loadDashBoard(){
        Stage dashBoardStage = new Stage();
        dashBoardStage.initModality(Modality.APPLICATION_MODAL);
        DashBoardController dashBoardController = new DashBoardController();
        Scene dashBoardScene = sceneManager.loadScene("dashboardVIEW.fxml",dashBoardController);
        dashBoardStage.setScene(dashBoardScene);
        setCurrentStage(dashBoardStage);
        dashBoardController.setStage(dashBoardStage);
        currentStage.initStyle(StageStyle.TRANSPARENT);
        dashBoardScene.setFill(Color.TRANSPARENT);
        showStage();
    }
    public void loadMemberType(){
        Stage memberTypeStage = new Stage();
        memberTypeStage.initModality(Modality.APPLICATION_MODAL);
        Scene dashBoardScene = sceneManager.loadScene("member-type.fxml");
        memberTypeStage.setScene(dashBoardScene);
        setCurrentStage(memberTypeStage);
//        currentStage.initStyle(StageStyle.TRANSPARENT);
//        dashBoardScene.setFill(Color.TRANSPARENT);
        showStage();
    }

}



