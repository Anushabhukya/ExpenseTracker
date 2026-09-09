package dao;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ExpenseDAO {

    public boolean addExpense(int userId,int categoryId, double amount, String description)
        {
            try {
                Connection conn = DBConnection.getConnection();
                String sql = "INSERT INTO expenses(user_id, category_id, amount, description,expense_date) VALUES (?, ?, ?, ?,CURDATE())";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, userId);
                ps.setInt(2, categoryId);
                ps.setDouble(3, amount);
                ps.setString(4, description);

                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0; // Return true if the expense was added successfully
            } catch (Exception e) {
                e.printStackTrace();
            }

      return false;
    }

    public void viewAllExpenses(int userId) {

    try {

        Connection conn = DBConnection.getConnection();

        String sql =
            "SELECT e.expense_id, c.category_name, e.amount, e.description, e.expense_date " +
            "FROM expenses e " +
            "JOIN categories c ON e.category_id = c.category_id " +
            "WHERE e.user_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n=== ALL EXPENSES ===");
        System.out.println("ID\tCategory\tAmount\tDescription\tDate");
        double total = 0;

        while (rs.next()) {
            double amount = rs.getDouble("amount");

             total += amount;

            System.out.println(
                rs.getInt("expense_id") + "\t" +
                rs.getString("category_name") + "\t\t" +
                rs.getDouble("amount") + "\t" +
                rs.getString("description") + "\t" +
                rs.getDate("expense_date")
            );
            
        }
        System.out.println("------------------------------------");
        System.out.println("Total Expenses: " + total +"/-");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
public void viewByCategory(int userId, int categoryId) {

    try {

        Connection conn = DBConnection.getConnection();

        String sql =
            "SELECT e.expense_id, c.category_name, e.amount, e.description, e.expense_date " +
            "FROM expenses e " +
            "JOIN categories c ON e.category_id = c.category_id " +
            "WHERE e.user_id = ? AND e.category_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, categoryId);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n=== CATEGORY EXPENSES ===");
        System.out.println("ID\tCategory\tAmount\tDescription\tDate");

        double total = 0;

        while (rs.next()) {

            double amount = rs.getDouble("amount");
            total += amount;

            System.out.println(
                rs.getInt("expense_id") + "\t" +
                rs.getString("category_name") + "\t\t" +
                amount + "\t" +
                rs.getString("description") + "\t" +
                rs.getDate("expense_date")
            );
        }

        System.out.println("------------------------------------");
        System.out.println("Category Total: " + total+"/-");

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void viewDailyExpenses(int userId) {

    try {

        Connection conn = DBConnection.getConnection();

        String sql =
            "SELECT e.expense_id, c.category_name, e.amount, e.description, e.expense_date " +
            "FROM expenses e " +
            "JOIN categories c ON e.category_id = c.category_id " +
            "WHERE e.user_id = ? AND e.expense_date = CURDATE()";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n=== TODAY'S EXPENSES ===");
        System.out.println("ID\tCategory\tAmount\tDescription\tDate");

        double total = 0;

        while (rs.next()) {

            double amount = rs.getDouble("amount");
            total += amount;

            System.out.println(
                rs.getInt("expense_id") + "\t" +
                rs.getString("category_name") + "\t\t" +
                amount + "\t" +
                rs.getString("description") + "\t" +
                rs.getDate("expense_date")
            );
        }

        System.out.println("------------------------------------");
        System.out.println("Today's Total: " + total+"/-");

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void viewMonthlyExpenses(int userId) {

    try {

        Connection conn = DBConnection.getConnection();

        String sql =
            "SELECT e.expense_id, c.category_name, e.amount, e.description, e.expense_date " +
            "FROM expenses e " +
            "JOIN categories c ON e.category_id = c.category_id " +
            "WHERE e.user_id = ? " +
            "AND MONTH(e.expense_date) = MONTH(CURDATE()) " +
            "AND YEAR(e.expense_date) = YEAR(CURDATE())";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n=== MONTHLY EXPENSES ===");
        System.out.println("ID\tCategory\tAmount\tDescription\tDate");

        double total = 0;

        while (rs.next()) {

            double amount = rs.getDouble("amount");
            total += amount;

            System.out.println(
                rs.getInt("expense_id") + "\t" +
                rs.getString("category_name") + "\t\t" +
                amount + "\t" +
                rs.getString("description") + "\t" +
                rs.getDate("expense_date")
            );
        }

        System.out.println("------------------------------------");
        System.out.println("Monthly Total: " + total+"/-");

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void viewYearlyExpenses(int userId) {

    try {

        Connection conn = DBConnection.getConnection();

        String sql =
            "SELECT e.expense_id, c.category_name, e.amount, e.description, e.expense_date " +
            "FROM expenses e " +
            "JOIN categories c ON e.category_id = c.category_id " +
            "WHERE e.user_id = ? " +
            "AND YEAR(e.expense_date) = YEAR(CURDATE())";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n=== YEARLY EXPENSES ===");
        System.out.println("ID\tCategory\tAmount\tDescription\tDate");

        double total = 0;

        while (rs.next()) {

            double amount = rs.getDouble("amount");
            total += amount;

            System.out.println(
                rs.getInt("expense_id") + "\t" +
                rs.getString("category_name") + "\t\t" +
                amount + "\t" +
                rs.getString("description") + "\t" +
                rs.getDate("expense_date")
            );
        }

        System.out.println("------------------------------------");
        System.out.println("Yearly Total: " + total+"/-");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
public double getTotalExpense(int userId) {

    double total = 0;

    try {

        Connection conn = DBConnection.getConnection();

        String sql =
                "SELECT SUM(amount) AS total " +
                "FROM expenses WHERE user_id = ?";

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
