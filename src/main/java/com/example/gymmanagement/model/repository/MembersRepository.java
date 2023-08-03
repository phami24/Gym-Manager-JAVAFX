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

    public void addMember(Members member) {
        String query = "INSERT INTO members (first_name, last_name, dob, gender, email, phone_number, address, join_date, end_date, membership_status_id, membership_type_id, instructor_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        executeMemberQuery(query, member);
    }

    public void updateMember(Members member) {
        String query = "UPDATE members " +
                "SET first_name = ?, last_name = ?, dob = ?, gender = ?, email = ?, phone_number = ?, address = ?, join_date = ?, end_date = ?, " +
                "membership_status_id = ?, membership_type_id = ?, instructor_id = ? " +
                "WHERE member_id = ?";
        executeMemberQuery(query, member);
    }

    public void deleteMember(int memberId) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM members WHERE member_id = ?")) {
            statement.setInt(1, memberId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Members getMemberById(int memberId) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM members WHERE member_id = ?")) {
            statement.setInt(1, memberId);
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

    public List<Members> getAllMembers() {
        List<Members> membersList = new ArrayList<>();
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM members");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                membersList.add(fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membersList;
    }

    public List<Members> getMembersByNames(String firstName, String lastName) {
        List<Members> membersList = new ArrayList<>();
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM members WHERE first_name = ? AND last_name = ?")) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    membersList.add(fromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membersList;
    }

    public List<Members> getMembersByInstructorId(int instructorId) {
        List<Members> membersList = new ArrayList<>();
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM members WHERE instructor_id = ?")) {
            statement.setInt(1, instructorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    membersList.add(fromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membersList;
    }

    public List<Members> getMembersByYearAndMonth(int year, int month) {
        List<Members> membersList = new ArrayList<>();
        String query = "SELECT * FROM members WHERE YEAR(join_date) = ? AND MONTH(join_date) = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, year);
            statement.setInt(2, month);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Members member = fromResultSet(resultSet);
                    membersList.add(member);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membersList;
    }


    public int getTotalMembers() {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(member_id) as count FROM members");
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Members fromResultSet(ResultSet resultSet) throws SQLException {
        Members member = new Members();
        member.setMember_id(resultSet.getInt("member_id"));
        member.setJoin_date(resultSet.getString("join_date"));
        member.setEnd_date(resultSet.getString("end_date"));
        member.setMembership_status_id(resultSet.getInt("membership_status_id"));
        member.setMembership_type_id(resultSet.getInt("membership_type_id"));
        member.setInstructorId(resultSet.getInt("instructor_id"));
        member.setFirst_name(resultSet.getString("first_name"));
        member.setLast_name(resultSet.getString("last_name"));
        member.setDob(resultSet.getString("dob"));
        member.setGender(resultSet.getString("gender"));
        member.setEmail(resultSet.getString("email"));
        member.setPhone_number(resultSet.getString("phone_number"));
        member.setAddress(resultSet.getString("address"));
        return member;
    }

    public void executeMemberQuery(String query, Members member) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
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
            statement.setInt(12, member.getInstructorId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
