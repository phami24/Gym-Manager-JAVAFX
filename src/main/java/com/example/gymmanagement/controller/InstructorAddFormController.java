package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.entity.MembershipStatus;
import com.example.gymmanagement.model.entity.MembershipType;
import com.example.gymmanagement.model.repository.InstructorRepository;
import com.example.gymmanagement.model.service.InstructorsService;
import com.example.gymmanagement.model.service.impl.InstructorsServiceImpl;
import com.example.gymmanagement.validation.ValidateInstructor;
import com.example.gymmanagement.validation.ValidateMember;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InstructorAddFormController implements Initializable {


    private final InstructorRepository instructorRepository = new InstructorRepository();

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
    private TextField experienceYearsField;

    @FXML
    private TextField baseSalaryField;

    @FXML
    private Button buttonAdd;

    @FXML
    private TableView<Instructors> tableView;

    public InstructorAddFormController(TableView<Instructors> tableView) {
        this.tableView = tableView;
    }

    private ObservableList<Instructors> instructorsData = FXCollections.observableArrayList();

    private InstructorsService instructorsService = new InstructorsServiceImpl();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        buttonAdd.setOnAction(event -> {
            validateAndAddInstructor();
        });
    }


    // Xóa dữ liệu khỏi các trường nhập liệu
    private void clearFields() {

    }


    void addInstructor() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Add");
        confirmationAlert.setHeaderText("Add Instructor");
        confirmationAlert.setContentText("Do you want to add this instructor?");
        confirmationAlert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                // Get input values
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String dob = dobPicker.getValue().toString();
                String gender = genderField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneNumberField.getText();
                String address = addressField.getText();
                int experienceYears = Integer.parseInt(experienceYearsField.getText());
                BigDecimal baseSalary = new BigDecimal(baseSalaryField.getText());

                // Create Instructor object and set values
                Instructors newInstructor = new Instructors();
                newInstructor.setFirst_name(firstName);
                newInstructor.setLast_name(lastName);
                newInstructor.setDob(dob);
                newInstructor.setGender(gender);
                newInstructor.setEmail(email);
                newInstructor.setPhone_number(phoneNumber);
                newInstructor.setAddress(address);
                newInstructor.setHireDate(LocalDate.now().toString()); // Current date
                newInstructor.setExperienceYears(experienceYears);
                newInstructor.setBaseSalary(baseSalary);

                // Add the instructor to the database
                instructorRepository.addInstructor(newInstructor);

                // Refresh TableView
                Platform.runLater(() -> {
                    // Thêm member mới vào cơ sở dữ liệu

                    // Cập nhật dữ liệu trong TableView
                    ObservableList<Instructors> instructorData= FXCollections.observableArrayList();
                    List<Instructors> instructorList = instructorRepository.getAllInstructors();
                    instructorData.addAll(instructorList);
                    tableView.setItems(instructorData);
                    tableView.refresh();
                });

                // Show success alert
                showSuccessAlert();

                // Clear input fields
                clearFields();
            }
        });
    }


    private boolean validateAndAddInstructor() {
        List<String> errors = new ArrayList<>();

        // Get input values
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String dob = (dobPicker.getValue() != null) ? dobPicker.getValue().toString() : "";
        String gender = genderField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        String address = addressField.getText();
        String experienceYearsStr = experienceYearsField.getText();
        String baseSalaryStr = baseSalaryField.getText();

        // Validate input values and add errors if necessary
        if (!ValidateInstructor.isValidFirstName(firstName)) {
            errors.add("Error: Invalid First Name - Please enter a valid first name.");
        }

        if (!ValidateInstructor.isValidLastName(lastName)) {
            errors.add("Error: Invalid Last Name - Please enter a valid last name.");
        }

        if (!ValidateInstructor.isValidDate(dob)) {
            errors.add("Error: Invalid Date of Birth - Please select a valid date of birth.");
        }

        if (!ValidateInstructor.isValidGender(gender)) {
            errors.add("Error: Invalid Gender - Please enter a valid gender.");
        }

        if (!ValidateInstructor.isValidEmail(email)) {
            errors.add("Error: Invalid Email - Please enter a valid email address.");
        }

        if (!ValidateInstructor.isValidPhoneNumber(phoneNumber)) {
            errors.add("Error: Invalid Phone Number - Please enter a valid phone number.");
        }

        if (!ValidateInstructor.isValidAddress(address)) {
            errors.add("Error: Invalid Address - Please enter a valid address.");
        }

        if (!ValidateInstructor.isValidExperienceYears(experienceYearsStr)) {
            errors.add("Error: Invalid Experience Years - Please enter a valid number of experience years.");
        }

        if (!ValidateInstructor.isValidBaseSalary(baseSalaryStr)) {
            errors.add("Error: Invalid Base Salary - Please enter a valid base salary.");
        }

        // Display error alerts if there are validation errors
        if (!errors.isEmpty()) {
            showErrorAlert("Validation Errors", String.join("\n", errors));
            return false;
        }

        // If validation is successful, proceed to adding the instructor
        addInstructor();
        return true;
    }

    private void showSuccessAlert() {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Instructor Added");
        successAlert.setContentText("Instructor has been added successfully.");
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
        // Lấy Stage hiện tại từ sự kiện MouseEvent
        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Đóng cửa sổ hiện tại
        currentStage.close();
    }

}
