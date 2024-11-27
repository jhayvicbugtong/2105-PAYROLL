package payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author sophi
 */
public class SecondPayrollPanel extends javax.swing.JFrame {
    private int employeeId;
    private String startDate;
    private String endDate;
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    // Setter for start and end dates
    public void setDateRange(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    /**
     * Creates new form SecondPayrollPanel
     */
    public SecondPayrollPanel(int employeeId) {
        this.employeeId = employeeId; // Save the employee_id
        initComponents(); // Initialize components (auto-generated code)
        loadFilteredPayrollDataToTable(); // Load timesheet data based on employee_id
    }
    
    public void loadFilteredPayrollDataToTable() {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // SQL query to fetch filtered data
    String query = "SELECT date, time_in, time_out, overtime, hours_worked " +
                   "FROM timesheet " +
                   "WHERE employee_id = ? AND date BETWEEN ? AND ?";

    try {
        // Set up database connection
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");
        pst = conn.prepareStatement(query);

        // Set query parameters
        pst.setInt(1, employeeId);  // Pass employeeId as int
        pst.setString(2, startDate);
        pst.setString(3, endDate);

        // Execute the query
        rs = pst.executeQuery();

        // Get the table model from the PayrollTimesheet table
        DefaultTableModel model = (DefaultTableModel) PayrollTimesheet.getModel();

        // Clear any existing rows
        model.setRowCount(0);

        // Populate the table with data from the ResultSet
        while (rs.next()) {
            Object[] row = new Object[5]; // Adjust to match the number of columns
            row[0] = rs.getDate("date");
            row[1] = rs.getTime("time_in");
            row[2] = rs.getTime("time_out");
            row[3] = rs.getInt("overtime");
            row[4] = rs.getDouble("hours_worked");

            model.addRow(row);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
    
public void loadEmployeeDetails(int employeeId) {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    String query = "SELECT e.name, p.position_name FROM employees e " +
                   "JOIN positions p ON e.position_id = p.position_id " +
                   "WHERE e.employee_id = ?";
    try {
        // Connect to the database
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");

        // Prepare the SQL statement to retrieve the employee's name and position
        pst = conn.prepareStatement(query);
        pst.setInt(1, employeeId); // Set the employee ID parameter

        // Execute the query
        rs = pst.executeQuery();

        // If an employee is found
        if (rs.next()) {
            // Set the employee details in the text fields
            txtID.setText(String.valueOf(employeeId));  // Set employee ID
            txtName.setText(rs.getString("name"));      // Set employee name
            txtPosition.setText(rs.getString("position_name"));  // Set position name
        } else {
            JOptionPane.showMessageDialog(this, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

private void setupDeductionsTableSelectionListener() {
    DeductionsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            // Check if a row is selected
            if (!e.getValueIsAdjusting() && DeductionsTable.getSelectedRow() != -1) {
                // Get the selected row index
                int selectedRow = DeductionsTable.getSelectedRow();

                // Retrieve the deduction name and amount from the selected row
                String deductionName = (String) DeductionsTable.getValueAt(selectedRow, 1); // Deduction name column (index 1)
                Double deductionAmount = (Double) DeductionsTable.getValueAt(selectedRow, 2); // Deduction amount column (index 2)

                // Set the values into the text fields
                txtDeduction.setText(deductionName);  // Set the deduction name
                txtAmount.setText(String.valueOf(deductionAmount));  // Set the deduction amount
            }
        }
    });
}
    // Inside SecondPayrollPanel.java

public void loadDeductionsDataToTable(int employeeId) {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
        // SQL query to get the deductions information for the specific employee
        String query = "SELECT ed.employee_deduction_id, d.deduction_name, ed.deduction_amount " +
                       "FROM employee_deductions ed " +
                       "JOIN deductions d ON ed.deduction_id = d.deduction_id " +
                       "WHERE ed.employee_id = ?";
                       
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");
        pst = conn.prepareStatement(query);
        pst.setInt(1, employeeId);  // Set the employee ID parameter
        rs = pst.executeQuery();

        // Create the DefaultTableModel and add columns in the correct order
        DefaultTableModel model = (DefaultTableModel) DeductionsTable.getModel();
        model.setRowCount(0); // Clear any existing rows in the table

        // Loop through the result set and add rows to the table model
        while (rs.next()) {
            Object[] row = new Object[3];  // Three columns: employee_deduction_id, deduction_name, deduction_amount
            row[0] = rs.getInt("employee_deduction_id"); // Employee deduction ID
            row[1] = rs.getString("deduction_name"); // Deduction name (from deductions table)
            row[2] = rs.getDouble("deduction_amount"); // Deduction amount

            model.addRow(row); // Add the row to the table
        }

        // Optionally hide the employee_deduction_id column (column 0) from the user
        DeductionsTable.getColumnModel().getColumn(0).setMinWidth(0);
        DeductionsTable.getColumnModel().getColumn(0).setMaxWidth(0);
        DeductionsTable.getColumnModel().getColumn(0).setWidth(0); // Hide the first column (employee_deduction_id)

        setupDeductionsTableSelectionListener();
        
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    /**
     * Creates new form SecondPayrollPanel
     */
    public SecondPayrollPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        addDeduction = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        DeductionsTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        PayrollTimesheet = new javax.swing.JTable();
        txtName = new javax.swing.JTextField();
        txtSelectemployee = new javax.swing.JLabel();
        txtSelectemployee1 = new javax.swing.JLabel();
        txtPosition = new javax.swing.JTextField();
        txtSelectemployee2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtSelectemployee3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        backToPESelection = new javax.swing.JToggleButton();
        jLabel10 = new javax.swing.JLabel();
        BacktoDB1 = new javax.swing.JToggleButton();
        generatePayslipButton = new javax.swing.JToggleButton();
        editDeduction = new javax.swing.JToggleButton();
        deleteDeduction = new javax.swing.JToggleButton();
        txtDeduction = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(107, 36, 12));

        jPanel2.setBackground(new java.awt.Color(153, 77, 28));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(228, 143, 69));
        jLabel1.setText("EMPLOYEE PAYROLL DATA");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 27));

        addDeduction.setBackground(new java.awt.Color(228, 143, 69));
        addDeduction.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addDeduction.setForeground(new java.awt.Color(107, 36, 12));
        addDeduction.setText("Add");
        addDeduction.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addDeduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDeductionActionPerformed(evt);
            }
        });
        jPanel2.add(addDeduction, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 65, -1));

        DeductionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "", null},
                {null, "", null},
                {null, "", null},
                {null, null, null}
            },
            new String [] {
                "ID", "Deductions", "Amount"
            }
        ));
        DeductionsTable.setShowGrid(true);
        jScrollPane1.setViewportView(DeductionsTable);
        if (DeductionsTable.getColumnModel().getColumnCount() > 0) {
            DeductionsTable.getColumnModel().getColumn(0).setHeaderValue("ID");
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 630, 110));

        PayrollTimesheet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date", "Time In", "Time Out", "Overtime", "Total Hours"
            }
        ));
        PayrollTimesheet.setShowGrid(true);
        jScrollPane2.setViewportView(PayrollTimesheet);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 630, 224));

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        jPanel2.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 64, 150, -1));

        txtSelectemployee.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSelectemployee.setForeground(new java.awt.Color(245, 204, 160));
        txtSelectemployee.setText("Name:");
        jPanel2.add(txtSelectemployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 66, -1, -1));

        txtSelectemployee1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSelectemployee1.setForeground(new java.awt.Color(245, 204, 160));
        txtSelectemployee1.setText("Position:");
        jPanel2.add(txtSelectemployee1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 94, -1, -1));

        txtPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPositionActionPerformed(evt);
            }
        });
        jPanel2.add(txtPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 92, 150, -1));

        txtSelectemployee2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSelectemployee2.setForeground(new java.awt.Color(245, 204, 160));
        txtSelectemployee2.setText("ID:");
        jPanel2.add(txtSelectemployee2, new org.netbeans.lib.awtextra.AbsoluteConstraints(352, 66, -1, -1));

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });
        jPanel2.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 64, 60, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("............................................................................................................................................................");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, -1, -1));

        txtSelectemployee3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSelectemployee3.setForeground(new java.awt.Color(245, 204, 160));
        jPanel2.add(txtSelectemployee3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 610, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("............................................................................................................................................................");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        backToPESelection.setBackground(new java.awt.Color(228, 143, 69));
        backToPESelection.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backToPESelection.setForeground(new java.awt.Color(107, 36, 12));
        backToPESelection.setText("Back to Selection");
        backToPESelection.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backToPESelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToPESelectionActionPerformed(evt);
            }
        });
        jPanel2.add(backToPESelection, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 640, 120, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("............................................................................................................................................................");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 33, -1, -1));

        BacktoDB1.setBackground(new java.awt.Color(228, 143, 69));
        BacktoDB1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BacktoDB1.setForeground(new java.awt.Color(107, 36, 12));
        BacktoDB1.setText("Back to Dashboard");
        BacktoDB1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BacktoDB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BacktoDB1ActionPerformed(evt);
            }
        });
        jPanel2.add(BacktoDB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 610, 120, -1));

        generatePayslipButton.setBackground(new java.awt.Color(228, 143, 69));
        generatePayslipButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        generatePayslipButton.setForeground(new java.awt.Color(107, 36, 12));
        generatePayslipButton.setText("Generate Payslip");
        generatePayslipButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        generatePayslipButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatePayslipButtonActionPerformed(evt);
            }
        });
        jPanel2.add(generatePayslipButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 580, 120, -1));

        editDeduction.setBackground(new java.awt.Color(228, 143, 69));
        editDeduction.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        editDeduction.setForeground(new java.awt.Color(107, 36, 12));
        editDeduction.setText("Edit");
        editDeduction.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        editDeduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDeductionActionPerformed(evt);
            }
        });
        jPanel2.add(editDeduction, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 530, 65, -1));

        deleteDeduction.setBackground(new java.awt.Color(228, 143, 69));
        deleteDeduction.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        deleteDeduction.setForeground(new java.awt.Color(107, 36, 12));
        deleteDeduction.setText("Delete");
        deleteDeduction.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deleteDeduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteDeductionActionPerformed(evt);
            }
        });
        jPanel2.add(deleteDeduction, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 530, 65, -1));

        txtDeduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDeductionActionPerformed(evt);
            }
        });
        jPanel2.add(txtDeduction, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 490, 130, -1));

        jLabel2.setForeground(new java.awt.Color(245, 204, 160));
        jLabel2.setText("Deduction Name:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, -1, -1));

        jLabel3.setForeground(new java.awt.Color(245, 204, 160));
        jLabel3.setText("Amount:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 490, -1, -1));

        txtAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmountActionPerformed(evt);
            }
        });
        jPanel2.add(txtAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 490, 140, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backToPESelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToPESelectionActionPerformed
        this.setVisible(false);
        PayrollEmployeeSelection back = new PayrollEmployeeSelection();
        back.setVisible(true);
    }//GEN-LAST:event_backToPESelectionActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void txtPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPositionActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void addDeductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDeductionActionPerformed
    // Retrieve the deduction name and amount from the text fields
    String deductionName = txtDeduction.getText().trim();
    String amountText = txtAmount.getText().trim();

    // Validate input
    if (deductionName.isEmpty() || amountText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Parse the amount text to a double
    double amount = 0;
    try {
        amount = Double.parseDouble(amountText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid amount format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Retrieve the deduction_id for the given deduction_name
    int deductionId = getDeductionIdFromName(deductionName);
    if (deductionId == -1) {
        JOptionPane.showMessageDialog(this, "Deduction not found in the deductions table.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Now insert the new employee deduction record into the database
    String query = "INSERT INTO employee_deductions (employee_id, deduction_id, deduction_amount) VALUES (?, ?, ?)";
    
    Connection conn = null;
    PreparedStatement pst = null;

    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");
        pst = conn.prepareStatement(query);
        pst.setInt(1, employeeId);  // Pass the selected employee's ID
        pst.setInt(2, deductionId);         // Set the deduction_id from the deductions table
        pst.setDouble(3, amount);           // Set the deduction amount

        // Execute the insert
        int rowsInserted = pst.executeUpdate();

        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this, "Deduction added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Call the method to reload the table with the updated data
            loadDeductionsDataToTable(employeeId);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add deduction.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }  
    }//GEN-LAST:event_addDeductionActionPerformed
    private int getDeductionIdFromName(String deductionName) {
    String query = "SELECT deduction_id FROM deductions WHERE deduction_name = ?";
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");
        pst = conn.prepareStatement(query);
        pst.setString(1, deductionName);
        rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getInt("deduction_id");
        } else {
            return -1;  // Deduction name not found
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return -1;
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
    private void BacktoDB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BacktoDB1ActionPerformed
        this.setVisible(false);
        AdminDashboard back = new AdminDashboard();
        back.setVisible(true);
    }//GEN-LAST:event_BacktoDB1ActionPerformed

    private void generatePayslipButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatePayslipButtonActionPerformed
        GeneratePayslip GeneratePayslip = new GeneratePayslip();
        GeneratePayslip.setVisible(true);
        SecondPayrollPanel.this.setVisible(false);
        
        // Set the employee ID and date range
        GeneratePayslip.setEmployeeId(employeeId);  // Pass employeeId as int
        GeneratePayslip.setDateRange(startDate, endDate);
               
        // Load the employee's details in the second panel
        GeneratePayslip.loadEmployeeDetails(employeeId);
        
        // Load payroll data into the table
        GeneratePayslip.loadFilteredPayrollDataToTable();
        
        // Load deductions data into txtDeductions
        GeneratePayslip.loadDeductions();
        
        // compute net pay
        GeneratePayslip.computeNetPay();
    }//GEN-LAST:event_generatePayslipButtonActionPerformed

    private void editDeductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDeductionActionPerformed
    // Step 1: Get the selected row from the DeductionsTable
    int selectedRow = DeductionsTable.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row to edit.");
        return;
    }

    // Step 2: Get the employee_deductions_id from the selected row (first column, which is hidden)
    int employeeDeductionId = (int) DeductionsTable.getValueAt(selectedRow, 0);  // Assuming the first column has employee_deductions_id

    // Step 3: Retrieve the new deduction name and amount from the text fields
    String deductionName = txtDeduction.getText();  // Deduction name
    String amountText = txtAmount.getText();  // Deduction amount

    // Validate the inputs
    if (deductionName.isEmpty() || amountText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        return;
    }

    // Validate that the amount is a valid number (double)
    double amount;
    try {
        amount = Double.parseDouble(amountText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.");
        return;
    }

    // Step 4: Update the record in the database
    String url = "jdbc:mysql://localhost:3306/payroll_db";
    String user = "root";
    String pass = "";

    Connection conn = null;
    PreparedStatement pst = null;

    try {
        // Connect to the database
        conn = DriverManager.getConnection(url, user, pass);

        // Step 5: Get the deduction_id from the deductions table using the deductionName
        String getDeductionIdQuery = "SELECT deduction_id FROM deductions WHERE deduction_name = ?";
        pst = conn.prepareStatement(getDeductionIdQuery);
        pst.setString(1, deductionName);  // Use the deduction name entered by the user
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            // Get the deduction_id from the deductions table
            int deductionId = rs.getInt("deduction_id");

            // Step 6: Update the employee_deductions table
            String updateQuery = "UPDATE employee_deductions SET deduction_id = ?, deduction_amount = ? WHERE employee_deduction_id = ?";
            pst = conn.prepareStatement(updateQuery);
            pst.setInt(1, deductionId);  // Use the deduction_id obtained from the deductions table
            pst.setDouble(2, amount);     // Set the new deduction amount
            pst.setInt(3, employeeDeductionId);  // Use the employee_deductions_id to identify the row to update

            // Execute the update query
            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Deduction updated successfully.");

                // Step 7: Refresh the table to reflect the changes
                loadDeductionsDataToTable(employeeId);  // Assuming you have a method to reload the table data
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update deduction. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid deduction name. Please select a valid deduction.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    }//GEN-LAST:event_editDeductionActionPerformed

    private void deleteDeductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteDeductionActionPerformed

    int selectedRow = DeductionsTable.getSelectedRow();

    if (selectedRow != -1) {
        // Get the employee_deduction_id (integer) from the first column
        int employeeDeductionId = (int) DeductionsTable.getValueAt(selectedRow, 0);  // This should be the employee_deduction_id column

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this deduction?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Call the delete method, passing the employee_deduction_id
            deleteDeductionFromDatabase(employeeDeductionId);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a row to delete.");
    }
}

private void deleteDeductionFromDatabase(int employeeDeductionId) {
    Connection conn = null;
    PreparedStatement pst = null;

    try {
        // Database connection
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");

        // SQL query to delete the deduction record using employee_deduction_id
        String sql = "DELETE FROM employee_deductions WHERE employee_deduction_id = ?";
        pst = conn.prepareStatement(sql);
        pst.setInt(1, employeeDeductionId); // Set the employee_deduction_id parameter

        // Execute the delete query
        int rowsDeleted = pst.executeUpdate();

        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(this, "Deduction deleted successfully.");
            loadDeductionsDataToTable(employeeId);  // Reload the deductions table
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete the deduction.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error deleting deduction: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_deleteDeductionActionPerformed

    private void txtAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAmountActionPerformed

    private void txtDeductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDeductionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDeductionActionPerformed

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
            java.util.logging.Logger.getLogger(SecondPayrollPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SecondPayrollPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SecondPayrollPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SecondPayrollPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SecondPayrollPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton BacktoDB1;
    private javax.swing.JTable DeductionsTable;
    private javax.swing.JTable PayrollTimesheet;
    private javax.swing.JToggleButton addDeduction;
    private javax.swing.JToggleButton backToPESelection;
    private javax.swing.JToggleButton deleteDeduction;
    private javax.swing.JToggleButton editDeduction;
    private javax.swing.JToggleButton generatePayslipButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtDeduction;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPosition;
    private javax.swing.JLabel txtSelectemployee;
    private javax.swing.JLabel txtSelectemployee1;
    private javax.swing.JLabel txtSelectemployee2;
    private javax.swing.JLabel txtSelectemployee3;
    // End of variables declaration//GEN-END:variables
}
