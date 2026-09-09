package dao;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IncomeDAO {

    public boolean addIncome(int userId, double amount, String source) {

        try {

            Connection conn = DBConnection.getConnection();

            String sql =
                    "INSERT INTO income(user_id, amount, source, income_date) " +
                    "VALUES (?, ?, ?, CURDATE())";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setDouble(2, amount);
            ps.setString(3, source);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void viewAllIncome(int userId) {

        try {

            Connection conn = DBConnection.getConnection();

            String sql =
                    "SELECT income_id, amount, source, income_date " +
                    "FROM income WHERE user_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n=== ALL INCOME ===");
            System.out.println("ID\tAmount\tSource\tDate");

            double total = 0;

            while (rs.next()) {

                double amount = rs.getDouble("amount");
                total += amount;

                System.out.println(
                        rs.getInt("income_id") + "\t" +
                        amount + "\t" +
                        rs.getString("source") + "\t" +
                        rs.getDate("income_date")
                );
            }

            System.out.println("------------------------------------");
            System.out.println("Total Income: " + total + "/-");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getTotalIncome(int userId) {

        double total = 0;

        try {

            Connection conn = DBConnection.getConnection();

            String sql =
                    "SELECT SUM(amount) AS total " +
                    "FROM income WHERE user_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
}
