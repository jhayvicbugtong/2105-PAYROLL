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
    private int employeeId;
    /**

     */
    // Constructor that takes employee_id as argument
    public Timesheet(int employeeId) {
        this.employeeId = employeeId; // Save the employee_id
        initComponents(); // Initialize components (auto-generated code)
        loadTimesheetDataToTable(); // Load timesheet data based on employee_id
        setupTableSelectionListener();
    }
    
    private void setupTableSelectionListener() {
    // Add a ListSelectionListener to EmployeeInfoTable
    EmployeeTimesheetTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            // Check if a row is selected (i.e., the selection is not empty)
            if (!e.getValueIsAdjusting() && EmployeeTimesheetTable.getSelectedRow() != -1) {
                int selectedRow = EmployeeTimesheetTable.getSelectedRow();

             
    
                // Retrieve data from the selected row
                String date = EmployeeTimesheetTable.getValueAt(selectedRow, 1).toString();
                String timeIn = EmployeeTimesheetTable.getValueAt(selectedRow, 2).toString();
                String timeOut = EmployeeTimesheetTable.getValueAt(selectedRow, 3).toString();
                String overtimeStr = EmployeeTimesheetTable.getValueAt(selectedRow, 4).toString();
                String totalHoursStr = EmployeeTimesheetTable.getValueAt(selectedRow, 5).toString();
                

                // Set the values in the text fields
                txtDate.setText(date);
                txtTimeIn.setText(timeIn);
                txtTimeOut.setText(timeOut);
                txtOvertime.setText(overtimeStr);
                txtTotalHours.setText(totalHoursStr);
                
            }
        }
    });
}
    public void loadTimesheetDataToTable() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

    String query = "SELECT ts.employee_id, e.name, ts.date, ts.time_in, ts.time_out, ts.overtime, ts.hours_worked " +
               "FROM timesheet ts " +
               "JOIN employees e ON ts.employee_id = e.employee_id " +  // Join with the employee table to get the name
               "WHERE ts.employee_id = " + employeeId; // Use the passed employee_id
        

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            // Assuming you have a JTextField named jTextField1 to display the employee name
        if (rs.next()) {
            // Set the employee name to jTextField1
            jTextField1.setText(rs.getString("name"));
        }
            
            
            DefaultTableModel model = (DefaultTableModel) EmployeeTimesheetTable.getModel(); // Assuming you have a JTable named timesheetTable
            model.setRowCount(0); // Clear the table before populating

            while (rs.next()) {
                Object[] row = new Object[6]; // Adjust size of row as per the columns
                row[0] = rs.getInt("employee_id");
                row[1] = rs.getDate("date");
                row[2] = rs.getTime("time_in");
                row[3] = rs.getTime("time_out");
                row[4] = rs.getDouble("overtime");
                row[5] = rs.getDouble("hours_worked");

                model.addRow(row); // Add the row to the table
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
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

        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        EmployeeTimesheetTable = new javax.swing.JTable();
        saveButton = new javax.swing.JButton();
        editRow = new javax.swing.JButton();
        deleteButton1 = new javax.swing.JButton();
        backTodashboardButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
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

        saveButton.setBackground(new java.awt.Color(228, 143, 69));
        saveButton.setForeground(new java.awt.Color(107, 36, 12));
        saveButton.setText("Add Row");
        saveButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
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

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
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
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
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
        int selectedRow = EmployeeTimesheetTable.getSelectedRow();

    if (selectedRow == -1) {
        // No row is selected, show an error message
        JOptionPane.showMessageDialog(this, "Please select a timesheet entry to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Get the timesheet ID from the selected row (assuming the ID is in the first column)
    int timesheetId = (int) EmployeeTimesheetTable.getValueAt(selectedRow, 0); 

    // Collect updated information from user inputs
    String date = txtDate.getText();
    String timeIn = txtTimeIn.getText();
    String timeOut = txtTimeOut.getText();
    String overtimeStr = txtOvertime.getText();
    String totalHoursStr = txtTotalHours.getText();

    // Validate input
    if (date.isEmpty() || timeIn.isEmpty() || timeOut.isEmpty() || overtimeStr.isEmpty() || totalHoursStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Parse numeric fields
    double overtime, totalHours;
    try {
        overtime = Double.parseDouble(overtimeStr);
        totalHours = Double.parseDouble(totalHoursStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Overtime and Total Hours must be numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Update the timesheet entry in the database
    String url = "jdbc:mysql://localhost:3306/payroll_db";
    String user = "root";
    String pass = "";

    Connection conn = null;
    PreparedStatement pst = null;

    try {
        // Connect to the database
        conn = DriverManager.getConnection(url, user, pass);
        
        // SQL query to update the timesheet
        String sql = "UPDATE timesheet SET date = ?, time_in = ?, time_out = ?, overtime = ?, hours_worked = ? WHERE timesheet_id = ?";
                   
        pst = conn.prepareStatement(sql);
        pst.setString(1, date);
        pst.setString(2, timeIn);
        pst.setString(3, timeOut);
        pst.setDouble(4, overtime);
        pst.setDouble(5, totalHours);
        pst.setInt(6, timesheetId);

        // Execute the update
        int rowsUpdated = pst.executeUpdate();

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Timesheet entry updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshEmployeeTimesheet();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update timesheet entry.", "Error", JOptionPane.ERROR_MESSAGE);
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
    }//GEN-LAST:event_editRowActionPerformed

    private void backTodashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backTodashboardButtonActionPerformed
         this.setVisible(false);
     AdminDashboard back = new AdminDashboard();
     back.setVisible(true);
    }//GEN-LAST:event_backTodashboardButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // Get text values from the components
    String date1 = txtDate.getText();
    String timein1 = txtTimeIn.getText();
    String timeout1 = txtTimeOut.getText();
    String overtime1 = txtOvertime.getText();
    String totalhours1 = txtTotalHours.getText();

    // Database connection details
    String url = "jdbc:mysql://localhost:3306/payroll_db";
    String user = "root";
    String pass = "";

    // Validate that all fields are filled out
    if (date1.isEmpty() || timein1.isEmpty() || timeout1.isEmpty() || overtime1.isEmpty() || totalhours1.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Check if a row is selected in the table
    int selectedRow = EmployeeTimesheetTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row from the table.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Get the employee_id from the selected row (assuming it's in the first column of the table)
    int employeeId = (int) EmployeeTimesheetTable.getValueAt(selectedRow, 0); // Assuming the employee_id is in the first column

    Connection conn = null;
    PreparedStatement pst = null;
                  
    try {
        // Establish database connection
        conn = DriverManager.getConnection(url, user, pass);

        // Step 2: Insert the timesheet data associated with the employee_id
        String insertTimesheetSql = "INSERT INTO timesheet (employee_id, date, time_in, time_out, overtime, hours_worked) VALUES (?, ?, ?, ?, ?, ?)";
        pst = conn.prepareStatement(insertTimesheetSql);

        pst.setInt(1, employeeId); // Use the employee_id from the table row
        pst.setDate(2, java.sql.Date.valueOf(date1)); // Convert date string to SQL Date
        pst.setTime(3, java.sql.Time.valueOf(timein1)); // Convert time string to SQL Time
        pst.setTime(4, java.sql.Time.valueOf(timeout1)); // Convert time string to SQL Time
        pst.setDouble(5, Double.parseDouble(overtime1)); // Parse overtime as double
        pst.setDouble(6, Double.parseDouble(totalhours1)); // Parse total hours worked as double

        int rowsInserted = pst.executeUpdate();

        if (rowsInserted > 0) {
            // Add the data to the JTable
            DefaultTableModel model = (DefaultTableModel) EmployeeTimesheetTable.getModel();
            model.addRow(new Object[]{employeeId, date1, timein1, timeout1, overtime1, totalhours1});

            JOptionPane.showMessageDialog(this, "Timesheet entry added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear input fields
            txtDate.setText("");
            txtTimeIn.setText("");
            txtTimeOut.setText("");
            txtOvertime.setText("");
            txtTotalHours.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add timesheet entry.", "Error", JOptionPane.ERROR_MESSAGE);
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

    }//GEN-LAST:event_saveButtonActionPerformed
private void refreshEmployeeTimesheet() {
    DefaultTableModel model = (DefaultTableModel) EmployeeTimesheetTable.getModel();
    model.setRowCount(0); // Clear the table

    String url = "jdbc:mysql://localhost:3306/payroll_db";
    String user = "root";
    String pass = "";

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
        // Connect to the database
        conn = DriverManager.getConnection(url, user, pass);

        // SQL to fetch all timesheet records
        
        String sql = "SELECT employee_id, date, time_in, time_out, overtime, hours_worked FROM timesheet";
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();

        // Populate the table with data
        while (rs.next()) {
            int id = rs.getInt("employee_id");
            String date = rs.getString("date");
            String timeIn = rs.getString("time_in");
            String timeOut = rs.getString("time_out");
            double overtime = rs.getDouble("overtime");
            double hoursWorked = rs.getDouble("hours_worked");

            model.addRow(new Object[]{id, date, timeIn, timeOut, overtime, hoursWorked});
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
    private void deleteButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButton1ActionPerformed
int selectedRow = EmployeeTimesheetTable.getSelectedRow();

if (selectedRow == -1) {
    // No row is selected, show an error message
    JOptionPane.showMessageDialog(this, "Please select a timesheet entry to delete.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}

// Get the ID and Date from the selected row (assuming ID is in the first column and Date is in the second)
int timesheetId = (int) EmployeeTimesheetTable.getValueAt(selectedRow, 0); // Assuming ID is in the first column
String date = EmployeeTimesheetTable.getValueAt(selectedRow, 1).toString(); // Assuming Date is in the second column

// Ask for confirmation before deleting
int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this timesheet entry?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

if (confirmation == JOptionPane.YES_OPTION) {
    String url = "jdbc:mysql://localhost:3306/payroll_db";
    String user = "root";
    String pass = "";

    Connection conn = null;
    PreparedStatement pst = null;

    try {
        // Connect to the database
        conn = DriverManager.getConnection(url, user, pass);

        // SQL to delete the specific timesheet entry
        String deleteTimesheetSql = "DELETE FROM timesheet WHERE employee_id = ? AND date = ?";
        pst = conn.prepareStatement(deleteTimesheetSql);
        pst.setInt(1, timesheetId);
        pst.setString(2, date);

        // Execute the DELETE query
        int rowsDeleted = pst.executeUpdate();

        if (rowsDeleted > 0) {
            // Successfully deleted
            JOptionPane.showMessageDialog(this, "Timesheet entry deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Refresh the table to reflect the changes
            refreshEmployeeTimesheet();
        } else {
            // No entry found with the specified ID and Date
            JOptionPane.showMessageDialog(this, "Failed to delete timesheet entry. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
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
}       
    }//GEN-LAST:event_deleteButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtOvertime;
    private javax.swing.JTextField txtTimeIn;
    private javax.swing.JTextField txtTimeOut;
    private javax.swing.JTextField txtTotalHours;
    // End of variables declaration//GEN-END:variables
}
