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
    private Connection con = null;
    private PreparedStatement ps = null;

    private ResultSet rs = null;
    private Statement statement = null;

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

        try {
            con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();
            String query = "DELETE FROM members WHERE member_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, memberId);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            com.example.gymmanagement.database.dao.JDBCConnect.closeConnection(con);
            com.example.gymmanagement.database.dao.JDBCConnect.closePreparedStatement(ps);
        }
    }

    // Phương thức để lấy thông tin thành viên dựa trên ID
    public Members getMemberById(int memberId) {

        Members member = null;

        try {
            con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();
            String query = "SELECT * FROM members WHERE member_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, memberId);

            rs = ps.executeQuery();

            if (rs.next()) {
                member = new Members();
                member = fromResultSet(rs);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            com.example.gymmanagement.database.dao.JDBCConnect.closeConnection(con);
            com.example.gymmanagement.database.dao.JDBCConnect.closeResultSet(rs);
            com.example.gymmanagement.database.dao.JDBCConnect.closePreparedStatement(ps);
        }

        return member;
    }

    // Phương thức để lấy danh sách tất cả các thành viên
    public List<Members> getAllMembers() {

        List<Members> membersList = new ArrayList<>();

        try {
            con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();
            String query = "SELECT * FROM members";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                Members member = new Members();
                member = fromResultSet(rs);

                membersList.add(member);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            com.example.gymmanagement.database.dao.JDBCConnect.closeConnection(con);
            com.example.gymmanagement.database.dao.JDBCConnect.closeResultSet(rs);
            com.example.gymmanagement.database.dao.JDBCConnect.closePreparedStatement(ps);
        }
        return membersList;
    }

    //lấy từ database ra
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

    //lấy vào database
    public void executeMemberQuery(String query, Members member) {

        try {
            con = com.example.gymmanagement.database.dao.JDBCConnect.getJDBCConnection();
            ps = con.prepareStatement(query);

            ps.setString(1, member.getFirst_name());
            ps.setString(2, member.getLast_name());
            ps.setDate(3, java.sql.Date.valueOf(member.getDob()));
            ps.setString(4, member.getGender());
            ps.setString(5, member.getEmail());
            ps.setString(6, member.getPhone_number());
            ps.setString(7, member.getAddress());
            ps.setDate(8, java.sql.Date.valueOf(member.getJoin_date()));
            ps.setDate(9, java.sql.Date.valueOf(member.getEnd_date()));
            ps.setInt(10, member.getMembership_status_id());
            ps.setInt(11, member.getMembership_type_id());

            if (query.contains("UPDATE")) {
                ps.setInt(12, member.getMember_id());
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
            rs = statement.executeQuery("SELECT MAX(member_id) FROM members");

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