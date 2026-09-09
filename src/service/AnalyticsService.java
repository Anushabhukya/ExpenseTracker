package service;

import java.util.Scanner;

public class AnalyticsService {

    public void analyticsMenu(Scanner sc) {

        while (true) {

            System.out.println("\n=== ANALYTICS ===");
            System.out.println("1. Highest Spending Category");
            System.out.println("2. Monthly Comparison");
            System.out.println("3. Yearly Comparison");
            System.out.println("4. Expense Distribution");
            System.out.println("5. Daily Comparison");
            System.out.println("6. Budget Status");
            System.out.println("7. Back");

            System.out.print("Choose option: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    System.out.println("Highest Spending Category coming soon...");
                    break;

                case 2:
                    System.out.println("Monthly Comparison coming soon...");
                    break;

                case 3:
                    System.out.println("Yearly Comparison coming soon...");
                    break;

                case 4:
                    System.out.println("Expense Distribution coming soon...");
                    break;

                case 5:
                    System.out.println("Daily Comparison coming soon...");
                    break;

                case 6:
                    System.out.println("Budget Status coming soon...");
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}
