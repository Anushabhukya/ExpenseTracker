package gui;

import dao.IncomeDAO;
import db.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IncomeFrame extends JFrame {

private JTextField amountField;
private JTextField sourceField;
private JTable incomeTable;
private JLabel totalLabel;

public IncomeFrame() {

    setTitle("Expense Tracker - Income");
    setSize(700, 550);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);

    JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
    mainPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
    );
    mainPanel.setBackground(new Color(245, 247, 250));

    // Title
    JLabel titleLabel = new JLabel(
            "INCOME",
            SwingConstants.CENTER
    );
    titleLabel.setFont(
            new Font("Segoe UI", Font.BOLD, 26)
    );

    mainPanel.add(titleLabel, BorderLayout.NORTH);

    // Input panel
    JPanel inputPanel = new JPanel(new GridBagLayout());
    inputPanel.setBackground(new Color(245, 247, 250));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 8, 5, 8);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Amount
    gbc.gridx = 0;
    gbc.gridy = 0;

    JLabel amountLabel = new JLabel("Amount:");
    amountLabel.setFont(
            new Font("Segoe UI", Font.BOLD, 14)
    );

    inputPanel.add(amountLabel, gbc);

    gbc.gridx = 1;

    amountField = new JTextField();
    amountField.setPreferredSize(new Dimension(180, 35));

    inputPanel.add(amountField, gbc);

    // Source
    gbc.gridx = 0;
    gbc.gridy = 1;

    JLabel sourceLabel = new JLabel("Source:");
    sourceLabel.setFont(
            new Font("Segoe UI", Font.BOLD, 14)
    );

    inputPanel.add(sourceLabel, gbc);

    gbc.gridx = 1;

    sourceField = new JTextField();
    sourceField.setPreferredSize(new Dimension(180, 35));

    inputPanel.add(sourceField, gbc);

    // Add button
    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.gridheight = 2;

    JButton addButton = new JButton("ADD INCOME");
    addButton.setFont(
            new Font("Segoe UI", Font.BOLD, 14)
    );
    addButton.setFocusPainted(false);
    addButton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
    );

    inputPanel.add(addButton, gbc);

    mainPanel.add(inputPanel, BorderLayout.CENTER);

    // Table
    String[] columns = {
            "ID",
            "Amount",
            "Source",
            "Date"
    };

    DefaultTableModel model =
            new DefaultTableModel(columns, 0) {

                @Override
                public boolean isCellEditable(
                        int row,
                        int column
                ) {
                    return false;
                }
            };

    incomeTable = new JTable(model);

    incomeTable.setFont(
            new Font("Segoe UI", Font.PLAIN, 14)
    );

    incomeTable.setRowHeight(30);

    incomeTable.getTableHeader().setFont(
            new Font("Segoe UI", Font.BOLD, 14)
    );

    JScrollPane scrollPane =
            new JScrollPane(incomeTable);

    mainPanel.add(scrollPane, BorderLayout.SOUTH);

    // Bottom panel
    JPanel bottomPanel = new JPanel(
            new BorderLayout()
    );

    bottomPanel.setBackground(
            new Color(245, 247, 250)
    );

    totalLabel = new JLabel("Total Income: ₹0.00");
    totalLabel.setFont(
            new Font("Segoe UI", Font.BOLD, 16)
    );

    JButton backButton = new JButton("BACK");
    backButton.setFont(
            new Font("Segoe UI", Font.BOLD, 14)
    );
    backButton.setFocusPainted(false);
    backButton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
    );

    bottomPanel.add(totalLabel, BorderLayout.WEST);
    bottomPanel.add(backButton, BorderLayout.EAST);

    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    // Add Income action
    addButton.addActionListener(e -> addIncome());

    // Back action
    backButton.addActionListener(e -> dispose());

    // Load existing income
    loadIncome();

    add(mainPanel);
}

private void addIncome() {

    String amountText =
            amountField.getText().trim();

    String source =
            sourceField.getText().trim();

    if (amountText.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter the amount.",
                "Income",
                JOptionPane.WARNING_MESSAGE
        );

        amountField.requestFocus();
        return;
    }

    if (source.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter the income source.",
                "Income",
                JOptionPane.WARNING_MESSAGE
        );

        sourceField.requestFocus();
        return;
    }

    double amount;

    try {

        amount = Double.parseDouble(amountText);

        if (amount <= 0) {
            throw new NumberFormatException();
        }

    } catch (NumberFormatException e) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter a valid positive amount.",
                "Income",
                JOptionPane.ERROR_MESSAGE
        );

        amountField.requestFocus();
        return;
    }

    IncomeDAO incomeDAO = new IncomeDAO();

    boolean success = incomeDAO.addIncome(
            Session.currentUserId,
            amount,
            source
    );

    if (success) {

        JOptionPane.showMessageDialog(
                this,
                "Income Added Successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );

        amountField.setText("");
        sourceField.setText("");

        loadIncome();

    } else {

        JOptionPane.showMessageDialog(
                this,
                "Failed to Add Income!",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

private void loadIncome() {

    DefaultTableModel model =
            (DefaultTableModel) incomeTable.getModel();

    model.setRowCount(0);

    double total = 0;

    try {

        Connection conn =
                db.DBConnection.getConnection();

        String sql =
                "SELECT income_id, amount, source, income_date " +
                "FROM income WHERE user_id = ? " +
                "ORDER BY income_date DESC, income_id DESC";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, Session.currentUserId);

        ResultSet rs =
                ps.executeQuery();

        while (rs.next()) {

            int id =
                    rs.getInt("income_id");

            double amount =
                    rs.getDouble("amount");

            String source =
                    rs.getString("source");

            String date =
                    rs.getDate("income_date").toString();

            model.addRow(
                    new Object[]{
                            id,
                            "₹" + String.format("%.2f", amount),
                            source,
                            date
                    }
            );

            total += amount;
        }

        totalLabel.setText(
                "Total Income: ₹" +
                String.format("%.2f", total)
        );

    } catch (Exception e) {

        e.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                "Unable to load income data.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


}
