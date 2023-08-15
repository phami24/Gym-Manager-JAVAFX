package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.entity.MembershipStatus;
import com.example.gymmanagement.model.entity.MembershipType;
import com.example.gymmanagement.model.repository.InstructorRepository;
import com.example.gymmanagement.model.repository.MembersRepository;
import com.example.gymmanagement.model.repository.MembershipStatusRepository;
import com.example.gymmanagement.model.repository.MembershipTypesRepository;
import com.example.gymmanagement.validation.ValidateMember;
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

public class MemberUpdateFormController implements Initializable {
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
    private DatePicker joinDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<String> membershipStatusComboBox;

    @FXML
    private ComboBox<String> membershipTypeComboBox;

    @FXML
    private ComboBox<String> instructorComboBox;

    List<String> errors = new ArrayList<>();

    // Add more fields as needed

    private MembersRepository membersRepository = new MembersRepository();
    private final MembershipStatusRepository membershipStatusRepository = new MembershipStatusRepository();
    private final MembershipTypesRepository membershipTypesRepository = new MembershipTypesRepository();
    private final InstructorRepository instructorRepository = new InstructorRepository();
    private final int memberID;
    private TableView<Members> memberTableView;

    // Constructor
    public MemberUpdateFormController(int memberID, TableView<Members> memberTableView) {
        this.memberID = memberID;
        this.memberTableView = memberTableView;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        memberTableView.refresh();
        showData(memberID);
    }

    private void showData(int memberId) {
        // Retrieve the member from the database using memberId
        Members member = membersRepository.getMemberById(memberId);

        if (member == null) {
            // Handle the case where the member with the given ID doesn't exist
            // You can show an error message or perform any other necessary action
            return;
        }

        // Populate the fields with member data
        firstNameField.setText(member.getFirst_name());
        lastNameField.setText(member.getLast_name());
        dobPicker.setValue(LocalDate.parse(member.getDob())); // Assuming getDate_of_birth() returns a LocalDate
        genderField.setText(member.getGender());
        emailField.setText(member.getEmail());
        phoneNumberField.setText(member.getPhone_number());
        addressField.setText(member.getAddress());
        joinDatePicker.setValue(LocalDate.parse(member.getJoin_date())); // Assuming getJoinDate() returns a LocalDate
        endDatePicker.setValue(LocalDate.parse(member.getEnd_date())); // Assuming getEndDate() returns a LocalDate
        membershipStatusComboBox.setValue(membershipStatusRepository.getStatusNameById(member.getMembership_status_id()));
        membershipTypeComboBox.setValue(membershipTypesRepository.getTypeNameById(member.getMembership_type_id()));
        instructorComboBox.setValue(instructorRepository.getInstructorNameById(member.getInstructorId()));

        // You might also need to fetch and populate ComboBox items for membership status, type, and instructor
        populateComboBoxes();
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
        successAlert.setHeaderText("Member Updated");
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

    private void populateComboBoxes() {
        // Populate Membership Status ComboBox
        List<MembershipStatus> membershipStatusList = membershipStatusRepository.getAllMembershipStatus();
        for (MembershipStatus status : membershipStatusList) {
            if (status.getMembershipStatusId() != 4) {
                membershipStatusComboBox.getItems().add(status.getMembershipStatusName());
            }
        }

        // Populate Membership Type ComboBox
        List<MembershipType> membershipTypeList = membershipTypesRepository.getAllMembershipTypes();
        for (MembershipType type : membershipTypeList) {
            membershipTypeComboBox.getItems().add(type.getMembershipTypeName());
        }

        // Populate Instructor ComboBox
        List<Instructors> instructorList = instructorRepository.getAllInstructors();
        for (Instructors instructor : instructorList) {
            instructorComboBox.getItems().add(instructor.getFirst_name() + " " + instructor.getLast_name());
        }
        instructorComboBox.getItems().add("No Instructor");

    }


    private void updateFirstName(int memberId) {
        String newFirstName = firstNameField.getText();
        if (ValidateMember.isValidFirstName(newFirstName)) {
            membersRepository.updateFirstName(memberId, newFirstName);
        } else {
            errors.add("ERROR : Invalid first name");
        }
    }

    private void updateLastName(int memberId) {
        String newLastName = lastNameField.getText();
        if (ValidateMember.isValidLastName(newLastName)) {
            membersRepository.updateLastName(memberId, newLastName);
        } else {
            errors.add("ERROR : Invalid last name");
        }
    }

    private void updateDob(int memberId) {
        String newDob = dobPicker.getValue().toString();
        if (ValidateMember.isValidDate(newDob)) {
            membersRepository.updateDob(memberId, newDob);
        } else {
            errors.add("ERROR : Invalid date of birth");
        }
    }

    private void updateGender(int memberId) {
        String newGender = genderField.getText();
        if (ValidateMember.isValidGender(newGender)) {
            membersRepository.updateGender(memberId, newGender);
        } else {
            errors.add("ERROR : Invalid gender");
        }
    }

    private void updateEmail(int memberId) {
        String newEmail = emailField.getText();
        if (ValidateMember.isValidEmail(newEmail)) {
            membersRepository.updateEmail(memberId, newEmail);
        } else {
            errors.add("ERROR : Invalid email");
        }
    }

    private void updatePhoneNumber(int memberId) {
        String newPhoneNumber = phoneNumberField.getText();
        if (ValidateMember.isValidPhoneNumber(newPhoneNumber)) {
            membersRepository.updatePhoneNumber(memberId, newPhoneNumber);
        } else {
            errors.add("ERROR : Invalid phone number");
        }
    }

    private void updateAddress(int memberId) {
        String newAddress = addressField.getText();
        if (ValidateMember.isValidAddress(newAddress)) {
            membersRepository.updateAddress(memberId, newAddress);
        } else {
            errors.add("ERROR : Invalid address");
        }
    }

    private void updateJoinDate(int memberId) {
        String newJoinDate = joinDatePicker.getValue().toString();
        if (ValidateMember.isValidDate(newJoinDate)) {
            membersRepository.updateJoinDate(memberId, newJoinDate);
        } else {
            errors.add("ERROR : Invalid join date");
        }
    }

    private void updateEndDate(int memberId) {
        String newEndDate = endDatePicker.getValue().toString();
        if (ValidateMember.isValidDate(newEndDate)) {
            membersRepository.updateEndDate(memberId, newEndDate);
        } else {
            errors.add("ERROR : Invalid end date");
        }
    }

    private void updateInstructor(int memberId) {
        String selectedInstructor = instructorComboBox.getValue();
        if (ValidateMember.isValidInstructor(selectedInstructor)) {
            int selectedInstructorId = instructorRepository.getInstructorIdByName(selectedInstructor);
            membersRepository.updateInstructor(memberId, selectedInstructorId);
        } else {
            errors.add("ERROR : Invalid instructor");
        }
    }

    private void updateMembershipStatus(int memberId) {
        String selectedStatus = membershipStatusComboBox.getValue();
        if (ValidateMember.isValidMembershipStatus(selectedStatus)) {
            int selectedStatusId = membershipStatusRepository.getStatusIdByName(selectedStatus);
            membersRepository.updateMembershipStatus(memberId, selectedStatusId);
        } else {
            errors.add("ERROR : Invalid membership status");
        }
    }

    private void updateMembershipType(int memberId) {
        String selectedType = membershipTypeComboBox.getValue();
        if (ValidateMember.isValidMembershipType(selectedType)) {
            int selectedTypeId = membershipTypesRepository.getTypeIDByName(selectedType);
            membersRepository.updateMembershipType(memberId, selectedTypeId);
        } else {
            errors.add("ERROR : Invalid membership type");
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
                updateFirstName(memberID);
                updateLastName(memberID);
                updateDob(memberID);
                updateGender(memberID);
                updateEmail(memberID);
                updatePhoneNumber(memberID);
                updateAddress(memberID);
                updateJoinDate(memberID);
                updateEndDate(memberID);
                updateInstructor(memberID);
                updateMembershipStatus(memberID);
                updateMembershipType(memberID);
                Platform.runLater(() -> {
                    // Thêm member mới vào cơ sở dữ liệu

                    // Cập nhật dữ liệu trong TableView
                    ObservableList<Members> membersData = FXCollections.observableArrayList();
                    List<Members> membersList = membersRepository.getAllMembers();
                    membersData.addAll(membersList);
                    memberTableView.setItems(membersData);
                    memberTableView.refresh();
                });
                if (!errors.isEmpty()) {
                    showErrorAlert("Validation Errors", String.join("\n", errors));
                    errors.clear();
                } else {
                    showSuccessAlert();
                }
            }
        });

    }
}
