package gui;

import dao.ExpenseDAO;
import dao.IncomeDAO;
import db.Session;

import javax.swing.*;
import java.awt.*;

public class BalanceFrame extends JFrame {

    // ================= COLORS =================

    private final Color BACKGROUND = new Color(245, 248, 250);
    private final Color GREEN = new Color(46, 125, 50);
    private final Color RED = new Color(211, 47, 47);
    private final Color BLUE = new Color(30, 136, 229);
    private final Color DARK = new Color(40, 40, 40);
    private final Color WHITE = Color.WHITE;

    private JLabel incomeValue;
    private JLabel expenseValue;
    private JLabel balanceValue;
    private JLabel messageLabel;

    private IncomeDAO incomeDAO = new IncomeDAO();
    private ExpenseDAO expenseDAO = new ExpenseDAO();

    // ================= CONSTRUCTOR =================

    public BalanceFrame() {

        setTitle("Expense Tracker - Balance");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        createUI();
        loadBalance();
    }

    // =========================================================
    // CREATE UI
    // =========================================================

    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30
                )
        );

        // ================= HEADER =================

        JPanel headerPanel =
                new JPanel();

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        headerPanel.setBackground(BACKGROUND);

        JLabel titleLabel =
                new JLabel("EXPENSE TRACKER");

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        titleLabel.setForeground(DARK);
        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitleLabel =
                new JLabel("Balance");

        subtitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        18
                )
        );

        subtitleLabel.setForeground(BLUE);
        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        headerPanel.add(titleLabel);
        headerPanel.add(
                Box.createVerticalStrut(5)
        );
        headerPanel.add(subtitleLabel);
        headerPanel.add(
                Box.createVerticalStrut(25)
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ================= SUMMARY PANEL =================

        JPanel summaryPanel =
                new JPanel(
                        new GridLayout(
                                3, 1, 0, 15
                        )
                );

        summaryPanel.setBackground(BACKGROUND);

        // INCOME

        JPanel incomePanel =
                createCard(
                        "TOTAL INCOME",
                        GREEN
                );

        incomeValue =
                new JLabel("₹0.00");

        styleValue(
                incomeValue,
                GREEN
        );

        incomePanel.add(
                incomeValue,
                BorderLayout.EAST
        );

        // EXPENSE

        JPanel expensePanel =
                createCard(
                        "TOTAL EXPENSE",
                        RED
                );

        expenseValue =
                new JLabel("₹0.00");

        styleValue(
                expenseValue,
                RED
        );

        expensePanel.add(
                expenseValue,
                BorderLayout.EAST
        );

        // BALANCE

        JPanel balancePanel =
                createCard(
                        "CURRENT BALANCE",
                        BLUE
                );

        balanceValue =
                new JLabel("₹0.00");

        styleValue(
                balanceValue,
                BLUE
        );

        balancePanel.add(
                balanceValue,
                BorderLayout.EAST
        );

        summaryPanel.add(incomePanel);
        summaryPanel.add(expensePanel);
        summaryPanel.add(balancePanel);

        mainPanel.add(
                summaryPanel,
                BorderLayout.CENTER
        );

        // ================= BOTTOM =================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setBackground(BACKGROUND);

        messageLabel =
                new JLabel(
                        " "
                );

        messageLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        messageLabel.setForeground(DARK);

        bottomPanel.add(
                messageLabel,
                BorderLayout.WEST
        );

        JButton backButton =
                new JButton("← BACK");

        backButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        backButton.setBackground(BLUE);
        backButton.setForeground(WHITE);
        backButton.setFocusPainted(false);

        backButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        backButton.addActionListener(
                e -> dispose()
        );

        bottomPanel.add(
                backButton,
                BorderLayout.EAST
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }

    // =========================================================
    // CREATE CARD
    // =========================================================

    private JPanel createCard(
            String title,
            Color color
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                color,
                                2
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        titleLabel.setForeground(
                color
        );

        panel.add(
                titleLabel,
                BorderLayout.WEST
        );

        return panel;
    }

    // =========================================================
    // STYLE VALUE
    // =========================================================

    private void styleValue(
            JLabel label,
            Color color
    ) {

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        label.setForeground(color);
    }

    // =========================================================
    // LOAD BALANCE
    // =========================================================

    private void loadBalance() {

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
                String.format(
                        "₹%.2f",
                        totalIncome
                )
        );

        expenseValue.setText(
                String.format(
                        "₹%.2f",
                        totalExpense
                )
        );

        balanceValue.setText(
                String.format(
                        "₹%.2f",
                        balance
                )
        );

        // Same logic as your BalanceService

        if (totalIncome == 0) {

            messageLabel.setText(
                    "No income records found."
            );

        } else if (balance < 0) {

            messageLabel.setText(
                    "Your expenses are higher than your income."
            );

        } else {

            messageLabel.setText(
                    "Your current balance is positive."
            );
        }
    }
}