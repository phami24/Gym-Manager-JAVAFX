package com.example.gymmanagement.database.dao;



import com.example.gymmanagement.database.config.IDBConfig;

import java.sql.*;

public class JDBCConnect {
    public static Connection getJDBCConnection() {
        Connection connection = null;
        String connectionUrl = "jdbc:mysql://" + IDBConfig.HOSTNAME
                + ":" + IDBConfig.PORT + "/"
                + IDBConfig.DBNAME;
        System.out.println(connectionUrl);

        try {
            // Kết nối tới cơ sở dữ liệu
            connection = DriverManager.getConnection(connectionUrl, IDBConfig.USERNAME, IDBConfig.PASSWORD);
            System.out.println("MySQL JDBC Driver Registered!");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return connection;
        }
        return connection;
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Close Connection fails");
        }
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("Close ResultSet fails");
        }
    }

    public static void closePreparedStatement(PreparedStatement prepare) {
        try {
            if (prepare != null) {
                prepare.close();
            }
        } catch (SQLException e) {
            System.out.println("Close PreparedStatement fails");
        }
    }

//    public static void main(String[] args) {
//
//        System.out.println(JDBCConnect.getJDBCConnection());
//
//    }
}
