package service;

import dao.IncomeDAO;
import db.Session;
import java.util.Scanner;

public class IncomeService {

    IncomeDAO incomeDAO = new IncomeDAO();

    public void incomeMenu(Scanner sc) {

        while (true) {

            System.out.println("\n=== INCOME MENU ===");
            System.out.println("1. Add Income");
            System.out.println("2. View Income");
            System.out.println("3. Back");

            System.out.print("Choose option: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    addIncome(sc);
                    break;

                case 2:
                    viewAllIncome();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    public void addIncome(Scanner sc) {

        System.out.println("\n=== ADD INCOME ===");

        System.out.print("Enter Amount: ");
        double amount = Double.parseDouble(sc.nextLine());

        System.out.print("Enter Source: ");
        String source = sc.nextLine();

        boolean success = incomeDAO.addIncome(
                Session.currentUserId,
                amount,
                source
        );

        if (success) {
            System.out.println("Income Added Successfully!");
        } else {
            System.out.println("Failed to Add Income!");
        }
    }

    public void viewAllIncome() {

        incomeDAO.viewAllIncome(Session.currentUserId);
    }
}