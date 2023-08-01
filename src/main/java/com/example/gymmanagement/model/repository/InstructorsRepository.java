package com.example.gymmanagement.model.repository;

import com.example.gymmanagement.controller.Controller;
import com.example.gymmanagement.database.JDBCConnect;
import com.example.gymmanagement.model.entity.Instructors;
import com.example.gymmanagement.model.service.InstructorsService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;


public class InstructorsRepository {
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

    private final JDBCConnect jdbcConnect;
    private Connection con = null;
    private PreparedStatement ps = null;

    private ResultSet rs = null;
    private Statement statement = null;
    private int myIndex;

    public InstructorsRepository() {
        this.jdbcConnect = new JDBCConnect();
    }

    //báo lỗi
    public void emptyFields() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error message");
        alert.setHeaderText("");
        alert.setContentText("Please fill all blank fiels");
        alert.showAndWait();
    }

//    //xử lí comboBox
//    private ObservableList<Instructors> entity;
//    private String gender[] = {"Male", "Female", "Others"};
//
//    public void insGenderList() {
//        if (gender_input_i == null) {
//            gender_input_i = new ComboBox<>();
//        }
//        List<String> genderList = new ArrayList<>();
//        for (String data : gender) {
//            genderList.add(data);
//
//        }
//        ObservableList listGender = FXCollections.observableArrayList(genderList);
//        gender_input_i.setItems(listGender);
//    }

    // Phương thức để thêm một instructor vào cơ sở dữ liệu
    public void addInstructor(Instructors instructor) {
        con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();
        String query = "INSERT INTO instructors (first_name, last_name, dob, gender, email, phone_number, address, hire_date, specialization, experience_years) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Alert alert;
            if (id_input_i.getText().isEmpty() || firstname_input_i.getText().isEmpty() || lastname_input_i.getText().isEmpty()
                    || dob_input_i.getValue() == null || gender_input_i.getSelectionModel().getSelectedItem() == null
                    || email_input_i.getText().isEmpty() || phone_input_i.getText().isEmpty()
                    || address_input_i.getText().isEmpty() || hiredate_input_i.getValue() == null
                    || speci_input_i.getText().isEmpty() || year_input_i.getText() == null
            ) {
                emptyFields();
            } else {
                String checkData = "SELECT instructor_id FROM instructors WHERE instructor_id = '" +
                        id_input_i.getText() + "'";
                statement = con.createStatement();
                rs = statement.executeQuery(checkData);
                if (rs.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText("");
                    alert.setContentText("ID " + id_input_i.getText() + " was already taken!");
                    alert.showAndWait();
                } else {
                    ps = con.prepareStatement(query);
                    ps.setString(1, firstname_input_i.getText());
                    ps.setString(2, lastname_input_i.getText());
                    ps.setString(3, valueOf(dob_input_i.getValue()));
                    ps.setString(4, String.valueOf(gender_input_i.getValue()));
                    ps.setString(5, email_input_i.getText());
                    ps.setString(6, phone_input_i.getText());
                    ps.setString(7, address_input_i.getText());
                    ps.setString(8, valueOf(hiredate_input_i.getValue()));
                    ps.setString(9, speci_input_i.getText());
                    ps.setString(10, year_input_i.getText());
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText("");
                    alert.setContentText("Successfully added");
                    alert.showAndWait();
                    //to insert all data
                    ps.executeUpdate();

                    //show bảng sau khi add
                    new Controller().instructorShow();

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức để cập nhật thông tin instructor trong cơ sở dữ liệu
    public void updateInstructor(Instructors instructor) {
        if (dob_input_i != null) {
            LocalDate dob = dob_input_i.getValue();
            String sql = "UPDATE instructors SET first_name = '" + firstname_input_i.getText() + "',last_name ='" + lastname_input_i.getText() + "'" +
                    ",dob = '" + dob + "',gender = '" + gender_input_i.getSelectionModel().getSelectedItem() + "',email = '" + email_input_i.getText() + "'" +
                    ",phone_number = '" + phone_input_i.getText() + "',address = '" + address_input_i.getText() + "'" +
                    ",hire_date = '" + hiredate_input_i.getValue() + "'" +
                    ",specialization = '" + speci_input_i.getText() + "',experienceYears ='" + year_input_i.getText() + "' WHERE instructor_id ='" +
                    id_input_i.getText() + "'";
            con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();
            try {
                Alert alert;
                if (id_input_i.getText() == null || firstname_input_i.getText().isEmpty() || lastname_input_i.getText().isEmpty()
                        || dob_input_i.getValue() == null || gender_input_i.getSelectionModel().getSelectedItem() == null
                        || email_input_i.getText().isEmpty() || phone_input_i.getText().isEmpty()
                        || address_input_i.getText().isEmpty() || hiredate_input_i.getValue() == null
                        || speci_input_i.getText() == null || year_input_i.getText() == null) {
                    emptyFields();
                } else {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText("");
                    alert.setContentText("Are you sure you want to UPDATE memberID: " + id_input_i.getText() + " ?");

                    Optional<ButtonType> option = alert.showAndWait();
                    if (option.get().equals(ButtonType.OK)) {
                        ps = con.prepareStatement(sql);
                        ps.executeUpdate();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information message");
                        alert.setHeaderText("");
                        alert.setContentText("Successfully Update!!");
                        alert.showAndWait();

                        //to update tableview
//                    instructorShow();

                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Information message");
                        alert.setHeaderText("");
                        alert.setContentText("Error update.");
                        alert.showAndWait();
                    }
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    // Phương thức để xóa instructor khỏi cơ sở dữ liệu
    public void deleteInstructor() throws IOException {
//        int instructorId = Integer.parseInt(id_input_i.getText());
//        myIndex = instructor_tableView.getSelectionModel().getSelectedIndex();
        con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();


        Parent root = FXMLLoader.load(getClass().getResource("/com/example/gymmanagement/view/member.fxml"));
// Tìm TextField và lấy giá trị của nó
        TextField lastNameField = (TextField) root.lookup("#lastname_input_i");
        String lastName = lastNameField.getText();
        TextField firstNameField = (TextField) root.lookup("#firstname_input_i");
        String firstName = firstNameField.getText();
        TextField phoneField = (TextField) root.lookup("#phone_input_i");
        String phoneNum = phoneField.getText();
        TextField specField = (TextField) root.lookup("#speci_input_i");
        String spec = specField.getText();

        try {
            Alert alert;
//            LocalDate dob = dob_input_i.getValue();

            if (id_input_i == null || firstName.isEmpty() || lastName.isEmpty()
                    || dob_input_i == null || gender_input_i.getValue() == null
                    || email_input_i.getText().isEmpty() || phoneNum.isEmpty()
                    || address_input_i.getText().isEmpty() || hiredate_input_i.getValue() == null
                    || spec == null || year_input_i.getText() == null) {
                emptyFields();
            } else {
                String sql = "DELETE FROM instructors WHERE instructor_id = ?";
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Member ID: " + id_input_i + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(id_input_i.getText()));
                    ps.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful Delete!");
                    alert.showAndWait();

                    //to update tableview
//                    instructorShow();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
//    public void deleteInstructor(int instructorId) {
//        Connection connection = null;
//        PreparedStatement statement = null;
//
//        try {
//            connection = jdbcConnect.getJDBCConnection();
//            String query = "DELETE FROM instructors WHERE instructor_id = ?";
//            statement = connection.prepareStatement(query);
//            statement.setInt(1, instructorId);
//
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            jdbcConnect.closeConnection(connection);
//            jdbcConnect.closePreparedStatement(statement);
//        }
//    }

    // Phương thức để lấy thông tin instructor dựa trên ID
    public Instructors getInstructorById(int instructorId) {

        Instructors instructor = null;

        try {
            con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();
            String query = "SELECT * FROM instructors WHERE instructor_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, instructorId);

            rs = ps.executeQuery();

            if (rs.next()) {
                instructor = new Instructors();
                instructor = fromResultSet(rs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
//        finally {
//            jdbcConnect.closeConnection(connection);
//            jdbcConnect.closeResultSet(resultSet);
//            jdbcConnect.closePreparedStatement(statement);
//        }

        return instructor;
    }

    // Phương thức để lấy danh sách tất cả các instructor
    public ObservableList<Instructors> getAllInstructors() {

        ObservableList<Instructors> listIntructor = FXCollections.observableArrayList();

        try {
            con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();
            String query = "SELECT * FROM instructors";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                Instructors instructors = new Instructors();
                instructors = fromResultSet(rs);

                listIntructor.add(instructors);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(con);
            jdbcConnect.closeResultSet(rs);
            jdbcConnect.closePreparedStatement(ps);
        }
        return listIntructor;
    }

    //lấy từ data base ra entity
    public Instructors fromResultSet(ResultSet resultSet) throws SQLException {
        Instructors instructor = new Instructors();
        instructor.setInstructor_id(resultSet.getInt("instructor_id"));
        instructor.setHire_date(resultSet.getString("hire_date"));
        instructor.setSpecialization(resultSet.getString("specialization"));
        instructor.setExperienceYears(resultSet.getInt("experienceYears"));
        instructor.setFirst_name(resultSet.getString("first_name"));
        instructor.setLast_name(resultSet.getString("last_name"));
        instructor.setDob(resultSet.getDate("dob").toString());
        instructor.setGender(resultSet.getString("gender"));
        instructor.setEmail(resultSet.getString("email"));
        instructor.setPhone_number(resultSet.getString("phone_number"));
        instructor.setAddress(resultSet.getString("address"));
        return instructor;
    }


    public void executeInstructorQuery(String query, Instructors instructor) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = jdbcConnect.getJDBCConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, instructor.getFirst_name());
            statement.setString(2, instructor.getLast_name());
            statement.setDate(3, Date.valueOf(instructor.getDob()));
            statement.setString(4, instructor.getGender());
            statement.setString(5, instructor.getEmail());
            statement.setString(6, instructor.getPhone_number());
            statement.setString(7, instructor.getAddress());
            statement.setDate(8, Date.valueOf(instructor.getHire_date()));
            statement.setString(9, instructor.getSpecialization());
            statement.setInt(10, instructor.getExperienceYears());

            if (query.contains("UPDATE")) {
                statement.setInt(11, instructor.getInstructor_id());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closePreparedStatement(statement);
        }
    }

}