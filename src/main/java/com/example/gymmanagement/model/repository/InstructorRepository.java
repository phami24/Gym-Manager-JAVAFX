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
        String query = "INSERT INTO instructors (first_name, last_name, dob, gender, email, phone_number, address, hire_date, experienceYears, base_salary, salary) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            statement.setInt(9, instructor.getExperienceYears());
            statement.setBigDecimal(10, instructor.getBaseSalary());
            statement.setBigDecimal(11, instructor.getSalary());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInstructor(Instructors instructor) {
        String query = "UPDATE instructors " +
                "SET first_name = ?, last_name = ?, dob = ?, gender = ?, email = ?, phone_number = ?, address = ?, hire_date = ?, " +
                "experienceYears = ?, base_salary = ?, salary = ? " +
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
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM instructors Where status = 1");
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
            statement.setInt(9, instructor.getExperienceYears());
            statement.setBigDecimal(10, instructor.getBaseSalary());
            statement.setBigDecimal(11, instructor.getSalary());
            statement.setInt(12, instructor.getInstructor_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getInstructorIdByName(String instructorName) {
        Connection connection = jdbcConnect.getJDBCConnection();
        String query = "SELECT instructor_id  FROM instructors WHERE CONCAT(first_name, ' ', last_name) = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, instructorName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("instructor_id");
                }
                return -1; // Return a default value indicating not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Ném lại ngoại lệ để lớp gọi viên có thể xử lý ngoại lệ này
        }
    }

    public String getInstructorNameById(int instructorId) {
        Connection connection = jdbcConnect.getJDBCConnection();
        String query = "SELECT CONCAT(first_name, ' ', last_name) AS instructor_name FROM instructors WHERE instructor_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, instructorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("instructor_name");
                }
                return null; // Return null if instructor not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Ném lại ngoại lệ để lớp gọi viên có thể xử lý ngoại lệ này
        }
    }

    public void updateInstructorStatus(int instructorId, int newStatusId) {
        String query = "UPDATE instructors SET status = ? WHERE instructor_id = ?";

        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, newStatusId);
            statement.setInt(2, instructorId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateFirstName(int instructorID, String newFirstName) {
        String query = "UPDATE instructors SET first_name = ? WHERE instructor_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newFirstName);
            statement.setInt(2, instructorID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLastName(int instructorID, String newLastName) {
        String query = "UPDATE instructors SET last_name = ? WHERE instructor_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newLastName);
            statement.setInt(2, instructorID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDob(int instructorID, String newDob) {
        String query = "UPDATE instructors SET dob = ? WHERE instructor_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(newDob));
            statement.setInt(2, instructorID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGender(int instructorID, String newGender) {
        String query = "UPDATE instructors SET gender = ? WHERE instructor_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newGender);
            statement.setInt(2, instructorID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmail(int instructorID, String newEmail) {
        String query = "UPDATE instructors SET email = ? WHERE instructor_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newEmail);
            statement.setInt(2, instructorID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePhoneNumber(int instructorID, String newPhoneNumber) {
        String query = "UPDATE instructors SET phone_number = ? WHERE instructor_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newPhoneNumber);
            statement.setInt(2, instructorID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAddress(int instructorID, String newAddress) {
        String query = "UPDATE instructors SET address = ? WHERE instructor_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newAddress);
            statement.setInt(2, instructorID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateHireDate(int instructorID, String newHireDate) {
        String query = "UPDATE instructors SET hire_date = ? WHERE instructor_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(newHireDate));
            statement.setInt(2, instructorID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateExperienceYears(int instructorID, int newExperienceYears) {
        String query = "UPDATE instructors SET experienceYears = ? WHERE instructor_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, newExperienceYears);
            statement.setInt(2, instructorID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBaseSalary(int instructorId, double newBaseSalary) {
        String query = "UPDATE instructors SET base_salary = ? WHERE instructor_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBigDecimal(1, BigDecimal.valueOf(newBaseSalary));
            statement.setInt(2, instructorId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSalary(int instructorId, double newSalary) {
        String query = "UPDATE instructors SET salary = ? WHERE instructor_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBigDecimal(1, BigDecimal.valueOf(newSalary));
            statement.setInt(2, instructorId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTotalInstructor() {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(instructor_id) as count FROM instructors");
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Instructors> getInstructorByPage(int currentPage, int pageSize) {
        List<Instructors> instructorsList = new ArrayList<>();
        int offset = (currentPage - 1) * pageSize;

        String query = "SELECT * FROM instructors WHERE status = 1 LIMIT ? OFFSET ?";

        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, pageSize);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    instructorsList.add(fromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instructorsList;
    }

}

