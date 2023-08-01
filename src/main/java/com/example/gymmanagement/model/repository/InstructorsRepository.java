package com.example.gymmanagement.model.repository;


import com.example.gymmanagement.database.JDBCConnect;
import com.example.gymmanagement.model.entity.Instructors;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;


public class InstructorsRepository {

    private final JDBCConnect jdbcConnect;
    private Connection con = null;
    private PreparedStatement ps = null;

    private ResultSet rs = null;
    private Statement statement = null;
    private int myIndex;

    public InstructorsRepository() {
        this.jdbcConnect = new JDBCConnect();
    }


    // Phương thức để thêm một instructor vào cơ sở dữ liệu
    public void addInstructor(Instructors instructor) {
        con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();
        String query = "INSERT INTO instructors (first_name, last_name, dob, gender, email, phone_number, address, hire_date, specialization, experienceYears) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        executeInstructorQuery(query, instructor);
    }

    // Phương thức để cập nhật thông tin instructor trong cơ sở dữ liệu
    public void updateInstructor(Instructors instructor) {
        String query = "UPDATE instructors " +
                "SET first_name = ?, last_name = ?, dob = ?, gender = ?, email = ?, phone_number = ?, address = ?, hire_date = ?, specialization = ?, experienceYears = ? " +
                "WHERE instructor_id = ?";
        executeInstructorQuery(query, instructor);
    }

    // Phương thức để xóa instructor khỏi cơ sở dữ liệu
    public void deleteInstructor(int instructorId) {

        try {
            con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();
            String query = "DELETE FROM instructors WHERE instructor_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, instructorId);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            com.example.gymmanagement.database.dao.JDBCConnect.closeConnection(con);
            com.example.gymmanagement.database.dao.JDBCConnect.closePreparedStatement(ps);
        }
    }

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
        finally {
            com.example.gymmanagement.database.dao.JDBCConnect.closeConnection(con);
            com.example.gymmanagement.database.dao.JDBCConnect.closeResultSet(rs);
            com.example.gymmanagement.database.dao.JDBCConnect.closePreparedStatement(ps);
        }

        return instructor;
    }

    // Phương thức để lấy danh sách tất cả các instructor
    public List<Instructors> getAllInstructors() {

        List<Instructors> instructorsList = new ArrayList<>();

        try {
            con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();
            String query = "SELECT * FROM instructors";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                Instructors instructor = new Instructors();
                instructor = fromResultSet(rs);

                instructorsList.add(instructor);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            com.example.gymmanagement.database.dao.JDBCConnect.closeConnection(con);
            com.example.gymmanagement.database.dao.JDBCConnect.closeResultSet(rs);
            com.example.gymmanagement.database.dao.JDBCConnect.closePreparedStatement(ps);
        }
        return instructorsList;
    }

    //lấy từ database ra
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

        try {
            con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();
            ps = con.prepareStatement(query);

            ps.setString(1, instructor.getFirst_name());
            ps.setString(2, instructor.getLast_name());
            ps.setDate(3, Date.valueOf(instructor.getDob()));
            ps.setString(4, instructor.getGender());
            ps.setString(5, instructor.getEmail());
            ps.setString(6, instructor.getPhone_number());
            ps.setString(7, instructor.getAddress());
            ps.setDate(8, Date.valueOf(instructor.getHire_date()));
            ps.setString(9, instructor.getSpecialization());
            ps.setInt(10, instructor.getExperienceYears());

            if (query.contains("UPDATE")) {
                ps.setInt(11, instructor.getInstructor_id());
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            com.example.gymmanagement.database.dao.JDBCConnect.closeConnection(con);
            com.example.gymmanagement.database.dao.JDBCConnect.closePreparedStatement(ps);
        }
    }
//set ID
    public int getNextMemberID() {
        int nextID = 0;

        try {
            con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT MAX(instructor_id) FROM instructors");

            if (rs.next()) {
                nextID = rs.getInt(1) + 1;
            }

            rs.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextID;
    }

}