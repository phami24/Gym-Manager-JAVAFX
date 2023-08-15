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

    public int getTypeIDByName(String typeName) {
        String query = "SELECT membership_type_id FROM membership_types WHERE membership_type_name = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, typeName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("membership_type_id");
                }
                return -1; // Return a default value indicating not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Ném lại ngoại lệ để lớp gọi viên có thể xử lý ngoại lệ này
        }
    }

    public String getTypeNameById(int membershipTypeId) {
        Connection connection = jdbcConnect.getJDBCConnection();
        String query = "SELECT membership_type_name FROM membership_types WHERE membership_type_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, membershipTypeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("membership_type_name");
                }
                return null; // Return null if type not found
            }
        } catch (SQLException e) {
            // Handle SQLException here, e.g., print stack trace or throw a different exception
            e.printStackTrace();
            return null; // Or throw a different exception to signal the error to the calling class
        }
    }

    public int getDurationById(int membershipTypeId) {
        Connection connection = jdbcConnect.getJDBCConnection();
        String query = "SELECT duration FROM membership_types WHERE membership_type_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, membershipTypeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("duration");
                }
                return -1; // Return a default value indicating not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Ném lại ngoại lệ để lớp gọi viên có thể xử lý ngoại lệ này
        }
    }


}
