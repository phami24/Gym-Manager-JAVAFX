package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.*;
import com.example.gymmanagement.model.repository.InstructorsRepository;
import com.example.gymmanagement.model.service.InstructorsService;
import com.example.gymmanagement.model.service.impl.*;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.Member;
import java.math.BigDecimal;
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
    @FXML
    private TableColumn<Instructors, Void> delete_col;
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
    private Map<String, Integer> statusMap;

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

    //fxml Class

    @FXML
    private TableColumn<Classes, Integer> classID_col;
    @FXML
    private TableColumn<Classes, String> className_col;
    @FXML
    private TableColumn<Classes, Integer> insID_col;
    @FXML
    private TableColumn<Classes, String> schedule_col;
    @FXML
    private TableColumn<Classes, Integer> capacity_col;

    @FXML
    private TableView<Classes> class_tableView;
    @FXML
    private TextField id_input_class;
    @FXML
    private TextField classname_input;
    @FXML
    private TextField instructorID_input;
    @FXML
    private TextField schedule_input;
    @FXML
    private TextField capacity_input;
    @FXML
    private AnchorPane class_form;
    @FXML
    private Button class_btn;

    //fxml equipment
    @FXML
    private Button equipment_btn;
    @FXML
    private AnchorPane equipment_form;
    @FXML
    private TableView<Equipment> equipment_tableView;
    @FXML
    private TableColumn<?, ?> equipment_id_col;
    @FXML
    private TableColumn<?, ?> equipment_name_col;
    @FXML
    private TableColumn<?, ?> category_col;
    @FXML
    private TableColumn<?, ?> price_col;
    @FXML
    private TableColumn<?, ?> purchase_col;
    @FXML
    private TableColumn<?, ?> status_col;
    @FXML
    private TableColumn<?, ?> note_col;

    @FXML
    private TextField id_input_equipment;
    @FXML
    private TextField equipment_name_input;
    @FXML
    private TextField category_input;
    @FXML
    private TextField status_input_equipment;
    @FXML
    private TextField price_input;
    @FXML
    private DatePicker purchase_date_input;
    @FXML
    private TextArea note_input;


    //fxml user
    @FXML
    private Button user_btn;


    // Khởi tạo đối tượng Service
    private InstructorsServiceImpl instructorsService;
    private MembersServiceImpl membersService;
    private ClassesServiceImpl classesService;
    private EquipmentServiceImpl equipmentService;
    private UserServiceImpl userService;

    public Controller() {
        instructorsService = new InstructorsServiceImpl();
        membersService = new MembersServiceImpl();
        classesService = new ClassesServiceImpl();
        equipmentService = new EquipmentServiceImpl();
        userService = new UserServiceImpl();
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
                equipment_form.setVisible(false);
                add_member_form.setVisible(false);
                main_form.setVisible(false);
                member_form.setVisible(false);
                class_form.setVisible(false);
                insGenderList(gender_input_i);
            });

        } else if (event.getSource() == dashboard_btn) {
            executor.execute(() -> {
                main_form.setVisible(true);
                add_instructor_form.setVisible(false);
                equipment_form.setVisible(false);
                add_member_form.setVisible(false);
                member_form.setVisible(false);
                class_form.setVisible(false);
            });
        } else if (event.getSource() == member_btn) {
            executor.execute(() -> {
                member_form.setVisible(true);
                equipment_form.setVisible(false);
                add_member_form.setVisible(false);
                add_instructor_form.setVisible(false);
                main_form.setVisible(false);
                class_form.setVisible(false);

                insGenderList(gender_input_m);
                memberStatusList(status_input_m);
            });
        } else if (event.getSource() == class_btn) {
            executor.execute(() -> {
                class_form.setVisible(true);
                equipment_form.setVisible(false);
                member_form.setVisible(false);
                add_member_form.setVisible(false);
                add_instructor_form.setVisible(false);
                main_form.setVisible(false);

                insGenderList(gender_input_m);
                memberStatusList(status_input_m);
            });
        } else if (event.getSource() == equipment_btn) {
            executor.execute(() -> {
                equipment_form.setVisible(true);
                class_form.setVisible(false);
                member_form.setVisible(false);
                add_member_form.setVisible(false);
                add_instructor_form.setVisible(false);
                main_form.setVisible(false);

                insGenderList(gender_input_m);
                memberStatusList(status_input_m);
            });
        } else if (event.getSource() == user_btn) {
            executor.execute(() -> {
                class_form.setVisible(true);
                equipment_form.setVisible(false);
                member_form.setVisible(false);
                add_member_form.setVisible(false);
                add_instructor_form.setVisible(false);
                main_form.setVisible(false);

                insGenderList(gender_input_m);
                memberStatusList(status_input_m);
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


    //Main
    @FXML
    private void initialize() {
        // Load danh sách instructor và hiển thị lên TableView khi Controller được khởi tạo.
        List<Instructors> instructors = instructorsService.getAllInstructors();
        instructor_tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showInstructorDetails(newValue);
            }
        });
        loadInstructorsData();
        insGenderList(gender_input_i);

        //Load danh sach members va hien thi table view
        List<Members> members = membersService.getAllMembers();
        member_tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValueMember) -> {
            if (newValueMember != null) {
                showMembers(newValueMember);
            }
        });
        loadMembersData();
        memberStatusList(status_input_m);
        insGenderList(gender_input_m);


        //load danh sach class
        class_tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValueClass) -> {
            if (newValueClass != null) {
                showClass(newValueClass);
            }
        });
        loadClassData();

        //load danh sach equipment
        equipment_tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValueEquipment) -> {
            if (newValueEquipment != null) {
                showEquipment(newValueEquipment);
            }
        });
        loadEquipment();

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
        id_input_i.clear();
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to add this instructor?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {

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
            instructor_tableView.refresh();
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to update this instructor?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {
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
    }


    @FXML
    private void deleteInstructor() {
        Instructors instructors = instructor_tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to delete this instructor?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {
            instructorsService.deleteInstructor(instructors.getInstructor_id());
            // Cập nhật danh sách members sau khi xóa và hiển thị lại view
            instructor_tableView.getItems().remove(instructors);
            clearIns();
        }


//        Instructors instructors = instructor_tableView.getSelectionModel().getSelectedItem();
//        if (instructors != null) {
//            instructorsService.deleteInstructor(instructors.getInstructor_id());
//            instructor_tableView.getItems().remove(instructors);
//        }
    }


    //controller members

    public void clearMembers() {
        id_input_m.clear();
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to add this member?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {
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
            newMembers.setMembership_status_id(Integer.parseInt(String.valueOf(status)));
            newMembers.setMembership_type_id(Integer.parseInt(type));

            membersService.addMember(newMembers);
            clearMembers();
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
    }

    @FXML
    private void updateMember() {
        Members members = member_tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to update this member?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {
            if (members != null) {
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
    }

    @FXML
    private void deleteMember() {
        Members members = member_tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to delete this member?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {
            if (members != null) {
                membersService.deleteMember(members.getMember_id());
                member_tableView.getItems().remove(members);
                clearMembers();
            }
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
//        member_col_enddate.setCellValueFactory(new PropertyValueFactory<>("end_date"));
//        member_col_joindate.setCellValueFactory(new PropertyValueFactory<>("join_date"));
//        member_col_status.setCellValueFactory(new PropertyValueFactory<>("membership_status_id"));
//        member_col_type.setCellValueFactory(new PropertyValueFactory<>("membership_type_id"));

        // Thực hiện truy vấn để lấy dữ liệu từ cơ sở dữ liệu
        // Giả định có phương thức để lấy danh sách giảng viên từ cơ sở dữ liệu
        List<Members> membersList = membersService.getAllMembers();
        // Đổ dữ liệu vào TableView
        member_tableView.getItems().addAll(membersList);


    }


    //controller class
    public void clearClass() {
        id_input_class.clear();
        classname_input.clear();
        instructorID_input.clear();
        schedule_input.clear();
        capacity_input.clear();
    }

    @FXML
    private void addClass() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message.");
        alert.setHeaderText("Are you sure you want to add this class?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {

            String className = classname_input.getText();
            int insId = Integer.parseInt(instructorID_input.getText());
            String schedule = schedule_input.getText();
            int capacity = Integer.parseInt(capacity_input.getText());
            Classes newClass = new Classes();

            newClass.setClass_name(className);
            newClass.setInstructor_id(insId);
            newClass.setSchedule(schedule);
            newClass.setCapacity(capacity);


            classesService.addClass(newClass);
            clearClass();
            nextMemberID++;
            id_input_class.setText(String.valueOf(nextMemberID));
            if (true) {
                classesService.addClass(newClass);
                class_tableView.getItems().add(newClass);
                clearIns();
            } else {
                // Hiển thị thông báo lỗi để thông báo cho người dùng biết rằng dữ liệu nhập vào không hợp lệ.
                emptyFields();
            }

        }
    }

    @FXML
    private void updateClass() {
        Classes classes = class_tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message.");
        alert.setHeaderText("Are you sure you want to update this class?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {
            if (classes != null) {
                String className = classname_input.getText();
                int insID = Integer.valueOf(instructorID_input.getText());
                String schedule = schedule_input.getText();
                int capacity = Integer.parseInt(capacity_input.getText());

                classes.setClass_name(className);
                classes.setInstructor_id(insID);
                classes.setSchedule(schedule);
                classes.setCapacity(capacity);

                classesService.updateClass(classes);

                class_tableView.refresh();
                clearClass();
            }
        }
    }

    @FXML
    private void deleteClass() {
        Classes classes = class_tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to delete this class?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {
            if (classes != null) {
                classesService.deleteClass(classes.getClass_id());
                class_tableView.getItems().remove(classes);
                clearClass();
            }
        }
    }

    private void showClass(Classes classes) {
        id_input_class.setText(String.valueOf(classes.getClass_id()));
        classname_input.setText(classes.getClass_name());
        instructorID_input.setText(String.valueOf(classes.getInstructor_id()));
        schedule_input.setText(classes.getSchedule());
        capacity_input.setText(String.valueOf(classes.getCapacity()));

    }

    private void loadClassData() {
        classID_col.setCellValueFactory(new PropertyValueFactory<>("class_id"));
        className_col.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        insID_col.setCellValueFactory(new PropertyValueFactory<>("instructor_id"));
        schedule_col.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        capacity_col.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        // Thực hiện truy vấn để lấy dữ liệu từ cơ sở dữ liệu
        // Giả định có phương thức để lấy danh sách giảng viên từ cơ sở dữ liệu
        List<Classes> classesList = classesService.getAllClasses();
        // Đổ dữ liệu vào TableView
        class_tableView.getItems().addAll(classesList);
    }

    //controller equipment
    @FXML
    private void addEquipment() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to add this equipment?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {
            String equipment_name = equipment_name_input.getText();
            String category = category_input.getText();
            LocalDate purchase_date = purchase_date_input.getValue();
            String price = price_input.getText();
            String status = status_input_equipment.getText();
            String note = note_input.getText();
            Equipment newEquipment = new Equipment();

            newEquipment.setEquipment_name(equipment_name);
            newEquipment.setCategory(category);
            newEquipment.setPurchase_date(String.valueOf(purchase_date));
            BigDecimal bigDecimalPrice = new BigDecimal(price);
            newEquipment.setPrice(bigDecimalPrice);
            newEquipment.setStatus(status);
            newEquipment.setNotes(note);


            equipmentService.addEquipment(newEquipment);
            clearEquipment();
            nextMemberID++;
            id_input_equipment.setText(String.valueOf(nextMemberID));
            if (true) {
                equipmentService.addEquipment(newEquipment);
                equipment_tableView.getItems().add(newEquipment);
                clearEquipment();
            } else {
                // Hiển thị thông báo lỗi để thông báo cho người dùng biết rằng dữ liệu nhập vào không hợp lệ.
                emptyFields();
            }


        }
    }

    @FXML
    private void updateEquipment() {
        Equipment equipment = equipment_tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to update this equipment?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {
            if (equipment != null) {
                String equipment_name = equipment_name_input.getText();
                String category = category_input.getText();
                LocalDate purchase_date = purchase_date_input.getValue();
                String price = price_input.getText();
                String status = status_input_equipment.getText();
                String note = note_input.getText();

                equipment.setEquipment_name(equipment_name);
                equipment.setCategory(category);
                equipment.setPurchase_date(String.valueOf(purchase_date));
                BigDecimal bigDecimalPrice = new BigDecimal(price);
                equipment.setPrice(bigDecimalPrice);
                equipment.setNotes(note);

                equipmentService.updateEquipment(equipment);

                equipment_tableView.refresh();
                clearEquipment();
            }
        }
    }

    @FXML
    private void deleteEquipment() {
        Equipment equipment = equipment_tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to delete this equipment?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {
            if (equipment != null) {
                equipmentService.deleteEquipment(equipment.getEquipment_id());
                equipment_tableView.getItems().remove(equipment);
                clearEquipment();
            }
        }
    }

    @FXML
    private void clearEquipment() {
        id_input_equipment.clear();
        equipment_name_input.clear();
        category_input.clear();
        status_input_equipment.clear();
        price_input.clear();
        purchase_date_input.setValue(null);
        note_input.clear();
    }

    private void showEquipment(Equipment equipment) {
        id_input_equipment.setText(String.valueOf(equipment.getEquipment_id()));
        equipment_name_input.setText(equipment.getEquipment_name());
        category_input.setText(equipment.getCategory());
        status_input_equipment.setText(equipment.getStatus());
        price_input.setText(String.valueOf(equipment.getPrice()));
        purchase_date_input.setValue(LocalDate.parse(equipment.getPurchase_date()));
        note_input.setText(equipment.getNotes());
    }

    private void loadEquipment() {
        equipment_id_col.setCellValueFactory(new PropertyValueFactory<>("equipment_id"));
        equipment_name_col.setCellValueFactory(new PropertyValueFactory<>("equipment_name"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("category"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        purchase_col.setCellValueFactory(new PropertyValueFactory<>("purchase_date"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
        note_col.setCellValueFactory(new PropertyValueFactory<>("notes"));
        // Thực hiện truy vấn để lấy dữ liệu từ cơ sở dữ liệu
        // Giả định có phương thức để lấy danh sách giảng viên từ cơ sở dữ liệu
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        // Đổ dữ liệu vào TableView
        equipment_tableView.getItems().addAll(equipmentList);
    }


    //controller user
    @FXML
    private void addUser() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to add this user?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {


        }
    }

    @FXML
    private void updateUser() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to update this user?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {

        }
    }

    @FXML
    private void deleteUser() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText("Are you sure you want to delete this user?");
        alert.setContentText("");

        ButtonType confirm = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(confirm, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirm) {

        }
    }

    private void clearUser() {

    }

    private void showUser(Users users) {

    }

    private void loadUser() {

    }
}
