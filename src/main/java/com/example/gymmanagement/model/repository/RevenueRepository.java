package com.example.gymmanagement.model.repository;

import com.example.gymmanagement.database.JDBCConnect;
import com.example.gymmanagement.model.entity.Revenue;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RevenueRepository {
    private final JDBCConnect jdbcConnect;

    public RevenueRepository() {
        this.jdbcConnect = new JDBCConnect();
    }

    public void addRevenue(Revenue revenue) {
        String query = "INSERT INTO revenue (year, month, total_amount) VALUES (?, ?, ?)";
        executeRevenueQuery(query, revenue);
    }

    public void updateRevenue(Revenue revenue) {
        String query = "UPDATE revenue SET year = ?, month = ?, total_amount = ? WHERE revenue_id = ?";
        executeRevenueQuery(query, revenue);
    }

    public void deleteRevenue(int revenueId) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM revenue WHERE revenue_id = ?")) {
            statement.setInt(1, revenueId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Revenue getRevenueById(int revenueId) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM revenue WHERE revenue_id = ?")) {
            statement.setInt(1, revenueId);
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

    public List<Revenue> getAllRevenue() {
        List<Revenue> revenueList = new ArrayList<>();
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM revenue");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                revenueList.add(fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenueList;
    }


    public List<Revenue>getRevenueByYearAndMonth(int year, int month) {
        List<Revenue> revenueList = new ArrayList<>();
        String query = "SELECT * FROM revenue WHERE year = ? AND month = ?";
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, year);
            statement.setInt(2, month);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Revenue revenue = new Revenue();
                    revenue.setRevenueId(resultSet.getInt("revenue_id"));
                    revenue.setYear(resultSet.getInt("year"));
                    revenue.setMonth(resultSet.getInt("month"));
                    revenue.setTotalAmount(resultSet.getBigDecimal("total_amount"));
                    revenueList.add(revenue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenueList;
    }

    public Revenue fromResultSet(ResultSet resultSet) throws SQLException {
        Revenue revenue = new Revenue();
        revenue.setRevenueId(resultSet.getInt("revenue_id"));
        revenue.setYear(resultSet.getInt("year"));
        revenue.setMonth(resultSet.getInt("month"));
        revenue.setTotalAmount(resultSet.getBigDecimal("total_amount"));
        return revenue;
    }

    public void executeRevenueQuery(String query, Revenue revenue) {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, revenue.getYear());
            statement.setInt(2, revenue.getMonth());
            statement.setBigDecimal(3, revenue.getTotalAmount());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getTotalRevenue() {
        try (Connection connection = jdbcConnect.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(revenue_id) as count FROM revenue");
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
