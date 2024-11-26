package payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

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
        jToggleButton5 = new javax.swing.JToggleButton();
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
        txtID6 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        backToPESelection = new javax.swing.JToggleButton();
        txtSelectemployee8 = new javax.swing.JLabel();
        txtID11 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        BacktoDB1 = new javax.swing.JToggleButton();
        generatePayslipButton = new javax.swing.JToggleButton();
        jToggleButton6 = new javax.swing.JToggleButton();
        jToggleButton7 = new javax.swing.JToggleButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(107, 36, 12));

        jPanel2.setBackground(new java.awt.Color(153, 77, 28));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(228, 143, 69));
        jLabel1.setText("EMPLOYEE PAYROLL DATA");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 27));

        jToggleButton5.setBackground(new java.awt.Color(228, 143, 69));
        jToggleButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jToggleButton5.setForeground(new java.awt.Color(107, 36, 12));
        jToggleButton5.setText("Add");
        jToggleButton5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jToggleButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 65, -1));

        DeductionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null},
                {"", null},
                {"", null},
                {null, null}
            },
            new String [] {
                "Deductions", "Amount"
            }
        ));
        DeductionsTable.setShowGrid(true);
        jScrollPane1.setViewportView(DeductionsTable);

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
        txtSelectemployee3.setText("Total Salary:  ");
        jPanel2.add(txtSelectemployee3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 610, -1, -1));

        txtID6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtID6ActionPerformed(evt);
            }
        });
        jPanel2.add(txtID6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 610, 272, -1));

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

        txtSelectemployee8.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSelectemployee8.setForeground(new java.awt.Color(245, 204, 160));
        txtSelectemployee8.setText("Net Salary: ");
        jPanel2.add(txtSelectemployee8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, -1, -1));

        txtID11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtID11ActionPerformed(evt);
            }
        });
        jPanel2.add(txtID11, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 580, 272, -1));

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

        jToggleButton6.setBackground(new java.awt.Color(228, 143, 69));
        jToggleButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jToggleButton6.setForeground(new java.awt.Color(107, 36, 12));
        jToggleButton6.setText("Edit");
        jToggleButton6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jToggleButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 530, 65, -1));

        jToggleButton7.setBackground(new java.awt.Color(228, 143, 69));
        jToggleButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jToggleButton7.setForeground(new java.awt.Color(107, 36, 12));
        jToggleButton7.setText("Delete");
        jToggleButton7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jToggleButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 530, 65, -1));
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 490, 130, -1));

        jLabel2.setForeground(new java.awt.Color(245, 204, 160));
        jLabel2.setText("Deduction Name:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, -1, -1));

        jLabel3.setForeground(new java.awt.Color(245, 204, 160));
        jLabel3.setText("Amount:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 490, -1, -1));

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 490, 140, -1));

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

    private void txtID11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtID11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtID11ActionPerformed

    private void backToPESelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToPESelectionActionPerformed
        this.setVisible(false);
        PayrollEmployeeSelection back = new PayrollEmployeeSelection();
        back.setVisible(true);
    }//GEN-LAST:event_backToPESelectionActionPerformed

    private void txtID6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtID6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtID6ActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void txtPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPositionActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void jToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton5ActionPerformed

    private void BacktoDB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BacktoDB1ActionPerformed
        this.setVisible(false);
        AdminDashboard back = new AdminDashboard();
        back.setVisible(true);
    }//GEN-LAST:event_BacktoDB1ActionPerformed

    private void generatePayslipButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatePayslipButtonActionPerformed
        GeneratePayslip GeneratePayslip = new GeneratePayslip();
        GeneratePayslip.setVisible(true);
        SecondPayrollPanel.this.setVisible(false);
    }//GEN-LAST:event_generatePayslipButtonActionPerformed

    private void jToggleButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton6ActionPerformed

    private void jToggleButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton7ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

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
    private javax.swing.JToggleButton backToPESelection;
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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton6;
    private javax.swing.JToggleButton jToggleButton7;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtID11;
    private javax.swing.JTextField txtID6;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPosition;
    private javax.swing.JLabel txtSelectemployee;
    private javax.swing.JLabel txtSelectemployee1;
    private javax.swing.JLabel txtSelectemployee2;
    private javax.swing.JLabel txtSelectemployee3;
    private javax.swing.JLabel txtSelectemployee8;
    // End of variables declaration//GEN-END:variables
}
