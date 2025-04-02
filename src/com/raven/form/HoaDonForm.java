/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.raven.qrcode.QrCodeHoaDon;
import com.raven.qrcode.QrCodeListener;
import com.raven.repository.HoaDonChiTietRepository;
import com.raven.repository.HoaDonRepository;
import com.raven.repository.LichSuHoaDonRepository;
import com.raven.response.HoaDonChiTietResponse;
import com.raven.response.HoaDonResponse;
import com.raven.response.LichSuHoaDonResponse;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import raven.toast.Notifications;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.text.SimpleDateFormat;

/**
 *
 * @author ADMIN
 */
public class HoaDonForm extends javax.swing.JPanel implements QrCodeListener {

    /**
     * Creates new form HoaDon
     */
    private final HoaDonRepository hoaDonRepository;

    private final HoaDonChiTietRepository hoaDonChiTietRepository;

    private final LichSuHoaDonRepository lichSuHoaDonRepository;

    private final DefaultTableModel dtmHoaDon;

    private final DefaultTableModel dtmHoaDonChiTiet;

    private final DefaultTableModel dtmLichSuHoaDon;

    private ArrayList<HoaDonChiTietResponse> list = new ArrayList<>();

    private ArrayList<HoaDonResponse> lists = new ArrayList<>();

    private ArrayList<HoaDonResponse> currentList;

    private int page = 1;
    private int totalPage = 1;
    private final int limit = 5;

    private String currentKeyword = "";

    private Integer currentTrangThai;
    private Integer currentHinhThuc;
    private Double currentGiaMin;
    private Double currentGiaMax;

    public HoaDonForm() {
        initComponents();
        hoaDonRepository = new HoaDonRepository();
        hoaDonChiTietRepository = new HoaDonChiTietRepository();
        lichSuHoaDonRepository = new LichSuHoaDonRepository();
        dtmHoaDon = (DefaultTableModel) tbHoaDon.getModel();
        dtmHoaDonChiTiet = (DefaultTableModel) tbHoaDonChiTiet.getModel();
        dtmLichSuHoaDon = (DefaultTableModel) tbLichSuHoaDon.getModel();

        ((AbstractDocument) txtGiaTu.getDocument()).setDocumentFilter(new NumberOnlyFilter()); // không cho nhập kí tự
        ((AbstractDocument) txtGiaDen.getDocument()).setDocumentFilter(new NumberOnlyFilter()); // không cho nhập kí tự

        showTableHoaDon(hoaDonRepository.getAllAndPaging(page, limit));
        loadPage();

    }

//    private void showTableHoaDon(ArrayList<HoaDonResponse> lists) {
//        dtmHoaDon.setRowCount(0);
//        AtomicInteger index = new AtomicInteger(1);
//        lists.forEach(s -> dtmHoaDon.addRow(new Object[]{
//            index.getAndIncrement(), s.getMaHD(), s.getMaNV(),
//            s.getTenKH(), s.getDiaChiKH(), s.getSdtKH(), s.getNgayTao(), s.getTongTien(), s.getTrangThaiHD() == 1 ? "Chưa Thanh Toán" : (s.getTrangThaiHD() == 2 ? "Đã Thanh Toán" : "Đã Hủy")
//        }));
//    }
    public void showTableHoaDon(ArrayList<HoaDonResponse> lists) {
        dtmHoaDon.setRowCount(0);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        // Calculate the starting index for the current page
        int startIndex = (page - 1) * limit + 1;
        AtomicInteger index = new AtomicInteger(startIndex);

        lists.forEach(s -> {
            System.out.println("Adding row: " + s.getMaHD()); // Debug: Kiểm tra dữ liệu được thêm vào bảng
            String formattedTongTien = currencyFormatter.format(s.getTongTien());
            String formattedNgayTao = dateFormatter.format(s.getNgayTao());
            dtmHoaDon.addRow(new Object[]{
                index.getAndIncrement(), s.getMaHD(), s.getMaNV(),
                s.getTenKH(), s.getDiaChiKH(), s.getSdtKH(), formattedNgayTao, formattedTongTien,
                s.getTrangThaiHD() == 1 ? "Chưa Thanh Toán"
                : (s.getTrangThaiHD() == 2 ? "Đã Thanh Toán"
                : (s.getTrangThaiHD() == 3 ? "Đã Hủy"
                : (s.getTrangThaiHD() == 4 ? "Chờ Giao Hàng"
                : (s.getTrangThaiHD() == 5 ? "Đang Giao Hàng" : "Không Xác Định"))))});
        });

        // Store the current list for easy access during mouse click event
        this.currentList = lists;
    }

    public void showTableHoaDonChiTiet(ArrayList<HoaDonChiTietResponse> lists) {
        dtmHoaDonChiTiet.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        lists.forEach(s -> {
            String formattedDonGia = currencyFormatter.format(s.getDonGia());
            String formattedThanhTien = currencyFormatter.format(s.getThanhTien());
            dtmHoaDonChiTiet.addRow(new Object[]{
                index.getAndIncrement(), s.getMaHd(), s.getMaCtsp(), s.getTenSP(), s.getSoLuong(), formattedDonGia,
                formattedThanhTien, s.getChatLieu(), s.getKichThuoc(),
                s.getMauSac(), s.getDeGiay(), s.getNSX(), s.getHang(), s.getCoGiay(), s.getKhoiLuong(), s.getXuatXu()
            });
        });
    }

    public void showTableLichSuHoaDon(ArrayList<LichSuHoaDonResponse> lists) {
        dtmLichSuHoaDon.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        lists.forEach(s -> {
            String formattedNgayCapNhat = dateFormatter.format(s.getNgayCapNhat());
            dtmLichSuHoaDon.addRow(new Object[]{
                index.getAndIncrement(), s.getMaNV(), formattedNgayCapNhat, s.getHanhDongNguoiThaoTac()
            });
        });
    }

    private void loadPage() {
        dtmHoaDon.setRowCount(0);

        try {
            int rowCount = hoaDonRepository.getTotalRowCount();
            totalPage = (int) Math.ceil(rowCount / (double) limit);

            // Fetch the correct subset of data for the current page
            ArrayList<HoaDonResponse> lists = hoaDonRepository.getAllAndPaging(page, limit);
            showTableHoaDon(lists);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        lblPage.setText(page + " / " + totalPage);
    }

    private void loadPageSearch(int page) {
        dtmHoaDon.setRowCount(0); // Clear existing data

        try {
            int rowCount = hoaDonRepository.getRowCountSearch(currentKeyword);
            totalPage = (int) Math.ceil(rowCount / (double) limit + 1);

            ArrayList<HoaDonResponse> lists = hoaDonRepository.searchAndPaging(currentKeyword, page, limit);
            showTableHoaDon(lists);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        lblPage.setText(page + " / " + totalPage);
    }

    private void loadPageFilter(int page) {
        dtmHoaDon.setRowCount(0); // Clear existing data

        try {
            int rowCount = hoaDonRepository.getRowCountFilter(currentTrangThai, currentHinhThuc, currentGiaMin, currentGiaMax);
            totalPage = (int) Math.ceil(rowCount / (double) limit + 1);

            ArrayList<HoaDonResponse> lists = hoaDonRepository.filterAndPaging(currentTrangThai, currentHinhThuc, currentGiaMin, currentGiaMax, page, limit);
            showTableHoaDon(lists);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        lblPage.setText(page + " / " + totalPage);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btSearch = new javax.swing.JButton();
        btQuetQR = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbHoaDon = new javax.swing.JTable();
        btClear = new javax.swing.JButton();
        btLoc = new javax.swing.JButton();
        btInHoaDon = new javax.swing.JButton();
        btXuatExel = new javax.swing.JButton();
        lblPage = new javax.swing.JLabel();
        btLast = new javax.swing.JButton();
        btNext = new javax.swing.JButton();
        btLastMin = new javax.swing.JButton();
        btNextMax = new javax.swing.JButton();
        txtGiaTu = new textfield.TextField();
        cboHinhThucThanhToan = new combobox.Combobox();
        cboTinhTrangHoaDon = new combobox.Combobox();
        txtGiaDen = new textfield.TextField();
        txtSearch = new textfield.TextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbHoaDonChiTiet = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbLichSuHoaDon = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 204, 255));
        jLabel1.setText("Hóa Đơn");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa Đơn"));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Search");

        btSearch.setBackground(new java.awt.Color(35, 166, 97));
        btSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btSearch.setForeground(new java.awt.Color(255, 255, 255));
        btSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/img/9042816_page_search_icon.png"))); // NOI18N
        btSearch.setText("Search");
        btSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchActionPerformed(evt);
            }
        });

        btQuetQR.setBackground(new java.awt.Color(35, 166, 97));
        btQuetQR.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btQuetQR.setForeground(new java.awt.Color(255, 255, 255));
        btQuetQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/img/9104276_scan_barcode_qr_qr code_scanner_icon.png"))); // NOI18N
        btQuetQR.setText("Quét QR");
        btQuetQR.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btQuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btQuetQRActionPerformed(evt);
            }
        });

        jLabel5.setText("Tìm Theo Giá");

        tbHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã hóa đơn", "Mã nhân viên", "Tên khách hàng", "Địa chỉ", "SDT", "Ngày tạo", "Tổng Tiền", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbHoaDon.setGridColor(new java.awt.Color(255, 255, 255));
        tbHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbHoaDon);

        btClear.setBackground(new java.awt.Color(35, 166, 97));
        btClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btClear.setForeground(new java.awt.Color(255, 255, 255));
        btClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/img/211882_refresh_icon.png"))); // NOI18N
        btClear.setText("Làm mới");
        btClear.setToolTipText("");
        btClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btClearActionPerformed(evt);
            }
        });

        btLoc.setBackground(new java.awt.Color(35, 166, 97));
        btLoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btLoc.setForeground(new java.awt.Color(255, 255, 255));
        btLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/img/9044379_filter_edit_icon.png"))); // NOI18N
        btLoc.setText("Lọc");
        btLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLocActionPerformed(evt);
            }
        });

        btInHoaDon.setBackground(new java.awt.Color(35, 166, 97));
        btInHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btInHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btInHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/img/bill.png"))); // NOI18N
        btInHoaDon.setText("In Hóa Đơn");
        btInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInHoaDonActionPerformed(evt);
            }
        });

        btXuatExel.setBackground(new java.awt.Color(35, 166, 97));
        btXuatExel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btXuatExel.setForeground(new java.awt.Color(255, 255, 255));
        btXuatExel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/img/excel.png"))); // NOI18N
        btXuatExel.setText("Xuất Exel");
        btXuatExel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btXuatExelActionPerformed(evt);
            }
        });

        lblPage.setText("0");

        btLast.setText("<");
        btLast.setEnabled(false);
        btLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLastActionPerformed(evt);
            }
        });

        btNext.setText(">");
        btNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNextActionPerformed(evt);
            }
        });

        btLastMin.setText("<<");
        btLastMin.setEnabled(false);
        btLastMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLastMinActionPerformed(evt);
            }
        });

        btNextMax.setText(">>");
        btNextMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNextMaxActionPerformed(evt);
            }
        });

        txtGiaTu.setLabelText("Từ");
        txtGiaTu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGiaTuFocusGained(evt);
            }
        });
        txtGiaTu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTuActionPerformed(evt);
            }
        });

        cboHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tiền Mặt", "Chuyển Khoản", "Kết Hợp" }));
        cboHinhThucThanhToan.setSelectedIndex(-1);
        cboHinhThucThanhToan.setLabeText("Hình Thức Thanh Toán");

        cboTinhTrangHoaDon.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tất Cả", "Chưa thanh toán", "Đã thanh toán", "Đã hủy" }));
        cboTinhTrangHoaDon.setSelectedIndex(-1);
        cboTinhTrangHoaDon.setLabeText("Tình Trạng Hóa Đơn");
        cboTinhTrangHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTinhTrangHoaDonActionPerformed(evt);
            }
        });

        txtGiaDen.setLabelText("Đến");
        txtGiaDen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGiaDenFocusGained(evt);
            }
        });

        txtSearch.setLabelText("Mã HĐ - Mã NV - Tên KH - Địa Chỉ - SĐT");
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(17, 17, 17))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btLastMin, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btLast, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(lblPage, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btNext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btNextMax, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(405, 405, 405)
                .addComponent(btInHoaDon)
                .addGap(56, 56, 56)
                .addComponent(btXuatExel)
                .addGap(60, 60, 60))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(btSearch)
                        .addGap(134, 134, 134)
                        .addComponent(btQuetQR, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(196, 196, 196))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cboTinhTrangHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(38, 38, 38)
                        .addComponent(txtGiaTu, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(txtGiaDen, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 356, Short.MAX_VALUE)
                        .addComponent(btClear, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btSearch)
                    .addComponent(btQuetQR, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btClear, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btNext)
                                .addComponent(lblPage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btLast)
                                .addComponent(btLastMin)
                                .addComponent(btNextMax))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btInHoaDon)
                                .addComponent(btXuatExel)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaTu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTinhTrangHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa Đơn Chi Tiết"));

        tbHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã HD", "Mã SPCT", "Tên sản phẩm", "Số lượng", "Đơn Giá", "Tổng tiền", "Chất Liệu", "Kích thước", "Màu sắc", "Đế Giày", "NSX", "Hãng", "Cổ Giầy", "Khối Lượng", "Xuất Xứ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbHoaDonChiTiet);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lịch Sử Hóa Đơn"));

        tbLichSuHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã NV", "Ngày", "Hành động"
            }
        ));
        jScrollPane3.setViewportView(tbLichSuHoaDon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(651, 651, 651)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hóa Đơn", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 838, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(168, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btClearActionPerformed

        clearForm();
        showTableHoaDon(hoaDonRepository.getAllAndPaging(page, limit));
    }//GEN-LAST:event_btClearActionPerformed

    private void tbHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHoaDonMouseClicked
        int selectedRow = tbHoaDon.getSelectedRow();
        if (selectedRow != -1) {
            // Retrieve the id of the selected invoice from the current list
            int id = currentList.get(selectedRow).getId();
            System.out.println("Selected Invoice ID: " + id); // Debugging statement
            showTableHoaDonChiTiet(hoaDonChiTietRepository.getAll(id));
            showTableLichSuHoaDon(lichSuHoaDonRepository.getAll(id));
        }
    }//GEN-LAST:event_tbHoaDonMouseClicked

    private void btSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchActionPerformed
        currentKeyword = txtSearch.getText().trim();
        if (checkSearch()) {
//            showTableHoaDon(hoaDonRepository.search(txtSearch.getText().trim()));

            showTableHoaDon(hoaDonRepository.searchAndPaging(currentKeyword, page, limit));
            page = 1;
            loadPageSearch(page);
        }
//        loadPage();
    }//GEN-LAST:event_btSearchActionPerformed

    private void btLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLocActionPerformed
        // TODO add your handling code here:

        if (txtGiaTu.getText().trim().equalsIgnoreCase("") || txtGiaDen.getText().trim().equalsIgnoreCase("")) {
            txtGiaTu.setText("0");
            txtGiaDen.setText("10000000000");

            txtGiaTu.setForeground(new Color(255, 255, 255));
            txtGiaDen.setForeground(new Color(255, 255, 255));
        }

        currentTrangThai = cboTinhTrangHoaDon.getSelectedIndex();
        currentHinhThuc = cboHinhThucThanhToan.getSelectedIndex();
        currentGiaMin = Double.valueOf(txtGiaTu.getText().trim());
        currentGiaMax = Double.valueOf(txtGiaDen.getText().trim());

        if (checkLoc()) {
//            showTableHoaDon(hoaDonRepository.loc(cboTinhTrangHoaDon.getSelectedIndex(), cboHinhThucThanhToan.getSelectedIndex(), Double.valueOf(txtGiaTu.getText().trim()), Double.valueOf(txtGiaDen.getText().trim())));
            showTableHoaDon(hoaDonRepository.filterAndPaging(currentTrangThai, currentHinhThuc, currentGiaMin, currentGiaMax, page, limit));
            page = 1;
            loadPageFilter(page);
        }
        btNext.setEnabled(true);
        btNextMax.setEnabled(true);
    }//GEN-LAST:event_btLocActionPerformed

    private void btNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNextActionPerformed

        if (page < totalPage) {
            page++;
            loadPage();
        }
        checkButtonPage();
//        loadPage();
//        if (totalPage > 1) {
//            btNext.setEnabled(true);
//        }
    }//GEN-LAST:event_btNextActionPerformed

    private void btLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLastActionPerformed

        if (page > 1) {
            page--;
            loadPage();
        }
        checkButtonPage();
    }//GEN-LAST:event_btLastActionPerformed

    private void btLastMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLastMinActionPerformed

        page = 1;
        loadPage();
        checkButtonPage();
    }//GEN-LAST:event_btLastMinActionPerformed

    private void btNextMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNextMaxActionPerformed

        page = totalPage;
        loadPage();
        checkButtonPage();
    }//GEN-LAST:event_btNextMaxActionPerformed

    private void cboTinhTrangHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTinhTrangHoaDonActionPerformed

        if (cboTinhTrangHoaDon.getSelectedIndex() == 0) {
            showTableHoaDon(hoaDonRepository.getAllAndPaging(page, limit));
            loadPage();
        }

    }//GEN-LAST:event_cboTinhTrangHoaDonActionPerformed

    private void btXuatExelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btXuatExelActionPerformed

        int selectedRow = tbHoaDon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xuất!");
            return;
        }

        int check = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xuất Excel?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            Workbook workbook = new XSSFWorkbook();
            try {
                Sheet sheet1 = workbook.createSheet("HoaDon");
                exportSelectedRowToSheet(tbHoaDon, selectedRow, sheet1);

                Sheet sheet2 = workbook.createSheet("HoaDonChiTiet");
                exportTableToSheet(tbHoaDonChiTiet, sheet2);

                try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    workbook.write(outputStream);
                }
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Xuất thành công");
                // JOptionPane.showMessageDialog(this, "Xuất thành công");
            } catch (Exception e) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Xuất thất bại");
                // JOptionPane.showMessageDialog(this, "Xuất thất bại");
                e.printStackTrace();
            } finally {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }//GEN-LAST:event_btXuatExelActionPerformed

    private void txtGiaTuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGiaTuFocusGained
        // TODO add your handling code here:
        txtGiaTu.setText("");
        txtGiaTu.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_txtGiaTuFocusGained

    private void txtGiaDenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGiaDenFocusGained
        // TODO add your handling code here:
        txtGiaDen.setText("");
        txtGiaDen.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_txtGiaDenFocusGained

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusGained
        // TODO add your handling code here:
        txtSearch.setText("");
        loadPage();
        btNext.setEnabled(true);
        btNextMax.setEnabled(true);
    }//GEN-LAST:event_txtSearchFocusGained

    private void btQuetQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btQuetQRActionPerformed
        // TODO add your handling code here:
        QrCodeHoaDon qrCodeHoaDon = new QrCodeHoaDon();
        qrCodeHoaDon.setQrCodeListener(this);
        qrCodeHoaDon.setVisible(true);
    }//GEN-LAST:event_btQuetQRActionPerformed

    private void btInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInHoaDonActionPerformed

        int selectedRow = tbHoaDon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để in!");
            return;
        }
        int check = JOptionPane.showConfirmDialog(this, "Bạn chắn chắn muốn in hóa đơn ?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        String directoryPath = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int x = j.showSaveDialog(this);
        if (x == JFileChooser.APPROVE_OPTION) {
            directoryPath = j.getSelectedFile().getPath();
        } else {
            return; // Người dùng hủy bỏ
        }

        String fileName = JOptionPane.showInputDialog(this, "Nhập tên file (không cần .pdf):");
        if (fileName == null || fileName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên file không hợp lệ!");
            return;
        }

        String filePath = directoryPath + "/" + fileName + ".pdf";

        Document doc = new Document();
//        HoaDonResponse selectedHoaDon = lists.get(selectedRow);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(filePath));

            doc.open();

            // Thêm tiêu đề
            Font titleFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph title = new Paragraph("BILL OF SALE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);

            doc.add(new Paragraph(" ")); // Thêm khoảng trống
            Paragraph storeInfo = new Paragraph("GROUP2.com\nNo 00000 Address Line One\nAddress Line 02 SRI LANKA\nwww.facebook.com/SD19310\n+947000000000");
            storeInfo.setAlignment(Element.ALIGN_CENTER);
            doc.add(storeInfo);

            doc.add(new Paragraph(" ")); // Thêm khoảng trống
            doc.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            // Thêm bảng thông tin khách hàng
//            PdfPTable customerTable = new PdfPTable(6);
//            customerTable.setWidthPercentage(100);
//            customerTable.setSpacingBefore(10f);
//            customerTable.setSpacingAfter(10f);
//
//            float[] customerColumnWidths = {2f, 4f, 3f, 2f, 2f, 2f};
//            customerTable.setWidths(customerColumnWidths);
//
//            BaseFont baseFont = BaseFont.createFont("src/com/raven/style/font-times-new-roman/times-new-roman-14.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            Font font = new Font(baseFont, 12);
//
//            customerTable.addCell(new PdfPCell(new Paragraph("Mã hóa đơn", font)));
//            customerTable.addCell(new PdfPCell(new Paragraph("Tên khách hàng", font)));
//            customerTable.addCell(new PdfPCell(new Paragraph("Địa chỉ", font)));
//            customerTable.addCell(new PdfPCell(new Paragraph("SDT", font)));
//
//            customerTable.addCell(new PdfPCell(new Paragraph("Mã Nhân Viên", font)));
//            customerTable.addCell(new PdfPCell(new Paragraph("Ngày Tạo", font)));
//
//            // Lấy thông tin từ hàng được chọn
//            String maHoaDon = tbHoaDon.getValueAt(selectedRow, 1).toString();
//            String tenKhachHang = tbHoaDon.getValueAt(selectedRow, 3).toString();
//            String diaChi = tbHoaDon.getValueAt(selectedRow, 4).toString();
//            String sdt = tbHoaDon.getValueAt(selectedRow, 5).toString();
//            String maNV = tbHoaDon.getValueAt(selectedRow, 2).toString();
//            String ngayTao = tbHoaDon.getValueAt(selectedRow, 6).toString();
//
//            customerTable.addCell(new PdfPCell(new Paragraph(maHoaDon, font)));
//            customerTable.addCell(new PdfPCell(new Paragraph(tenKhachHang, font)));
//            customerTable.addCell(new PdfPCell(new Paragraph(diaChi, font)));
//            customerTable.addCell(new PdfPCell(new Paragraph(sdt, font)));
//            customerTable.addCell(new PdfPCell(new Paragraph(maNV, font)));
//            customerTable.addCell(new PdfPCell(new Paragraph(ngayTao, font)));
//
//            doc.add(customerTable);
//
//            doc.add(new Paragraph(" ")); // Thêm khoảng trống

            HoaDonResponse selectedHoaDon = currentList.get(selectedRow);

//            String maHoaDon = tbHoaDon.getValueAt(selectedRow, 1).toString();
//            String tenKhachHang = tbHoaDon.getValueAt(selectedRow, 3).toString();
//            String diaChi = tbHoaDon.getValueAt(selectedRow, 4).toString();
//            String sdt = tbHoaDon.getValueAt(selectedRow, 5).toString();
//            String maNV = tbHoaDon.getValueAt(selectedRow, 2).toString();
//            String ngayTao = tbHoaDon.getValueAt(selectedRow, 6).toString();
//
//            BaseFont baseFont = BaseFont.createFont("src/com/raven/style/font-times-new-roman/times-new-roman-14.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            Font font = new Font(baseFont, 12);
//            Font boldFont = new Font(baseFont, 12, Font.BOLD);
//
//            // Create a table with two columns
//            PdfPTable customerTable = new PdfPTable(4);
//            customerTable.setWidthPercentage(100);
//            customerTable.setSpacingBefore(10f);
//            customerTable.setSpacingAfter(10f);
//
//            float[] customerColumnWidths = {1f, 2f, 1f, 2f};
//            customerTable.setWidths(customerColumnWidths);
//
//            // Create a cell with no border
//            PdfPCell cell;
//
//            // Add the customer information in a layout similar to the image
//            cell = new PdfPCell(new Paragraph("Mã HĐ", boldFont));
//            cell.setBorder(PdfPCell.NO_BORDER);
//            customerTable.addCell(cell);
//            cell = new PdfPCell(new Paragraph(maHoaDon, font));
//            cell.setBorder(PdfPCell.NO_BORDER);
//            customerTable.addCell(cell);
//            cell = new PdfPCell(new Paragraph("Thu Ngân", boldFont));
//            cell.setBorder(PdfPCell.NO_BORDER);
//            customerTable.addCell(cell);
//            cell = new PdfPCell(new Paragraph(maNV, font));
//            cell.setBorder(PdfPCell.NO_BORDER);
//            customerTable.addCell(cell);
//
//            cell = new PdfPCell(new Paragraph("Tên Khách", boldFont));
//            cell.setBorder(PdfPCell.NO_BORDER);
//            customerTable.addCell(cell);
//            cell = new PdfPCell(new Paragraph(tenKhachHang, font));
//            cell.setBorder(PdfPCell.NO_BORDER);
//            customerTable.addCell(cell);
//            cell = new PdfPCell(new Paragraph("Ngày Tạo", boldFont));
//            cell.setBorder(PdfPCell.NO_BORDER);
//            customerTable.addCell(cell);
//            cell = new PdfPCell(new Paragraph(ngayTao, font));
//            cell.setBorder(PdfPCell.NO_BORDER);
//            customerTable.addCell(cell);
//
//            // Add the table to the document
//            doc.add(customerTable);
            String maHoaDon = tbHoaDon.getValueAt(selectedRow, 1).toString();
            String tenKhachHang = tbHoaDon.getValueAt(selectedRow, 3).toString();
            String diaChi = tbHoaDon.getValueAt(selectedRow, 4).toString();
            String sdt = tbHoaDon.getValueAt(selectedRow, 5).toString();
            String maNV = tbHoaDon.getValueAt(selectedRow, 2).toString();
            String ngayTao = tbHoaDon.getValueAt(selectedRow, 6).toString();

            BaseFont baseFont = BaseFont.createFont("src/com/raven/style/font-times-new-roman/times-new-roman-14.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 12);
            Font boldFont = new Font(baseFont, 12, Font.BOLD);

// Create a table with two columns
            PdfPTable customerTable = new PdfPTable(4);
            customerTable.setWidthPercentage(100);
            customerTable.setSpacingBefore(10f);
            customerTable.setSpacingAfter(10f);

            float[] customerColumnWidths = {1f, 2f, 1f, 2f};
            customerTable.setWidths(customerColumnWidths);

// Create a cell with no border
            PdfPCell cell;

// Add the customer information in a layout similar to the image
            cell = new PdfPCell(new Paragraph("Mã HĐ", boldFont));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell(new Paragraph(maHoaDon, font));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell(new Paragraph("Thu Ngân", boldFont));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell(new Paragraph(maNV, font));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);

            cell = new PdfPCell(new Paragraph("Tên Khách", boldFont));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell(new Paragraph(tenKhachHang, font));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell(new Paragraph("Ngày Tạo", boldFont));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell(new Paragraph(ngayTao, font));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);

            cell = new PdfPCell(new Paragraph("Địa chỉ", boldFont));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell(new Paragraph(diaChi, font));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);

            cell = new PdfPCell(new Paragraph("SDT", boldFont));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell(new Paragraph(sdt, font));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);

// Add the table to the document
            doc.add(customerTable);

            doc.add(new Paragraph(" ")); // Add a blank line for spacing

            doc.add(new Paragraph(" ")); // Add a blank line for spacing
            // Thêm bảng thông tin hóa đơn
            PdfPTable table = new PdfPTable(11);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths = {1f, 2f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
            table.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("STT", font));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Product's name", font));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Material", font));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Size", font));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Color", font));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Sole", font));
            PdfPCell cell7 = new PdfPCell(new Paragraph("The firm", font));
            PdfPCell cell8 = new PdfPCell(new Paragraph("Shoe collar", font));
            PdfPCell cell9 = new PdfPCell(new Paragraph("Mass", font));
            PdfPCell cell10 = new PdfPCell(new Paragraph("Quantity", font));
            PdfPCell cell11 = new PdfPCell(new Paragraph("Total Amount", font));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell8);
            table.addCell(cell9);
            table.addCell(cell10);
            table.addCell(cell11);

            for (int i = 0; i < tbHoaDonChiTiet.getRowCount(); i++) {
                String stt = tbHoaDonChiTiet.getValueAt(i, 0).toString();
                String productName = tbHoaDonChiTiet.getValueAt(i, 3).toString();
                String material = tbHoaDonChiTiet.getValueAt(i, 7).toString();
                String size = tbHoaDonChiTiet.getValueAt(i, 8).toString();
                String color = tbHoaDonChiTiet.getValueAt(i, 9).toString();
                String sole = tbHoaDonChiTiet.getValueAt(i, 10).toString();
                String theFirm = tbHoaDonChiTiet.getValueAt(i, 12).toString();
                String shoeCollar = tbHoaDonChiTiet.getValueAt(i, 13).toString();
                String mass = tbHoaDonChiTiet.getValueAt(i, 14).toString();
                String quantity = tbHoaDonChiTiet.getValueAt(i, 4).toString();
                String totalAmount = tbHoaDonChiTiet.getValueAt(i, 6).toString();

                table.addCell(new PdfPCell(new Paragraph(stt, font)));
                table.addCell(new PdfPCell(new Paragraph(productName, font)));
                table.addCell(new PdfPCell(new Paragraph(material, font)));
                table.addCell(new PdfPCell(new Paragraph(size, font)));
                table.addCell(new PdfPCell(new Paragraph(color, font)));
                table.addCell(new PdfPCell(new Paragraph(sole, font)));
                table.addCell(new PdfPCell(new Paragraph(theFirm, font)));
                table.addCell(new PdfPCell(new Paragraph(shoeCollar, font)));
                table.addCell(new PdfPCell(new Paragraph(mass, font)));
                table.addCell(new PdfPCell(new Paragraph(quantity, font)));
                table.addCell(new PdfPCell(new Paragraph(totalAmount, font)));
            }

            doc.add(table);

            doc.add(new Paragraph(" ")); // Thêm khoảng trống

//            HoaDonResponse selectedHoaDon = currentList.get(selectedRow);
            String thanhTien = String.valueOf(selectedHoaDon.getTongTien());
            String tienChietKhau = String.valueOf(selectedHoaDon.getIdKH());

            Double tienMat = selectedHoaDon.getTienMat();
            Double tienChuyenKhoan = selectedHoaDon.getTienChuyenKhoan();
            if (tienMat == null) {
                tienMat = 0.0;
            }
            if (tienChuyenKhoan == null) {
                tienChuyenKhoan = 0.0;
            }
            String tienKhachDua = String.valueOf(tienMat + tienChuyenKhoan);

            Integer phuongThucThanhToan = selectedHoaDon.getTenHTTT();
            String pttt;
            if (phuongThucThanhToan == 1) {
                pttt = "Tiền mặt";
            } else if (phuongThucThanhToan == 2) {
                pttt = "Chuyển khoản";
            } else {
                pttt = "Kết hợp";
            }

            // Format currency in VND
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            Paragraph paymentInfo = new Paragraph();
            paymentInfo.add(new Paragraph("Thành Tiền: " + currencyFormat.format(Double.parseDouble(thanhTien)), font));
            paymentInfo.add(new Paragraph("Tiền Chiết Khấu: " + currencyFormat.format(Double.parseDouble(tienChietKhau)), font));
            paymentInfo.add(new Paragraph("Thành Tiền Sau Khi Giảm: " + currencyFormat.format(Double.parseDouble(thanhTien) - Double.parseDouble(tienChietKhau)), font));
            paymentInfo.add(new Paragraph("Tiền Khách Đưa: " + currencyFormat.format(Double.parseDouble(tienKhachDua)), font));
            paymentInfo.add(new Paragraph("Phương Thức Thanh Toán: " + pttt, font));
            doc.add(paymentInfo);

            doc.add(new Paragraph(" ")); // Thêm khoảng trống
            doc.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            // Thêm QR code
            try {
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(maHoaDon, BarcodeFormat.QR_CODE, 200, 200);

                ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
                byte[] pngData = pngOutputStream.toByteArray();

                Image qrCode = Image.getInstance(pngData);
                qrCode.setAlignment(Element.ALIGN_CENTER);
                qrCode.scaleAbsolute(160, 160);
                doc.add(qrCode);
            } catch (WriterException | IOException e) {
                e.printStackTrace();
            }
            //size qr, number (dạng)
            // 200 : number 1
            // 180 : number 2
            // 

            // Thêm lời cảm ơn
            Paragraph thankYou = new Paragraph("THANK YOU COME AGAIN\n"
                    + "★★★★**★★★★★★★★＊★★★★★\n"
                    + "SOFTWARE BY: GROUP 2\n"
                    + "CONTACT: group2@SD19310.com");
            thankYou.setAlignment(Element.ALIGN_CENTER);
            doc.add(thankYou);

        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(HoaDonForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HoaDonForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            doc.close();
        }

        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Export thành công");

    }//GEN-LAST:event_btInHoaDonActionPerformed

    private void txtGiaTuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTuActionPerformed

    private boolean checkSearch() {

        if (txtSearch.getText().trim().equals("")) {
//            JOptionPane.showMessageDialog(this, "tìm kiếm đang trống");
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "tìm kiếm đang trống");
            return false;
        }

        if (txtSearch.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "tìm kiếm đang trống");
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Nhập đúng định dạng");
            return false;
        }

        return true;
    }

    private boolean checkLoc() {

        Double giaMin = Double.valueOf(txtGiaTu.getText().trim());
        Double giaMax = Double.valueOf(txtGiaDen.getText().trim());

        if (giaMin > giaMax) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "giá min không thể nhỏ hơn giá max");
//            JOptionPane.showMessageDialog(this, "giá min không thể nhỏ hơn giá max");
            return false;
        }

        return true;
    }

    private void checkButtonPage() {
        if (page == 1) {
            btLast.setEnabled(false);
        } else {
            btLast.setEnabled(true);
        }

        if (page >= totalPage) {
            btNext.setEnabled(false);
        } else {
            btNext.setEnabled(true);
        }

        if (page == 1) {
            btLastMin.setEnabled(false);
        } else {
            btLastMin.setEnabled(true);
        }

        if (page >= totalPage) {
            btNextMax.setEnabled(false);
        } else {
            btNextMax.setEnabled(true);
        }
    }

    private void clearForm() {
        cboHinhThucThanhToan.setSelectedIndex(-1);
        cboTinhTrangHoaDon.setSelectedIndex(-1);

        txtSearch.setText("");
        txtGiaDen.setText("");
        txtGiaTu.setText("");

        dtmHoaDonChiTiet.setRowCount(0);
        dtmLichSuHoaDon.setRowCount(0);

//        btLast.setEnabled(true);
        btNext.setEnabled(true);
        btNextMax.setEnabled(true);
//        btLastMin.setEnabled(true);

        loadPage();
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Clear thành công");
    }

    private void exportTableToSheet(JTable table, Sheet sheet) {
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnCount; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(table.getColumnName(i));
        }

        // Tạo các dòng dữ liệu
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < columnCount; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(table.getValueAt(i, j).toString());
            }
        }
    }

    private void exportSelectedRowToSheet(JTable table, int selectedRow, Sheet sheet) {
        // Create the header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < table.getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(table.getColumnName(i));
        }

        // Create the data row for the selected row
        Row row = sheet.createRow(1);
        for (int j = 0; j < table.getColumnCount(); j++) {
            Cell cell = row.createCell(j);
            Object value = table.getValueAt(selectedRow, j);
            if (value instanceof String) {
                cell.setCellValue((String) value);
            } else if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else if (value instanceof Double) {
                cell.setCellValue((Double) value);
            } else {
                cell.setCellValue(value.toString());
            }
        }
    }

    @Override
    public void onQrCodeRead(String qrCode) {
        txtSearch.setText(qrCode);
        System.out.println("QR Code read: " + qrCode); // Debug: Kiểm tra mã QR được quét
        updateTable(qrCode);
    }

    private void updateTable(String qrCode) {
        showTableHoaDon(hoaDonRepository.searchAndPaging(txtSearch.getText().trim(), page, limit));
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "QR Code thành công");
    }

    // class set kí tự extends tới DocumentFilter/  chỉ cho nhập số
    class NumberOnlyFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("\\d*")) { // Allow empty string and digits
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches("\\d*")) { // Allow empty string and digits
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btClear;
    private javax.swing.JButton btInHoaDon;
    private javax.swing.JButton btLast;
    private javax.swing.JButton btLastMin;
    private javax.swing.JButton btLoc;
    private javax.swing.JButton btNext;
    private javax.swing.JButton btNextMax;
    private javax.swing.JButton btQuetQR;
    private javax.swing.JButton btSearch;
    private javax.swing.JButton btXuatExel;
    private combobox.Combobox cboHinhThucThanhToan;
    private combobox.Combobox cboTinhTrangHoaDon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblPage;
    private javax.swing.JTable tbHoaDon;
    private javax.swing.JTable tbHoaDonChiTiet;
    private javax.swing.JTable tbLichSuHoaDon;
    private textfield.TextField txtGiaDen;
    private textfield.TextField txtGiaTu;
    private textfield.TextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
