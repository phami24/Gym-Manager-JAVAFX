package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.*;
import com.example.gymmanagement.model.repository.*;
import com.example.gymmanagement.validation.ValidateGymClass;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GymClassAddFormController {
    @FXML
    private TextField classIdField;

    @FXML
    private TextField classNameField;

    @FXML
    private ComboBox<String> instructorComboBox;

    @FXML
    private TextField scheduleField;

    @FXML
    private TextField capacityField;

    @FXML
    private Button buttonAdd;

    private ClassesRepository classesRepository = new ClassesRepository();
    private InstructorRepository instructorRepository = new InstructorRepository();
    private TableView<Classes> gymClassTableView;

    // Constructor
    public GymClassAddFormController(TableView<Classes> gymClassTableView) {
        this.gymClassTableView = gymClassTableView;
    }

    @FXML
    private void initialize() {
        populateComboBox();
        buttonAdd.setOnAction(event -> {
            validateAndAddGymClass();
        });
    }

    // Xóa dữ liệu khỏi các trường nhập liệu
    private void clearFields() {
        classIdField.clear();
        classNameField.clear();
        instructorComboBox.getSelectionModel().clearSelection();
        scheduleField.clear();
        capacityField.clear();
    }

    private void populateComboBox() {
        // Populate Instructor ComboBox
        List<Instructors> instructorList = instructorRepository.getAllInstructors();
        for (Instructors instructor : instructorList) {
            instructorComboBox.getItems().add(instructor.getFirst_name() + " " + instructor.getLast_name());
        }
    }

    private void addGymClass() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Add");
        confirmationAlert.setHeaderText("Add Gym Class");
        confirmationAlert.setContentText("Do you want to add this gym class?");
        confirmationAlert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                String className = classNameField.getText();
                String instructorName = instructorComboBox.getValue();
                String schedule = scheduleField.getText();
                int capacity = Integer.parseInt(capacityField.getText());

                int instructorId = instructorRepository.getInstructorIdByName(instructorName);

                Classes newGymClass = new Classes();
                newGymClass.setClass_name(className);
                newGymClass.setInstructor_id(instructorId);
                newGymClass.setSchedule(schedule);
                newGymClass.setCapacity(capacity);

                classesRepository.addClass(newGymClass);

                Platform.runLater(() -> {
                    ObservableList<Classes> gymClassData = FXCollections.observableArrayList();
                    List<Classes> gymClassList = classesRepository.getAllClasses();
                    gymClassData.addAll(gymClassList);
                    gymClassTableView.setItems(gymClassData);
                    gymClassTableView.refresh();
                });

                showSuccessAlert();
                clearFields();
            }
        });
    }

    private boolean validateAndAddGymClass() {
        List<String> errors = new ArrayList<>();

        String className = classNameField.getText();
        String instructorName = instructorComboBox.getValue();
        String schedule = scheduleField.getText();
        String capacityStr = capacityField.getText();

        if (!ValidateGymClass.isValidClassName(className)) {
            errors.add("Error: Invalid Class Name - Please enter a valid class name.");
        }

        if (!ValidateGymClass.isValidInstructor(instructorName)) {
            errors.add("Error: Invalid Instructor - Please select a valid instructor.");
        }

        if (!ValidateGymClass.isValidSchedule(schedule)) {
            errors.add("Error: Invalid Schedule - Please enter a valid schedule.");
        }

        if (!ValidateGymClass.isValidCapacity(capacityStr)) {
            errors.add("Error: Invalid Capacity - Please enter a valid capacity.");
        }

        if (!errors.isEmpty()) {
            showErrorAlert("Validation Errors", String.join("\n", errors));
            return false;
        }

        addGymClass();
        return true;
    }

    private void showSuccessAlert() {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Gym Class Added");
        successAlert.setContentText("Gym class has been added successfully.");
        successAlert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void close(MouseEvent event) {
        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
