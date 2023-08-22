package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.InstructorRepository;
import com.example.gymmanagement.validation.ValidateInstructor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InstructorUpdateFormController implements Initializable {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TextField genderField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField addressField;

    @FXML
    private DatePicker hireDatePicker;

    @FXML
    private TextField experienceYearsField;

    @FXML
    private TextField baseSalaryField;

    @FXML
    private TextField salaryField;

    @FXML
    private ComboBox<String> statusComboBox;

    List<String> errors = new ArrayList<>();

    private InstructorRepository instructorRepository = new InstructorRepository();
    private final int instructorID;
    private TableView<Instructors> instructorTableView;

    // Constructor
    public InstructorUpdateFormController(int instructorID, TableView<Instructors> instructorTableView) {
        this.instructorID = instructorID;
        this.instructorTableView = instructorTableView;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instructorTableView.refresh();
        showData(instructorID);
    }

    private void showData(int instructorID) {
        Instructors instructor = instructorRepository.getInstructorById(instructorID);
        if (instructor == null) {
            // Handle the case where the instructor with the given ID doesn't exist
            showErrorAlert("Instructor Not Found", "The instructor with ID " + instructorID + " does not exist.");
            return;
        }

        // Populate the fields with instructor data
        firstNameField.setText(instructor.getFirst_name());
        lastNameField.setText(instructor.getLast_name());
        dobPicker.setValue(LocalDate.parse(instructor.getDob())); // Assuming getDob() returns a LocalDate
        genderField.setText(instructor.getGender());
        emailField.setText(instructor.getEmail());
        phoneNumberField.setText(instructor.getPhone_number());
        addressField.setText(instructor.getAddress());
        hireDatePicker.setValue(LocalDate.parse(instructor.getHireDate())); // Assuming getHireDate() returns a LocalDate
        experienceYearsField.setText(String.valueOf(instructor.getExperienceYears()));
        baseSalaryField.setText(String.valueOf(instructor.getBaseSalary()));
        salaryField.setText(String.valueOf(instructor.getSalary()));

        // You might also need to fetch and populate ComboBox items for instructor status
    }

    @FXML
        private void closeForm() {
        // Get the Stage for the update form and close it
        Stage currentStage = (Stage) firstNameField.getScene().getWindow();
        currentStage.close();
    }

    private void showSuccessAlert() {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Instructor Updated");
        successAlert.setContentText("Update successfully.");
        successAlert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Update method for First Name
    private void updateFirstName(int instructorID) {
        String newFirstName = firstNameField.getText();
        if (ValidateInstructor.isValidFirstName(newFirstName)) {
            instructorRepository.updateFirstName(instructorID, newFirstName);
        } else {
            errors.add("ERROR : Invalid first name");
        }
    }

    // Update method for Last Name
    private void updateLastName(int instructorID) {
        String newLastName = lastNameField.getText();
        if (ValidateInstructor.isValidLastName(newLastName)) {
            instructorRepository.updateLastName(instructorID, newLastName);
        } else {
            errors.add("ERROR : Invalid last name");
        }
    }

    // Update method for Date of Birth
    private void updateDob(int instructorID) {
        String newDob = dobPicker.getValue().toString();
        if (ValidateInstructor.isValidDate(newDob)) {
            instructorRepository.updateDob(instructorID, newDob);
        } else {
            errors.add("ERROR : Invalid date of birth");
        }
    }

    // Update method for Gender
    private void updateGender(int instructorID) {
        String newGender = genderField.getText();
        if (ValidateInstructor.isValidGender(newGender)) {
            instructorRepository.updateGender(instructorID, newGender);
        } else {
            errors.add("ERROR : Invalid gender");
        }
    }

    // Update method for Email
    private void updateEmail(int instructorID) {
        String newEmail = emailField.getText();
        if (ValidateInstructor.isValidEmail(newEmail)) {
            instructorRepository.updateEmail(instructorID, newEmail);
        } else {
            errors.add("ERROR : Invalid email");
        }
    }

    // Update method for Phone Number
    private void updatePhoneNumber(int instructorID) {
        String newPhoneNumber = phoneNumberField.getText();
        if (ValidateInstructor.isValidPhoneNumber(newPhoneNumber)) {
            instructorRepository.updatePhoneNumber(instructorID, newPhoneNumber);
        } else {
            errors.add("ERROR : Invalid phone number");
        }
    }

    // Update method for Address
    private void updateAddress(int instructorID) {
        String newAddress = addressField.getText();
        if (ValidateInstructor.isValidAddress(newAddress)) {
            instructorRepository.updateAddress(instructorID, newAddress);
        } else {
            errors.add("ERROR : Invalid address");
        }
    }

    // Update method for Hire Date
    private void updateHireDate(int instructorID) {
        LocalDate newHireDate = hireDatePicker.getValue();
        if (ValidateInstructor.isValidDate(newHireDate.toString())) {
            instructorRepository.updateHireDate(instructorID, newHireDate.toString());
        } else {
            errors.add("ERROR : Invalid hire date");
        }
    }

    // Update method for Experience Years
    private void updateExperienceYears(int instructorID) {
        String newExperienceYearsStr = experienceYearsField.getText();
        if (ValidateInstructor.isValidExperienceYears(newExperienceYearsStr)) {
            int newExperienceYears = Integer.parseInt(newExperienceYearsStr);
            instructorRepository.updateExperienceYears(instructorID, newExperienceYears);
        } else {
            errors.add("ERROR : Invalid experience years");
        }
    }

    // Update method for Base Salary
    // Update method for Base Salary
    private void updateBaseSalary(int instructorId) {
        String newBaseSalaryStr = baseSalaryField.getText();
        if (ValidateInstructor.isValidBaseSalary(newBaseSalaryStr)) {
            double newBaseSalary = Double.parseDouble(newBaseSalaryStr);
            instructorRepository.updateBaseSalary(instructorId, newBaseSalary);
        } else {
            errors.add("ERROR : Invalid base salary");
        }
    }

    // Update method for Salary
    private void updateSalary(int instructorId) {
        String newSalaryStr = salaryField.getText();
        if (ValidateInstructor.isValidSalary(newSalaryStr)) {
            double newSalary = Double.parseDouble(newSalaryStr);
            instructorRepository.updateSalary(instructorId, newSalary);
        } else {
            errors.add("ERROR : Invalid salary");
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
                updateFirstName(instructorID);
                updateLastName(instructorID);
                updateDob(instructorID);
                updateGender(instructorID);
                updateEmail(instructorID);
                updatePhoneNumber(instructorID);
                updateAddress(instructorID);
                updateHireDate(instructorID);
                updateExperienceYears(instructorID);
                updateBaseSalary(instructorID);
                updateSalary(instructorID);

                Platform.runLater(() -> {
                    // Thêm member mới vào cơ sở dữ liệu

                    // Cập nhật dữ liệu trong TableView
                    ObservableList<Instructors> instructorsData = FXCollections.observableArrayList();
                    List<Instructors> instructorsList = instructorRepository.getAllInstructors();
                    instructorsData.addAll(instructorsList);
                    instructorTableView.setItems(instructorsData);
                    instructorTableView.refresh();
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
