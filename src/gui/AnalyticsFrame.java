package gui;

import db.DBConnection;
import db.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsFrame extends JFrame {

    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color BLUE = new Color(30, 136, 229);
    private final Color DARK = new Color(40, 40, 40);
    private final Color GRID = new Color(225, 225, 225);

    public AnalyticsFrame() {

        setTitle("Expense Tracker - Analytics");
        setSize(850, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                new EmptyBorder(15, 20, 15, 20)
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel = new JPanel();

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        headerPanel.setBackground(BACKGROUND);

        JLabel titleLabel =
                new JLabel("ANALYTICS");

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        titleLabel.setForeground(DARK);

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Analyze your spending patterns"
                );

        subtitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitleLabel.setForeground(Color.GRAY);

        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        headerPanel.add(titleLabel);

        headerPanel.add(
                Box.createVerticalStrut(5)
        );

        headerPanel.add(subtitleLabel);

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABS
        // =====================================================

        JTabbedPane tabbedPane =
                new JTabbedPane();

        tabbedPane.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        tabbedPane.addTab(
                "Category",
                new CategoryPanel()
        );

        tabbedPane.addTab(
                "Daily",
                new DailyPanel()
        );

        tabbedPane.addTab(
                "Monthly",
                new MonthlyPanel()
        );

        tabbedPane.addTab(
                "Yearly",
                new YearlyPanel()
        );

        mainPanel.add(
                tabbedPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // BACK BUTTON
        // =====================================================

        JPanel bottomPanel =
                new JPanel();

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

        backButton.setForeground(Color.WHITE);

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
    }

    // =========================================================
    // CATEGORY PANEL
    // =========================================================

    class CategoryPanel extends JPanel {

        private List<String> categories =
                new ArrayList<>();

        private List<Double> amounts =
                new ArrayList<>();

        public CategoryPanel() {

            setBackground(Color.WHITE);

            loadData();

            setPreferredSize(
                    new Dimension(800, 450)
            );
        }

        private void loadData() {

            String sql =
                    "SELECT c.category_name, " +
                    "SUM(e.amount) AS total " +
                    "FROM expenses e " +
                    "JOIN categories c " +
                    "ON e.category_id = c.category_id " +
                    "WHERE e.user_id = ? " +
                    "GROUP BY c.category_id, c.category_name " +
                    "ORDER BY total DESC";

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

                while (rs.next()) {

                    categories.add(
                            rs.getString(
                                    "category_name"
                            )
                    );

                    amounts.add(
                            rs.getDouble(
                                    "total"
                            )
                    );
                }

                repaint();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int width = getWidth();
            int height = getHeight();

            if (amounts.isEmpty()) {

                drawNoData(
                        g2,
                        width,
                        height,
                        "No expense data available."
                );

                g2.dispose();

                return;
            }

            double total = 0;

            for (double value : amounts) {

                total += value;
            }

            int centerX = width / 2;
            int centerY = height / 2;

            int diameter =
                    Math.min(width, height) - 150;

            int startAngle = 0;

            for (int i = 0;
                 i < amounts.size();
                 i++) {

                int angle;

                if (i == amounts.size() - 1) {

                    angle =
                            360 - startAngle;

                } else {

                    angle =
                            (int) Math.round(
                                    amounts.get(i)
                                            / total
                                            * 360
                            );
                }

                g2.setColor(
                        getChartColor(i)
                );

                g2.fillArc(
                        centerX - diameter / 2,
                        centerY - diameter / 2,
                        diameter,
                        diameter,
                        startAngle,
                        angle
                );

                startAngle += angle;
            }

            // =================================================
            // LEGEND
            // =================================================

            int legendX =
                    width - 190;

            int legendY = 80;

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            12
                    )
            );

            for (int i = 0;
                 i < categories.size();
                 i++) {

                g2.setColor(
                        getChartColor(i)
                );

                g2.fillRect(
                        legendX,
                        legendY + i * 35,
                        15,
                        15
                );

                g2.setColor(DARK);

                String text =
                        categories.get(i)
                                + " ₹"
                                + String.format(
                                        "%.2f",
                                        amounts.get(i)
                                );

                g2.drawString(
                        text,
                        legendX + 25,
                        legendY + 13 + i * 35
                );
            }

            g2.dispose();
        }
    }

    // =========================================================
    // DAILY PANEL
    // =========================================================

    class DailyPanel extends JPanel {

        private List<String> dates =
                new ArrayList<>();

        private List<Double> amounts =
                new ArrayList<>();

        public DailyPanel() {

            setBackground(Color.WHITE);

            loadDailyExpenses();

            setPreferredSize(
                    new Dimension(800, 450)
            );
        }

        private void loadDailyExpenses() {

            dates.clear();

            amounts.clear();

            String sql =
                    "SELECT expense_date, " +
                    "SUM(amount) AS total " +
                    "FROM expenses " +
                    "WHERE user_id = ? " +
                    "AND expense_date >= " +
                    "DATE_SUB(CURDATE(), INTERVAL 29 DAY) " +
                    "AND expense_date <= CURDATE() " +
                    "GROUP BY expense_date " +
                    "ORDER BY expense_date";

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

                Map<String, Double> expenseMap =
                        new HashMap<>();

                while (rs.next()) {

                    String date =
                            rs.getDate(
                                    "expense_date"
                            ).toString();

                    double total =
                            rs.getDouble(
                                    "total"
                            );

                    expenseMap.put(
                            date,
                            total
                    );
                }

                LocalDate today =
                        LocalDate.now();

                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern(
                                "dd MMM"
                        );

                for (int i = 29; i >= 0; i--) {

                    LocalDate date =
                            today.minusDays(i);

                    dates.add(
                            date.format(formatter)
                    );

                    amounts.add(
                            expenseMap.getOrDefault(
                                    date.toString(),
                                    0.0
                            )
                    );
                }

                repaint();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int width = getWidth();
            int height = getHeight();

            int left = 70;
            int right = 30;
            int top = 55;
            int bottom = 70;

            int chartWidth =
                    width - left - right;

            int chartHeight =
                    height - top - bottom;

            // =================================================
            // TITLE
            // =================================================

            g2.setColor(DARK);

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            20
                    )
            );

            g2.drawString(
                    "Daily Expenses - Last 30 Days",
                    left,
                    30
            );

            // =================================================
            // MAX
            // =================================================

            double max = 0;

            for (double amount : amounts) {

                max =
                        Math.max(
                                max,
                                amount
                        );
            }

            if (max == 0) {

                max = 100;
            }

            // =================================================
            // GRID
            // =================================================

            for (int i = 0; i <= 5; i++) {

                int y =
                        top +
                        chartHeight -
                        i * chartHeight / 5;

                g2.setColor(GRID);

                g2.drawLine(
                        left,
                        y,
                        width - right,
                        y
                );

                g2.setColor(
                        new Color(
                                100,
                                100,
                                100
                        )
                );

                g2.setFont(
                        new Font(
                                "Segoe UI",
                                Font.PLAIN,
                                11
                        )
                );

                g2.drawString(
                        String.format(
                                "₹%.0f",
                                max * i / 5
                        ),
                        15,
                        y + 5
                );
            }

            // =================================================
            // AXES
            // =================================================

            int xAxisY =
                    top + chartHeight;

            g2.setColor(
                    new Color(
                            80,
                            80,
                            80
                    )
            );

            g2.setStroke(
                    new BasicStroke(2)
            );

            g2.drawLine(
                    left,
                    top,
                    left,
                    xAxisY
            );

            g2.drawLine(
                    left,
                    xAxisY,
                    width - right,
                    xAxisY
            );

            // =================================================
            // LINE
            // =================================================

            int previousX = 0;
            int previousY = 0;

            for (int i = 0;
                 i < amounts.size();
                 i++) {

                int x =
                        left +
                        i * chartWidth /
                                (amounts.size() - 1);

                int y =
                        top +
                        chartHeight -
                        (int)(
                                amounts.get(i)
                                        / max
                                        * chartHeight
                        );

                if (i > 0) {

                    g2.setColor(BLUE);

                    g2.setStroke(
                            new BasicStroke(3)
                    );

                    g2.drawLine(
                            previousX,
                            previousY,
                            x,
                            y
                    );
                }

                g2.setColor(BLUE);

                g2.fillOval(
                        x - 5,
                        y - 5,
                        10,
                        10
                );

                previousX = x;

                previousY = y;
            }

            // =================================================
            // DAILY X LABELS
            // =================================================

            g2.setColor(DARK);

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            10
                    )
            );

            for (int i = 0;
                 i < dates.size();
                 i += 5) {

                int x =
                        left +
                        i * chartWidth /
                                (dates.size() - 1);

                g2.drawString(
                        dates.get(i),
                        x - 15,
                        xAxisY + 25
                );
            }

            g2.dispose();
        }
    }

    // =========================================================
    // MONTHLY PANEL
    // =========================================================

    class MonthlyPanel extends JPanel {

        private List<String> months =
                new ArrayList<>();

        private List<Double> amounts =
                new ArrayList<>();

        public MonthlyPanel() {

            setBackground(Color.WHITE);

            loadData();

            setPreferredSize(
                    new Dimension(800, 450)
            );
        }

        private void loadData() {

            months.clear();

            amounts.clear();

            String sql =
                    "SELECT MONTH(expense_date) AS month, " +
                    "SUM(amount) AS total " +
                    "FROM expenses " +
                    "WHERE user_id = ? " +
                    "AND YEAR(expense_date) = YEAR(CURDATE()) " +
                    "GROUP BY MONTH(expense_date) " +
                    "ORDER BY month";

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

                Map<Integer, Double> monthlyMap =
                        new HashMap<>();

                while (rs.next()) {

                    monthlyMap.put(
                            rs.getInt("month"),
                            rs.getDouble("total")
                    );
                }

                String[] monthNames = {

                        "Jan",
                        "Feb",
                        "Mar",
                        "Apr",
                        "May",
                        "Jun",
                        "Jul",
                        "Aug",
                        "Sep",
                        "Oct",
                        "Nov",
                        "Dec"
                };

                // ALWAYS SHOW ALL 12 MONTHS

                for (int i = 1; i <= 12; i++) {

                    months.add(
                            monthNames[i - 1]
                    );

                    amounts.add(
                            monthlyMap.getOrDefault(
                                    i,
                                    0.0
                            )
                    );
                }

                repaint();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // IMPORTANT:
            // Pass THIS PANEL'S dimensions

            drawBarChart(
                    g2,
                    getWidth(),
                    getHeight(),
                    months,
                    amounts,
                    "Monthly Expenses - "
                            + LocalDate.now().getYear(),
                    "Month"
            );

            g2.dispose();
        }
    }

    // =========================================================
    // YEARLY PANEL
    // =========================================================

    class YearlyPanel extends JPanel {

        private List<String> years =
                new ArrayList<>();

        private List<Double> amounts =
                new ArrayList<>();

        public YearlyPanel() {

            setBackground(Color.WHITE);

            loadData();

            setPreferredSize(
                    new Dimension(800, 450)
            );
        }

        private void loadData() {

            years.clear();

            amounts.clear();

            String sql =
                    "SELECT YEAR(expense_date) AS year, " +
                    "SUM(amount) AS total " +
                    "FROM expenses " +
                    "WHERE user_id = ? " +
                    "AND YEAR(expense_date) >= " +
                    "YEAR(CURDATE()) - 4 " +
                    "AND YEAR(expense_date) <= YEAR(CURDATE()) " +
                    "GROUP BY YEAR(expense_date) " +
                    "ORDER BY year";

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

                Map<Integer, Double> yearlyMap =
                        new HashMap<>();

                while (rs.next()) {

                    yearlyMap.put(
                            rs.getInt("year"),
                            rs.getDouble("total")
                    );
                }

                int currentYear =
                        LocalDate.now().getYear();

                // ALWAYS SHOW LAST 5 YEARS

                for (int i = 4; i >= 0; i--) {

                    int year =
                            currentYear - i;

                    years.add(
                            String.valueOf(year)
                    );

                    amounts.add(
                            yearlyMap.getOrDefault(
                                    year,
                                    0.0
                            )
                    );
                }

                repaint();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // IMPORTANT:
            // Pass THIS PANEL'S dimensions

            drawBarChart(
                    g2,
                    getWidth(),
                    getHeight(),
                    years,
                    amounts,
                    "Yearly Expenses - Last 5 Years",
                    "Year"
            );

            g2.dispose();
        }
    }

    // =========================================================
    // BAR CHART
    // =========================================================

    private void drawBarChart(
            Graphics2D g2,
            int width,
            int height,
            List<String> labels,
            List<Double> values,
            String title,
            String xAxisTitle
    ) {

        // =====================================================
        // CHART AREA
        // =====================================================

        int left = 70;
        int right = 35;

        /*
         * More space at the top because
         * labels will be ABOVE the bars.
         */
        int top = 75;

        int bottom = 55;

        int chartWidth =
                width - left - right;

        int chartHeight =
                height - top - bottom;

        int xAxisY =
                top + chartHeight;

        // =====================================================
        // TITLE
        // =====================================================

        g2.setColor(DARK);

        g2.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        g2.drawString(
                title,
                left,
                35
        );

        if (values.isEmpty()) {

            drawNoData(
                    g2,
                    width,
                    height,
                    "No expense data available."
            );

            return;
        }

        // =====================================================
        // FIND MAXIMUM VALUE
        // =====================================================

        double max = 0;

        for (double value : values) {

            max =
                    Math.max(
                            max,
                            value
                    );
        }

        if (max == 0) {

            max = 100;
        }

        /*
         * Extra 25% space above the highest bar.
         * This creates room for the Month/Year label.
         */
        double chartMax =
                max * 1.25;

        // =====================================================
        // GRID LINES
        // =====================================================

        for (int i = 0; i <= 5; i++) {

            int y =
                    top +
                    chartHeight -
                    i * chartHeight / 5;

            // Grid

            g2.setColor(GRID);

            g2.drawLine(
                    left,
                    y,
                    width - right,
                    y
            );

            // Y value

            g2.setColor(
                    new Color(
                            100,
                            100,
                            100
                    )
            );

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            11
                    )
            );

            g2.drawString(
                    String.format(
                            "₹%.0f",
                            chartMax * i / 5
                    ),
                    15,
                    y + 5
            );
        }

        // =====================================================
        // AXES
        // =====================================================

        g2.setColor(
                new Color(
                        80,
                        80,
                        80
                )
        );

        g2.setStroke(
                new BasicStroke(2)
        );

        // Y axis

        g2.drawLine(
                left,
                top,
                left,
                xAxisY
        );

        // X axis

        g2.drawLine(
                left,
                xAxisY,
                width - right,
                xAxisY
        );

        // =====================================================
        // BAR SETTINGS
        // =====================================================

        int count =
                values.size();

        double slotWidth =
                (double) chartWidth / count;

        int barWidth;

        if (count <= 5) {

            barWidth = 50;

        } else {

            barWidth = 30;
        }

        // =====================================================
        // DRAW BARS
        // =====================================================

        for (int i = 0;
             i < count;
             i++) {

            double value =
                    values.get(i);

            int barHeight =
                    (int)(
                            value /
                            chartMax *
                            chartHeight
                    );

            int centerX =
                    (int)(
                            left +
                            i * slotWidth +
                            slotWidth / 2
                    );

            int barX =
                    centerX -
                    barWidth / 2;

            int barY =
                    xAxisY -
                    barHeight;

            // =================================================
            // BAR
            // =================================================

            g2.setColor(BLUE);

            g2.fillRoundRect(
                    barX,
                    barY,
                    barWidth,
                    barHeight,
                    8,
                    8
            );

            // =================================================
            // MONTH / YEAR LABEL ABOVE BAR
            // =================================================

            String label =
                    labels.get(i);

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            12
                    )
            );

            FontMetrics labelMetrics =
                    g2.getFontMetrics();

            int labelWidth =
                    labelMetrics.stringWidth(
                            label
                    );

            int labelX =
                    centerX -
                    labelWidth / 2;

            /*
             * This is the important part:
             *
             * The Month / Year is ALWAYS
             * drawn above its corresponding bar.
             */
            int labelY =
                    barY - 12;

            g2.setColor(DARK);

            g2.drawString(
                    label,
                    labelX,
                    labelY
            );

            // =================================================
            // VALUE
            // =================================================

            String valueText =
                    String.format(
                            "₹%.0f",
                            value
                    );

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            10
                    )
            );

            FontMetrics valueMetrics =
                    g2.getFontMetrics();

            int valueWidth =
                    valueMetrics.stringWidth(
                            valueText
                    );

            /*
             * For a sufficiently tall bar,
             * show the amount inside it.
             */

            if (barHeight >= 35) {

                g2.setColor(Color.WHITE);

                g2.drawString(
                        valueText,
                        centerX -
                        valueWidth / 2,
                        barY + 20
                );

            } else {

                /*
                 * Small bars:
                 * Show amount just below the
                 * Month/Year label.
                 */

                g2.setColor(DARK);

                g2.drawString(
                        valueText,
                        centerX -
                        valueWidth / 2,
                        barY - 27
                );
            }

            // =================================================
            // SMALL TICK BELOW BAR
            // =================================================

            g2.setColor(DARK);

            g2.drawLine(
                    centerX,
                    xAxisY,
                    centerX,
                    xAxisY + 6
            );
        }

        // =====================================================
        // X AXIS TITLE
        // =====================================================

        g2.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        int xAxisTitleWidth =
                g2.getFontMetrics()
                        .stringWidth(
                                xAxisTitle
                        );

        g2.setColor(DARK);

        g2.drawString(
                xAxisTitle,
                left +
                (chartWidth -
                        xAxisTitleWidth) / 2,
                height - 18
        );
    }

    // =========================================================
    // NO DATA
    // =========================================================

    private void drawNoData(
            Graphics2D g2,
            int width,
            int height,
            String message
    ) {

        g2.setColor(
                new Color(
                        100,
                        100,
                        100
                )
        );

        g2.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        int textWidth =
                g2.getFontMetrics()
                        .stringWidth(
                                message
                        );

        g2.drawString(
                message,
                (width - textWidth) / 2,
                height / 2
        );
    }

    // =========================================================
    // CHART COLORS
    // =========================================================

    private Color getChartColor(int index) {

        Color[] colors = {

                new Color(30, 136, 229),
                new Color(67, 160, 71),
                new Color(251, 140, 0),
                new Color(229, 57, 53),
                new Color(142, 36, 170),
                new Color(0, 137, 123)

        };

        return colors[
                index % colors.length
        ];
    }
}