package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Classes;
import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.repository.ClassesRepository;
import com.example.gymmanagement.model.repository.InstructorRepository;
import com.example.gymmanagement.validation.ValidateGymClass;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GymClassUpdateController implements Initializable {
    @FXML
    private TextField classNameField;

    @FXML
    private ComboBox<String> instructorComboBox;

    @FXML
    private TextField scheduleField;

    @FXML
    private TextField capacityField;

    List<String> errors = new ArrayList<>();

    private InstructorRepository instructorRepository = new InstructorRepository();
    private ClassesRepository classesRepository = new ClassesRepository();
    private final int gymClassID;
    private TableView<Classes> gymClassTableView;

    // Constructor
    public GymClassUpdateController(int gymClassID, TableView<Classes> gymClassTableView) {
        this.gymClassID = gymClassID;
        this.gymClassTableView = gymClassTableView;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gymClassTableView.refresh();
        showData(gymClassID);
    }

    private void showData(int gymClassID) {
        Classes gymClass = classesRepository.getClassById(gymClassID);
        if (gymClass == null) {
            showErrorAlert("Gym Class Not Found", "The gym class with ID " + gymClassID + " does not exist.");
            return;
        }

        // Populate the fields with gym class data
        classNameField.setText(gymClass.getClass_name());
        instructorComboBox.setValue(instructorRepository.getInstructorNameById(gymClass.getInstructor_id()));
        scheduleField.setText(gymClass.getSchedule());
        capacityField.setText(String.valueOf(gymClass.getCapacity()));
        List<Instructors> instructorsList = instructorRepository.getAllInstructors();
        ObservableList<String> instructorNames = FXCollections.observableArrayList();
        for (Instructors instructor : instructorsList) {
            instructorNames.add(instructor.getFirst_name() + " " + instructor.getLast_name());
        }
        instructorComboBox.setItems(instructorNames);

    }

    @FXML
    private void closeForm() {
        // Get the Stage for the update form and close it
        Stage currentStage = (Stage) classNameField.getScene().getWindow();
        currentStage.close();
    }

    private void showSuccessAlert() {
        Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Gym Class Updated");
        successAlert.setContentText("Update successfully.");
        successAlert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Update method for Class Name
    private void updateClassName(int gymClassID) {
        String newClassName = classNameField.getText();
        if (ValidateGymClass.isValidClassName(newClassName)) {
            classesRepository.updateClassName(gymClassID, newClassName);
        } else {
            errors.add("ERROR : Invalid class name");
        }
    }


    // Update method for Instructor
    private void updateInstructor(int gymClassID) {
        String newInstructor = instructorComboBox.getValue();
        if (ValidateGymClass.isValidInstructor(newInstructor)) {
            classesRepository.updateInstructor(gymClassID, newInstructor);
        } else {
            errors.add("ERROR : Invalid instructor");
        }
    }

    // Update method for Max Participants
    private void updateSchedule(int gymClassID) {
        String newSchedule = scheduleField.getText();
        if (ValidateGymClass.isValidSchedule(newSchedule)) {
            classesRepository.updateSchedule(gymClassID, newSchedule);
        } else {
            errors.add("ERROR : Invalid schedule");
        }
    }

    // Update method for Description
    private void updateCapacity(int gymClassID) {
        String newCapacityStr = capacityField.getText();
        if (ValidateGymClass.isValidCapacity(newCapacityStr)) {
            int newCapacity = Integer.parseInt(newCapacityStr);
            classesRepository.updateCapacity(gymClassID, newCapacity);
        } else {
            errors.add("ERROR : Invalid capacity");
        }
    }

    @FXML
    public void saveUpdate() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Update");
        confirmationAlert.setHeaderText("Update Equipment");
        confirmationAlert.setContentText("Do you want to update this equipment?");
        confirmationAlert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                updateClassName(gymClassID);
                updateInstructor(gymClassID);
                updateSchedule(gymClassID);
                updateCapacity(gymClassID);

                Platform.runLater(() -> {
                    // Update data in TableView
                    ObservableList<Classes> gymClassData = FXCollections.observableArrayList();
                    List<Classes> gymClassList = classesRepository.getAllClasses();
                    gymClassData.addAll(gymClassList);
                    gymClassTableView.setItems(gymClassData);
                    gymClassTableView.refresh();
                });

                if (!errors.isEmpty()) {
                    showErrorAlert("Errors", String.join("\n", errors));
                    errors.clear();
                } else {
                    showSuccessAlert();
                }
            }
        });

    }
}
