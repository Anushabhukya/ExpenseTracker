package service;

import dao.ExpenseDAO;
import db.Session;
import java.util.Scanner;

public class ExpenseService {

    ExpenseDAO expenseDAO = new ExpenseDAO();

    public void addExpense(Scanner sc) {

        System.out.println("\n=== ADD EXPENSE ===");

        System.out.println("1. Food");
        System.out.println("2. Travel");
        System.out.println("3. Groceries");
        System.out.println("4. Bills");
        System.out.println("5. Entertainment");
        System.out.println("6. Others");

        System.out.print("Choose Category: ");
        int categoryId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Amount: ");
        double amount = Double.parseDouble(sc.nextLine());

        System.out.print("Enter Description: ");
        String description = sc.nextLine();

        boolean success = expenseDAO.addExpense(
                Session.currentUserId,
                categoryId,
                amount,
                description
        );

        if (success) {
            System.out.println("Expense Added Successfully!");
        } else {
            System.out.println("Failed to Add Expense!");
        }
    }

   public void viewAllExpenses(Scanner sc) {

        while (true) {

            System.out.println("\n=== VIEW EXPENSES ===");
            System.out.println("1. All Expenses");
            System.out.println("2. Category-wise Expenses");
            System.out.println("3. Daily Expenses");
            System.out.println("4. Monthly Expenses");
            System.out.println("5. Yearly Expenses");
            System.out.println("6. Back");
           

            System.out.print("Choose option: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    expenseDAO.viewAllExpenses(Session.currentUserId);
                    break;

                case 2:

                    System.out.println("\nChoose Category:");
                    System.out.println("1. Food");
                    System.out.println("2. Travel");
                    System.out.println("3. Groceries");
                    System.out.println("4. Bills");
                    System.out.println("5. Entertainment");
                    System.out.println("6. Others");

                    System.out.print("Enter Category ID: ");
                    int categoryId = Integer.parseInt(sc.nextLine());

                    expenseDAO.viewByCategory(
                      Session.currentUserId,
                      categoryId
                    );
                    break;

                case 3:
                    expenseDAO.viewDailyExpenses(Session.currentUserId);
                    break;

                case 4:
                    expenseDAO.viewMonthlyExpenses(Session.currentUserId);
                    break;

                case 5:
                    expenseDAO.viewYearlyExpenses(Session.currentUserId);
                    break;

               

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}



