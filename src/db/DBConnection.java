package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/expense_tracker";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";

    private static Connection conn = null;

    public static Connection getConnection() {

        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database Connected Successfully!");
            } catch (SQLException e) {
                System.out.println("Connection Failed!");
                 e.printStackTrace();
                
               
            }
        }

        return conn;
    }
}