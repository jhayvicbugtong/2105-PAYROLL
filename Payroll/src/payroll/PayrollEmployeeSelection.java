package payroll;

import com.sun.jdi.connect.spi.Connection;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author sophi
 */
public class PayrollEmployeeSelection extends javax.swing.JFrame {

    /**
     * Creates new form MainPayrollPanel
     */
    public PayrollEmployeeSelection() {
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
        txtSelectemployee = new javax.swing.JLabel();
        txtEmployeeselection = new javax.swing.JLabel();
        goToPayrollButton = new javax.swing.JButton();
        txtEmployee = new javax.swing.JTextField();
        txtStartDate = new javax.swing.JTextField();
        txtEndDate = new javax.swing.JTextField();
        txtSelectemployee1 = new javax.swing.JLabel();
        txtSelectemployee2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(107, 36, 12));

        txtSelectemployee.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSelectemployee.setForeground(new java.awt.Color(245, 204, 160));
        txtSelectemployee.setText("Type Employee ID:");

        txtEmployeeselection.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        txtEmployeeselection.setForeground(new java.awt.Color(245, 204, 160));
        txtEmployeeselection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtEmployeeselection.setText("Employee Selection");
        txtEmployeeselection.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(153, 77, 28)));

        goToPayrollButton.setBackground(new java.awt.Color(228, 143, 69));
        goToPayrollButton.setForeground(new java.awt.Color(107, 36, 12));
        goToPayrollButton.setText("GO");
        goToPayrollButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        goToPayrollButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToPayrollButtonActionPerformed(evt);
            }
        });

        txtEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmployeeActionPerformed(evt);
            }
        });

        txtStartDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStartDateActionPerformed(evt);
            }
        });

        txtEndDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEndDateActionPerformed(evt);
            }
        });

        txtSelectemployee1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSelectemployee1.setForeground(new java.awt.Color(245, 204, 160));
        txtSelectemployee1.setText("Start Date:");

        txtSelectemployee2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSelectemployee2.setForeground(new java.awt.Color(245, 204, 160));
        txtSelectemployee2.setText("End Date:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSelectemployee)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtEmployee, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtSelectemployee1)
                                .addComponent(txtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(40, 40, 40)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSelectemployee2)))))
                .addGap(61, 61, 61))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(txtEmployeeselection, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(goToPayrollButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(txtEmployeeselection, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSelectemployee)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSelectemployee1)
                    .addComponent(txtSelectemployee2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(goToPayrollButton)
                .addContainerGap(21, Short.MAX_VALUE))
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

    private void txtEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmployeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmployeeActionPerformed

    private void goToPayrollButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToPayrollButtonActionPerformed
try {
        // Parse Employee ID from the text field (txtEmployee)
        int employeeId = Integer.parseInt(txtEmployee.getText());  // Parse employee ID to int

        // Retrieve start and end dates from the text fields (txtStartDate, txtEndDate)
        String startDate = txtStartDate.getText();
        String endDate = txtEndDate.getText();

        // Validate that the fields are not empty
        if (startDate.isEmpty() || endDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        // Now create the SecondPayrollPanel and pass the retrieved data
        SecondPayrollPanel secondPayrollPanel = new SecondPayrollPanel();

        // Set the employee ID and date range
        secondPayrollPanel.setEmployeeId(employeeId);  // Pass employeeId as int
        secondPayrollPanel.setDateRange(startDate, endDate);

        // Load the employee's details in the second panel
        secondPayrollPanel.loadEmployeeDetails(employeeId);

        // Call the method to load the filtered payroll data into the table
        secondPayrollPanel.loadFilteredPayrollDataToTable();

        // Display the SecondPayrollPanel and hide the current panel if necessary
        secondPayrollPanel.setVisible(true);
        this.setVisible(false); // Optional: Hide current panel if transitioning to second panel

    } catch (NumberFormatException e) {
        // Handle invalid input for employee ID (non-integer value)
        JOptionPane.showMessageDialog(this, "Invalid Employee ID format. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

// Function to check if the employee exists in the payroll database
private boolean doesEmployeeExist(String employeeNameOrId) {
    boolean exists = false;

    java.sql.Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    String url = "jdbc:mysql://localhost:3306/payroll_db";
    String user = "root";
    String pass = "";

    try {
        // Connect to the database
        conn = DriverManager.getConnection(url, user, pass);

        // Query to check if employee exists in the payroll table
        String query = "SELECT * FROM employees WHERE employee_id = ? OR name = ?";
        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, employeeNameOrId);
        preparedStatement.setString(2, employeeNameOrId);

        // Execute the query
        rs = preparedStatement.executeQuery();

        // If a result is found, the employee exists
        exists = rs.next();
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

    return exists;
    }//GEN-LAST:event_goToPayrollButtonActionPerformed

    private void txtEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEndDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEndDateActionPerformed

    private void txtStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStartDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStartDateActionPerformed

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
            java.util.logging.Logger.getLogger(PayrollEmployeeSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PayrollEmployeeSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PayrollEmployeeSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PayrollEmployeeSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PayrollEmployeeSelection().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton goToPayrollButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtEmployee;
    private javax.swing.JLabel txtEmployeeselection;
    private javax.swing.JTextField txtEndDate;
    private javax.swing.JLabel txtSelectemployee;
    private javax.swing.JLabel txtSelectemployee1;
    private javax.swing.JLabel txtSelectemployee2;
    private javax.swing.JTextField txtStartDate;
    // End of variables declaration//GEN-END:variables
}
