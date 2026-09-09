package gui;

import dao.ExpenseDAO;
import dao.IncomeDAO;
import db.DBConnection;
import db.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfileFrame extends JFrame {

    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color BLUE = new Color(30, 136, 229);
    private final Color DARK = new Color(40, 40, 40);
    private final Color GRAY = new Color(100, 100, 100);
    private final Color GREEN = new Color(46, 125, 50);
    private final Color RED = new Color(211, 47, 47);
    private final Color WHITE = Color.WHITE;

    private JLabel nameLabel;
    private JLabel userIdLabel;
    private JLabel emailLabel;

    private JLabel incomeValue;
    private JLabel expenseValue;
    private JLabel balanceValue;

    public ProfileFrame() {

        setTitle("Expense Tracker - Profile");
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(
                new EmptyBorder(20, 30, 20, 30)
        );

        // ================= HEADER =================

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(
                new BoxLayout(headerPanel, BoxLayout.Y_AXIS)
        );
        headerPanel.setBackground(BACKGROUND);

        JLabel titleLabel = new JLabel("MY PROFILE");

        titleLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 28)
        );

        titleLabel.setForeground(DARK);
        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitleLabel =
                new JLabel("Your personal and financial overview");

        subtitleLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ================= CENTER =================

        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(
                new BoxLayout(
                        centerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        centerPanel.setBackground(BACKGROUND);

        centerPanel.add(
                Box.createVerticalStrut(20)
        );

        // ================= PROFILE CARD =================

        JPanel profileCard = new JPanel();

        profileCard.setLayout(
                new BoxLayout(
                        profileCard,
                        BoxLayout.Y_AXIS
                )
        );

        profileCard.setBackground(WHITE);

        profileCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BLUE,
                                2
                        ),
                        new EmptyBorder(
                                20, 25, 20, 25
                        )
                )
        );

        profileCard.setMaximumSize(
                new Dimension(540, 220)
        );

        // Avatar
        JLabel avatar = new JLabel("👤");

        avatar.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        45
                )
        );

        avatar.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        profileCard.add(avatar);

        profileCard.add(
                Box.createVerticalStrut(10)
        );

        nameLabel =
                new JLabel("Loading...");

        nameLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        nameLabel.setForeground(DARK);

        nameLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        profileCard.add(nameLabel);

        profileCard.add(
                Box.createVerticalStrut(15)
        );

        userIdLabel =
                createInfoLabel("");

        emailLabel =
                createInfoLabel("");

        profileCard.add(userIdLabel);

        profileCard.add(
                Box.createVerticalStrut(5)
        );

        profileCard.add(emailLabel);

        centerPanel.add(profileCard);

        centerPanel.add(
                Box.createVerticalStrut(20)
        );

        // ================= FINANCIAL SUMMARY =================

        JLabel financialHeading =
                new JLabel("FINANCIAL SUMMARY");

        financialHeading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        financialHeading.setForeground(DARK);

        financialHeading.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        centerPanel.add(financialHeading);

        centerPanel.add(
                Box.createVerticalStrut(12)
        );

        JPanel financialPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                12,
                                0
                        )
                );

        financialPanel.setBackground(
                BACKGROUND
        );

        financialPanel.setMaximumSize(
                new Dimension(540, 110)
        );

        incomeValue =
                new JLabel("₹ 0.00");

        expenseValue =
                new JLabel("₹ 0.00");

        balanceValue =
                new JLabel("₹ 0.00");

        financialPanel.add(
                createFinancialCard(
                        "TOTAL INCOME",
                        incomeValue,
                        GREEN
                )
        );

        financialPanel.add(
                createFinancialCard(
                        "TOTAL EXPENSES",
                        expenseValue,
                        RED
                )
        );

        financialPanel.add(
                createFinancialCard(
                        "BALANCE",
                        balanceValue,
                        BLUE
                )
        );

        centerPanel.add(financialPanel);

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // ================= BOTTOM =================

        JPanel bottomPanel = new JPanel();

        bottomPanel.setBackground(
                BACKGROUND
        );

        JButton backButton =
                new JButton("← BACK");

        backButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        backButton.setForeground(WHITE);
        backButton.setBackground(BLUE);

        backButton.setPreferredSize(
                new Dimension(180, 40)
        );

        backButton.setFocusPainted(false);
        backButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        backButton.addActionListener(
                e -> dispose()
        );

        bottomPanel.add(backButton);

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        setContentPane(mainPanel);

        // Load actual user data
        loadProfileData();
    }

    // =========================================================
    // LOAD PROFILE DATA
    // =========================================================

    private void loadProfileData() {

        String sql =
                "SELECT user_id, name, email " +
                "FROM users " +
                "WHERE user_id = ?";

        try {

            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(
                    1,
                    Session.currentUserId
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                nameLabel.setText(
                        rs.getString("name")
                );

                userIdLabel.setText(
                        "User ID: " +
                        rs.getInt("user_id")
                );

                emailLabel.setText(
                        "Email: " +
                        rs.getString("email")
                );
            }

            // ================= FINANCIAL DATA =================

            IncomeDAO incomeDAO =
                    new IncomeDAO();

            ExpenseDAO expenseDAO =
                    new ExpenseDAO();

            double totalIncome =
                    incomeDAO.getTotalIncome(
                            Session.currentUserId
                    );

            double totalExpense =
                    expenseDAO.getTotalExpense(
                            Session.currentUserId
                    );

            double balance =
                    totalIncome - totalExpense;

            incomeValue.setText(
                    "₹ " +
                    String.format(
                            "%.2f",
                            totalIncome
                    )
            );

            expenseValue.setText(
                    "₹ " +
                    String.format(
                            "%.2f",
                            totalExpense
                    )
            );

            balanceValue.setText(
                    "₹ " +
                    String.format(
                            "%.2f",
                            balance
                    )
            );

            // Green if positive, red if negative
            if (balance < 0) {

                balanceValue.setForeground(
                        RED
                );

            } else {

                balanceValue.setForeground(
                        GREEN
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load profile information.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // INFO LABEL
    // =========================================================

    private JLabel createInfoLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        label.setForeground(GRAY);

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        return label;
    }

    // =========================================================
    // FINANCIAL CARD
    // =========================================================

    private JPanel createFinancialCard(
            String title,
            JLabel value,
            Color color
    ) {

        JPanel card = new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                color,
                                2
                        ),
                        new EmptyBorder(
                                12, 5, 12, 5
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        titleLabel.setForeground(color);

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        value.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        value.setForeground(DARK);

        value.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(value);

        return card;
    }
}
