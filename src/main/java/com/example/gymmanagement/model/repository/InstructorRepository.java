package com.example.gymmanagement.model.repository;

import com.example.gymmanagement.database.JDBCConnect;
import com.example.gymmanagement.model.entity.Instructors;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorRepository {
    private final JDBCConnect jdbcConnect;

    public InstructorRepository() {
        this.jdbcConnect = new JDBCConnect();
    }

    public void addInstructor(Instructors instructor) {
        String query = "INSERT INTO instructors (first_name, last_name, dob, gender, email, phone_number, address, hire_date, specialization, experienceYears, base_salary, salary) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        executeInstructorQuery(query, instructor);
    }

    public void updateInstructor(Instructors instructor) {
        String query = "UPDATE instructors " +
                "SET first_name = ?, last_name = ?, dob = ?, gender = ?, email = ?, phone_number = ?, address = ?, hire_date = ?, " +
                "specialization = ?, experienceYears = ?, base_salary = ?, salary = ? " +
                "WHERE instructor_id = ?";
        executeInstructorQuery(query, instructor);
    }

    public void deleteInstructor(int instructorId) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM instructors WHERE instructor_id = ?")) {
            statement.setInt(1, instructorId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Instructors getInstructorById(int instructorId) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM instructors WHERE instructor_id = ?")) {
            statement.setInt(1, instructorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Instructors> getAllInstructors() {
        List<Instructors> instructorsList = new ArrayList<>();
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM instructors");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                instructorsList.add(fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instructorsList;
    }



    public Instructors fromResultSet(ResultSet resultSet) throws SQLException {
        Instructors instructor = new Instructors();
        instructor.setInstructor_id(resultSet.getInt("instructor_id"));
        instructor.setHireDate(resultSet.getString("hire_date"));
        instructor.setSpecialization(resultSet.getString("specialization"));
        instructor.setExperienceYears(resultSet.getInt("experienceYears"));
        instructor.setBaseSalary(resultSet.getBigDecimal("base_salary"));
        instructor.setSalary(resultSet.getBigDecimal("salary"));
        instructor.setFirst_name(resultSet.getString("first_name"));
        instructor.setLast_name(resultSet.getString("last_name"));
        instructor.setDob(resultSet.getString("dob"));
        instructor.setGender(resultSet.getString("gender"));
        instructor.setEmail(resultSet.getString("email"));
        instructor.setPhone_number(resultSet.getString("phone_number"));
        instructor.setAddress(resultSet.getString("address"));
        return instructor;
    }

    public void executeInstructorQuery(String query, Instructors instructor) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, instructor.getFirst_name());
            statement.setString(2, instructor.getLast_name());
            statement.setDate(3, Date.valueOf(instructor.getDob()));
            statement.setString(4, instructor.getGender());
            statement.setString(5, instructor.getEmail());
            statement.setString(6, instructor.getPhone_number());
            statement.setString(7, instructor.getAddress());
            statement.setDate(8, Date.valueOf(instructor.getHireDate()));
            statement.setString(9, instructor.getSpecialization());
            statement.setInt(10, instructor.getExperienceYears());
            statement.setBigDecimal(11, instructor.getBaseSalary());
            statement.setBigDecimal(12, instructor.getSalary());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
