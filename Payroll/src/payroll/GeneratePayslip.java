/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author bugtong
 */
public class GeneratePayslip extends javax.swing.JFrame {

    /**
     * Creates new form GeneratePayslip
     */
    public GeneratePayslip() {
        initComponents();
    }
    
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
    
    public void loadFilteredPayrollDataToTable() {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // SQL query to fetch filtered data
    String query = 
            "SELECT e.employee_id AS empID,"
            + " p.salary_per_hour AS rate_per_hour,"
            + " SUM(t.hours_worked) AS total_hours,"
            + " ROUND(SUM(COALESCE(t.hours_worked, 0) * COALESCE(p.salary_per_hour, 0)), 2) AS total_salary"
            + " FROM timesheet t"
            + " JOIN "
            + "employees e ON t.employee_id = e.employee_id "
            + " JOIN "
            + "positions p ON e.position_id = p.position_id "
            + "WHERE e.employee_id = ? AND t.date BETWEEN ? AND ? "
            + "GROUP BY e.employee_id, p.salary_per_hour, e.position_id";

    try {
        // Set up database connection
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");
        pst = conn.prepareStatement(query);

        // Set query parameters
        pst.setInt(1, employeeId); // Pass employeeId as int
        pst.setString(2, startDate);
        pst.setString(3, endDate);

        // Execute the query
        rs = pst.executeQuery();

        // Get the table model from the PayrollTimesheet table
        DefaultTableModel model = (DefaultTableModel) PayrollComputationsTable.getModel();

        // Clear any existing rows
        model.setRowCount(0);

        // Populate the table with data from the ResultSet
        while (rs.next()) {
            Object[] row = new Object[5]; // Adjust to match the number of columns
            row[0] = rs.getString("total_hours");
            row[1] = rs.getString("rate_per_hour");
            row[2] = rs.getString("total_salary"); // gross pay w/out deductions
            txtGross.setText(rs.getString("total_salary"));
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
    
    public void loadDeductions() {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // SQL query to fetch deductions data
    String query =
            "SELECT SUM(deduction_amount) AS total_deductions "
            + "FROM employee_deductions "
            + "WHERE employee_id = ?";

    try {
        // Set up database connection
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");
        pst = conn.prepareStatement(query);

        // Set query parameters
        pst.setInt(1, employeeId); // Pass employeeId as int

        // Execute the query
        rs = pst.executeQuery();
        
        // Check if there are deductions and populate the txtDeductions text box
        if (rs.next()) {
            // If no deductions (null or zero), display a message
            double totalDeductions = rs.getDouble("total_deductions");
            if (totalDeductions == 0) {
                txtDeductions.setText("No deductions");
            } else {
                txtDeductions.setText(String.valueOf(totalDeductions));
            }
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

    
    public void loadDeductionsToTable(int employeeId) {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    // Query to fetch deduction names and amounts for the employee
    String query = "SELECT deduction_name, deduction_amount " +
                   "FROM employee_deductions " +
                   "WHERE employee_id = ?";
    
    try {
        // Set up database connection
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");
        pst = conn.prepareStatement(query);

        // Set query parameter for employee ID
        pst.setInt(1, employeeId);

        // Execute the query
        rs = pst.executeQuery();

        // Get the table model from the deductions table
        DefaultTableModel model = (DefaultTableModel) PayrollComputationsTable.getModel();

        // Clear any existing rows
        model.setRowCount(0);

        // Populate the table with data from the ResultSet
        while (rs.next()) {
            // Create an array for each row
            Object[] row = new Object[2]; // Two columns: Deduction Name and Deduction Amount
            row[0] = rs.getString("deduction_name"); // Deduction name
            row[1] = rs.getDouble("deduction_amount"); // Deduction amount
            model.addRow(row); // Add the row to the table model
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtGross = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtPosition = new javax.swing.JTextField();
        txtID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        PayrollComputationsTable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtNetPay = new javax.swing.JTextField();
        txtDeductions = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        BackToDashboard1 = new javax.swing.JButton();
        dateStart = new javax.swing.JTextField();
        dateEnd = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();

        jTextField3.setText("jTextField1");

        jLabel9.setText("jLabel9");

        txtGross.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGrossActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(107, 36, 12));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setForeground(new java.awt.Color(245, 204, 160));
        jLabel2.setText("End Date:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, -1, -1));

        jLabel3.setForeground(new java.awt.Color(245, 204, 160));
        jLabel3.setText("Position: ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jLabel4.setForeground(new java.awt.Color(245, 204, 160));
        jLabel4.setText("ID:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, -1, -1));

        txtName.setEditable(false);
        txtName.setBackground(new java.awt.Color(107, 36, 12));
        txtName.setForeground(new java.awt.Color(245, 204, 160));
        txtName.setBorder(null);
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        jPanel1.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 154, -1));

        txtPosition.setBackground(new java.awt.Color(107, 36, 12));
        txtPosition.setForeground(new java.awt.Color(245, 204, 160));
        txtPosition.setBorder(null);
        jPanel1.add(txtPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 155, -1));

        txtID.setEditable(false);
        txtID.setBackground(new java.awt.Color(107, 36, 12));
        txtID.setForeground(new java.awt.Color(245, 204, 160));
        txtID.setBorder(null);
        txtID.setName("txtID"); // NOI18N
        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });
        jPanel1.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 71, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(245, 204, 160));
        jLabel5.setText("EARNINGS:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        PayrollComputationsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Total Hours", "Rate per hour", "Gross Salary"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PayrollComputationsTable.setShowGrid(true);
        jScrollPane2.setViewportView(PayrollComputationsTable);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 570, 50));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(245, 204, 160));
        jLabel8.setText("Total Deductions:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 270, 100, -1));

        txtNetPay.setEditable(false);
        txtNetPay.setBackground(new java.awt.Color(107, 36, 12));
        txtNetPay.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtNetPay.setForeground(new java.awt.Color(245, 204, 160));
        txtNetPay.setBorder(null);
        txtNetPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNetPayActionPerformed(evt);
            }
        });
        jPanel1.add(txtNetPay, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 300, 130, -1));

        txtDeductions.setEditable(false);
        txtDeductions.setBackground(new java.awt.Color(107, 36, 12));
        txtDeductions.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtDeductions.setForeground(new java.awt.Color(245, 204, 160));
        txtDeductions.setBorder(null);
        txtDeductions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDeductionsActionPerformed(evt);
            }
        });
        jPanel1.add(txtDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 270, 130, -1));
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 557, 79, -1));

        jSeparator1.setBackground(new java.awt.Color(245, 204, 160));
        jSeparator1.setForeground(new java.awt.Color(245, 204, 160));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 594, 1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(245, 204, 160));
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 557, 1, -1));

        BackToDashboard1.setBackground(new java.awt.Color(245, 204, 160));
        BackToDashboard1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BackToDashboard1.setForeground(new java.awt.Color(107, 36, 12));
        BackToDashboard1.setText("Back to Dashboard");
        BackToDashboard1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BackToDashboard1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToDashboard1ActionPerformed(evt);
            }
        });
        jPanel1.add(BackToDashboard1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 130, 30));

        dateStart.setBackground(new java.awt.Color(107, 36, 12));
        dateStart.setForeground(new java.awt.Color(245, 204, 160));
        dateStart.setBorder(null);
        dateStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateStartActionPerformed(evt);
            }
        });
        jPanel1.add(dateStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 70, 90, -1));

        dateEnd.setBackground(new java.awt.Color(107, 36, 12));
        dateEnd.setForeground(new java.awt.Color(245, 204, 160));
        dateEnd.setBorder(null);
        jPanel1.add(dateEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 90, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(245, 204, 160));
        jLabel6.setText("EMPLOYEE PAYSLIP");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, -1));

        jLabel10.setForeground(new java.awt.Color(245, 204, 160));
        jLabel10.setText("Name:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 37, -1));

        jLabel11.setForeground(new java.awt.Color(245, 204, 160));
        jLabel11.setText("Start Date:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(427, 70, 60, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(228, 143, 69));
        jLabel14.setText(".....................................................................................................");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 570, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(228, 143, 69));
        jLabel15.setText(".....................................................................................................");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 570, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(245, 204, 160));
        jLabel18.setText("Net Pay:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 300, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtDeductionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDeductionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDeductionsActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void txtGrossActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGrossActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGrossActionPerformed

    private void txtNetPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNetPayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNetPayActionPerformed

    private void BackToDashboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboard1ActionPerformed
        this.setVisible(false);
        AdminDashboard back = new AdminDashboard();
        back.setVisible(true);
    }//GEN-LAST:event_BackToDashboard1ActionPerformed

    private void dateStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateStartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateStartActionPerformed

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
            java.util.logging.Logger.getLogger(GeneratePayslip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GeneratePayslip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GeneratePayslip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GeneratePayslip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GeneratePayslip().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackToDashboard1;
    private javax.swing.JTable PayrollComputationsTable;
    private javax.swing.JTextField dateEnd;
    private javax.swing.JTextField dateStart;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField txtDeductions;
    private javax.swing.JTextField txtGross;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNetPay;
    private javax.swing.JTextField txtPosition;
    // End of variables declaration//GEN-END:variables

    public void computeNetPay() {
    try {
        // Parse values from the gross salary text field
        double gross = Double.parseDouble(txtGross.getText());
        
        // Handle empty or invalid deductions
        String deductionsText = txtDeductions.getText();
        double deductions = 0; // Default to 0 if empty or invalid input
        
        if (deductionsText != null && !deductionsText.trim().isEmpty()) {
            try {
                deductions = Double.parseDouble(deductionsText);
            } catch (NumberFormatException e) {
                // If deductions field is not a valid number, default to 0
                txtDeductions.setText("0");
                deductions = 0;
            }
        }

        // Perform subtraction to get the net pay
        double netPay = gross - deductions;

        // Display the net pay in the text field
        txtNetPay.setText(String.valueOf(netPay));
        
    } catch (NumberFormatException e) {
        // Handle invalid gross salary input
        JOptionPane.showMessageDialog(null, "Please enter a valid gross salary.", "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}
// In the Payslip class
public void setStartDate(String startDate) {
    dateStart.setText(startDate); // Replace 'startTextField' with your actual JTextField or TextField variable name
}


public void setEndDate(String endDate) {
    dateEnd.setText(endDate); // Replace 'endTextField' with your actual JTextField or TextField variable name
}

}
