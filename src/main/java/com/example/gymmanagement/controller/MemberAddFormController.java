package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.entity.MembershipStatus;
import com.example.gymmanagement.model.entity.MembershipType;
import com.example.gymmanagement.model.repository.InstructorRepository;
import com.example.gymmanagement.model.repository.MembersRepository;
import com.example.gymmanagement.model.repository.MembershipStatusRepository;
import com.example.gymmanagement.model.repository.MembershipTypesRepository;
import com.example.gymmanagement.model.service.EmailService;
import com.example.gymmanagement.validation.ValidateMember;
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

public class MemberAddFormController {
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
    private ComboBox<String> membershipStatusComboBox;

    @FXML
    private ComboBox<String> membershipTypeComboBox;

    @FXML
    private ComboBox<String> instructorComboBox;

    @FXML
    private Button buttonAdd;

    private MembersRepository membersRepository = new MembersRepository();
    private MembershipStatusRepository membershipStatusRepository = new MembershipStatusRepository();
    private MembershipTypesRepository membershipTypesRepository = new MembershipTypesRepository();
    private InstructorRepository instructorRepository = new InstructorRepository();
    private TableView<Members> memberTableView;
    private int currentPage;
    private int pageSize;

    // Constructor
    public MemberAddFormController(TableView<Members> memberTableView ,int currentPage , int pageSize) {
        this.memberTableView = memberTableView;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    private final EmailService emailService = new EmailService();

    @FXML
    private void initialize() {
        populateComboBoxes();
        buttonAdd.setOnAction(event -> {
            validateAndAddMember();
        });
    }


    // Xóa dữ liệu khỏi các trường nhập liệu
    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        dobPicker.setValue(null);
        genderField.clear();
        emailField.clear();
        phoneNumberField.clear();
        addressField.clear();
        membershipStatusComboBox.getSelectionModel().clearSelection();
        membershipTypeComboBox.getSelectionModel().clearSelection();
        instructorComboBox.getSelectionModel().clearSelection();
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

    void addMember() {// Hiển thị cửa sổ thông báo hỏi người dùng có muốn thêm hay không
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Add");
        confirmationAlert.setHeaderText("Add Member");
        confirmationAlert.setContentText("Do you want to add this member?");
        confirmationAlert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                // Lấy thông tin từ các trường nhập liệu
                // Làm thêm id nhưng ko hiển thị .
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String dob = dobPicker.getValue().toString();
                String gender = genderField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneNumberField.getText();
                String address = addressField.getText();
                String statusName = membershipStatusComboBox.getValue();
                String typeName = membershipTypeComboBox.getValue();
                String instructorName = instructorComboBox.getValue();

                // Tính toán ngày kết thúc dựa trên thời gian đăng ký (duration)
                int membershipTypeId = membershipTypesRepository.getTypeIDByName(typeName);
                int duration = membershipTypesRepository.getDurationById(membershipTypeId);

                LocalDate joinDate = LocalDate.now(); // Ngày hiện tại
                LocalDate endDate = joinDate.plusDays(duration); // Thêm thời gian đăng ký vào ngày tham gia

                // Tạo đối tượng Members và thiết lập các giá trị
                Members newMember = new Members();
                newMember.setFirst_name(firstName);
                newMember.setLast_name(lastName);
                newMember.setDob(dob);
                newMember.setGender(gender);
                newMember.setEmail(email);
                newMember.setPhone_number(phoneNumber);
                newMember.setAddress(address);
                newMember.setJoin_date(joinDate.toString());
                newMember.setEnd_date(endDate.toString());
                newMember.setMembership_status_id(membershipStatusRepository.getStatusIdByName(statusName));
                newMember.setMembership_type_id(membershipTypesRepository.getTypeIDByName(typeName));
                newMember.setInstructorId(instructorRepository.getInstructorIdByName(instructorName));


                membersRepository.addMember(newMember);
                // Sau khi thêm thành viên cập nhật TableView
                Platform.runLater(() -> {
                    // Thêm member mới vào cơ sở dữ liệu
                    loadMembersData();
                });

                showSuccessAlert();
                // Xóa dữ liệu khỏi các trường nhập liệu
                clearFields();

            }
        });
    }  // Cập nhật dữ liệu trong TableView

    ObservableList<Members> membersData = FXCollections.observableArrayList();

    private void loadMembersData() {
        List<Members> membersList = membersRepository.getMembersByPage(currentPage, pageSize);
        membersData.clear();
        membersData.addAll(membersList);
        memberTableView.setItems(membersData);
    }

    private boolean validateAndAddMember() {
        List<String> errors = new ArrayList<>();

        // Lấy thông tin từ các trường nhập liệu
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String dob = null;
        if (dobPicker.getValue() != null) {
            dob = dobPicker.getValue().toString();
            // Tiếp tục xử lý với biến dob
        }
        String gender = genderField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        String address = addressField.getText();
        String statusName = membershipStatusComboBox.getValue();
        String typeName = membershipTypeComboBox.getValue();
        String instructorName = instructorComboBox.getValue();

        // Validate dữ liệu trước khi thêm và thêm lỗi vào danh sách nếu cần
        if (!ValidateMember.isValidFirstName(firstName)) {
            errors.add("Error: Invalid First Name - Please enter a valid first name.");
        }

        if (!ValidateMember.isValidLastName(lastName)) {
            errors.add("Error: Invalid Last Name - Please enter a valid last name.");
        }

        if (!ValidateMember.isValidDate(dob)) {
            errors.add("Error: Invalid Date of Birth - Please select a valid date of birth.");
        }

        if (!ValidateMember.isValidGender(gender)) {
            errors.add("Error: Invalid Gender - Please enter a valid gender.");
        }

        if (!ValidateMember.isValidEmail(email)) {
            errors.add("Error: Invalid Email - Please enter a valid email address.");
        }

        if (!ValidateMember.isValidPhoneNumber(phoneNumber)) {
            errors.add("Error: Invalid Phone Number - Please enter a valid phone number.");
        }

        if (!ValidateMember.isValidAddress(address)) {
            errors.add("Error: Invalid Address - Please enter a valid address.");
        }

        if (!ValidateMember.isValidMembershipStatus(statusName)) {
            errors.add("Error: Invalid Membership Status - Please select a valid membership status.");
        }

        if (!ValidateMember.isValidMembershipType(typeName)) {
            errors.add("Error: Invalid Membership Type - Please select a valid membership type.");
        }

        if (!ValidateMember.isValidInstructor(instructorName)) {
            errors.add("Error: Invalid Instructor - Please select a valid instructor.");
        }

        // Hiển thị cửa sổ thông báo nếu có lỗi
        if (!errors.isEmpty()) {
            showErrorAlert("Validation Errors", String.join("\n", errors));
            return false;
        }

        // Nếu tất cả dữ liệu hợp lệ, thêm thành viên
        addMember();
        List<String> emails = new ArrayList<>();
        emails.add(email);
        emailService.sendThankYouEmail(emails);
        emails.clear();
        return true;
    }

    private void showSuccessAlert() {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Member Added");
        successAlert.setContentText("Member has been added successfully.");
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
