/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.raven.config.DBConnect;
import com.raven.main.Main;
import com.raven.repository.NhanVienRepository;
import com.raven.respose.NhanVienResponse;
import com.raven.util.GuiMail;
import com.raven.util.LuuIdQuenMk;
import com.raven.util.LuuMaXacNhan;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import raven.toast.Notifications;

/**
 *
 * @author ADMIN
 */
public class DoiMatKhauForm extends javax.swing.JPanel {

    private NhanVienRepository repo = new NhanVienRepository();
//    private NhanVienResponse currentUser = Main.getNhanVienDangNhap();

    /**
     * Creates new form DangXuatForm
     */
    public DoiMatKhauForm() {
        initComponents();
    }

    public boolean checkMatKhauHienTai(String matKhau) {
        String query = "SELECT COUNT(*) FROM [dbo].[NhanVien] WHERE MatKhau = ?";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setObject(1, matKhau);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // Mật khẩu hiện tại đúng
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Mật khẩu hiện tại sai
    }

    public boolean checkXacNhanMatKhau() {
        return txtMatKhauMoi.getText().equalsIgnoreCase(txtXacNhanMatKhau.getText());
    }

    public boolean check() {
        String matKhauHienTai = txtMatKhau.getText();
        String matKhauMoi = txtMatKhauMoi.getText();
        String xacNhanMatKhau = txtXacNhanMatKhau.getText();
        if (matKhauHienTai.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu hiện tại!");
            txtMatKhau.requestFocus();
            return false;
        } else if (matKhauMoi.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu mới!");
            txtMatKhauMoi.requestFocus();
            return false;
        } else if (xacNhanMatKhau.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng xác nhận mật khẩu mới vừa nhập!");
            txtXacNhanMatKhau.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    private NhanVienResponse getForm() {
        String matkhau = txtMatKhauMoi.getText();
        NhanVienResponse nv = new NhanVienResponse(matkhau);
        return nv;
    }

    public boolean checkMaXacNhan() {
        return txtMaXacNhan.getText().equals(LuuMaXacNhan.getGeneratedCode());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtMatKhau = new swing.TextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMatKhauMoi = new swing.TextField();
        txtXacNhanMatKhau = new swing.TextField();
        btnDoi = new javax.swing.JButton();
        btnXacNhan = new com.raven.swing.Button();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNhapMail = new swing.TextField();
        jLabel1 = new javax.swing.JLabel();
        txtMaXacNhan = new swing.TextField();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        txtMatKhau.setLabelText("Mật khẩu hiện tại");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Mật khẩu hiện tại");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Mật khẩu mới");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Xác nhận mật khẩu");

        txtMatKhauMoi.setLabelText("Mật khẩu mới");

        txtXacNhanMatKhau.setLabelText("Xác nhận mật khẩu");

        btnDoi.setBackground(new java.awt.Color(35, 166, 97));
        btnDoi.setForeground(new java.awt.Color(255, 255, 255));
        btnDoi.setText(" Đổi mật khẩu");
        btnDoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiActionPerformed(evt);
            }
        });

        btnXacNhan.setBackground(new java.awt.Color(35, 166, 97));
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("Xác Nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Vui lòng nhập email để xác nhận");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Nhập Email của bạn");

        txtNhapMail.setLabelText("Email");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Mã xác nhận");

        txtMaXacNhan.setLabelText("Mã xác nhận");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("ĐỔI MẬT KHẨU");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(btnDoi, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(91, 91, 91)
                                .addComponent(txtNhapMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(129, 129, 129)
                                .addComponent(txtMaXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 1, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(78, 78, 78)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtMatKhau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtMatKhauMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtXacNhanMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(242, 242, 242))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel4)
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMatKhauMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtXacNhanMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(37, 37, 37)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNhapMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(txtMaXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(84, 84, 84)
                .addComponent(btnDoi, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiActionPerformed
        // TODO add your handling code here:
//        NhanVienResponse currentUser = Main.getNhanVienDangNhap();
        int check = JOptionPane.showConfirmDialog(this, "Bạn chắn chắn muốn thay đổi mật khẩu?", "Phần mềm bán giày Sneaker HAVEN", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        String matKhauHienTai = txtMatKhau.getText();
        if (check()) {
            if (checkMatKhauHienTai(matKhauHienTai)) {
                if (checkXacNhanMatKhau()) {
                    if (checkMaXacNhan()) {
                        repo.updateMatKhau(getForm(), LuuIdQuenMk.getUserId());
                        JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
                    } else {
                        JOptionPane.showMessageDialog(this, "Mã xác nhận không đúng");
                    }
                } else {
                    // Xác nhận mật khẩu mới không khớp
                    JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu không khớp");
                }
            } else {
                // Mật khẩu hiện tại sai
                JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không đúng");
            }
        }
    }//GEN-LAST:event_btnDoiActionPerformed
    public boolean isValidEmail(String email) {
        // Biểu thức chính quy để kiểm tra định dạng email
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Tạo Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Tạo Matcher object
        Matcher matcher = pattern.matcher(email);

        // Kiểm tra xem email có khớp với biểu thức chính quy không
        return matcher.matches();
    }
    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        // TODO add your handling code here:
        String email = txtNhapMail.getText();

        if (check()) {
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Email Nhân Viên không hợp lệ");
            }
            // Kiểm tra email tồn tại
            if (!repo.checkEmailTonTai(email)) {
                JOptionPane.showMessageDialog(this, "Email không tồn tại.");
                return;
            }

            int userId = repo.getIdByEmail(email);
            if (userId == -1) {
                JOptionPane.showMessageDialog(this, "Không thể lấy ID từ email.");

                return;
            }
            String subject = "Xác nhận thay đổi mật khẩu";
            String generatedCode = GuiMail.generateRandomCode(); // Generate code here
            String body = "Đây là mã xác nhận của bạn: " + generatedCode;

            // Gửi email
            try {
                GuiMail.sendEmail(email, subject, body);
                LuuMaXacNhan.setGeneratedCode(generatedCode); // Save code
                // Lưu userId vào đâu đó để sử dụng sau này, ví dụ một class tạm lưu trữ thông tin tạm thời
                LuuIdQuenMk.setUserId(userId);
                JOptionPane.showMessageDialog(this, "Mã xác nhận đã được gửi đến địa chỉ email.");

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi gửi mã xác nhận.");
            }
        }
    }//GEN-LAST:event_btnXacNhanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDoi;
    private com.raven.swing.Button btnXacNhan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private swing.TextField txtMaXacNhan;
    private swing.TextField txtMatKhau;
    private swing.TextField txtMatKhauMoi;
    private swing.TextField txtNhapMail;
    private swing.TextField txtXacNhanMatKhau;
    // End of variables declaration//GEN-END:variables

}
