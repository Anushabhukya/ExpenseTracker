\# Expense Tracker



A desktop-based Expense Tracker application developed using Java and Java Swing, with MySQL database integration using JDBC.



The application allows users to manage their income and expenses, track their financial balance, categorize spending, and analyze expenses through daily, monthly, yearly, and category-wise visualizations.



\## Features



\- User Registration and Login

\- Income Management

\- Expense Management

\- Expense Categorization

\- Daily Expense Tracking

\- Monthly Expense Tracking

\- Yearly Expense Tracking

\- Financial Balance Summary

\- User Profile

\- Category-wise Expense Analytics

\- Daily Expense Analytics

\- Monthly Expense Analytics

\- Yearly Expense Analytics

\- Interactive Java Swing GUI



\## Technologies Used



\- \*\*Java\*\*

\- \*\*Java Swing\*\*

\- \*\*JDBC\*\*

\- \*\*MySQL\*\*

\- \*\*MySQL Workbench\*\*

\- \*\*Git \& GitHub\*\*



\## Project Structure



```text

Expense-Tracker-Java/

│

├── database/

│   └── schema.sql

│

├── lib/

│   └── mysql-connector-j-9.7.0.jar

│

└── src/

&#x20;   ├── dao/

&#x20;   │   ├── ExpenseDAO.java

&#x20;   │   ├── IncomeDAO.java

&#x20;   │   └── UserDAO.java

&#x20;   │

&#x20;   ├── db/

&#x20;   │   ├── DBConnection.java

&#x20;   │   └── Session.java

&#x20;   │

&#x20;   ├── gui/

&#x20;   │   ├── AnalyticsFrame.java

&#x20;   │   ├── DashboardFrame.java

&#x20;   │   ├── ExpenseFrame.java

&#x20;   │   ├── IncomeFrame.java

&#x20;   │   ├── LoginFrame.java

&#x20;   │   ├── ProfileFrame.java

&#x20;   │   └── RegisterFrame.java

&#x20;   │

&#x20;   └── service/

&#x20;       ├── AnalyticsService.java

&#x20;       ├── DashboardService.java

&#x20;       ├── ExpenseService.java

&#x20;       ├── IncomeService.java

&#x20;       └── ProfileService.java

