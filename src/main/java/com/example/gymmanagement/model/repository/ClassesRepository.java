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

    private InstructorRepository instructorRepository = new InstructorRepository();

    // Phương thức để thêm một lớp học vào cơ sở dữ liệu
    public void addClass(Classes classes) {
        String query = "INSERT INTO classes (class_name, instructor_id, schedule, capacity) " + "VALUES (?, ?, ?, ?)";
        executeClassesQuery(query, classes);
    }

    // Phương thức để cập nhật thông tin lớp học trong cơ sở dữ liệu
    public void updateClass(Classes classes) {
        String query = "UPDATE classes " + "SET class_name = ?, instructor_id = ?, schedule = ?, capacity = ? " + "WHERE class_id = ?";
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

    public List<Classes> getClassesByInstructorId(int instructorId) {
        List<Classes> classesList = new ArrayList<>();
        try (Connection connection = jdbcConnect.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM classes WHERE instructor_id = ?")) {
            statement.setInt(1, instructorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    classesList.add(fromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    // Phương thức để cập nhật tên lớp học
    public void updateClassName(int gymClassID, String newClassName) {
        String query = "UPDATE classes SET class_name = ? WHERE class_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newClassName);
            statement.setInt(2, gymClassID);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Phương thức để cập nhật tên giảng viên
    public void updateInstructor(int gymClassID, String newInstructor) {
        String query = "UPDATE classes SET instructor_id = ? WHERE class_id = ?";
        int instructorId = instructorRepository.getInstructorIdByName(newInstructor); // Chưa biết phương thức này trong instructorRepository
        try (Connection connection = jdbcConnect.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, instructorId);
            statement.setInt(2, gymClassID);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Phương thức để cập nhật lịch học
    public void updateSchedule(int gymClassID, String newSchedule) {
        String query = "UPDATE classes SET schedule = ? WHERE class_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newSchedule);
            statement.setInt(2, gymClassID);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Phương thức để cập nhật sức chứa
    public void updateCapacity(int gymClassID, int newCapacity) {
        String query = "UPDATE classes SET capacity = ? WHERE class_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, newCapacity);
            statement.setInt(2, gymClassID);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getTotalClasses() {
        try (Connection connection = jdbcConnect.getJDBCConnection(); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(class_id) as count FROM classes"); ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Classes> getClassByPage(int pageNumber, int pageSize) {
        List<Classes> classList = new ArrayList<>();
        int offset = (pageNumber - 1) * pageSize;
        String query = "SELECT * FROM classes LIMIT ? OFFSET ?";

        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    classList.add(fromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classList;
    }


    public List<Classes> getClassByName(String searchTerm) {
        List<Classes> classList = new ArrayList<>();
        String query = "SELECT * FROM classes WHERE class_name LIKE ?";

        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Using '%' to search for classes with names containing the searchTerm
            statement.setString(1, "%" + searchTerm + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    classList.add(fromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classList;
    }

}
