package dao;

import db.DBConnection;
import db.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDAO {
  

public int login(String email, String password) {

    try {
        Connection conn = DBConnection.getConnection();

        String sql = "SELECT user_id, name FROM users WHERE email = ? AND password = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
          Session.currentUserName = rs.getString("name");
          return rs.getInt("user_id");
     }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return -1; // login failed
}

    public int registerUser(String name, String email, String password) {

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1); // newly created user_id
        }  
            

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    public void viewProfile(int userId) {

    try {

        Connection conn = DBConnection.getConnection();

        String sql =
                "SELECT user_id, name, email " +
                "FROM users WHERE user_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            System.out.println("\n=== MY PROFILE ===");

            System.out.println("User ID : " + rs.getInt("user_id"));
            System.out.println("Name    : " + rs.getString("name"));
            System.out.println("Email   : " + rs.getString("email"));

        } else {
            System.out.println("Profile not found!");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}