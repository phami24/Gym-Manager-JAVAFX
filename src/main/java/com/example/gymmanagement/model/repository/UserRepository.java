package com.example.gymmanagement.model.repository;

import com.example.gymmanagement.database.JDBCConnect;
import com.example.gymmanagement.model.entity.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final JDBCConnect jdbcConnect;

    public UserRepository() {
        this.jdbcConnect = new JDBCConnect();
    }

    // Method to add a user to the database
    public void addUser(Users user) {
        String query = "INSERT INTO users (username, password, role_id) VALUES (?, ?, ?)";
        executeUserQuery(query, user);
    }

    // Method to update user information in the database
    public void updateUser(Users user) {

        String query = "UPDATE users SET username = ?, password = ?, role_id = ? WHERE user_id = ?";
        executeUserQuery(query, user);
    }

    // Method to delete a user from the database
    public void deleteUser(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = jdbcConnect.getJDBCConnection();
            String query = "DELETE FROM users WHERE user_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closePreparedStatement(statement);
        }
    }

    // Method to get user information by user ID
    public Users getUserById(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Users user = null;

        try {
            connection = jdbcConnect.getJDBCConnection();
            String query = "SELECT * FROM users WHERE user_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = fromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closeResultSet(resultSet);
            jdbcConnect.closePreparedStatement(statement);
        }

        return user;
    }

    // Method to get all users from the database
    public List<Users> getAllUsers() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Users> userList = new ArrayList<>();

        try {
            connection = jdbcConnect.getJDBCConnection();
            String query = "SELECT * FROM users";
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Users user = fromResultSet(resultSet);

                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closeResultSet(resultSet);
            jdbcConnect.closePreparedStatement(statement);
        }

        return userList;
    }

    public Users fromResultSet(ResultSet resultSet) throws SQLException {
        Users user = new Users();
        user.setUser_id(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setRole_id(resultSet.getInt("role_id"));
        return user;
    }

    public void executeUserQuery(String query, Users user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = jdbcConnect.getJDBCConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole_id());
            statement.setInt(4, user.getUser_id());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            jdbcConnect.closeConnection(connection);
            jdbcConnect.closePreparedStatement(statement);
        }
    }


}
