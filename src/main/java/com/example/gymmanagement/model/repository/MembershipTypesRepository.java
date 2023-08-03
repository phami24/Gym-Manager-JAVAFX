package com.example.gymmanagement.model.repository;

import com.example.gymmanagement.database.JDBCConnect;
import com.example.gymmanagement.model.entity.MembershipType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MembershipTypesRepository {
    private final JDBCConnect jdbcConnect;

    public MembershipTypesRepository() {
        this.jdbcConnect = new JDBCConnect();
    }

    public void addMembershipType(MembershipType membershipType) {
        String query = "INSERT INTO membership_types (membership_type_name, duration, description, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, membershipType.getMembershipTypeName());
            statement.setInt(2, membershipType.getDuration());
            statement.setString(3, membershipType.getDescription());
            statement.setBigDecimal(4, membershipType.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MembershipType> getAllMembershipTypes() {
        List<MembershipType> membershipTypes = new ArrayList<>();
        String query = "SELECT * FROM membership_types";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                MembershipType membershipType = new MembershipType();
                membershipType.setMembershipTypeId(resultSet.getInt("membership_type_id"));
                membershipType.setMembershipTypeName(resultSet.getString("membership_type_name"));
                membershipType.setDuration(resultSet.getInt("duration"));
                membershipType.setDescription(resultSet.getString("description"));
                membershipType.setPrice(resultSet.getBigDecimal("price"));
                membershipTypes.add(membershipType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membershipTypes;
    }

    public MembershipType getMembershipTypeById(int membershipTypeId) {
        MembershipType membershipType = null;
        String query = "SELECT * FROM membership_types WHERE membership_type_id = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, membershipTypeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    membershipType = new MembershipType();
                    membershipType.setMembershipTypeId(resultSet.getInt("membership_type_id"));
                    membershipType.setMembershipTypeName(resultSet.getString("membership_type_name"));
                    membershipType.setDuration(resultSet.getInt("duration"));
                    membershipType.setDescription(resultSet.getString("description"));
                    membershipType.setPrice(resultSet.getBigDecimal("price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membershipType;
    }
}
