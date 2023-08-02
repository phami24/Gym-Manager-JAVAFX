package com.example.gymmanagement.model.repository;

import com.example.gymmanagement.database.JDBCConnect;
import com.example.gymmanagement.model.entity.Classes;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClassesRepository {
    private final JDBCConnect jdbcConnect;

    public ClassesRepository() {
        this.jdbcConnect = new JDBCConnect();
    }

    // Phương thức để thêm một lớp học vào cơ sở dữ liệu
    public void addClass(Classes classes) {
        String query = "INSERT INTO classes (class_name, instructor_id, schedule, capacity) " +
                "VALUES (?, ?, ?, ?)";
        executeClassesQuery(query, classes);
    }

    // Phương thức để cập nhật thông tin lớp học trong cơ sở dữ liệu
    public void updateClass(Classes classes) {
        String query = "UPDATE classes " +
                "SET class_name = ?, instructor_id = ?, schedule = ?, capacity = ? " +
                "WHERE class_id = ?";
        executeClassesQuery(query, classes);
    }

    // Phương thức để xóa lớp học khỏi cơ sở dữ liệu
    public void deleteClass(int classId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = jdbcConnect.getJDBCConnection();
            String query = "DELETE FROM classes WHERE class_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, classId);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closePreparedStatement(statement);
        }
    }

    // Phương thức để lấy thông tin lớp học dựa trên ID
    public Classes getClassById(int classId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Classes classes = null;

        try {
            connection = jdbcConnect.getJDBCConnection();
            String query = "SELECT * FROM classes WHERE class_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, classId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                classes = fromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closeResultSet(resultSet);
            jdbcConnect.closePreparedStatement(statement);
        }

        return classes;
    }

    // Phương thức để lấy danh sách tất cả các lớp học
    public List<Classes> getAllClasses() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Classes> classesList = new ArrayList<>();

        try {
            connection = jdbcConnect.getJDBCConnection();
            String query = "SELECT * FROM classes";
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Classes classes = fromResultSet(resultSet);
                classesList.add(classes);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closeResultSet(resultSet);
            jdbcConnect.closePreparedStatement(statement);
        }

        return classesList;
    }

    public Classes fromResultSet(ResultSet resultSet) throws SQLException {
        Classes classes = new Classes();
        classes.setClass_id(resultSet.getInt("class_id"));
        classes.setClass_name(resultSet.getString("class_name"));
        classes.setInstructor_id(resultSet.getInt("instructor_id"));
        classes.setSchedule(resultSet.getString("schedule"));
        classes.setCapacity(resultSet.getInt("capacity"));
        return classes;
    }

    private void executeClassesQuery(String query, Classes classes) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = jdbcConnect.getJDBCConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, classes.getClass_name());
            statement.setInt(2, classes.getInstructor_id());
            statement.setString(3, classes.getSchedule());
            statement.setInt(4, classes.getCapacity());
            if (query.contains("UPDATE")) {
                statement.setInt(5, classes.getClass_id());
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
