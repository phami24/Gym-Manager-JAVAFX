package com.example.gymmanagement.model.repository;

import com.example.gymmanagement.database.JDBCConnect;
import com.example.gymmanagement.model.entity.Instructors;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class InstructorsRepository {
    private final JDBCConnect jdbcConnect;

    public InstructorsRepository() {
        this.jdbcConnect = new JDBCConnect();
    }

    // Phương thức để thêm một instructor vào cơ sở dữ liệu
    public void addInstructor(Instructors instructor) {

        String query = "INSERT INTO instructors (first_name, last_name, dob, gender, email, phone_number, address, hire_date, specialization, experience_years) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        executeInstructorQuery(query, instructor);
    }

    // Phương thức để cập nhật thông tin instructor trong cơ sở dữ liệu
    public void updateInstructor(Instructors instructor) {
        String query = "UPDATE instructors " +
                "SET first_name = ?, last_name = ?, dob = ?, gender = ?, email = ?, phone_number = ?, address = ?, hire_date = ?, specialization = ?, experience_years = ? " +
                "WHERE instructor_id = ?";
        executeInstructorQuery(query, instructor);

    }

    // Phương thức để xóa instructor khỏi cơ sở dữ liệu
    public void deleteInstructor(int instructorId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = jdbcConnect.getJDBCConnection();
            String query = "DELETE FROM instructors WHERE instructor_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, instructorId);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closePreparedStatement(statement);
        }
    }

    // Phương thức để lấy thông tin instructor dựa trên ID
    public Instructors getInstructorById(int instructorId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Instructors instructor = null;

        try {
            connection = jdbcConnect.getJDBCConnection();
            String query = "SELECT * FROM instructors WHERE instructor_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, instructorId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                instructor = new Instructors();
                instructor = fromResultSet(resultSet);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closeResultSet(resultSet);
            jdbcConnect.closePreparedStatement(statement);
        }

        return instructor;
    }

    // Phương thức để lấy danh sách tất cả các instructor
    public List<Instructors> getAllInstructors() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Instructors> instructorsList = new ArrayList<>();

        try {
            connection = jdbcConnect.getJDBCConnection();
            String query = "SELECT * FROM instructors";
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Instructors instructor = new Instructors();
                instructor = fromResultSet(resultSet);

                instructorsList.add(instructor);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closeResultSet(resultSet);
            jdbcConnect.closePreparedStatement(statement);
        }
        return instructorsList;
    }

    public Instructors fromResultSet(ResultSet resultSet) throws SQLException {
        Instructors instructor = new Instructors();
        instructor.setInstructor_id(resultSet.getInt("instructor_id"));
        instructor.setHire_date(resultSet.getString("hire_date"));
        instructor.setSpecialization(resultSet.getString("specialization"));
        instructor.setExperienceYears(resultSet.getInt("experience_years"));
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