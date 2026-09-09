
package gui;

import dao.UserDAO;
import db.Session;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {

        // Window settings
        setTitle("Expense Tracker - Login");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 35, 10, 35);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // Expense Tracker title
        JLabel titleLabel = new JLabel("EXPENSE TRACKER");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        mainPanel.add(welcomeLabel, gbc);

        // Email label
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(emailLabel, gbc);

        // Email field
        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailField.setPreferredSize(new Dimension(350, 42));

        gbc.gridy = 3;
        mainPanel.add(emailField, gbc);

        // Password label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        gbc.gridy = 4;
        mainPanel.add(passwordLabel, gbc);

        // Password field
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setPreferredSize(new Dimension(350, 42));

        gbc.gridy = 5;
        mainPanel.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setPreferredSize(new Dimension(350, 45));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy = 6;
        gbc.insets = new Insets(25, 35, 10, 35);
        mainPanel.add(loginButton, gbc);

        
       // Register button
       JButton registerButton = new JButton("REGISTER");
       registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
       registerButton.setPreferredSize(new Dimension(350, 40));
       registerButton.setFocusPainted(false);
       registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

       gbc.gridy = 7;
       gbc.insets = new Insets(10, 35, 10, 35);
       mainPanel.add(registerButton, gbc);

      // Open Register page
       registerButton.addActionListener(e -> {
         dispose();
         new RegisterFrame().setVisible(true);
     });

        // Date and time
        JLabel dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 8;
        mainPanel.add(dateTimeLabel, gbc);

        // Login button action
        loginButton.addActionListener(e -> login());

        // Press Enter to login
        passwordField.addActionListener(e -> login());

        // Update date/time every second
        Timer timer = new Timer(1000, e -> {
            java.time.LocalDateTime now = java.time.LocalDateTime.now();

            java.time.format.DateTimeFormatter formatter =
                    java.time.format.DateTimeFormatter.ofPattern(
                            "dd MMM yyyy  |  hh:mm:ss a"
                    );

            dateTimeLabel.setText(now.format(formatter));
        });

        timer.start();

        // Show window
        add(mainPanel);
        setVisible(true);
    }

    private void login() {

        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Validation
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your email.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );
            emailField.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );
            passwordField.requestFocus();
            return;
        }

        // Database login
        UserDAO userDAO = new UserDAO();

        int userId = userDAO.login(email, password);

        if (userId != -1) {

            // Store logged-in user
            Session.currentUserId = userId;

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful!",
                    "Welcome",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Close login window
            dispose();

            // Open Swing Dashboard
            new DashboardFrame().setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid email or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");
            passwordField.requestFocus();
        }
    }
}

