package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.entity.Members;
import com.example.gymmanagement.model.repository.InstructorsRepository;
import com.example.gymmanagement.model.service.InstructorsService;
import com.example.gymmanagement.model.service.impl.InstructorsServiceImpl;
import com.example.gymmanagement.model.service.impl.MembersServiceImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.lang.reflect.Member;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.lang.String.valueOf;

public class Controller {
    @FXML
    private TextField lastname_input_i;
    @FXML
    private TextField phone_input_i;
    @FXML
    private TextField speci_input_i;
    @FXML
    private TextField year_input_i;
    @FXML
    private DatePicker dob_input_i;
    @FXML
    private TextField email_input_i;
    @FXML
    private TextField address_input_i;
    @FXML
    private TextField firstname_input_i;
    @FXML
    private ComboBox<String> gender_input_i;
    @FXML
    private TextField id_input_i;
    @FXML
    private DatePicker hiredate_input_i;
    @FXML
    private Button instructor_btn;
    @FXML
    private Button member_btn;
    @FXML
    private AnchorPane add_instructor_form;
    @FXML
    private AnchorPane add_member_form;
    @FXML
    private AnchorPane member_form;
    @FXML
    private AnchorPane main_form;
    @FXML
    private Button dashboard_btn;
    @FXML
    private Button logout;
    @FXML
    private Button inst_add_btn;
    @FXML
    private Button inst_update_btn;
    @FXML
    private Button inst_delete_btn;
    @FXML
    private TableView<Instructors> instructor_tableView;
    @FXML
    private TableColumn<Instructors, String> ins_col_address;
    @FXML
    private TableColumn<Instructors, String> ins_col_dob;
    @FXML
    private TableColumn<Instructors, String> ins_col_email;
    @FXML
    private TableColumn<Instructors, String> ins_col_fullname;
    @FXML
    private TableColumn<Instructors, String> ins_col_gender;
    @FXML
    private TableColumn<Instructors, String> ins_col_hiredate;
    @FXML
    private TableColumn<Instructors, Integer> ins_col_id;
    @FXML
    private TableColumn<Instructors, String> ins_col_phone;
    @FXML
    private TableColumn<Instructors, String> ins_col_spec;
    @FXML
    private TableColumn<Instructors, String> ins_col_status;


    //fxml members
    @FXML
    private TableColumn<Member, String> member_col_address;

    @FXML
    private TableColumn<Member, String> member_col_dob;

    @FXML
    private TableColumn<Member, String> member_col_email;

    @FXML
    private TableColumn<Member, String> member_col_enddate;

    @FXML
    private TableColumn<Members, String> member_col_fullname = new TableColumn<>("Name");

    @FXML
    private TableColumn<Member, String> member_col_gender;

    @FXML
    private TableColumn<Members, Integer> member_col_id = new TableColumn<>("ID");

    @FXML
    private TableColumn<Member, String> member_col_joindate;

    @FXML
    private TableColumn<Member, String> member_col_phone;

    @FXML
    private TableColumn<Member, String> member_col_status;
    @FXML
    private TableColumn<Member, String> member_col_type;
    @FXML
    private DatePicker joindate_input_m;
    @FXML
    private TextField type_input_m;
    @FXML
    private TextField lastname_input_m;
    @FXML
    private ComboBox<String> status_input_m;
    private Map<String,Integer> statusMap;

    @FXML
    private DatePicker dob_input_m;

    @FXML
    private TextField email_input_m;
    @FXML
    private TextField phone_input_m;

    @FXML
    private TextField address_input_m;

    @FXML
    private DatePicker enddate_input_m;

    @FXML
    private TextField firstname_input_m;

    @FXML
    private ComboBox<String> gender_input_m;

    @FXML
    private TextField id_input_m;
    @FXML
    private TableView<Members> member_tableView;
    //set ID

    @FXML
    private Button inst_add_btn1;
    private int nextMemberID = 0;

    // Khởi tạo đối tượng Service
    private InstructorsServiceImpl instructorsService;
    private MembersServiceImpl membersService;

    public Controller() {
        instructorsService = new InstructorsServiceImpl();
        membersService = new MembersServiceImpl();
    }

    //xử lí comboBox
    private String gender[] = {"Male", "Female", "Others"};

    public void insGenderList(ComboBox<String> genders) {
        if (genders == null) {
            genders = new ComboBox<>();
        }
        List<String> genderList = new ArrayList<>();
        for (String data : gender) {
            genderList.add(data);

        }
        ObservableList listGender = FXCollections.observableArrayList(genderList);
        genders.setItems(listGender);
    }

    //    private String status[] = {"Active", "Suspended", "Expired"};
    private String status[] = {"1", "2", "3"};

    public void memberStatusList(ComboBox<String> statuss) {
        if (statuss == null) {
            statuss = new ComboBox<>();
        }
        List<String> statusList = new ArrayList<>();
        for (String data : status) {
            statusList.add(data);

        }
        ObservableList listStatus = FXCollections.observableArrayList(statusList);
        statuss.setItems(listStatus);
    }


    //setVisible
    private Executor executor = Executors.newFixedThreadPool(3);

    public void switchForm(ActionEvent event) {
        if (event.getSource() == instructor_btn) {
            executor.execute(() -> {
                add_instructor_form.setVisible(true);
                add_member_form.setVisible(false);
                main_form.setVisible(false);
                member_form.setVisible(false);
                insGenderList(gender_input_i);
//                instructorShow();
            });

        } else if (event.getSource() == member_btn) {
            executor.execute(() -> {
                add_instructor_form.setVisible(false);
                add_member_form.setVisible(false);
                main_form.setVisible(true);
                member_form.setVisible(false);
            });
        } else if (event.getSource() == dashboard_btn) {
            executor.execute(() -> {
                member_form.setVisible(true);
                add_member_form.setVisible(false);
                add_instructor_form.setVisible(false);
                main_form.setVisible(false);

                insGenderList(gender_input_m);
                memberStatusList(status_input_m);
//                memberShow();
            });
        }
    }

    //báo lỗi
    public void emptyFields() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error message");
        alert.setHeaderText("");
        alert.setContentText("Please fill all blank fiels");
        alert.showAndWait();
    }

    //logout
    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure  you want to logout?");
            Optional<ButtonType> optional = alert.showAndWait();

            if (optional.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("member.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

//                MouseDragHandler mouseDragHandler = new MouseDragHandler(stage);
//                root.setOnMousePressed(mouseDragHandler);
//                root.setOnMouseDragged(mouseDragHandler);
//                root.setOnMouseReleased(mouseDragHandler);


                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            } else {

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //close
    public void close() {
        javafx.application.Platform.exit();
    }

    //chuyển sang form khác
    public void addmemberForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/gymmanagement/view/addmember.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setMinHeight(400 + 20);
            stage.setMinWidth(600 + 20);

            stage.initStyle(StageStyle.DECORATED);

//            selectRecord();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        // Load danh sách instructor và hiển thị lên TableView khi Controller được khởi tạo.
        List<Instructors> instructors = instructorsService.getAllInstructors();
//        instructor_tableView.getItems().addAll(instructors);
        instructor_tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showInstructorDetails(newValue);
            }
        });

        loadInstructorsData();
        insGenderList(gender_input_i);

        //Load danh sach members va hien thi table view
        List<Members> members = membersService.getAllMembers();
//        member_tableView.getItems().addAll(members);
        member_tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValueMember) -> {
            if (newValueMember != null) {
                showMembers(newValueMember);
            }
        });
        loadMembersData();
        memberStatusList(status_input_m);
        insGenderList(gender_input_m);

        //set ID member
        nextMemberID = membersService.getNextMemberID();
        id_input_m.setText(String.valueOf(nextMemberID));
        inst_add_btn1.setOnAction(e -> addMember());

        //set ID instructor
        nextMemberID = instructorsService.getNextMemberID();
        id_input_i.setText(String.valueOf(nextMemberID));
        inst_add_btn.setOnAction(e -> addInstructor());
    }

    //show lên textfield
    private void showInstructorDetails(Instructors instructor) {
        id_input_i.setText(String.valueOf(instructor.getInstructor_id()));
        firstname_input_i.setText(instructor.getFirst_name());
        lastname_input_i.setText(instructor.getLast_name());
        dob_input_i.setValue(LocalDate.parse(instructor.getDob()));
        gender_input_i.setValue(instructor.getGender());
        email_input_i.setText(instructor.getEmail());
        phone_input_i.setText(instructor.getPhone_number());
        address_input_i.setText(instructor.getAddress());
        hiredate_input_i.setValue(LocalDate.parse(instructor.getHire_date()));
        speci_input_i.setText(instructor.getSpecialization());
        year_input_i.setText(String.valueOf(instructor.getExperienceYears()));
    }

    //show lên bảng
    private void loadInstructorsData() {
        ins_col_id.setCellValueFactory(new PropertyValueFactory<>("instructor_id"));
        ins_col_fullname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name() + " " + cellData.getValue().getLast_name()));
        ins_col_dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        ins_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ins_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        ins_col_phone.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        ins_col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        ins_col_hiredate.setCellValueFactory(new PropertyValueFactory<>("hire_date"));
        ins_col_spec.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        ins_col_status.setCellValueFactory(new PropertyValueFactory<>("experienceYears"));
        // Thực hiện truy vấn để lấy dữ liệu từ cơ sở dữ liệu
        // Giả định có phương thức để lấy danh sách giảng viên từ cơ sở dữ liệu
        List<Instructors> instructorsList = instructorsService.getAllInstructors();
        // Đổ dữ liệu vào TableView
        instructor_tableView.getItems().addAll(instructorsList);
    }

    //clear text
    public void clearIns() {
        firstname_input_i.clear();
        lastname_input_i.clear();
        dob_input_i.setValue(null);
        gender_input_i.setValue(null);
        email_input_i.clear();
        phone_input_i.clear();
        address_input_i.clear();
        hiredate_input_i.setValue(null);
        speci_input_i.clear();
        year_input_i.clear();
    }
//Controller instructor
    @FXML
    private void addInstructor() {
        String firstName = firstname_input_i.getText();
        String lastName = lastname_input_i.getText();
        LocalDate dob = dob_input_i.getValue();
        String gender = gender_input_i.getValue();
        String email = email_input_i.getText();
        String phoneNumber = phone_input_i.getText();
        String address = address_input_i.getText();
        LocalDate hireDate = hiredate_input_i.getValue();
        String specialization = speci_input_i.getText();
        Integer experienceYears = Integer.valueOf(year_input_i.getText());

        Instructors newInstructors = new Instructors();
        newInstructors.setFirst_name(firstName);
        newInstructors.setLast_name(lastName);
        newInstructors.setDob(String.valueOf(dob));
        newInstructors.setGender(gender);
        newInstructors.setEmail(email);
        newInstructors.setPhone_number(phoneNumber);
        newInstructors.setAddress(address);
        newInstructors.setHire_date(String.valueOf(hireDate));
        newInstructors.setSpecialization(specialization);
        newInstructors.setExperienceYears(experienceYears);

        instructorsService.addInstructor(newInstructors);
        nextMemberID++;
        id_input_i.setText(String.valueOf(nextMemberID));

        if (isValidInput()) {
            instructorsService.addInstructor(newInstructors);
            instructor_tableView.getItems().add(newInstructors);
            clearIns();
        } else {
            // Hiển thị thông báo lỗi để thông báo cho người dùng biết rằng dữ liệu nhập vào không hợp lệ.
            emptyFields();
        }
    }

    //check valid
    private boolean isValidInput() {
        String firstName = firstname_input_i.getText();
        String lastName = lastname_input_i.getText();
        LocalDate dob = dob_input_i.getValue();
        String gender = gender_input_i.getValue();
        String email = email_input_i.getText();
        String phoneNumber = phone_input_i.getText();
        String address = address_input_i.getText();
        LocalDate hireDate = hiredate_input_i.getValue();
        String specialization = speci_input_i.getText();
        Integer experienceYears = Integer.valueOf(year_input_i.getText());

        if (firstName.isEmpty() || lastName.isEmpty() || dob == null || gender == null ||
                email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || hireDate == null ||
                specialization.isEmpty() || experienceYears == null) {
            // Kiểm tra xem các trường bắt buộc có được điền hay không.
            return false;
        }

//        try {
//            // Kiểm tra xem trường năm kinh nghiệm có dữ liệu hợp lệ hay không.
//            int experienceYears = Integer.parseInt(experienceYears);
//            if (experienceYears < 0) {
//                return false;
//            }
//        } catch (NumberFormatException e) {
//            // Nếu không thể chuyển đổi năm kinh nghiệm thành số nguyên, tức là dữ liệu không hợp lệ.
//            return false;
//        }

        // Nếu tất cả kiểm tra đều hợp lệ, trả về true.
        return true;
    }

    @FXML
    private void updateInstructor() {

        Instructors instructors = instructor_tableView.getSelectionModel().getSelectedItem();
        if (instructors != null) {
            String firstName = firstname_input_i.getText();
            String lastName = lastname_input_i.getText();
            LocalDate dob = dob_input_i.getValue();
            String gender = gender_input_i.getValue();
            String email = email_input_i.getText();
            String phoneNumber = phone_input_i.getText();
            String address = address_input_i.getText();
            LocalDate hireDate = hiredate_input_i.getValue();
            String specialization = speci_input_i.getText();
            Integer experienceYears = Integer.valueOf(year_input_i.getText());

            instructors.setFirst_name(firstName);
            instructors.setLast_name(lastName);
            instructors.setDob(String.valueOf(dob));
            instructors.setGender(gender);
            instructors.setEmail(email);
            instructors.setPhone_number(phoneNumber);
            instructors.setAddress(address);
            instructors.setHire_date(String.valueOf(hireDate));
            instructors.setSpecialization(specialization);
            instructors.setExperienceYears(experienceYears);

            instructorsService.updateInstructor(instructors);

            instructor_tableView.refresh();
            clearIns();
        }
    }


    @FXML
    private void deleteInstructor() {
        Instructors instructors = instructor_tableView.getSelectionModel().getSelectedItem();
        if (instructors != null) {
            instructorsService.deleteInstructor(instructors.getInstructor_id());
            instructor_tableView.getItems().remove(instructors);
        }
    }


    //controller members

    public void clearMembers() {
        firstname_input_m.clear();
        lastname_input_m.clear();
        dob_input_m.setValue(null);
        gender_input_m.setValue(null);
        email_input_m.clear();
        phone_input_m.clear();
        address_input_m.clear();
        enddate_input_m.setValue(null);
        joindate_input_m.setValue(null);
        status_input_m.setValue(null);
        type_input_m.clear();
    }

    @FXML
    private void addMember() {

        String firstName = firstname_input_m.getText();
        String lastName = lastname_input_m.getText();
        LocalDate dob = dob_input_m.getValue();
        String gender = gender_input_m.getValue();
        String email = email_input_m.getText();
        String phoneNumber = phone_input_m.getText();
        String address = address_input_m.getText();
        LocalDate enddate = enddate_input_m.getValue();
        LocalDate joindate = joindate_input_m.getValue();
        String status = status_input_m.getValue();
        String type = type_input_m.getText();

        Members newMembers = new Members();

        newMembers.setFirst_name(firstName);
        newMembers.setLast_name(lastName);
        newMembers.setDob(String.valueOf(dob));
        newMembers.setGender(gender);
        newMembers.setEmail(email);
        newMembers.setPhone_number(phoneNumber);
        newMembers.setAddress(address);
        newMembers.setEnd_date(String.valueOf(enddate));
        newMembers.setJoin_date(String.valueOf(joindate));
        newMembers.setMembership_status_id(Integer.parseInt(status));
        newMembers.setMembership_type_id(Integer.parseInt(type));

        membersService.addMember(newMembers);
        nextMemberID++;
        id_input_m.setText(String.valueOf(nextMemberID));
        if (true) {
            membersService.addMember(newMembers);
            member_tableView.getItems().add(newMembers);
            clearIns();
        } else {
            // Hiển thị thông báo lỗi để thông báo cho người dùng biết rằng dữ liệu nhập vào không hợp lệ.
            emptyFields();
        }
    }

    @FXML
    private void updateMember() {
        Members members = member_tableView.getSelectionModel().getSelectedItem();
        if (members != null) {
            String firstName = firstname_input_i.getText();
            String lastName = lastname_input_i.getText();
            LocalDate dob = dob_input_i.getValue();
            String gender = gender_input_i.getValue();
            String email = email_input_i.getText();
            String phoneNumber = phone_input_i.getText();
            String address = address_input_i.getText();
            LocalDate enddate = enddate_input_m.getValue();
            LocalDate joindate = joindate_input_m.getValue();
            String status = status_input_m.getValue();
            String type = type_input_m.getText();

            members.setFirst_name(firstName);
            members.setLast_name(lastName);
            members.setDob(String.valueOf(dob));
            members.setGender(gender);
            members.setEmail(email);
            members.setPhone_number(phoneNumber);
            members.setAddress(address);
            members.setEnd_date(String.valueOf(enddate));
            members.setJoin_date(String.valueOf(joindate));
            members.setMembership_status_id(Integer.parseInt(status));
            members.setMembership_type_id(Integer.parseInt(type));

            membersService.updateMember(members);

            member_tableView.refresh();
            clearMembers();
        }
    }

    @FXML
    private void deleteMember() {
        Members members = member_tableView.getSelectionModel().getSelectedItem();
        if (members != null) {
            membersService.deleteMember(members.getMember_id());
            member_tableView.getItems().remove(members);
        }
    }

    private void showMembers(Members members) {
        id_input_m.setText(String.valueOf(members.getMember_id()));
        firstname_input_m.setText(members.getFirst_name());
        lastname_input_m.setText(members.getLast_name());
        dob_input_m.setValue(LocalDate.parse(members.getDob()));
        gender_input_m.setValue(members.getGender());
        email_input_m.setText(members.getEmail());
        phone_input_m.setText(members.getPhone_number());
        address_input_m.setText(members.getAddress());
        enddate_input_m.setValue(LocalDate.parse(members.getEnd_date()));
        joindate_input_m.setValue(LocalDate.parse(members.getJoin_date()));
        status_input_m.setValue(String.valueOf(members.getMembership_status_id()));
        type_input_m.setText(String.valueOf(members.getMembership_type_id()));
    }

    private void loadMembersData() {
        member_col_id.setCellValueFactory(new PropertyValueFactory<>("member_id"));
        member_col_fullname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name() + " " + cellData.getValue().getLast_name()));
        member_col_dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        member_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        member_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        member_col_phone.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        member_col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        member_col_enddate.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        member_col_joindate.setCellValueFactory(new PropertyValueFactory<>("join_date"));
        member_col_status.setCellValueFactory(new PropertyValueFactory<>("membership_status_id"));
        member_col_type.setCellValueFactory(new PropertyValueFactory<>("membership_type_id"));
        // Thực hiện truy vấn để lấy dữ liệu từ cơ sở dữ liệu
        // Giả định có phương thức để lấy danh sách giảng viên từ cơ sở dữ liệu
        List<Members> membersList = membersService.getAllMembers();
        // Đổ dữ liệu vào TableView
        member_tableView.getItems().addAll(membersList);


    }


}
