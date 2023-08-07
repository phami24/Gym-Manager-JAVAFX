package com.example.gymmanagement.model.repository;

import com.example.gymmanagement.database.JDBCConnect;
import com.example.gymmanagement.model.entity.MembershipStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MembershipStatusRepository {

    private final JDBCConnect jdbcConnect;

    public MembershipStatusRepository() {
        this.jdbcConnect = new JDBCConnect();
    }

    public List<MembershipStatus> getAllMembershipStatus() {
        List<MembershipStatus> membershipStatusList = new ArrayList<>();
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM membership_status");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                MembershipStatus membershipStatus = fromResultSet(resultSet);
                membershipStatusList.add(membershipStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membershipStatusList;
    }

    public MembershipStatus fromResultSet(ResultSet resultSet) throws SQLException {
        MembershipStatus membershipStatus = new MembershipStatus();
        membershipStatus.setMembershipStatusId(resultSet.getInt("membership_status_id"));
        membershipStatus.setMembershipStatusName(resultSet.getString("membership_status_name"));
        return membershipStatus;
    }

    public int getStatusIdByName(String statusName) {
        Connection connection = jdbcConnect.getJDBCConnection();
        String query = "SELECT membership_status_id FROM membership_status WHERE membership_status_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, statusName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("membership_status_id");
                }
                return -1; // Return a default value indicating not found
            }
        } catch (SQLException e) {
            // Xử lý ngoại lệ SQLException ở đây, ví dụ:
            e.printStackTrace();
            return -1; // Hoặc ném ngoại lệ khác để thông báo lỗi cho lớp gọi
        }
    }

    public String getStatusNameById(int membershipStatusId) {
        Connection connection = jdbcConnect.getJDBCConnection();
        String query = "SELECT membership_status_name FROM membership_status WHERE membership_status_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, membershipStatusId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("membership_status_name");
                }
                return null; // Return null if status not found
            }
        } catch (SQLException e) {
            // Handle SQLException here, e.g., print stack trace or throw a different exception
            e.printStackTrace();
            return null; // Or throw a different exception to signal the error to the calling class
        }
    }

}
