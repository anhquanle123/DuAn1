/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import com.raven.cell.TableActionCellEditor;
import com.raven.cell.TableActionCellRender;
import com.raven.cell.TableActionEvent;
import com.raven.entity.PhieuGiamGia;
import com.raven.repository.PhieuGiamGiaRespository;
import com.raven.util.Auth;
import com.raven.util.Helper;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jdk.jfr.consumer.EventStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author ADMIN
 */
public class PhieuGiamGiaForm extends javax.swing.JPanel {

    private DefaultTableModel dtm = new DefaultTableModel();
    private PhieuGiamGiaRespository repo;

    /**
     * Creates new form PhieuGiamGiaForm
     */
    public PhieuGiamGiaForm() {
        initComponents();
        repo = new PhieuGiamGiaRespository();
        tbPhieuGiamGia.getColumnModel().getColumn(10).setCellRenderer(new TableActionCellRender());
        dtm = (DefaultTableModel) tbPhieuGiamGia.getModel();
        edit();
        runThreadCheckNgayKetThuc();
        showDataTable(repo.getAll());
    }

    private void edit() {
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row, String newSatus) {
                tbPhieuGiamGia.setValueAt(newSatus, row, 9);
            }

            @Override
            public void onViewDetails(int row) {
                int index = tbPhieuGiamGia.getSelectedRow();
                PhieuGiamGia pgg = repo.getAll().get(index);
                if (index != -1) {
                    String ma = tbPhieuGiamGia.getValueAt(index, 1).toString();
                    String ten = tbPhieuGiamGia.getValueAt(index, 2).toString();
                    int soLuong = Integer.parseInt(tbPhieuGiamGia.getValueAt(index, 3).toString());
                    String loai = tbPhieuGiamGia.getValueAt(index, 4).toString();
                    String gtriTT = tbPhieuGiamGia.getValueAt(index, 5).toString();
                    String gtriTD = tbPhieuGiamGia.getValueAt(index, 6).toString();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Date ngayBD = (Date) tbPhieuGiamGia.getValueAt(index, 7);
                    Date ngayKT = (Date) tbPhieuGiamGia.getValueAt(index, 8);
                    String trangThai = tbPhieuGiamGia.getValueAt(index, 9).toString();
                    double dieuKien = pgg.getDieuKien();

                    openDetail(ma, ten, loai, gtriTT, gtriTD, ngayBD, ngayKT, soLuong, dieuKien, trangThai);
                }
            }
        };
        tbPhieuGiamGia.getColumnModel().getColumn(10).setCellRenderer(new TableActionCellRender());
        tbPhieuGiamGia.getColumnModel().getColumn(10).setCellEditor(new TableActionCellEditor(event));
    }

    private void showDataTable(ArrayList<PhieuGiamGia> list) {
        dtm.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        for (PhieuGiamGia pgg : list) {

            String formatGTTT = currencyFormatter.format(pgg.getGiaTriToiThieu());
            String formatGTTD = currencyFormatter.format(pgg.getGiaTriToiDa());

            formatGTTT = formatGTTT.substring(0, formatGTTT.length() - 1) + "VNĐ";
            formatGTTD = formatGTTD.substring(0, formatGTTD.length() - 1) + "VNĐ";

            dtm.addRow(new Object[]{
                index.getAndIncrement(), pgg.getMa(), pgg.getTen(), pgg.getSoLuong(), pgg.getLoai(), formatGTTT,
                formatGTTD, pgg.getNgayBatDau(), pgg.getNgayKetThuc(), pgg.getTrangThai()
            });
        }
    }

    public void deatailPhieuGiamGia(int index) {
        PhieuGiamGia pgg = repo.getAll().get(index);
        txtMaVoucher.setText(pgg.getMa());
        txtTenVoucher.setText(pgg.getTen());
        txtDieuKien.setText(String.valueOf(pgg.getDieuKien()));
        txtGiaTriToiDa.setText(String.valueOf(pgg.getGiaTriToiDa()));
        txtGiaTriToiThieu.setText(String.valueOf(pgg.getGiaTriToiThieu()));
        txtSoLuong.setText(String.valueOf(pgg.getSoLuong()));
        dateNgayBatDau.setDate(pgg.getNgayBatDau());
        dateNgayKetThuc.setDate(pgg.getNgayKetThuc());
        cbbLoai.setSelectedItem(pgg.getLoai());
    }

    private void openDetail(String ma, String ten, String loai, String gtrTT, String gtrTD,
            Date ngayBD, Date ngayKT, int soLuong, double dieuKien, String trangThai) {

        PhieuGiamGiaJFrame frame = new PhieuGiamGiaJFrame(this);

        frame.setDeatail(ma, ten, loai, gtrTT, gtrTD, ngayBD, ngayKT, soLuong, dieuKien, trangThai);
        frame.setVisible(true);
    }

    private PhieuGiamGia getForm() {
        String ma = txtMaVoucher.getText().trim();
        String ten = txtTenVoucher.getText().trim();
        if (ten == null || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống");
            txtTenVoucher.requestFocus();
            return null;
        }
        if (!Helper.checkDoDaiCuaChuoi(ten)) {
            JOptionPane.showMessageDialog(this, "Độ dài của tên không hợp lệ. Từ 3-20 kí tự !");
            txtTenVoucher.requestFocus();
            return null;
        }
        if (txtSoLuong.getText() == null || txtSoLuong.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số lượng không được để trống");
            txtSoLuong.requestFocus();
            return null;
        }
        int soLuong;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0 !");
                txtSoLuong.requestFocus();
                return null;
            }
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải nhập số !");
            txtSoLuong.requestFocus();
            return null;
        }

        String loai = (String) cbbLoai.getSelectedItem();
        if (loai == null || loai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Loại không được để trống");
            return null;
        }

        if (txtGiaTriToiThieu.getText() == null || txtGiaTriToiThieu.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Gía trị tối đa không được để trống");
            txtGiaTriToiThieu.requestFocus();
            return null;
        }

        double giaTriToiThieu;
        try {
            giaTriToiThieu = Double.parseDouble(txtGiaTriToiThieu.getText().trim());
            if (giaTriToiThieu <= 0) {
                JOptionPane.showMessageDialog(this, "Giá trị tối thiểu phải lớn hơn 0");
                txtGiaTriToiThieu.requestFocus();
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Gía trị tối thiểu phải nhập số !");
            txtGiaTriToiThieu.requestFocus();
            return null;
        }

        if (txtGiaTriToiDa.getText() == null || txtGiaTriToiDa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Gía trị tối đa không được để trống");
            txtGiaTriToiDa.requestFocus();
            return null;
        }

        double giaTriToiDa;
        try {
            giaTriToiDa = Double.parseDouble(txtGiaTriToiDa.getText().trim());
            if (giaTriToiDa <= 0) {
                JOptionPane.showMessageDialog(this, "Giá trị tối đa phải lớn hơn 0");
                txtGiaTriToiDa.requestFocus();
                return null;
            }
            if (giaTriToiThieu > giaTriToiDa) {
                JOptionPane.showMessageDialog(this, "Gía trị tối đa phải lớn hơn giá trị tối thiểu");
                txtGiaTriToiDa.requestFocus();
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Gía trị tối đa phải nhập số !");
            txtGiaTriToiDa.requestFocus();
            return null;
        }

        Date starDate = dateNgayBatDau.getDate();

        if (starDate == null) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được để trống!");
            return null;
        }
        Date endDate = dateNgayKetThuc.getDate();

        if (endDate == null) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được để trống!");
            return null;
        }

        if (endDate.getTime() < starDate.getTime()) {
            JOptionPane.showMessageDialog(this,
                    "Ngày kết thúc phải sau ngày bắt đầu!");
            return null;
        }

        if (starDate.getTime() > endDate.getTime()) {
            JOptionPane.showMessageDialog(this,
                    "Ngày bắt đầu phải trước ngày kết thúc!");
            return null;
        }

        if (txtDieuKien.getText() == null || txtDieuKien.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Điều kiện không được để trống");
            txtDieuKien.requestFocus();
            return null;
        }
        double dieuKien;
        try {
            dieuKien = Double.parseDouble(txtDieuKien.getText().trim());
            if (dieuKien <= 0) {
                JOptionPane.showMessageDialog(this, "Điều kiện phải lớn hơn 0");
                txtSoLuong.requestFocus();
                return null;
            }
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điều kiện phải nhập số!");
            txtDieuKien.requestFocus();
            return null;
        }

        Date currentDate = new Date();
        String trangThai;
        if (currentDate.before(starDate)) {
            trangThai = "Sắp diễn ra";
        } else if (currentDate.after(endDate)) {
            trangThai = "Hết hạn";
        } else {
            trangThai = "Đang diễn ra";
        }
        return new PhieuGiamGia(ma, ten, soLuong, starDate, endDate,
                dieuKien, loai, giaTriToiThieu, giaTriToiDa, trangThai);
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
        jLabel4 = new javax.swing.JLabel();
        cbbLoai = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        dateNgayBatDau = new com.toedter.calendar.JDateChooser();
        dateNgayKetThuc = new com.toedter.calendar.JDateChooser();
        txtMaVoucher = new com.raven.util.TextField();
        txtTenVoucher = new com.raven.util.TextField();
        txtSoLuong = new com.raven.util.TextField();
        txtDieuKien = new com.raven.util.TextField();
        txtGiaTriToiThieu = new com.raven.util.TextField();
        txtGiaTriToiDa = new com.raven.util.TextField();
        btnXuatExcel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cbbLoaiSearch = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cbbTrangThai = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnLoc = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPhieuGiamGia = new javax.swing.JTable();
        dateLocNgayBatDau = new com.toedter.calendar.JDateChooser();
        dateLocNgayKetThuc = new com.toedter.calendar.JDateChooser();
        btnTimKiem = new javax.swing.JButton();
        btnLamMoiTimKiem = new javax.swing.JButton();
        txtSearch = new com.raven.util.TextField();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin phiếu giảm giá", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel4.setText("Loại giảm giá : ");

        cbbLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phần trăm", "Số Tiền" }));

        jLabel6.setText("Ngày bắt đầu : ");

        jLabel7.setText("Ngày kết thúc : ");

        btnThem.setBackground(new java.awt.Color(35, 166, 97));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Add.png"))); // NOI18N
        btnThem.setText("Thêm mới");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(35, 166, 97));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Upload.png"))); // NOI18N
        btnUpdate.setText("Cập nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(35, 166, 97));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Refresh.png"))); // NOI18N
        btnReset.setText("Làm mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        dateNgayBatDau.setDateFormatString("dd-MM-yyyy");

        dateNgayKetThuc.setDateFormatString("dd-MM-yyyy");

        txtMaVoucher.setEditable(false);
        txtMaVoucher.setLabelText("Mã voucher");

        txtTenVoucher.setLabelText("Tên voucher");

        txtSoLuong.setLabelText("Số lượng");

        txtDieuKien.setLabelText("Điều kiện");

        txtGiaTriToiThieu.setLabelText("Giá trị tối thiểu");

        txtGiaTriToiDa.setLabelText("Giá trị tối đa");

        btnXuatExcel.setBackground(new java.awt.Color(35, 166, 97));
        btnXuatExcel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Excel_2013_23480.png"))); // NOI18N
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addComponent(txtMaVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addComponent(txtDieuKien, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
                        .addGap(18, 22, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGiaTriToiThieu, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addComponent(txtGiaTriToiDa, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(35, 35, 35)
                                .addComponent(dateNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(jLabel4)
                                .addGap(22, 22, 22)
                                .addComponent(cbbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(215, 215, 215)
                                .addComponent(btnThem)
                                .addGap(65, 65, 65)
                                .addComponent(btnUpdate)
                                .addGap(67, 67, 67)
                                .addComponent(btnReset)
                                .addGap(68, 68, 68)
                                .addComponent(btnXuatExcel))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(35, 35, 35)
                                .addComponent(dateNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtGiaTriToiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtMaVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTenVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDieuKien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaTriToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(dateNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cbbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(dateNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnUpdate)
                    .addComponent(btnReset)
                    .addComponent(btnXuatExcel))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jLabel1.setBackground(new java.awt.Color(0, 153, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("QUẢN LÝ PHIẾU GIẢM GIÁ");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách phiếu giảm giá", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel10.setText("Loại Giảm");

        cbbLoaiSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Phần trăm", "Số Tiền" }));
        cbbLoaiSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLoaiSearchActionPerformed(evt);
            }
        });

        jLabel11.setText("Trạng thái");

        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đang diễn ra", "Sắp diễn ra", "Hết hạn" }));
        cbbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTrangThaiActionPerformed(evt);
            }
        });

        jLabel12.setText("Ngày bắt đầu : ");

        jLabel13.setText("Ngày kết thúc : ");

        btnLoc.setBackground(new java.awt.Color(35, 166, 97));
        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLoc.setForeground(new java.awt.Color(255, 255, 255));
        btnLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/1608702_filter_icon.png"))); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        tbPhieuGiamGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã voucher", "Tên voucher", "Số lượng", "Loại", "Giá trị tối thiểu", "Giá trị tối đa", "Ngày BĐ", "Ngày KT", "Trạng thái", "Hành động"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPhieuGiamGia.setRowHeight(35);
        tbPhieuGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPhieuGiamGiaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPhieuGiamGia);
        if (tbPhieuGiamGia.getColumnModel().getColumnCount() > 0) {
            tbPhieuGiamGia.getColumnModel().getColumn(0).setResizable(false);
            tbPhieuGiamGia.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbPhieuGiamGia.getColumnModel().getColumn(3).setResizable(false);
            tbPhieuGiamGia.getColumnModel().getColumn(3).setPreferredWidth(25);
            tbPhieuGiamGia.getColumnModel().getColumn(10).setPreferredWidth(35);
        }

        dateLocNgayBatDau.setDateFormatString("dd-MM-yyyy");

        dateLocNgayKetThuc.setDateFormatString("dd-MM-yyyy");

        btnTimKiem.setBackground(new java.awt.Color(35, 166, 97));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Search_1.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnLamMoiTimKiem.setBackground(new java.awt.Color(35, 166, 97));
        btnLamMoiTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoiTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Refresh.png"))); // NOI18N
        btnLamMoiTimKiem.setText("Làm mới");
        btnLamMoiTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiTimKiemActionPerformed(evt);
            }
        });

        txtSearch.setLabelText("Tìm kiếm theo Mã, Tên phiếu giảm giá");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateLocNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateLocNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLamMoiTimKiem))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbbLoaiSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cbbLoaiSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnTimKiem)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13))
                            .addComponent(dateLocNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateLocNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLoc)
                            .addComponent(btnLamMoiTimKiem))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(354, 354, 354)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLamMoiTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiTimKiemActionPerformed
        // TODO add your handling code here:
        txtSearch.setText("");
        cbbLoaiSearch.setSelectedItem("Tất cả");
        cbbTrangThai.setSelectedItem("Tất cả");
        dateLocNgayBatDau.setDate(null);
        dateLocNgayKetThuc.setDate(null);
        showDataTable(repo.getAll());
    }//GEN-LAST:event_btnLamMoiTimKiemActionPerformed

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        // TODO add your handling code here:
        try {
            String path = "C:\\Users\\ThuPC\\Documents\\DA1";
            JFileChooser jFileChooser = new JFileChooser(path);
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();

            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = (Sheet) wb.createSheet("Account");

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < tbPhieuGiamGia.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tbPhieuGiamGia.getColumnName(i));
                }
                for (int j = 0; j < tbPhieuGiamGia.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tbPhieuGiamGia.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tbPhieuGiamGia.getValueAt(j, k) != null) {
                            cell.setCellValue(tbPhieuGiamGia.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                EventStream.openFile(saveFile.toPath());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnXuatExcelActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        String keyword = txtSearch.getText().trim();
        String trangThai = cbbTrangThai.getSelectedItem().toString();
        String loai = cbbLoaiSearch.getSelectedItem().toString();
        if (loai.equals("Tất cả")) {
            loai = null;
        }
        if (trangThai.equals("Tất cả")) {
            trangThai = null;
        }
        showDataTable(repo.search(keyword, loai, trangThai));

    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed

        String keyword = txtSearch.getText().trim();
        String trangThai = cbbTrangThai.getSelectedItem().toString();
        String loai = cbbLoaiSearch.getSelectedItem().toString();
        if (loai.equals("Tất cả")) {
            loai = null;
        }
        if (trangThai.equals("Tất cả")) {
            trangThai = null;
        }
        showDataTable(repo.search(keyword, loai, trangThai));
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tbPhieuGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPhieuGiamGiaMouseClicked
        // TODO add your handling code here:
        int index = tbPhieuGiamGia.getSelectedRow();
        deatailPhieuGiamGia(index);
    }//GEN-LAST:event_tbPhieuGiamGiaMouseClicked

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        // TODO add your handling code here:
        Date starDate = dateLocNgayBatDau.getDate();
        Date endDate = dateLocNgayKetThuc.getDate();
        if (starDate != null && endDate != null && starDate.compareTo(endDate) > 0) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày kết thúc");
            return;
        } else if (starDate != null && endDate != null && starDate.compareTo(endDate) < 0) {
            showDataTable(repo.locTheoKhoangThoiGian(starDate, endDate));
            return;
        } else {
            showDataTable(repo.locTheoKhoangThoiGian(starDate, endDate));
            return;
        }
    }//GEN-LAST:event_btnLocActionPerformed

    private void cbbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTrangThaiActionPerformed
        // TODO add your handling code here:
        if (cbbTrangThai.getSelectedIndex() == 0) {
            showDataTable(repo.getAll());
        }
    }//GEN-LAST:event_cbbTrangThaiActionPerformed

    private void cbbLoaiSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLoaiSearchActionPerformed
        // TODO add your handling code here:
        if (cbbLoaiSearch.getSelectedIndex() == 0) {
            showDataTable(repo.getAll());
        }
    }//GEN-LAST:event_cbbLoaiSearchActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reSet();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (Auth.isLogin() && !Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Chỉ Quản lý mới có quyền sửa phiếu giảm giá");
            return;
        }
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa không?", "CONFIRM", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        int index = tbPhieuGiamGia.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn phiếu giảm giá để sửa", "Sửa phiếu giảm giá", JOptionPane.ERROR_MESSAGE);
        } else {
            PhieuGiamGia pgg = repo.getAll().get(tbPhieuGiamGia.getSelectedRow());
            if (repo.update(getForm(), pgg.getId())) {
                JOptionPane.showMessageDialog(this, "Sửa thành công");
                showDataTable(repo.getAll());
                this.reSet();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại");
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (Auth.isLogin() && !Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Chỉ Quản lý mới có quyền thêm phiếu giảm giá");
            return;
        }
        int check = JOptionPane.showConfirmDialog(this, "Bạn chắn chắn muốn thêm ?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        if (repo.add(getForm())) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            showDataTable(repo.getAll());
            this.reSet();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại ");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void reSet() {
        txtMaVoucher.setText("");
        txtTenVoucher.setText("");
        txtGiaTriToiThieu.setText("");
        txtGiaTriToiDa.setText("");
        txtSoLuong.setText("");
        txtDieuKien.setText("");
        cbbLoai.setSelectedItem(null);
        txtSoLuong.setText("");
        dateNgayBatDau.setDate(null);
        dateNgayKetThuc.setDate(null);
        dateLocNgayBatDau.setDate(null);
        dateLocNgayKetThuc.setDate(null);
        showDataTable(repo.getAll());
    }

    private void runThreadCheckNgayKetThuc() {
        Thread checkNgayKetThuc = new Thread() {
            @Override
            public void run() {
                while (true) {
                    Date currentDate = new Date();
                    for (PhieuGiamGia pgg : repo.getAll()) {
                        if (currentDate.after(pgg.getNgayKetThuc())) {
                            repo.updateNgayHetHan(pgg.getId());
//                            showDataTable(repo.getAll());
//                            tbPhieuGiamGia.setValueAt("Hết hạn", pgg.getId(), 9);
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        checkNgayKetThuc.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoiTimKiem;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JComboBox<String> cbbLoai;
    private javax.swing.JComboBox<String> cbbLoaiSearch;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private com.toedter.calendar.JDateChooser dateLocNgayBatDau;
    private com.toedter.calendar.JDateChooser dateLocNgayKetThuc;
    private com.toedter.calendar.JDateChooser dateNgayBatDau;
    private com.toedter.calendar.JDateChooser dateNgayKetThuc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbPhieuGiamGia;
    private com.raven.util.TextField txtDieuKien;
    private com.raven.util.TextField txtGiaTriToiDa;
    private com.raven.util.TextField txtGiaTriToiThieu;
    private com.raven.util.TextField txtMaVoucher;
    private com.raven.util.TextField txtSearch;
    private com.raven.util.TextField txtSoLuong;
    private com.raven.util.TextField txtTenVoucher;
    // End of variables declaration//GEN-END:variables

}
