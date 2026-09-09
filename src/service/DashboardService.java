package service;

import db.Session;
import java.util.Scanner;
public class DashboardService {

    public void dashboard() {

        System.out.println("\n=== DASHBOARD ===");
        System.out.println("1. Add Expense");
        System.out.println("2. View Expenses");
        System.out.println("3. Income Management");
        System.out.println("4. View Balance");
        System.out.println("5. Analytics");
        System.out.println("6. View Profile");
        System.out.println("7. Logout");
        System.out.print("Choose: ");
        Scanner sc = new Scanner(System.in);
        int choice=Integer.parseInt(sc.nextLine());
        System.out.println("You selected option: " + choice);
        ExpenseService expenseService = new ExpenseService();
        
        switch (choice) {

    case 1:
        System.out.println("Add Expense");
        expenseService.addExpense(sc);
        break;

    case 2:
        System.out.println("View Expenses");
        expenseService.viewAllExpenses(sc);
        break;

    case 3:
        System.out.println("Income Management");
        IncomeService incomeService = new IncomeService();
        incomeService.incomeMenu(sc);
        break;

    case 4:
        System.out.println("View Balance");
        BalanceService balanceService = new BalanceService();
        balanceService.viewBalance();
        break;

    case 5:
       System.out.println("Analytics");
       AnalyticsService analyticsService = new AnalyticsService();
       analyticsService.analyticsMenu(sc);
       break;

    case 6:
        System.out.println("View Profile");
        ProfileService profileService = new ProfileService();
        profileService.viewProfile();
        break;

    case 7:
       System.out.println("Logged out successfully!");
       Session.currentUserId = 0;
       Session.currentUserName = null;

       return;
        

    default:
        System.out.println("Invalid Choice");
}
    }
}