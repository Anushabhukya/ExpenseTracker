package gui;

import dao.ExpenseDAO;
import db.DBConnection;
import db.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExpenseFrame extends JFrame {

    // ================= COLORS =================

    private final Color BACKGROUND = new Color(250, 245, 245);
    private final Color RED = new Color(211, 47, 47);
    private final Color DARK_RED = new Color(183, 28, 28);
    private final Color LIGHT_RED = new Color(255, 235, 238);
    private final Color BLUE = new Color(30, 136, 229);

    // ================= COMPONENTS =================

    private JTextField amountField;
    private JTextField descriptionField;

    private JComboBox<String> categoryBox;

    private JTable expenseTable;
    private DefaultTableModel tableModel;

    private JLabel totalLabel;

    private ExpenseDAO expenseDAO = new ExpenseDAO();

    // ================= CONSTRUCTOR =================

    public ExpenseFrame() {

        setTitle("Expense Tracker - Expenses");
        setSize(850, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        createUI();

        loadAllExpenses();
    }

    // =========================================================
    // CREATE UI
    // =========================================================

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        // ================= HEADER =================

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(
                new BoxLayout(headerPanel, BoxLayout.Y_AXIS)
        );
        headerPanel.setBackground(BACKGROUND);

        JLabel titleLabel = new JLabel("EXPENSE TRACKER");
        titleLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 24)
        );
        titleLabel.setForeground(DARK_RED);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Expenses");
        subtitleLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 18)
        );
        subtitleLabel.setForeground(RED);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);
        headerPanel.add(Box.createVerticalStrut(15));

        // ================= INPUT PANEL =================

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);

        inputPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                LIGHT_RED, 2
                        ),
                        BorderFactory.createEmptyBorder(
                                10, 15, 10, 15
                        )
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // CATEGORY

        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        categoryBox = new JComboBox<>(
                new String[]{
                        "Food",
                        "Travel",
                        "Groceries",
                        "Bills",
                        "Entertainment",
                        "Others"
                }
        );

        categoryBox.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        inputPanel.add(categoryLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;

        inputPanel.add(categoryBox, gbc);

        // AMOUNT

        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        amountField = new JTextField();
        amountField.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        gbc.gridx = 2;
        gbc.weightx = 0;

        inputPanel.add(amountLabel, gbc);

        gbc.gridx = 3;
        gbc.weightx = 1;

        inputPanel.add(amountField, gbc);

        // DESCRIPTION

        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        descriptionField = new JTextField();
        descriptionField.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        inputPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        inputPanel.add(descriptionField, gbc);

        // ADD BUTTON

        JButton addButton = new JButton("＋ ADD EXPENSE");

        addButton.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        addButton.setBackground(RED);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;

        inputPanel.add(addButton, gbc);

        addButton.addActionListener(
                e -> addExpense()
        );

        // ================= TOP =================

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND);

        topPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        topPanel.add(
                inputPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // ================= TABLE =================

        tableModel = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Category",
                        "Amount",
                        "Description",
                        "Date"
                },
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        expenseTable = new JTable(tableModel);

        expenseTable.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        expenseTable.setRowHeight(28);

        expenseTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        expenseTable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        expenseTable.getTableHeader().setBackground(RED);
        expenseTable.getTableHeader().setForeground(Color.WHITE);

        expenseTable.setGridColor(
                new Color(230, 230, 230)
        );

        JScrollPane scrollPane =
                new JScrollPane(expenseTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        LIGHT_RED, 2
                )
        );

        JPanel tablePanel =
                new JPanel(new BorderLayout());

        tablePanel.setBackground(BACKGROUND);

        tablePanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 0, 10, 0
                )
        );

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                tablePanel,
                BorderLayout.CENTER
        );

        // ================= BOTTOM =================

        JPanel bottomPanel =
                new JPanel(new BorderLayout());

        bottomPanel.setBackground(BACKGROUND);

        totalLabel = new JLabel(
                "Total Expenses: ₹0.00"
        );

        totalLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );

        totalLabel.setForeground(DARK_RED);

        bottomPanel.add(
                totalLabel,
                BorderLayout.WEST
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        buttonPanel.setBackground(BACKGROUND);

        // VIEW EXPENSES

        JButton viewButton =
                new JButton("VIEW EXPENSES");

        viewButton.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        viewButton.setBackground(RED);
        viewButton.setForeground(Color.WHITE);
        viewButton.setFocusPainted(false);

        viewButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        // BACK

        JButton backButton =
                new JButton("← BACK");

        backButton.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        backButton.setBackground(BLUE);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);

        backButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        buttonPanel.add(viewButton);
        buttonPanel.add(backButton);

        bottomPanel.add(
                buttonPanel,
                BorderLayout.EAST
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // ================= ACTIONS =================

        viewButton.addActionListener(
                e -> showViewExpensesMenu()
        );

        backButton.addActionListener(
                e -> dispose()
        );

        add(mainPanel);
    }

    // =========================================================
    // ADD EXPENSE
    // =========================================================

    private void addExpense() {

        String amountText =
                amountField.getText().trim();

        String description =
                descriptionField.getText().trim();

        if (amountText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter amount.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter description.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        double amount;

        try {

            amount =
                    Double.parseDouble(amountText);

            if (amount <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid positive amount.",
                    "Invalid Amount",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Category ID:
        // Food = 1
        // Travel = 2
        // Groceries = 3
        // Bills = 4
        // Entertainment = 5
        // Others = 6

        int categoryId =
                categoryBox.getSelectedIndex() + 1;

        boolean success =
                expenseDAO.addExpense(
                        Session.currentUserId,
                        categoryId,
                        amount,
                        description
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expense Added Successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            amountField.setText("");
            descriptionField.setText("");

            loadAllExpenses();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to Add Expense!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // VIEW EXPENSES MENU
    // =========================================================

    private void showViewExpensesMenu() {

        String[] options = {
                "All Expenses",
                "Category-wise Expenses",
                "Daily Expenses",
                "Monthly Expenses",
                "Yearly Expenses",
                "Cancel"
        };

        int choice =
                JOptionPane.showOptionDialog(
                        this,
                        "Choose what you want to view:",
                        "VIEW EXPENSES",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]
                );

        switch (choice) {

            case 0:
                loadAllExpenses();
                break;

            case 1:
                showCategoryExpenses();
                break;

            case 2:
                showDailyDateDialog();
                break;

            case 3:
                showMonthlyDialog();
                break;

            case 4:
                showYearlyDialog();
                break;

            default:
                break;
        }
    }

    // =========================================================
    // ALL EXPENSES
    // =========================================================

    private void loadAllExpenses() {

        tableModel.setRowCount(0);

        double total = 0;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT e.expense_id, " +
                    "c.category_name, " +
                    "e.amount, " +
                    "e.description, " +
                    "e.expense_date " +
                    "FROM expenses e " +
                    "JOIN categories c " +
                    "ON e.category_id = c.category_id " +
                    "WHERE e.user_id = ? " +
                    "ORDER BY e.expense_date DESC, " +
                    "e.expense_id DESC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(
                    1,
                    Session.currentUserId
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                double amount =
                        rs.getDouble("amount");

                total += amount;

                tableModel.addRow(
                        new Object[]{
                                rs.getInt("expense_id"),
                                rs.getString(
                                        "category_name"
                                ),
                                String.format(
                                        "₹%.2f",
                                        amount
                                ),
                                rs.getString(
                                        "description"
                                ),
                                rs.getDate(
                                        "expense_date"
                                )
                        }
                );
            }

            totalLabel.setText(
                    String.format(
                            "Total Expenses: ₹%.2f",
                            total
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load expenses.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // CATEGORY-WISE
    // =========================================================

    private void showCategoryExpenses() {

        String[] categories = {
                "Food",
                "Travel",
                "Groceries",
                "Bills",
                "Entertainment",
                "Others"
        };

        String selectedCategory =
                (String) JOptionPane.showInputDialog(
                        this,
                        "Choose Category:",
                        "Category-wise Expenses",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        categories,
                        categories[0]
                );

        if (selectedCategory == null) {
            return;
        }

        int categoryId = 0;

        for (int i = 0;
             i < categories.length;
             i++) {

            if (categories[i].equals(
                    selectedCategory)) {

                categoryId = i + 1;
                break;
            }
        }

        loadCategoryExpenses(categoryId);
    }

    private void loadCategoryExpenses(
            int categoryId) {

        tableModel.setRowCount(0);

        double total = 0;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT e.expense_id, " +
                    "c.category_name, " +
                    "e.amount, " +
                    "e.description, " +
                    "e.expense_date " +
                    "FROM expenses e " +
                    "JOIN categories c " +
                    "ON e.category_id = c.category_id " +
                    "WHERE e.user_id = ? " +
                    "AND e.category_id = ? " +
                    "ORDER BY e.expense_date DESC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(
                    1,
                    Session.currentUserId
            );

            ps.setInt(
                    2,
                    categoryId
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                double amount =
                        rs.getDouble("amount");

                total += amount;

                tableModel.addRow(
                        new Object[]{
                                rs.getInt("expense_id"),
                                rs.getString(
                                        "category_name"
                                ),
                                String.format(
                                        "₹%.2f",
                                        amount
                                ),
                                rs.getString(
                                        "description"
                                ),
                                rs.getDate(
                                        "expense_date"
                                )
                        }
                );
            }

            totalLabel.setText(
                    String.format(
                            "Category Total: ₹%.2f",
                            total
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load category expenses.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // DAILY EXPENSES - SELECT DATE
    // =========================================================

    private void showDailyDateDialog() {

        JPanel panel =
                new JPanel(new GridLayout(3, 2, 10, 10));

        JTextField dayField =
                new JTextField();

        JTextField monthField =
                new JTextField();

        JTextField yearField =
                new JTextField();

        panel.add(new JLabel("Day (DD):"));
        panel.add(dayField);

        panel.add(new JLabel("Month (MM):"));
        panel.add(monthField);

        panel.add(new JLabel("Year (YYYY):"));
        panel.add(yearField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Select Date",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String date =
                yearField.getText().trim()
                        + "-"
                        + monthField.getText().trim()
                        + "-"
                        + dayField.getText().trim();

        try {

            java.sql.Date.valueOf(date);

            loadDailyExpenses(date);

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date.\nPlease enter YYYY/MM/DD correctly.",
                    "Invalid Date",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadDailyExpenses(
            String date) {

        loadFilteredExpenses(
                "Daily Expenses",
                "AND e.expense_date = '" +
                        date +
                        "'"
        );
    }

    // =========================================================
    // MONTHLY EXPENSES - SELECT MONTH + YEAR
    // =========================================================

    private void showMonthlyDialog() {

        JPanel panel =
                new JPanel(new GridLayout(2, 2, 10, 10));

        String[] months = {
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };

        JComboBox<String> monthBox =
                new JComboBox<>(months);

        JTextField yearField =
                new JTextField();

        panel.add(
                new JLabel("Select Month:")
        );

        panel.add(monthBox);

        panel.add(
                new JLabel("Enter Year:")
        );

        panel.add(yearField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Select Month",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String year =
                yearField.getText().trim();

        if (!year.matches("\\d{4}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid year.",
                    "Invalid Year",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        int month =
                monthBox.getSelectedIndex() + 1;

        loadMonthlyExpenses(
                month,
                Integer.parseInt(year)
        );
    }

    private void loadMonthlyExpenses(
            int month,
            int year) {

        tableModel.setRowCount(0);

        double total = 0;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT e.expense_id, " +
                    "c.category_name, " +
                    "e.amount, " +
                    "e.description, " +
                    "e.expense_date " +
                    "FROM expenses e " +
                    "JOIN categories c " +
                    "ON e.category_id = c.category_id " +
                    "WHERE e.user_id = ? " +
                    "AND MONTH(e.expense_date) = ? " +
                    "AND YEAR(e.expense_date) = ? " +
                    "ORDER BY e.expense_date DESC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(
                    1,
                    Session.currentUserId
            );

            ps.setInt(2, month);

            ps.setInt(3, year);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                double amount =
                        rs.getDouble("amount");

                total += amount;

                tableModel.addRow(
                        new Object[]{
                                rs.getInt("expense_id"),
                                rs.getString(
                                        "category_name"
                                ),
                                String.format(
                                        "₹%.2f",
                                        amount
                                ),
                                rs.getString(
                                        "description"
                                ),
                                rs.getDate(
                                        "expense_date"
                                )
                        }
                );
            }

            totalLabel.setText(
                    String.format(
                            "Monthly Total: ₹%.2f",
                            total
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load monthly expenses.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // YEARLY EXPENSES - SELECT YEAR
    // =========================================================

    private void showYearlyDialog() {

        String year =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Year (YYYY):",
                        "Select Year",
                        JOptionPane.PLAIN_MESSAGE
                );

        if (year == null) {
            return;
        }

        year = year.trim();

        if (!year.matches("\\d{4}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid year.",
                    "Invalid Year",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        loadYearlyExpenses(
                Integer.parseInt(year)
        );
    }

    private void loadYearlyExpenses(
            int year) {

        tableModel.setRowCount(0);

        double total = 0;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT e.expense_id, " +
                    "c.category_name, " +
                    "e.amount, " +
                    "e.description, " +
                    "e.expense_date " +
                    "FROM expenses e " +
                    "JOIN categories c " +
                    "ON e.category_id = c.category_id " +
                    "WHERE e.user_id = ? " +
                    "AND YEAR(e.expense_date) = ? " +
                    "ORDER BY e.expense_date DESC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(
                    1,
                    Session.currentUserId
            );

            ps.setInt(2, year);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                double amount =
                        rs.getDouble("amount");

                total += amount;

                tableModel.addRow(
                        new Object[]{
                                rs.getInt("expense_id"),
                                rs.getString(
                                        "category_name"
                                ),
                                String.format(
                                        "₹%.2f",
                                        amount
                                ),
                                rs.getString(
                                        "description"
                                ),
                                rs.getDate(
                                        "expense_date"
                                )
                        }
                );
            }

            totalLabel.setText(
                    String.format(
                            "Yearly Total: ₹%.2f",
                            total
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load yearly expenses.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // COMMON FILTER METHOD
    // =========================================================

    private void loadFilteredExpenses(
            String title,
            String condition) {

        tableModel.setRowCount(0);

        double total = 0;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT e.expense_id, " +
                    "c.category_name, " +
                    "e.amount, " +
                    "e.description, " +
                    "e.expense_date " +
                    "FROM expenses e " +
                    "JOIN categories c " +
                    "ON e.category_id = c.category_id " +
                    "WHERE e.user_id = ? " +
                    condition +
                    " ORDER BY e.expense_date DESC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(
                    1,
                    Session.currentUserId
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                double amount =
                        rs.getDouble("amount");

                total += amount;

                tableModel.addRow(
                        new Object[]{
                                rs.getInt("expense_id"),
                                rs.getString(
                                        "category_name"
                                ),
                                String.format(
                                        "₹%.2f",
                                        amount
                                ),
                                rs.getString(
                                        "description"
                                ),
                                rs.getDate(
                                        "expense_date"
                                )
                        }
                );
            }

            totalLabel.setText(
                    String.format(
                            "%s Total: ₹%.2f",
                            title,
                            total
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load expenses.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}