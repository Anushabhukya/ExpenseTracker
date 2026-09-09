package gui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {


private JTextField nameField;
private JTextField emailField;
private JPasswordField passwordField;
private JPasswordField confirmPasswordField;

public RegisterFrame() {

    setTitle("Expense Tracker - Register");
    setSize(450, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new GridBagLayout());
    mainPanel.setBackground(new Color(245, 247, 250));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 30, 8, 30);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;

    // Title
    JLabel titleLabel = new JLabel("EXPENSE TRACKER", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 26));

    gbc.gridy = 0;
    mainPanel.add(titleLabel, gbc);

    // Register heading
    JLabel registerLabel = new JLabel("Create Account", SwingConstants.CENTER);
    registerLabel.setFont(new Font("Arial", Font.BOLD, 20));

    gbc.gridy = 1;
    mainPanel.add(registerLabel, gbc);

    // Name
    JLabel nameLabel = new JLabel("Name");
    nameField = new JTextField();

    gbc.gridy = 2;
    mainPanel.add(nameLabel, gbc);

    gbc.gridy = 3;
    mainPanel.add(nameField, gbc);

    // Email
    JLabel emailLabel = new JLabel("Email");
    emailField = new JTextField();

    gbc.gridy = 4;
    mainPanel.add(emailLabel, gbc);

    gbc.gridy = 5;
    mainPanel.add(emailField, gbc);

    // Password
    JLabel passwordLabel = new JLabel("Password");
    passwordField = new JPasswordField();

    gbc.gridy = 6;
    mainPanel.add(passwordLabel, gbc);

    gbc.gridy = 7;
    mainPanel.add(passwordField, gbc);

    // Confirm Password
    JLabel confirmLabel = new JLabel("Confirm Password");
    confirmPasswordField = new JPasswordField();

    gbc.gridy = 8;
    mainPanel.add(confirmLabel, gbc);

    gbc.gridy = 9;
    mainPanel.add(confirmPasswordField, gbc);

    // Register button
    JButton registerButton = new JButton("Register");
    registerButton.setFont(new Font("Arial", Font.BOLD, 16));

    gbc.gridy = 10;
    mainPanel.add(registerButton, gbc);

    // Back to Login
    JButton loginButton = new JButton("Back to Login");

    gbc.gridy = 11;
    mainPanel.add(loginButton, gbc);

    // Register action
    registerButton.addActionListener(e -> {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword =
                new String(confirmPasswordField.getPassword());

        // Empty field validation
        if (name.isEmpty() || email.isEmpty()
                || password.isEmpty() || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Password confirmation
        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Register user using existing DAO
        UserDAO userDAO = new UserDAO();

        int userId = userDAO.registerUser(name, email, password);

        if (userId != -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            new LoginFrame().setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Failed!\nEmail may already exist.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    });

    // Back to Login action
    loginButton.addActionListener(e -> {

        dispose();
        new LoginFrame().setVisible(true);
    });

    add(mainPanel);
}


}
