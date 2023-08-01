package com.example.gymmanagement.model.repository;

import com.example.gymmanagement.database.JDBCConnect;
import com.example.gymmanagement.model.entity.Members;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembersRepository {
    private final JDBCConnect jdbcConnect;

    public MembersRepository() {
        this.jdbcConnect = new JDBCConnect();
    }

    // Phương thức để thêm một thành viên vào cơ sở dữ liệu

    public void addMember(Members member) {
        String query = "INSERT INTO members (first_name, last_name, dob, gender, email, phone_number, address, join_date, end_date, membership_status_id, membership_type_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        executeMemberQuery(query, member);
    }

    // Phương thức để cập nhật thông tin thành viên trong cơ sở dữ liệu
    public void updateMember(Members member) {

        String query = "UPDATE members " +
                "SET first_name = ?, last_name = ?, dob = ?, gender = ?, email = ?, phone_number = ?, address = ?, join_date = ?, end_date = ?, " +
                "membership_status_id = ?, membership_type_id = ? " +
                "WHERE member_id = ?";
        executeMemberQuery(query, member);
    }

    // Phương thức để xóa thành viên khỏi cơ sở dữ liệu
    public void deleteMember(int memberId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = jdbcConnect.getJDBCConnection();
            String query = "DELETE FROM members WHERE member_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, memberId);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closePreparedStatement(statement);
        }
    }

    // Phương thức để lấy thông tin thành viên dựa trên ID
    public Members getMemberById(int memberId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Members member = null;

        try {
            connection = jdbcConnect.getJDBCConnection();
            String query = "SELECT * FROM members WHERE member_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, memberId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                member = new Members();
                member = fromResultSet(resultSet);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closeResultSet(resultSet);
            jdbcConnect.closePreparedStatement(statement);
        }

        return member;
    }

    // Phương thức để lấy danh sách tất cả các thành viên
    public List<Members> getAllMembers() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Members> membersList = new ArrayList<>();

        try {
            connection = jdbcConnect.getJDBCConnection();
            String query = "SELECT * FROM members";
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Members member = new Members();
                member = fromResultSet(resultSet);

                membersList.add(member);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closeResultSet(resultSet);
            jdbcConnect.closePreparedStatement(statement);
        }
        return membersList;
    }

    //lấy từ database ra entity
    public Members fromResultSet(ResultSet resultSet) throws SQLException {
        Members member = new Members();
        member.setMember_id(resultSet.getInt("member_id"));
        member.setJoin_date(resultSet.getString("join_date"));
        member.setEnd_date(resultSet.getString("end_date"));
        member.setMembership_status_id(resultSet.getInt("membership_status_id"));
        member.setMembership_type_id(resultSet.getInt("membership_type_id"));
        member.setFirst_name(resultSet.getString("first_name"));
        member.setLast_name(resultSet.getString("last_name"));
        member.setDob(resultSet.getDate("dob").toString());
        member.setGender(resultSet.getString("gender"));
        member.setEmail(resultSet.getString("email"));
        member.setPhone_number(resultSet.getString("phone_number"));
        member.setAddress(resultSet.getString("address"));
        return member;
    }

    //lấy từ entity vào database
    public void executeMemberQuery(String query, Members member) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = jdbcConnect.getJDBCConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, member.getFirst_name());
            statement.setString(2, member.getLast_name());
            statement.setDate(3, Date.valueOf(member.getDob()));
            statement.setString(4, member.getGender());
            statement.setString(5, member.getEmail());
            statement.setString(6, member.getPhone_number());
            statement.setString(7, member.getAddress());
            statement.setDate(8, Date.valueOf(member.getJoin_date()));
            statement.setDate(9, Date.valueOf(member.getEnd_date()));
            statement.setInt(10, member.getMembership_status_id());
            statement.setInt(11, member.getMembership_type_id());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closePreparedStatement(statement);
        }
    }
}