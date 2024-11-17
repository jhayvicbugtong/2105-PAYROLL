package payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class Login extends javax.swing.JFrame {

   
    public Login() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        loginbotton = new javax.swing.JButton();
        userName = new javax.swing.JTextField();
        userPassword = new javax.swing.JPasswordField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(107, 36, 12));

        jPanel2.setBackground(new java.awt.Color(153, 77, 28));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(228, 143, 69));
        jLabel2.setText("Username:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        txtPassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(228, 143, 69));
        txtPassword.setText("Password:");
        jPanel2.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 68, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/Resto Logo 2.png"))); // NOI18N
        jLabel4.setText("jLabel4");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-80, 150, 440, 430));

        loginbotton.setBackground(new java.awt.Color(228, 143, 69));
        loginbotton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        loginbotton.setForeground(new java.awt.Color(107, 36, 12));
        loginbotton.setText("LOGIN");
        loginbotton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        loginbotton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginbottonActionPerformed(evt);
            }
        });
        jPanel2.add(loginbotton, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 90, -1));

        userName.setBackground(new java.awt.Color(153, 77, 28));
        userName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        userName.setForeground(new java.awt.Color(228, 143, 69));
        userName.setBorder(null);
        userName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameActionPerformed(evt);
            }
        });
        jPanel2.add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 170, 20));

        userPassword.setBackground(new java.awt.Color(153, 77, 28));
        userPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        userPassword.setForeground(new java.awt.Color(228, 143, 69));
        userPassword.setBorder(null);
        userPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userPasswordActionPerformed(evt);
            }
        });
        jPanel2.add(userPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 170, 20));

        jSeparator1.setForeground(new java.awt.Color(228, 143, 69));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 170, -1));

        jSeparator2.setForeground(new java.awt.Color(228, 143, 69));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 170, -1));

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 2, 9)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(228, 143, 69));
        jCheckBox1.setText("Show Password");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel2.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, -1, 10));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(228, 143, 69));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SJB PAYROLL SYSTEM");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loginbottonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginbottonActionPerformed
     String uN = userName.getText();
     char[] passwordArray = userPassword.getPassword();
     String UP = new String(passwordArray);
     
       
       String url = "jdbc:mysql://localhost:3306/payroll_db";
       String user = "root";
       String pass = "";
       
       
       if("".equals(uN)) {
          JOptionPane.showMessageDialog(new JFrame(), "UserName is requires", "Dialog", JOptionPane.ERROR_MESSAGE);
       return;
       }
       
        if ("".equals(UP)) {
           JOptionPane.showMessageDialog(new JFrame(), "Password is requires", "Dialog", JOptionPane.ERROR_MESSAGE);    
       return;
       }
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con = DriverManager.getConnection(url, user, pass);
             Statement st = con.createStatement();
        
             String query = "SELECT * FROM login WHERE userName = '" + uN + "' AND password = '" + UP + "'";
        
             ResultSet rs = st.executeQuery(query);
        
        if (rs.next()) {
            JOptionPane.showMessageDialog(null, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);
            AdminDashboard adminDashboard = new AdminDashboard();
            adminDashboard.setVisible(true);
            
        }
        
        else {
             JOptionPane.showMessageDialog(null, "Invalid Username or Password", "ERROR", JOptionPane.INFORMATION_MESSAGE);            
       }
            rs.close();
            st.close();
            con.close();
            
       }catch(Exception e){
       JOptionPane.showMessageDialog(new JFrame(), "ERROR:" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
       
       }
    }//GEN-LAST:event_loginbottonActionPerformed

    private void userNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameActionPerformed
        
        
    }//GEN-LAST:event_userNameActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if(jCheckBox1.isSelected())
        {
            userPassword.setEchoChar((char)0);
            jCheckBox1.setText("Hide password");
            
        }
        else
        {
            userPassword.setEchoChar('*');
            jCheckBox1.setText("Show password");
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void userPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userPasswordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton loginbotton;
    private javax.swing.JLabel txtPassword;
    private javax.swing.JTextField userName;
    private javax.swing.JPasswordField userPassword;
    // End of variables declaration//GEN-END:variables
}

