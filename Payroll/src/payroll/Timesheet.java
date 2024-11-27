package payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
// */

/**
 *
 * @author sophi
 */
public class Timesheet extends javax.swing.JFrame {
    private int employeeId; // Store employee ID
    /**

     */
    // Constructor that takes employee_id as argument
    public Timesheet(int employeeId) {
        this.employeeId = employeeId; // Save the employee_id
        initComponents(); // Initialize components (auto-generated code)
        fetchTimesheetData(employeeId);
        
        
EmployeeTimesheetTable.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        // Get the selected row
        int selectedRow = EmployeeTimesheetTable.getSelectedRow();

        if (selectedRow != -1) {
            // Get the values from the selected row (correct column indices for Date, Time In, Time Out, Overtime, Total Hours)
            String date = EmployeeTimesheetTable.getValueAt(selectedRow, 1).toString();  // Date is in column index 2
            String timeIn = EmployeeTimesheetTable.getValueAt(selectedRow, 2).toString();  // Time In is in column index 3
            String timeOut = EmployeeTimesheetTable.getValueAt(selectedRow, 3).toString();  // Time Out is in column index 4
            String overtime = EmployeeTimesheetTable.getValueAt(selectedRow, 4).toString();  // Overtime is in column index 5
            String totalHours = EmployeeTimesheetTable.getValueAt(selectedRow, 5).toString();  // Total Hours is in column index 6

            // Set the values in the respective text fields
            txtDate.setText(date);
            txtTimeIn.setText(timeIn);
            txtTimeOut.setText(timeOut);
            txtOvertime.setText(overtime);
            txtTotalHours.setText(totalHours);  // Set the Total Hours text field
        }
    }
});

        }

    

    private void fetchTimesheetData(int employeeId) {
        // Fetch data from the database
        java.sql.Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/payroll_db";
        String user = "root";
        String pass = "";

        try {
            // Connect to the database
            conn = DriverManager.getConnection(url, user, pass);

            // SQL query to join the timesheet and employees table
            String query = "SELECT t.timesheet_id, e.name, t.date, t.time_in, t.time_out, t.overtime, t.hours_worked " +
                           "FROM timesheet t " +
                           "INNER JOIN employees e ON t.employee_id = e.employee_id " +
                           "WHERE t.employee_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, employeeId);  // Use the passed employeeId

            rs = preparedStatement.executeQuery();

            // Clear the existing rows in the table before adding new data
            DefaultTableModel model = (DefaultTableModel) EmployeeTimesheetTable.getModel();
            model.setRowCount(0);  // Clear previous data

            // Initialize a variable to hold the employee's name
            String employeeName = "";

            // Populate the table with the fetched timesheet data
            while (rs.next()) {
                // Retrieve the employee's name only once
                if (employeeName.isEmpty()) {
                    employeeName = rs.getString("name");  // Get the name of the employee
                    txtName.setText(employeeName);  // Set the name in the txtName field
                }

                // Retrieve the timesheet data
                int timesheetId = rs.getInt("timesheet_id");
                String date = rs.getString("date");
                String timeIn = rs.getString("time_in");
                String timeOut = rs.getString("time_out");
                String overtime = rs.getString("overtime");
                String totalHours = rs.getString("hours_worked");

                // Add the data to the table model
                model.addRow(new Object[]{timesheetId, date, timeIn, timeOut, overtime, totalHours});
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error closing database resources: " + ex.getMessage());
            }
        }
    }

    
private void addTimesheetToDatabase(String date, String timeIn, String timeOut, String overtime, String totalHours) {
    // Database connection details
    String url = "jdbc:mysql://localhost:3306/payroll_db";
    String user = "root";
    String pass = "";

    Connection conn = null;
    PreparedStatement preparedStatement = null;

    try {
        // Connect to the database
        conn = DriverManager.getConnection(url, user, pass);

        // Prepare the SQL query to insert a new record
        String query = "INSERT INTO timesheet (date, time_in, time_out, overtime, hours_worked) VALUES (?, ?, ?, ?, ?)";
        preparedStatement = conn.prepareStatement(query);

        // Set the values for the query
        preparedStatement.setString(1, date);
        preparedStatement.setString(2, timeIn);
        preparedStatement.setString(3, timeOut);
        preparedStatement.setString(4, overtime);
        preparedStatement.setString(5, totalHours);

        // Execute the query
        preparedStatement.executeUpdate();

        // Optionally, show a success message
        JOptionPane.showMessageDialog(this, "Timesheet added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        // Close resources
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error closing database resources: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void refreshTable() {
    // Clear the existing rows in the table model
    DefaultTableModel model = (DefaultTableModel) EmployeeTimesheetTable.getModel();
    model.setRowCount(0);  // Clear the existing rows, but keep the column headers

    // Query to fetch all records from the database (or filtered data if needed)
    String query = "SELECT * FROM timesheet WHERE employee_id = ?";  // Use employee_id as a filter if necessary

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");
         PreparedStatement stmt = conn.prepareStatement(query)) {

        // Set employee_id parameter if you want to filter the timesheet by employee
        stmt.setInt(1, employeeId);  // employeeId from the selected employee

        ResultSet rs = stmt.executeQuery();

        // Loop through the result set and add rows to the table
        while (rs.next()) {
            int timesheetId = rs.getInt("timesheet_id");
            String date = rs.getString("date");
            String timeIn = rs.getString("time_in");
            String timeOut = rs.getString("time_out");
            String overtime = rs.getString("overtime");
            String totalHours = rs.getString("hours_worked");

            // Add each row to the table
            model.addRow(new Object[]{timesheetId, date, timeIn, timeOut, overtime, totalHours});
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error fetching records: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}


    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        EmployeeTimesheetTable = new javax.swing.JTable();
        addRow = new javax.swing.JButton();
        editRow = new javax.swing.JButton();
        deleteButton1 = new javax.swing.JButton();
        backTodashboardButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        backToTSelectionButton = new javax.swing.JButton();
        Name = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        address = new javax.swing.JLabel();
        address1 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        txtTimeIn = new javax.swing.JTextField();
        txtTimeOut = new javax.swing.JTextField();
        txtOvertime = new javax.swing.JTextField();
        txtTotalHours = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(107, 36, 12));

        EmployeeTimesheetTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Date", "Time In", "Time Out", "Overtime", "Total Hours"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        EmployeeTimesheetTable.setShowGrid(true);
        jScrollPane1.setViewportView(EmployeeTimesheetTable);

        addRow.setBackground(new java.awt.Color(228, 143, 69));
        addRow.setForeground(new java.awt.Color(107, 36, 12));
        addRow.setText("Add Row");
        addRow.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowActionPerformed(evt);
            }
        });

        editRow.setBackground(new java.awt.Color(228, 143, 69));
        editRow.setForeground(new java.awt.Color(107, 36, 12));
        editRow.setText("Edit Selected Row");
        editRow.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        editRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editRowActionPerformed(evt);
            }
        });

        deleteButton1.setBackground(new java.awt.Color(228, 143, 69));
        deleteButton1.setForeground(new java.awt.Color(107, 36, 12));
        deleteButton1.setText("Delete Row");
        deleteButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deleteButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButton1ActionPerformed(evt);
            }
        });

        backTodashboardButton.setBackground(new java.awt.Color(228, 143, 69));
        backTodashboardButton.setForeground(new java.awt.Color(107, 36, 12));
        backTodashboardButton.setText("Back to Dashboard");
        backTodashboardButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backTodashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backTodashboardButtonActionPerformed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(245, 204, 160));
        jLabel7.setText("Employee:");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        backToTSelectionButton.setBackground(new java.awt.Color(228, 143, 69));
        backToTSelectionButton.setForeground(new java.awt.Color(107, 36, 12));
        backToTSelectionButton.setText("Back to Selection");
        backToTSelectionButton.setToolTipText("");
        backToTSelectionButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backToTSelectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToTSelectionButtonActionPerformed(evt);
            }
        });

        Name.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Name.setForeground(new java.awt.Color(245, 204, 160));
        Name.setText("DATE:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(245, 204, 160));
        jLabel4.setText("TIME IN:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(245, 204, 160));
        jLabel3.setText("TIME OUT:");

        address.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        address.setForeground(new java.awt.Color(245, 204, 160));
        address.setText("OVERTIME:");

        address1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        address1.setForeground(new java.awt.Color(245, 204, 160));
        address1.setText("TOTAL HOURS:");

        txtDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateActionPerformed(evt);
            }
        });

        txtTimeIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimeInActionPerformed(evt);
            }
        });

        txtTimeOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimeOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(Name)
                                .addComponent(jLabel4)
                                .addComponent(address)
                                .addComponent(address1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDate)
                                .addComponent(txtTimeOut)
                                .addComponent(txtOvertime)
                                .addComponent(txtTimeIn)
                                .addComponent(txtTotalHours, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(backTodashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(addRow, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(deleteButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(editRow, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(backToTSelectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addRow)
                    .addComponent(deleteButton1)
                    .addComponent(editRow)
                    .addComponent(backToTSelectionButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backTodashboardButton)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Name))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimeIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(address)
                            .addComponent(txtOvertime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotalHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(address1))))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        txtDate.getAccessibleContext().setAccessibleDescription("");

        jPanel2.setBackground(new java.awt.Color(153, 77, 28));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(245, 204, 160));
        jLabel1.setText("TIMESHEET");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(276, 276, 276))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void editRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editRowActionPerformed
// Get the selected row from the table
    int selectedRow = EmployeeTimesheetTable.getSelectedRow();

    if (selectedRow != -1) {  // Ensure that a row is selected
        // Retrieve values from the text fields
        String date = txtDate.getText();
        String timeIn = txtTimeIn.getText();
        String timeOut = txtTimeOut.getText();
        String overtime = txtOvertime.getText();
        String totalHours = txtTotalHours.getText();

        // Get the timesheet_id from the selected row
        int timesheetId = (int) EmployeeTimesheetTable.getValueAt(selectedRow, 0); // assuming timesheet_id is in column 0

        // Update the database with the new values
        String query = "UPDATE timesheet SET date = ?, time_in = ?, time_out = ?, overtime = ?, hours_worked = ? WHERE timesheet_id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set values for the prepared statement
            stmt.setString(1, date);
            stmt.setString(2, timeIn);
            stmt.setString(3, timeOut);
            stmt.setString(4, overtime);
            stmt.setString(5, totalHours);
            stmt.setInt(6, timesheetId); // Specify which row to update by timesheet_id

            // Execute the update query
            stmt.executeUpdate();

            // Optionally, refresh the table or show a success message
            JOptionPane.showMessageDialog(null, "Row updated successfully!");

            // Refresh the table to show the updated data
            refreshTable(); // Update the table to reflect the changes

            // Clear the text fields after the update (optional)
            txtDate.setText("");
            txtTimeIn.setText("");
            txtTimeOut.setText("");
            txtOvertime.setText("");
            txtTotalHours.setText("");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Please select a row to edit.");
    }
    }//GEN-LAST:event_editRowActionPerformed

    private void backTodashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backTodashboardButtonActionPerformed
    this.setVisible(false);  
    AdminDashboard back = new AdminDashboard();  
    back.setVisible(true); 
    }//GEN-LAST:event_backTodashboardButtonActionPerformed

    private void addRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowActionPerformed
   // Retrieve values from text fields
    String date = txtDate.getText();
    String timeIn = txtTimeIn.getText();
    String timeOut = txtTimeOut.getText();
    String overtime = txtOvertime.getText();
    String totalHours = txtTotalHours.getText();

    // Retrieve the employee ID from the previous panel (TimesheetEmployeeSelection panel)
    // Assuming the employee ID is stored in a variable called employeeId
    String employeeIdText = txtName.getText(); // Assuming employee name's text field is used for employee ID

    // Handle empty totalHours and set a default value or validate the field
    if (totalHours.isEmpty()) {
        totalHours = "0";  // You can set a default value or handle it based on your logic
    }

    // Query for inserting into the timesheet table
    String query = "INSERT INTO timesheet (employee_id, date, time_in, time_out, overtime, hours_worked) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");
         PreparedStatement stmt = conn.prepareStatement(query)) {

        // Set values for each parameter in the prepared statement
        stmt.setInt(1, employeeId);        // employee_id
        stmt.setString(2, date);           // date
        stmt.setString(3, timeIn);         // time_in
        stmt.setString(4, timeOut);        // time_out
        stmt.setString(5, overtime);       // overtime
        stmt.setString(6, totalHours);     // total_hours (ensure it's not empty)

        // Execute the query to insert the data
        stmt.executeUpdate();

        // Optionally, refresh the table or show a success message
        JOptionPane.showMessageDialog(null, "Row added successfully!");
        
        refreshTable();

        // Clear the text fields after insertion (optional)
        txtDate.setText("");
        txtTimeIn.setText("");
        txtTimeOut.setText("");
        txtOvertime.setText("");
        txtTotalHours.setText("");

    } catch (SQLException e) {
        // Display an error message in case of a database error
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
    }

    }//GEN-LAST:event_addRowActionPerformed

    private void deleteButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButton1ActionPerformed
 // Get the selected row index
    int selectedRow = EmployeeTimesheetTable.getSelectedRow();

    if (selectedRow != -1) {
        // Get the timesheet_id of the selected row
        int timesheetId = (int) EmployeeTimesheetTable.getValueAt(selectedRow, 0); // Assuming timesheet_id is in the first column

        // SQL query to delete the record
        String query = "DELETE FROM timesheet WHERE timesheet_id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the timesheet_id parameter
            stmt.setInt(1, timesheetId);

            // Execute the delete query
            stmt.executeUpdate();

            // Refresh the table to reflect changes
            refreshTable();

            // Optionally, show a success message
            JOptionPane.showMessageDialog(null, "Row deleted successfully!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Please select a row to delete.");
    }

    }//GEN-LAST:event_deleteButton1ActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void backToTSelectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToTSelectionButtonActionPerformed
        this.setVisible(false);
        TimesheetEmployeeSelection back = new TimesheetEmployeeSelection();
        back.setVisible(true);
    }//GEN-LAST:event_backToTSelectionButtonActionPerformed

    private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDateActionPerformed

    private void txtTimeInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimeInActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimeInActionPerformed

    private void txtTimeOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimeOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimeOutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Timesheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Timesheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Timesheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Timesheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Timesheet(122).setVisible(true);
                
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable EmployeeTimesheetTable;
    private javax.swing.JLabel Name;
    private javax.swing.JButton addRow;
    private javax.swing.JLabel address;
    private javax.swing.JLabel address1;
    private javax.swing.JButton backToTSelectionButton;
    private javax.swing.JButton backTodashboardButton;
    private javax.swing.JButton deleteButton1;
    private javax.swing.JButton editRow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtOvertime;
    private javax.swing.JTextField txtTimeIn;
    private javax.swing.JTextField txtTimeOut;
    private javax.swing.JTextField txtTotalHours;
    // End of variables declaration//GEN-END:variables
}
