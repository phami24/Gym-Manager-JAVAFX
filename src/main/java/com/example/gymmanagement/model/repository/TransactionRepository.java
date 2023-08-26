package com.example.gymmanagement.model.repository;

import com.example.gymmanagement.database.JDBCConnect;
import com.example.gymmanagement.model.entity.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private final JDBCConnect jdbcConnect = new JDBCConnect();

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions";
        Connection connection = jdbcConnect.getJDBCConnection();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getLong("transaction_id"));
                transaction.setMemberId(resultSet.getLong("member_id"));
                transaction.setTransactionType(resultSet.getString("transaction_type"));
                transaction.setTransactionDate(resultSet.getDate("transaction_date").toString());
                transaction.setAmount(resultSet.getBigDecimal("amount"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<Transaction> getTransactionsByPage(int pageNumber, int pageSize) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions ORDER BY transaction_date DESC LIMIT ? OFFSET ?";
        Connection connection = jdbcConnect.getJDBCConnection();
        int offset = (pageNumber - 1) * pageSize;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(resultSet.getLong("transaction_id"));
                    transaction.setMemberId(resultSet.getLong("member_id"));
                    transaction.setEquipmentId(resultSet.getLong("equipment_id")); // Add this line
                    transaction.setTransactionType(resultSet.getString("transaction_type"));
                    transaction.setTransactionDate(resultSet.getString("transaction_date"));
                    transaction.setAmount(resultSet.getBigDecimal("amount"));
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }


    public int getTotalTransactions() {
        String query = "SELECT COUNT(*) as total FROM transactions";
        Connection connection = jdbcConnect.getJDBCConnection();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }




}
