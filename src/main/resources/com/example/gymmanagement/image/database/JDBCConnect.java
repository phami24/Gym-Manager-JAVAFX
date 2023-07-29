package com.example.gymmanagement.database;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCConnect {

    public Connection getJDBCConnection() {
        Connection connection = null;
        Properties properties = new Properties();
        try (InputStream inputStream = JDBCConnect.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
            String hostname = properties.getProperty("HOSTNAME");
            String port = properties.getProperty("PORT");
            String dbname = properties.getProperty("DBNAME");
            String username = properties.getProperty("USERNAME");
            String password = properties.getProperty("PASSWORD");

            System.out.println(hostname);
            String connectionUrl = "jdbc:mysql://" + hostname
                    + ":" + port + "/"
                    + dbname;
            System.out.println(connectionUrl);

            try {
                // Kết nối tới cơ sở dữ liệu
                connection = DriverManager.getConnection(connectionUrl, username, password);
                System.out.println("MySQL JDBC Driver Registered!");
            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
                return connection;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Close Connection fails");
        }
    }

    public void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("Close ResultSet fails");
        }
    }

    public void closePreparedStatement(PreparedStatement prepare) {
        try {
            if (prepare != null) {
                prepare.close();
            }
        } catch (SQLException e) {
            System.out.println("Close PreparedStatement fails");
        }
    }
    public static void main(String[] args){
        JDBCConnect jdbcConnect = new JDBCConnect();
        Connection connection = jdbcConnect.getJDBCConnection();
    }

}
