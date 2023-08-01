package com.example.gymmanagement.controller;

import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.repository.InstructorsRepository;
import com.example.gymmanagement.model.service.InstructorsService;
import com.example.gymmanagement.model.service.impl.InstructorsServiceImpl;
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
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.lang.String.valueOf;

public class Controller implements Initializable {
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
    private TableView<Instructors> instructor_tableView ;
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

    //xử lí comboBox
    private ObservableList<Instructors> entity;
    private String gender[] = {"Male", "Female", "Others"};

    public void insGenderList() {
        if (gender_input_i == null) {
            gender_input_i = new ComboBox<>();
        }
        List<String> genderList = new ArrayList<>();
        for (String data : gender) {
            genderList.add(data);

        }
        ObservableList listGender = FXCollections.observableArrayList(genderList);
        gender_input_i.setItems(listGender);
    }


    //setVisible
    private Executor executor = Executors.newFixedThreadPool(3);

    public void switchForm(ActionEvent event) {
        if (event.getSource() == instructor_btn) {
            executor.execute(() -> {
                add_instructor_form.setVisible(true);
                add_member_form.setVisible(false);
                main_form.setVisible(false);
                insGenderList();
                instructorShow();
            });

        } else if (event.getSource() == member_btn) {
            executor.execute(() -> {
                add_instructor_form.setVisible(false);
                add_member_form.setVisible(true);
                main_form.setVisible(false);
            });
        } else if (event.getSource() == dashboard_btn) {
            executor.execute(() -> {
                main_form.setVisible(true);
                add_member_form.setVisible(false);
                add_instructor_form.setVisible(false);

//                memberGenderList();
//                memberStatusList();
//                memberShow();
            });
        }
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

    //show các colum lấy từ database
    public void instructorShow() {
        ObservableList<Instructors> instructorsList;
        instructorsList = new InstructorsServiceImpl().getAllInstructors();
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
        instructor_tableView.setItems(instructorsList);
    }
    //Hiển thị từ entity ra text field

    public void selectInsRecord() {
        instructor_tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Instructors>() {
            @Override
            public void changed(ObservableValue<? extends Instructors> observableValue, Instructors members, Instructors newValue) {
                if (newValue != null) {
                    id_input_i.setText(valueOf(newValue.getInstructor_id()));
//                    setID();
                    firstname_input_i.setText(valueOf(newValue.getFirst_name()));
                    lastname_input_i.setText(valueOf(newValue.getLast_name()));
                    dob_input_i.setValue(LocalDate.parse(valueOf(newValue.getDob())));
                    gender_input_i.setValue(String.valueOf(newValue.getGender()));
                    phone_input_i.setText(valueOf(newValue.getPhone_number()));
                    address_input_i.setText(valueOf(newValue.getAddress()));
                    email_input_i.setText(valueOf(newValue.getEmail()));
                    hiredate_input_i.setValue(LocalDate.parse(valueOf(newValue.getHire_date())));
                    speci_input_i.setText(valueOf(newValue.getSpecialization()));
                    year_input_i.setText(valueOf(newValue.getExperienceYears()));
                } else {
                    firstname_input_i.clear();
                    lastname_input_i.clear();
                    dob_input_i = null;
                    gender_input_i = null;
                    phone_input_i.clear();
                    address_input_i.clear();
                    email_input_i.clear();
                    hiredate_input_i = null;
                    year_input_i = null;
                }
            }

        });
    }

    private Instructors ins;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        insGenderList();
        instructorShow();
       selectInsRecord();
        inst_add_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new InstructorsServiceImpl().addInstructor(ins);
            }
        });
        inst_delete_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new InstructorsServiceImpl().deleteInstructor();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        inst_update_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new InstructorsServiceImpl().updateInstructor(ins);
            }
        });
    }


}
