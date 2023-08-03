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
}
