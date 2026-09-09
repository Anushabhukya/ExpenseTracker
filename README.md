 Expense Tracker

A Java Swing application for tracking personal income and expenses, using MySQL (via JDBC) for data storage. It lets a user register, log in, record income and expenses, view them by category, day, month, or year, see a financial summary, and visualize spending through charts.

 Features

 Authentication
- User registration with name, email, and password (email must be unique).
- Login with email and password.
- Registration form validates that all fields are filled and that the password and confirm-password fields match.
- Login form validates that email and password are not empty.
- Logged-in user is tracked in-memory for the duration of the session.

 Dashboard
- Central dashboard screen shown after login, with navigation buttons to Income, Expenses, Analytics, and Profile, plus Logout.
- Live date/time display that updates every second.

 Income Management
- Add income with an amount and a source.
- View all recorded income entries with a running total.

 Expense Management
- Add an expense by selecting a category (Food, Travel, Groceries, Bills, Entertainment, Others), entering an amount, and a description.
- View expenses filtered by:
  - All expenses
  - A specific category
  - A specific day
  - A specific month
  - A specific year
- Each view displays the matching records along with the total amount for that view.

 Profile
- Displays the logged-in user's name, user ID, and email.
- Shows a financial summary: total income, total expenses, and current balance (income − expenses), with the balance highlighted differently depending on whether it is positive or negative.

 Analytics
- Dedicated analytics screen with tabbed views:
  - Category – spending breakdown by category, rendered as a pie chart.
  - Daily – spending by day, rendered as a chart.
  - Monthly – spending by month, rendered as a chart.
  - Yearly – spending by year, rendered as a chart.
- Charts are custom-drawn using Java 2D graphics from live data aggregated (via SQL SUM/GROUP BY) from the database.

 Balance Screen
- A standalone Balance screen (BalanceFrame) is included in the codebase for displaying balance information.

 Technologies Used

- Java
- Java Swing (GUI)
- JDBC
- MySQL
- MySQL Connector/J (mysql-connector-j-9.7.0.jar)

 Database Schema

The MySQL schema (database/schema.sql) defines the following tables:

- users – user_id, name, email, password, created_at
- categories – category_id, user_id, category_name
- income – income_id, user_id, amount, source, income_date
- expenses – expense_id, user_id, category_id, amount, description, expense_date
- budgets – budget_id, user_id, category_id, budget_amount, budget_month

 Project Structure


ExpenseTracker_javaswing/
│
├── database/
│   └── schema.sql
│
├── lib/
│   └── mysql-connector-j-9.7.0.jar
│
└── src/
    ├── Main.java
    │
    ├── dao/
    │   ├── ExpenseDAO.java
    │   ├── IncomeDAO.java
    │   └── UserDAO.java
    │
    ├── db/
    │   ├── DBConnection.java
    │   └── Session.java
    │
    ├── gui/
    │   ├── LoginFrame.java
    │   ├── RegisterFrame.java
    │   ├── DashboardFrame.java
    │   ├── IncomeFrame.java
    │   ├── ExpenseFrame.java
    │   ├── AnalyticsFrame.java
    │   ├── ProfileFrame.java
    │   └── BalanceFrame.java
    │
    └── service/
        ├── DashboardService.java
        ├── ExpenseService.java
        ├── IncomeService.java
        ├── ProfileService.java
        └── AnalyticsService.java


 Application Flow

1. Main.java launches LoginFrame.
2. From the login screen, a user can log in or navigate to RegisterFrame to create an account.
3. On successful login, the user's ID is stored in Session and DashboardFrame opens.
4. From the dashboard, the user can navigate to Income, Expenses, Analytics, or Profile, and back, or log out to return to the login screen.


 Database Connection

Database connectivity is handled in db/DBConnection.java, which connects to a local MySQL instance (jdbc:mysql://localhost:3306/expense_tracker) using JDBC.h

Login?Register Window
<img width="594" height="729" alt="Screenshot 2026-09-09 221944" src="https://github.com/user-attachments/assets/79335cc7-ee6b-4da0-9c21-3ea50bde58bd" />




Dashboard
<img width="596" height="732" alt="Screenshot 2026-09-09 223034" src="https://github.com/user-attachments/assets/3fff53d4-c40a-4eff-9d63-fcac10918b46" />




Income Management
<img width="839" height="665" alt="Screenshot 2026-09-09 223149" src="https://github.com/user-attachments/assets/20a7342c-3560-4abd-8988-2927768e7fbe" />




Expense Management
<img width="1234" height="804" alt="Screenshot 2026-09-09 223344" src="https://github.com/user-attachments/assets/7e6f3421-bb66-4acf-ad2f-f535fab2049d" />



Analytics
<img width="1027" height="792" alt="Screenshot 2026-09-09 223447" src="https://github.com/user-attachments/assets/84700fd5-1f65-4a1c-b72a-df24a2764b22" />



<img width="1027" height="788" alt="Screenshot 2026-09-09 223523" src="https://github.com/user-attachments/assets/1a0f96ba-8949-4114-9c52-d75a3123080e" />



Profile
<img width="719" height="795" alt="Screenshot 2026-09-09 223606" src="https://github.com/user-attachments/assets/d77bb8a8-974b-4b35-ba25-cfb5c89d190a" />




