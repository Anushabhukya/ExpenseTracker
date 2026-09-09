package gui;

import db.Session;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardFrame extends JFrame {


private JLabel dateTimeLabel;

public DashboardFrame() {

    // Window settings
    setTitle("Expense Tracker - Dashboard");
    setSize(500, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);

    // Main panel
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridBagLayout());
    mainPanel.setBackground(new Color(245, 247, 250));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(8, 50, 8, 50);

    // EXPENSE TRACKER title
    JLabel titleLabel = new JLabel(
            "EXPENSE TRACKER",
            SwingConstants.CENTER
    );
    titleLabel.setFont(
            new Font("Segoe UI", Font.BOLD, 28)
    );

    gbc.gridy = 0;
    mainPanel.add(titleLabel, gbc);

    // Dashboard heading
    JLabel dashboardLabel = new JLabel(
            "Dashboard",
            SwingConstants.CENTER
    );
    dashboardLabel.setFont(
            new Font("Segoe UI", Font.BOLD, 20)
    );

    gbc.gridy = 1;
    mainPanel.add(dashboardLabel, gbc);

    // Date and time
    dateTimeLabel = new JLabel(
            "",
            SwingConstants.CENTER
    );
    dateTimeLabel.setFont(
            new Font("Segoe UI", Font.PLAIN, 13)
    );

    gbc.gridy = 2;
    mainPanel.add(dateTimeLabel, gbc);

    // Income button
    JButton incomeButton = new JButton("Income");
    incomeButton.setFont(
            new Font("Segoe UI", Font.BOLD, 16)
    );
    incomeButton.setPreferredSize(
            new Dimension(350, 45)
    );
    incomeButton.setFocusPainted(false);
    incomeButton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
    );

    gbc.gridy = 3;
    gbc.insets = new Insets(20, 50, 8, 50);
    mainPanel.add(incomeButton, gbc);

    // Expenses button
    JButton expenseButton = new JButton("Expenses");
    expenseButton.setFont(
            new Font("Segoe UI", Font.BOLD, 16)
    );
    expenseButton.setPreferredSize(
            new Dimension(350, 45)
    );
    expenseButton.setFocusPainted(false);
    expenseButton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
    );

    gbc.gridy = 4;
    gbc.insets = new Insets(8, 50, 8, 50);
    mainPanel.add(expenseButton, gbc);

    // Analytics button
    JButton analyticsButton = new JButton("Analytics");
    analyticsButton.setFont(
            new Font("Segoe UI", Font.BOLD, 16)
    );
    analyticsButton.setPreferredSize(
            new Dimension(350, 45)
    );
    analyticsButton.setFocusPainted(false);
    analyticsButton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
    );

    gbc.gridy = 5;
    mainPanel.add(analyticsButton, gbc);

    // Profile button
    JButton profileButton = new JButton("Profile");
    profileButton.setFont(
            new Font("Segoe UI", Font.BOLD, 16)
    );
    profileButton.setPreferredSize(
            new Dimension(350, 45)
    );
    profileButton.setFocusPainted(false);
    profileButton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
    );

    gbc.gridy = 6;
    mainPanel.add(profileButton, gbc);

    // Logout button
    JButton logoutButton = new JButton("Logout");
    logoutButton.setFont(
            new Font("Segoe UI", Font.BOLD, 16)
    );
    logoutButton.setPreferredSize(
            new Dimension(350, 45)
    );
    logoutButton.setFocusPainted(false);
    logoutButton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
    );

    gbc.gridy = 7;
    mainPanel.add(logoutButton, gbc);

    // Update date and time every second
    Timer timer = new Timer(1000, e -> {

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd MMM yyyy  |  hh:mm:ss a"
                );

        dateTimeLabel.setText(
                now.format(formatter)
        );
    });

    timer.start();

    // Logout action
    logoutButton.addActionListener(e -> {

        Session.currentUserId = 0;
        Session.currentUserName = null;

        timer.stop();

        dispose();

        new LoginFrame().setVisible(true);
    });

    incomeButton.addActionListener(e -> {
        new IncomeFrame().setVisible(true);
    });

    expenseButton.addActionListener(e -> {
         new ExpenseFrame().setVisible(true);
    });

    analyticsButton.addActionListener(e -> {
         new AnalyticsFrame().setVisible(true);
    });

    profileButton.addActionListener(e -> {
         new ProfileFrame().setVisible(true);
    });

    // Add panel
    add(mainPanel);
}


}
