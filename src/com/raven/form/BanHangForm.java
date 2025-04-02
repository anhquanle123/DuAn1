/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import com.raven.logic.*;
import AddtionalForm.ChooseKhachHang;
import AddtionalForm.ChooseNhanVien;
import com.raven.entity.PhieuGiamGia;
import com.raven.logic.RandomStringGenerator;
import com.raven.repository.sanpham.repo_chitietsanpham;
import com.raven.repository.sanpham.thuoctinh.repo_chatlieu;
import com.raven.repository.sanpham.thuoctinh.repo_cogiay;
import com.raven.repository.sanpham.thuoctinh.repo_degiay;
import com.raven.repository.sanpham.thuoctinh.repo_hang;
import com.raven.repository.sanpham.thuoctinh.repo_khoiluong;
import com.raven.repository.sanpham.thuoctinh.repo_kichthuoc;
import com.raven.repository.sanpham.thuoctinh.repo_nsx;
import com.raven.repository.sanpham.thuoctinh.repo_xuatxu;
import interfaces.interface_repo_thuoctinh;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import com.raven.model.sanpham_container.SanPhamChiTiet;
import com.raven.qrcode.QrCodeBanHang;
import com.raven.qrcode.QrCodeListener;
import com.raven.repository.BanHangRepository;
import com.raven.repository.HoaDonChiTietRepository;
import com.raven.repository.HoaDonRepository;
import com.raven.repository.PhieuGiamGiaRespository;
import com.raven.response.HoaDonChiTietResponse;
import com.raven.response.HoaDonResponse;
import com.raven.respose.NhanVienResponse;
import com.raven.respose.SanPhamChiTietRepose;
import com.raven.util.Auth;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import models.sanpham_container.thuoctinh.ChatLieu;
import models.sanpham_container.thuoctinh.CoGiay;
import models.sanpham_container.thuoctinh.DeGiay;
import models.sanpham_container.thuoctinh.Hang;
import models.sanpham_container.thuoctinh.KhoiLuong;
import models.sanpham_container.thuoctinh.KichThuoc;
import models.sanpham_container.thuoctinh.MauSac;
import models.sanpham_container.thuoctinh.NhaSanXuat;
import models.sanpham_container.thuoctinh.XuatXu;
import raven.toast.Notifications;
import repositories.sanpham.thuoctinh.repo_mausac;
import swing.TextField;
//////////
import AddtionalForm.models.ThuocTinh;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.raven.entity.NhanVien;
import com.raven.entity.PhieuGiamGia;
import com.raven.logic.RandomStringGenerator;
import com.raven.logic.ShowMessageCustom;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.khachhang_container.KhachHang;
import interfaces.EventFormChoose;
import java.awt.event.ActionEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
/////////

/**
 *
 * @author ADMIN
 */
public class BanHangForm extends javax.swing.JPanel implements EventFormChoose {

    NhanVienResponse nhanVienVanChuyen = null;

    TableRowSorter<TableModel> sorterSPCT;

    DefaultTableModel modelSPCT;

    private DefaultTableModel dtmHoaDonChiTiet;

    private DefaultTableModel dtmSanPhamChiTiet;

    ArrayList<SanPhamChiTiet> listSPCT;

    repo_chitietsanpham repo_chitietsanpham = new repo_chitietsanpham();

    List<JComboBox<String>> comboBoxes = null;

    private final DefaultTableModel dtmHoaDon;

    private HoaDonRepository repoHD = new HoaDonRepository();

    private BanHangRepository repoBh = new BanHangRepository();

    private HoaDonResponse hdRepose;

    int id = Auth.user.getId();

    private Integer indexHoaDonSelected;

    private HoaDonChiTietRepository hoaDonChiTietRepository = new HoaDonChiTietRepository();

    private PhieuGiamGiaRespository phieuGiamGiaRespository = new PhieuGiamGiaRespository();

    // private Set<String> addedVouchers;
    private PhieuGiamGia voucherss = new PhieuGiamGia();

    private ArrayList<HoaDonResponse> currentList;

    /**
     * Creates new form BanHangForm
     */
    public BanHangForm() {
        initComponents();
        // this.addedVouchers = new HashSet<>();

        dtmHoaDon = (DefaultTableModel) tbHoaDon.getModel();
        dtmHoaDonChiTiet = (DefaultTableModel) tbGioHang.getModel();
        dtmSanPhamChiTiet = (DefaultTableModel) tbDanhSachSanPham.getModel();
        comboBoxes = Arrays.asList(cboxHang, cboxChatLieu, cboxDeGiay, cboxKhoiLuong,
                cboxKichThuoc, cboxNhaSanXuat, cboxXuatXu, cboxCoGiay, cboxMauSac);

        modelSPCT = (DefaultTableModel) tbDanhSachSanPham.getModel();
        sorterSPCT = new TableRowSorter<>(modelSPCT);
        tbDanhSachSanPham.setRowSorter(sorterSPCT);

        for (JComboBox<String> comboBox : comboBoxes) {
            comboBox.addActionListener(e -> applyFilters());
        }

        loadComboboxThuocTinh();
        addColumnNamesToComboboxSearch();
        // loadListChiTietSanPhamToTable();
        initActionSorter();
        showTableHoaDon(repoBh.getAllBanHang(id));
        // lay ra dong dang chon trong bang hoa don
        indexHoaDonSelected = tbHoaDon.getSelectedRow();
        showTableSanPham(repo_chitietsanpham.loadSanPhamChiTietFromDb());

        initOnlyNumber();
        initDocumentListener();

    }

    public void select() {
        indexHoaDonSelected = tbHoaDon.getSelectedRow();
    }

    public void showTableHoaDon(ArrayList<HoaDonResponse> lists) {
        dtmHoaDon.setRowCount(0);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        AtomicInteger index = new AtomicInteger(1);

        String tenNvDangNhap = Auth.user.getHoTenNv();

        lists.forEach(s -> {
            System.out.println("Adding row: " + s.getMaHD()); // Debug: Kiểm tra dữ liệu được thêm vào bảng
            String formattedTongTien = currencyFormatter.format(s.getTongTien());
            String formattedNgayTao = dateFormatter.format(s.getNgayTao());
            dtmHoaDon.addRow(new Object[]{
                index.getAndIncrement(),
                s.getMaHD(),
                tenNvDangNhap,
                s.getTenKH(),
                s.getSdtKH(),
                formattedTongTien,
                formattedNgayTao,
                s.getTrangThaiHD() == 1 ? "Chưa Thanh Toán"
                : (s.getTrangThaiHD() == 2 ? "Đã Thanh Toán"
                : (s.getTrangThaiHD() == 3 ? "Đã Hủy"
                : (s.getTrangThaiHD() == 4 ? "Chờ Giao Hàng"
                : (s.getTrangThaiHD() == 5 ? "Đang Giao Hàng" : "Không Xác Định"))))
            });
        });
    }

    public void showTableHoaDonCHiTiet(ArrayList<HoaDonChiTietResponse> lists) {
        dtmHoaDonChiTiet.setRowCount(0);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        AtomicInteger index = new AtomicInteger(1);

        lists.forEach(s -> {
            String formattedTongTien = currencyFormatter.format(s.getThanhTien());

            dtmHoaDonChiTiet.addRow(new Object[]{
                index.getAndIncrement(),
                s.getMaCtsp(),
                s.getTenSP(),
                s.getHang(),
                s.getChatLieu(),
                s.getKichThuoc(),
                s.getMauSac(),
                s.getDeGiay(),
                s.getKhoiLuong(),
                s.getXuatXu(),
                s.getNSX(),
                s.getCoGiay(),
                s.getDonGia(),
                s.getSoLuong(),
                formattedTongTien,});
        });
    }

    public void showTableSanPham(ArrayList<SanPhamChiTietRepose> lists) {
        dtmSanPhamChiTiet.setRowCount(0);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        AtomicInteger index = new AtomicInteger(1);

        lists.forEach(s -> {
            String formattedTongTien = currencyFormatter.format(s.getDonGia());

            dtmSanPhamChiTiet.addRow(new Object[]{
                index.getAndIncrement(),
                s.getMaSP(),
                s.getTenSp(),
                s.getHang(),
                s.getChatLieu(),
                s.getKichThuoc(),
                s.getMauSac(),
                s.getDeGiay(),
                s.getKhoiLuong(),
                s.getXuatXu(),
                s.getNsx(),
                s.getCoGiay(),
                s.getSoLuong(),
                formattedTongTien
            });
        });
    }

    public void validateTaiQuay() {
        cboHinhThucThanhToanTq.setSelectedIndex(0);
        txtTienMatTq.setText("");
        txtTienChuyenKhoanTq.setText("");
    }

    public void validateDatHang() {
        txtPhiShipdh.setText("");
        cboHinhThucThanhToanDh.setSelectedIndex(0);
        txtTienChuyenKhoanTq.setText("");
        txtTienMatDh.setText("");
    }

    public void loadComboboxThuocTinh() {
        loadListThuocTinh(cboxHang, Hang.class, new repo_hang());
        loadListThuocTinh(cboxChatLieu, ChatLieu.class, new repo_chatlieu());
        loadListThuocTinh(cboxDeGiay, DeGiay.class, new repo_degiay());
        loadListThuocTinh(cboxKhoiLuong, KhoiLuong.class, new repo_khoiluong());
        loadListThuocTinh(cboxKichThuoc, KichThuoc.class, new repo_kichthuoc());
        loadListThuocTinh(cboxNhaSanXuat, NhaSanXuat.class, new repo_nsx());
        loadListThuocTinh(cboxXuatXu, XuatXu.class, new repo_xuatxu());
        loadListThuocTinh(cboxCoGiay, CoGiay.class, new repo_cogiay());
        loadListThuocTinh(cboxMauSac, MauSac.class, new repo_mausac());
    }

    public <T> int loadListThuocTinh(JComboBox<String> comboBox, Class<T> clazz, interface_repo_thuoctinh repo) {
        resetComboBox(comboBox);
        comboBox.addItem("---Tất cả---");
        ArrayList<Object> listThuocTinh = repo.loadListThuocTinhFromDb();
        for (Object i : listThuocTinh) {
            try {
                T castedT = clazz.cast(i);

                Method getMaThuocTinh = clazz.getMethod("getIdThuocTinh");
                String maThuocTinh = (String) getMaThuocTinh.invoke(castedT);

                Method getTenThuocTinh = clazz.getMethod("getThuocTinh");
                String tenThuocTinh = (String) getTenThuocTinh.invoke(castedT);

                ThuocTinh thuocTinh = new ThuocTinh(maThuocTinh, tenThuocTinh);

                comboBox.addItem(thuocTinh.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listThuocTinh.size();
    }

    class NumberOnlyFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && isNumeric(string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text != null && isNumeric(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }

        private boolean isNumeric(String str) {
            return str.matches("\\d+"); // Chỉ cho phép số không âm
        }
    }

    public void initOnlyNumber() {
        ((AbstractDocument) txtTienMatTq.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtTienMatDh.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtTienChuyenKhoanTq.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtTienChuyenKhoanDh.getDocument()).setDocumentFilter(new NumberOnlyFilter());
    }

    public void setDocumentListenter(textfield.TextField... textFields) {
        for (textfield.TextField i : textFields) {
            i.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    handleTongTien();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    handleTongTien();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    handleTongTien();
                }
            });
        }
    }

    public void initDocumentListener() {
        setDocumentListenter(txtTienMatTq, txtTienChuyenKhoanTq, txtTienMatDh, txtTienChuyenKhoanDh);
    }

    public void handleTongTien() {
        try {
            indexHoaDonSelected = tbHoaDon.getSelectedRow();
            if (indexHoaDonSelected != -1) {
                if (jTabbedPane1.getSelectedIndex() == 0) {
                    HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);
                    double tongTien = repose.getTongTien();

                    if (cboHinhThucThanhToanTq.getSelectedIndex() == 0) {
                        double tienMat = Double.parseDouble(txtTienMatTq.getText());
                        double tienThua = tienMat - tongTien;
                        if (tienThua > 0) {
                            lblTienThuaTq.setText(tienThua + "đ");
                        } else {
                            lblTienThuaTq.setText("0đ");
                        }
                    } else if (cboHinhThucThanhToanTq.getSelectedIndex() == 1) {
                        double tienChuyenKhoan = Double.parseDouble(txtTienChuyenKhoanTq.getText());
                        double tienThua = tienChuyenKhoan - tongTien;
                        if (tienThua > 0) {
                            lblTienThuaTq.setText(tienThua + "đ");
                        } else {
                            lblTienThuaTq.setText("0đ");
                        }
                    } else if (cboHinhThucThanhToanTq.getSelectedIndex() == 2) {
                        double tienMat = Double.parseDouble(txtTienMatTq.getText());
                        double tienChuyenKhoan = Double.parseDouble(txtTienChuyenKhoanTq.getText());
                        double tienThua = tienMat + tienChuyenKhoan - tongTien;
                        if (tienThua > 0) {
                            lblTienThuaTq.setText(tienThua + "đ");
                        } else {
                            lblTienThuaTq.setText("0đ");
                        }
                    }
                } else {
                    HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);
                    double tongTien = repose.getTongTien();

                    if (cboHinhThucThanhToanDh.getSelectedIndex() == 0) {
                        double tienMat = Double.parseDouble(txtTienMatDh.getText());
                        double tienThua = tienMat - tongTien;
                        if (tienThua > 0) {
                            lblTienThuaDh.setText(tienThua + "đ");
                        } else {
                            lblTienThuaDh.setText("0đ");
                        }
                    } else if (cboHinhThucThanhToanDh.getSelectedIndex() == 1) {
                        double tienChuyenKhoan = Double.parseDouble(txtTienChuyenKhoanDh.getText());
                        double tienThua = tienChuyenKhoan - tongTien;
                        if (tienThua > 0) {
                            lblTienThuaDh.setText(tienThua + "đ");
                        } else {
                            lblTienThuaDh.setText("0đ");
                        }
                    } else if (cboHinhThucThanhToanDh.getSelectedIndex() == 2) {
                        double tienMat = Double.parseDouble(txtTienMatDh.getText());
                        double tienChuyenKhoan = Double.parseDouble(txtTienChuyenKhoanDh.getText());
                        double tienThua = tienMat + tienChuyenKhoan - tongTien;
                        if (tienThua > 0) {
                            lblTienThuaDh.setText(tienThua + "đ");
                        } else {
                            lblTienThuaDh.setText("0đ");
                        }
                    }
                }
            }
            System.out.println("handle");
        } catch (Exception e) {
        }
    }

    private void resetComboBox(JComboBox<String> comboBox) {
        comboBox.setModel(new DefaultComboBoxModel<String>());
    }

    public void initActionSorter() {
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(txtSearch.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search(txtSearch.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search(txtSearch.getText());
            }

            private void search(String text) {
                int columnIndex = cboxSearch.getSelectedIndex();
                sorterSPCT.setRowFilter(RowFilter.regexFilter("(?i)" + text, columnIndex));
            }
        });
    }

    public void addColumnNamesToComboboxSearch() {
        for (String i : getColumnNameSPCT()) {
            cboxSearch.addItem(i);
        }
    }

    private void clearFilters() {
        sorterSPCT.setRowFilter(null);
        for (JComboBox i : comboBoxes) {
            i.setSelectedItem("---Tất cả---");
        }
    }

    public ArrayList<String> getColumnNameSPCT() {
        TableColumnModel columnModel = tbDanhSachSanPham.getColumnModel();
        ArrayList<String> columnNames = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            String columnName = columnModel.getColumn(i).getHeaderValue().toString();
            columnNames.add(columnName);
        }
        return columnNames;
    }

    public String formatCurrency(double amount) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        String formatted = numberFormat.format(amount);

        formatted = formatted.replace("₫", "VNĐ");

        formatted = formatted.replace(" ", "");

        return formatted;
    }

    private void applyFilters() {
        RowFilter<TableModel, Object> searhFilter = createSearchFilter(txtSearch, cboxSearch.getSelectedIndex());
        RowFilter<TableModel, Object> hangFilter = createRowFilter(cboxHang, 3);
        RowFilter<TableModel, Object> chatLieuFilter = createRowFilter(cboxChatLieu, 4);
        RowFilter<TableModel, Object> kichThuocFilter = createRowFilter(cboxKichThuoc, 5);
        RowFilter<TableModel, Object> mauSacFilter = createRowFilter(cboxMauSac, 6);
        RowFilter<TableModel, Object> deGiayFilter = createRowFilter(cboxDeGiay, 7);
        RowFilter<TableModel, Object> khoiLuongFilter = createRowFilter(cboxKhoiLuong, 8);
        RowFilter<TableModel, Object> xuatXuFilter = createRowFilter(cboxXuatXu, 9);
        RowFilter<TableModel, Object> nhaSanXuatFilter = createRowFilter(cboxNhaSanXuat, 10);
        RowFilter<TableModel, Object> coGiayFilter = createRowFilter(cboxCoGiay, 11);

        // Combine filters into a single filter
        RowFilter<TableModel, Object> compoundFilter = RowFilter.andFilter(
                java.util.Arrays.asList(hangFilter, chatLieuFilter, kichThuocFilter, mauSacFilter, deGiayFilter,
                        khoiLuongFilter, xuatXuFilter, nhaSanXuatFilter, coGiayFilter, searhFilter));

        sorterSPCT.setRowFilter(compoundFilter);
    }

    private RowFilter<TableModel, Object> createRowFilter(JComboBox<String> comboBox, int columnIndex) {
        String selectedFilter = (String) comboBox.getSelectedItem();
        if (selectedFilter == null || selectedFilter.equals("---Tất cả---")) {
            return RowFilter.regexFilter("");
        } else {
            return RowFilter.regexFilter("(?i)" + selectedFilter, columnIndex);
        }
    }

    private RowFilter<TableModel, Object> createSearchFilter(TextField textField, int columnIndex) {
        String selectedFilter = (String) textField.getText();
        if (selectedFilter.trim().isEmpty()) {
            return RowFilter.regexFilter("");
        } else {
            return RowFilter.regexFilter("(?i)" + selectedFilter, columnIndex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbHoaDon = new javax.swing.JTable();
        btQuetQR = new javax.swing.JButton();
        btTaoHoaDon = new javax.swing.JButton();
        btLamMoi = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbGioHang = new javax.swing.JTable();
        btnXoa = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbDanhSachSanPham = new javax.swing.JTable();
        cboxChatLieu = new combobox.Combobox();
        cboxKichThuoc = new combobox.Combobox();
        cboxMauSac = new combobox.Combobox();
        cboxDeGiay = new combobox.Combobox();
        cboxNhaSanXuat = new combobox.Combobox();
        cboxCoGiay = new combobox.Combobox();
        cboxSearch = new combobox.Combobox();
        txtSearch = new swing.TextField();
        cboxHang = new swing.Combobox();
        cboxXuatXu = new swing.Combobox();
        cboxKhoiLuong = new swing.Combobox();
        btnHuyLoc = new swing.ButtonCustom();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        txtSdtTq = new textfield.TextField();
        txtTenKhachHang1 = new textfield.TextField();
        btChon1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblMaHdTaiQuay = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblNgayTaoTq = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblMaNhanVienTq = new javax.swing.JLabel();
        cboHinhThucThanhToanTq = new combobox.Combobox();
        txtTienMatTq = new textfield.TextField();
        txtTienChuyenKhoanTq = new textfield.TextField();
        jLabel15 = new javax.swing.JLabel();
        lblTienThuaTq = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btThanhToanTq = new javax.swing.JButton();
        lblTongTq = new javax.swing.JLabel();
        cboPhieuGiamGiaTq = new combobox.Combobox();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtSDTKhDh = new textfield.TextField();
        txtTenKhachHangDh = new textfield.TextField();
        btChon = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblMaHDDh = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cboPhieuGiamGiadh = new combobox.Combobox();
        cboHinhThucThanhToanDh = new combobox.Combobox();
        lblTongTiendh = new javax.swing.JLabel();
        txtTienMatDh = new textfield.TextField();
        txtTienChuyenKhoanDh = new textfield.TextField();
        btnGiaoHang = new javax.swing.JButton();
        btnHuyGiaoHang = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblNhanViendh = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTienThuaDh = new javax.swing.JLabel();
        txtDiaChiDh = new textfield.TextField();
        txtPhiShipdh = new textfield.TextField();
        jPanel10 = new javax.swing.JPanel();
        txtSDTNvDh = new textfield.TextField();
        txtTenNhanVienDh = new textfield.TextField();
        btChon2 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("BÁN HÀNG");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa Đơn"));

        tbHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã hóa đơn", "Nhân viên", "Tên khách hàng", "SDT", "Tổng tiền", "Ngày tạo", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbHoaDon);

        btQuetQR.setText("Quét QR");
        btQuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btQuetQRActionPerformed(evt);
            }
        });

        btTaoHoaDon.setText("Tạo hóa đơn");
        btTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTaoHoaDonActionPerformed(evt);
            }
        });

        btLamMoi.setText("Làm mới");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btTaoHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btQuetQR)
                        .addGap(626, 626, 626)
                        .addComponent(btLamMoi))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btQuetQR)
                            .addComponent(btTaoHoaDon)))
                    .addComponent(btLamMoi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Giỏ Hàng"));

        tbGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SPCT", "Tên SP", "Hãng", "Chất liệu", "Size", "Màu sắc", "Đế giày", "Khối lượng", "Xuất xứ", "NSX", "Cổ giày", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbGioHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbGioHang);

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnXoa)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 853, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(btnXoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách sản phẩm"));

        tbDanhSachSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SPCT", "Tên SP", "Hãng", "Chất liệu", "Size", "Màu sắc", "Đế giày", "Khối lượng", "Xuất xứ", "NSX", "Cổ giày", "SL tồn", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDanhSachSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDanhSachSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbDanhSachSanPham);
        if (tbDanhSachSanPham.getColumnModel().getColumnCount() > 0) {
            tbDanhSachSanPham.getColumnModel().getColumn(8).setHeaderValue("Khối lượng");
        }

        cboxChatLieu.setLabeText("Chất Liệu");

        cboxKichThuoc.setLabeText("Kích Thước");

        cboxMauSac.setLabeText("Màu Sắc");

        cboxDeGiay.setLabeText("Đế Giày");

        cboxNhaSanXuat.setLabeText("NSX");

        cboxCoGiay.setLabeText("Cổ Giày");

        cboxSearch.setLabeText("Tìm kiếm theo");

        txtSearch.setLabelText("Tìm kiếm");
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }
        });
        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchMouseClicked(evt);
            }
        });
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        cboxHang.setLabeText("Hãng");

        cboxXuatXu.setLabeText("Xuất xứ");

        cboxKhoiLuong.setLabeText("Khối lượng");

        btnHuyLoc.setText("Hủy lọc");
        btnHuyLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(cboxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboxHang, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cboxXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboxKhoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboxNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHuyLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cboxKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboxMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboxDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboxCoGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 855, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxCoGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboxHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxKhoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyLoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Đơn hàng"));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 204, 204))); // NOI18N

        txtSdtTq.setLabelText("Số điện thoại");

        txtTenKhachHang1.setText("Khách lẻ");
        txtTenKhachHang1.setLabelText("Tên Khách Hàng");

        btChon1.setText("Chọn");
        btChon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChon1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(txtSdtTq, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btChon1)
                        .addContainerGap(49, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(txtTenKhachHang1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSdtTq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btChon1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTenKhachHang1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 204, 204))); // NOI18N

        jLabel3.setText("Mã hóa đơn");

        lblMaHdTaiQuay.setToolTipText("");
        lblMaHdTaiQuay.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel5.setText("Ngày tạo");

        lblNgayTaoTq.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel11.setText("Mã nhân viên");

        lblMaNhanVienTq.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        cboHinhThucThanhToanTq.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tiền mặt", "Chuyển khoản", "Kết Hợp" }));
        cboHinhThucThanhToanTq.setSelectedIndex(-1);
        cboHinhThucThanhToanTq.setLabeText("Hình thức thanh toán");
        cboHinhThucThanhToanTq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHinhThucThanhToanTqActionPerformed(evt);
            }
        });

        txtTienMatTq.setLabelText("Tiền Mặt");

        txtTienChuyenKhoanTq.setLabelText("Tiền Chuyển Khoản");

        jLabel15.setText("Tiền thừa");

        lblTienThuaTq.setText("0");

        jLabel17.setText("Tổng");

        btThanhToanTq.setText("Thanh Toán");
        btThanhToanTq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThanhToanTqActionPerformed(evt);
            }
        });

        lblTongTq.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        cboPhieuGiamGiaTq.setLabeText("Phiếu Giảm Giá");
        cboPhieuGiamGiaTq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPhieuGiamGiaTqActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboHinhThucThanhToanTq, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                            .addComponent(txtTienMatTq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTienChuyenKhoanTq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel11))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblNgayTaoTq, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                                    .addComponent(lblMaNhanVienTq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(cboPhieuGiamGiaTq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(203, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(29, 29, 29)
                                .addComponent(lblMaHdTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(lblTienThuaTq, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTongTq, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(btThanhToanTq)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(lblMaHdTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(lblMaNhanVienTq, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(lblNgayTaoTq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboPhieuGiamGiaTq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboHinhThucThanhToanTq, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTienMatTq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTienChuyenKhoanTq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lblTienThuaTq))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(lblTongTq, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btThanhToanTq)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 191, Short.MAX_VALUE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tại quầy", jPanel7);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Đơn hàng"));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 204, 204))); // NOI18N

        txtSDTKhDh.setLabelText("Số điện thoại");

        txtTenKhachHangDh.setLabelText("Tên Khách Hàng");

        btChon.setText("Chọn");
        btChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenKhachHangDh, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtSDTKhDh, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btChon)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btChon)
                    .addComponent(txtSDTKhDh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKhachHangDh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 204, 204))); // NOI18N

        jLabel2.setText("Mã hóa đơn");

        jLabel10.setText("Tổng tiền");

        cboPhieuGiamGiadh.setLabeText("Phiếu Giảm Giá");
        cboPhieuGiamGiadh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPhieuGiamGiadhActionPerformed(evt);
            }
        });

        cboHinhThucThanhToanDh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tiền mặt", "Chuyển khoản", "Kết Hợp" }));
        cboHinhThucThanhToanDh.setSelectedIndex(-1);
        cboHinhThucThanhToanDh.setLabeText("cboHinhThucThanhToan");
        cboHinhThucThanhToanDh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHinhThucThanhToanDhActionPerformed(evt);
            }
        });

        txtTienMatDh.setLabelText("Tiền mặt");

        txtTienChuyenKhoanDh.setLabelText("Tiền Chuyển Khoản");

        btnGiaoHang.setText("Giao hàng");
        btnGiaoHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGiaoHangActionPerformed(evt);
            }
        });

        btnHuyGiaoHang.setText("Hủy ");
        btnHuyGiaoHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyGiaoHangActionPerformed(evt);
            }
        });

        jLabel7.setText("Mã nhân viên");

        jLabel6.setText("Ngày muốn nhận");

        jLabel9.setText("Tiền thừa");

        lblTienThuaDh.setText("jLabel12");

        txtDiaChiDh.setLabelText("Địa chỉ");

        txtPhiShipdh.setLabelText("Phí ship");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(144, 144, 144)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNhanViendh)
                                    .addComponent(lblMaHDDh, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cboPhieuGiamGiadh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTienMatDh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTienChuyenKhoanDh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnGiaoHang, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel6))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTienThuaDh)
                                        .addComponent(lblTongTiendh)
                                        .addComponent(btnHuyGiaoHang))
                                    .addGap(34, 34, 34)))
                            .addComponent(txtDiaChiDh, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhiShipdh, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboHinhThucThanhToanDh, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 58, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaHDDh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblNhanViendh))
                .addGap(5, 5, 5)
                .addComponent(cboPhieuGiamGiadh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiaChiDh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPhiShipdh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboHinhThucThanhToanDh, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTienMatDh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTienChuyenKhoanDh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(lblTongTiendh))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGiaoHang)
                            .addComponent(btnHuyGiaoHang))
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(lblTienThuaDh)
                        .addContainerGap())))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "nhân viên vận chuyển", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 204, 204))); // NOI18N

        txtSDTNvDh.setLabelText("Số điện thoại");

        txtTenNhanVienDh.setLabelText("tên nhân viên");

        btChon2.setText("Chọn");
        btChon2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChon2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenNhanVienDh, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(txtSDTNvDh, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btChon2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btChon2))
                    .addComponent(txtSDTNvDh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTenNhanVienDh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 177, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 513, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        jTabbedPane1.addTab("Đặt hàng", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(536, 536, 536)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(15, 15, 15))
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbGioHangMouseClicked
        // Kiểm tra nếu người dùng nhấp đúp chuột

        HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);

        // Kiểm tra nếu người dùng nhấp đúp chuột
        if (evt.getClickCount() == 2) {
            int row = tbGioHang.getSelectedRow();

            // Lấy hóa đơn đang chọn
            if (indexHoaDonSelected == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn.");
                return;
            }

            // Lấy sản phẩm từ bảng hóa đơn chi tiết
            HoaDonChiTietResponse hdct = hoaDonChiTietRepository.getAll(repose.getId()).get(row);
            if (hdct == null) {
                JOptionPane.showMessageDialog(this, "Sản phẩm không tồn tại trong hóa đơn chi tiết.");
                return;
            }

            // Lấy chi tiết sản phẩm từ cơ sở dữ liệu dựa trên ID
            List<SanPhamChiTietRepose> spctList = repo_chitietsanpham.loadSanPhamChiTietFromDb();
            SanPhamChiTietRepose spct = null;
            for (SanPhamChiTietRepose item : spctList) {
                if (item.getIdSpct() == (hdct.getCtspID())) {
                    spct = item;
                    break;
                }
            }

            if (spct == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin sản phẩm chi tiết.");
                return;
            }

            // Giảm số lượng trong hóa đơn chi tiết
            hdct.setSoLuong(hdct.getSoLuong() - 1);
            hdct.setThanhTien(hdct.getSoLuong() * spct.getDonGia());

            if (hdct.getSoLuong() > 0) {
                // Cập nhật lại hóa đơn chi tiết
                hoaDonChiTietRepository.updateSoLuongHdct(hdct);
            } else {
                // Xóa sản phẩm khỏi hóa đơn chi tiết nếu số lượng = 0
                hoaDonChiTietRepository.deleteSpCT(hdct.getCtspID());
            }

            // Tăng số lượng tồn kho của sản phẩm
            spct.setSoLuong(spct.getSoLuong() + 1);
            // Cập nhật số lượng tồn kho vào cơ sở dữ liệu
            repo_chitietsanpham.updateSoluong(spct);

            // Load lại bảng hóa đơn chi tiết và sản phẩm
            showTableHoaDonCHiTiet(hoaDonChiTietRepository.getAll(repose.getId()));
            showTableSanPham(repo_chitietsanpham.loadSanPhamChiTietFromDb());

            // Cập nhật tổng tiền hóa đơn
            repose.setTongTien(showTotalMoney(hoaDonChiTietRepository.getAll(repose.getId())));

            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String formattedTongTien = currencyFormatter.format(repose.getTongTien());
            lblTongTq.setText(formattedTongTien); // Cập nhật tổng tiền bên phải

            // Cập nhật vào cơ sở dữ liệu
            hoaDonChiTietRepository.updateTongTien(repose.getTongTien(), repose.getId());
            showTableHoaDon(repoBh.getAllBanHang(id));

            ArrayList<PhieuGiamGia> allVouchers = phieuGiamGiaRespository.getAllStatusPhieu();

            // Lọc và thêm các voucher vào cboPhieuGiamGiaTq nếu thỏa mãn điều kiện và chưa được sử dụng
            updateVouchers(allVouchers, repose.getTongTien());
        }
    }//GEN-LAST:event_tbGioHangMouseClicked

    private void btChon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChon1ActionPerformed
        // TODO add your handling code here:
        ChooseKhachHang chooseKhachHang = new ChooseKhachHang();
        chooseKhachHang.setEventFormKhachHang(this);
        chooseKhachHang.setVisible(true);
    }//GEN-LAST:event_btChon1ActionPerformed

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {
        clearFilters();
    }

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_txtSearchMouseClicked
        // TODO add your handling code here:
    }

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSearchActionPerformed

    }

    private void btnHuyLocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHuyLocActionPerformed
        sorterSPCT.setRowFilter(null);
        for (JComboBox i : comboBoxes) {
            i.setSelectedItem("---Tất cả---");
        }
        txtSearch.setText("");
    }

    private void btQuetQRActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btQuetQRActionPerformed
        // TODO add your handling code here:
        QrCodeBanHang qrCodeBanHang = new QrCodeBanHang();
        qrCodeBanHang.setQrCodeListener((qrCodeText) -> {
            // Xử lý mã QR sau khi quét
            onQrCodeRead(qrCodeText);
        });
        qrCodeBanHang.setVisible(true);
    }

    private void btTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btTaoHoaDonActionPerformed
        // TODO add your handling code here: CHỊU ĐẤY

        // tạo hóa đơn tại quầy
        if (jTabbedPane1.getSelectedIndex() == 0) {
            Date ngayTao = new Date();
            String ma = RandomStringGenerator.generateRandomString("HD");
            int id = Auth.user.getId();
            if (tbHoaDon.getRowCount() <= 10) {
                repoBh.add(ma, Auth.user.getId(), 11, "", 0, ngayTao, 1, "Khách lẻ");
                showTableHoaDon(repoBh.getAllBanHang(id));
                JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Không thể tạo vượt quá 10 hóa đơn");
            }
        } else {
            // tạo hóa đơn đặt hàng
            Date ngayTao = new Date();
            String ma = RandomStringGenerator.generateRandomString("HD");
            int id = Auth.user.getId();
            if (tbHoaDon.getRowCount() <= 10) {
                repoBh.add(ma, Auth.user.getId(), 11, "", 0, ngayTao, 4, "Khách lẻ");
                showTableHoaDon(repoBh.getAllBanHang(id));
                JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Không thể tạo vượt quá 10 hóa đơn");
            }
        }
    }

    private void tbDanhSachSanPhamMouseClicked(java.awt.event.MouseEvent evt) {
        int row = tbDanhSachSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm.");
            return;
        }
        SanPhamChiTietRepose spct = repo_chitietsanpham.loadSanPhamChiTietFromDb().get(row);

        // Kiểm tra số lượng sản phẩm
        if (spct.getSoLuong() == 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng.");
            return;
        }

        // Lấy hóa đơn đang chọn
        if (indexHoaDonSelected == -1) {
            // No invoice selected, handle accordingly
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn.");
            return;
        }
        HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);

        String soLuong = JOptionPane.showInputDialog("Số lượng là: ", "0");

        // Kiểm tra số lượng nhập vào
        int soLuongInt;
        try {
            soLuongInt = Integer.parseInt(soLuong);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ.");
            return;
        }

        if (soLuongInt <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0.");
            return;
        }

        if (soLuongInt > spct.getSoLuong()) {
            JOptionPane.showMessageDialog(this, "Số lượng nhập vào lớn hơn số lượng sản phẩm hiện có.");
            return;
        }

        // Kiểm tra sản phẩm đã tồn tại trong hóa đơn chi tiết chưa
        List<HoaDonChiTietResponse> chiTietList = hoaDonChiTietRepository.getAll(repose.getId());
        HoaDonChiTietResponse existingChiTiet = null;
        for (HoaDonChiTietResponse chiTiet : chiTietList) {
            if (chiTiet.getCtspID().equals(spct.getIdSpct())) {
                existingChiTiet = chiTiet;
                break;
            }
        }

        if (existingChiTiet != null) {
            // Sản phẩm đã tồn tại, cộng dồn số lượng
            existingChiTiet.setSoLuong(existingChiTiet.getSoLuong() + soLuongInt);
            existingChiTiet.setThanhTien(existingChiTiet.getSoLuong() * spct.getDonGia());
            hoaDonChiTietRepository.updateSoLuongHdct(existingChiTiet);
        } else {
            // Sản phẩm chưa tồn tại, thêm mới
            HoaDonChiTietResponse hoaDonChiTietResponse = HoaDonChiTietResponse.builder()
                    .hoaDonID(repose.getId())
                    .ctspID(spct.getIdSpct())
                    .maSP(spct.getMaSP())
                    .tenSP(spct.getTenSp())
                    .soLuong(soLuongInt)
                    .donGia(spct.getDonGia())
                    .thanhTien(soLuongInt * spct.getDonGia())
                    .ghiChu(spct.getGhiChu())
                    .MauSac(spct.getMauSac())
                    .hang(spct.getHang())
                    .chatLieu(spct.getChatLieu())
                    .kichThuoc(spct.getKichThuoc())
                    .deGiay(spct.getDeGiay())
                    .NSX(spct.getNsx())
                    .build();

            hoaDonChiTietRepository.add(hoaDonChiTietResponse);
        }

        // Cập nhật số lượng sản phẩm của đối tượng vừa chọn
        spct.setSoLuong(spct.getSoLuong() - soLuongInt);
        // Cập nhật số lượng vào database
        repo_chitietsanpham.updateSoluong(spct);

        // Load lại table
        showTableSanPham(repo_chitietsanpham.loadSanPhamChiTietFromDb());
        showTableHoaDonCHiTiet(hoaDonChiTietRepository.getAll(repose.getId()));

        // Cập nhật tổng tiền bên phải
        repose.setTongTien(showTotalMoney(hoaDonChiTietRepository.getAll(repose.getId())));

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedTongTien = currencyFormatter.format(repose.getTongTien());
        lblTongTq.setText(formattedTongTien); // Cập nhật tổng tiền bên phải
        // Cập nhật vào cơ sở dữ liệu
        hoaDonChiTietRepository.updateTongTien(repose.getTongTien(), repose.getId());
        showTableHoaDon(repoBh.getAllBanHang(id));

        ArrayList<PhieuGiamGia> allVouchers = phieuGiamGiaRespository.getAllStatusPhieu();

        // Lọc và thêm các voucher vào cboPhieuGiamGiaTq nếu thỏa mãn điều kiện và chưa được sử dụng
        updateVouchers(allVouchers, repose.getTongTien());
    }

    public void updateVouchers(List<PhieuGiamGia> allVouchers, double tongTien) {
        // Xóa tất cả các mục hiện tại trong combobox
        cboPhieuGiamGiaTq.removeAllItems();
        // Cập nhật danh sách voucher
        for (PhieuGiamGia voucher : allVouchers) {
            if (tongTien >= voucher.getDieuKien()) {
                cboPhieuGiamGiaTq.addItem(voucher.getTen());
            }
        }
    }

    private Double showTotalMoney(ArrayList<HoaDonChiTietResponse> lists) {
        double sum = 0;
        for (HoaDonChiTietResponse hdct : lists) {
            sum += hdct.getThanhTien();
        }

        return sum;
    }

    private void tbHoaDonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tbHoaDonMouseClicked
        // TODO add your handling code here:
        if (jTabbedPane1.getSelectedIndex() == 0) {
            indexHoaDonSelected = tbHoaDon.getSelectedRow();
            HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);
            txtTenKhachHang1.setText(repose.getTenKH());

            showTableHoaDonCHiTiet(hoaDonChiTietRepository.getAll(repose.getId()));

            txtSdtTq.setText(repose.getSdtKH());
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            String formattedNgayTao = dateFormatter.format(repose.getNgayTao());

            lblNgayTaoTq.setText(formattedNgayTao);
            lblMaHdTaiQuay.setText(repose.getMaHD());
            String maNv = Auth.user.getMaNv();

            lblMaNhanVienTq.setText(maNv + " - " + Auth.user.getHoTenNv());

            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String formattedTongTien = currencyFormatter.format(repose.getTongTien());

            lblTongTq.setText(formattedTongTien);

            ArrayList<PhieuGiamGia> allVouchers = phieuGiamGiaRespository.getAllStatusPhieu();

            // Lọc và thêm các voucher vào cboPhieuGiamGiaTq nếu thỏa mãn điều kiện và chưa
            // được sử dụng
            for (PhieuGiamGia voucher : allVouchers) {
                if (repose.getTongTien() < voucher.getDieuKien()) {
                    cboPhieuGiamGiaTq.removeAllItems();
                }
            }
            cboPhieuGiamGiaTq.removeAllItems();
            for (PhieuGiamGia voucher : allVouchers) {
                System.out.println(allVouchers.size());
                if (repose.getTongTien() >= voucher.getDieuKien()) {
                    // cboPhieuGiamGiaTq.addItem("");
                    cboPhieuGiamGiaTq.addItem(voucher.getTen());
                }
            }
            validateTaiQuay();
            // tinhTienThua();
        } else {
            indexHoaDonSelected = tbHoaDon.getSelectedRow();
            HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);
            txtTenKhachHangDh.setText(repose.getTenKH());
            showTableHoaDonCHiTiet(hoaDonChiTietRepository.getAll(repose.getId()));

            txtSDTKhDh.setText(repose.getSdtKH());
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            String formattedNgayTao = dateFormatter.format(repose.getNgayTao());

            // lblNgayTaoTq.setText(formattedNgayTao);
            lblMaHDDh.setText(repose.getMaHD());
            String maNv = Auth.user.getMaNv();

            lblNhanViendh.setText(maNv + " - " + Auth.user.getHoTenNv());

            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String formattedTongTien = currencyFormatter.format(repose.getTongTien());

            lblTongTiendh.setText(formattedTongTien);

            ArrayList<PhieuGiamGia> allVouchers = phieuGiamGiaRespository.getAllStatusPhieu();

            // Lọc và thêm các voucher vào cboPhieuGiamGiaTq nếu thỏa mãn điều kiện và chưa
            // được sử dụng
            for (PhieuGiamGia voucher : allVouchers) {
                if (repose.getTongTien() < voucher.getDieuKien()) {
                    cboPhieuGiamGiadh.removeAllItems();
                }
            }
            cboPhieuGiamGiaTq.removeAllItems();
            for (PhieuGiamGia voucher : allVouchers) {
                if (repose.getTongTien() >= voucher.getDieuKien()) {
                    // cboPhieuGiamGiaTq.addItem("");
                    cboPhieuGiamGiadh.addItem(voucher.getTen());
                }
            }
            validateDatHang();
            // tinhTienThua();
        }

    }

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // Check if an invoice is selected
        if (indexHoaDonSelected == -1) {
            // No invoice selected, handle accordingly
            JOptionPane.showMessageDialog(this, "Please select an invoice.");
            return;
        }

        // Get the selected invoice
        HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);

        // Retrieve all products in the selected invoice
        List<HoaDonChiTietResponse> hoaDonChiTietList = hoaDonChiTietRepository.getAll(repose.getId());

        List<SanPhamChiTietRepose> allProducts = repo_chitietsanpham.loadSanPhamChiTietFromDb();

        // Update product quantities back to the inventory
        for (HoaDonChiTietResponse hoaDonChiTiet : hoaDonChiTietList) {
            // Find the product by its ID
            for (SanPhamChiTietRepose spct : allProducts) {
                if (spct.getIdSpct() == hoaDonChiTiet.getCtspID()) {
                    // Update the product quantity
                    spct.setSoLuong(spct.getSoLuong() + hoaDonChiTiet.getSoLuong());
                    // Save the updated product back to the database
                    repo_chitietsanpham.updateSoluong(spct);
                    break;
                }
            }
        }

        // Delete all products from the invoice in the database
        hoaDonChiTietRepository.delete(repose);

        // Update the UI tables accordingly
        showTableSanPham(repo_chitietsanpham.loadSanPhamChiTietFromDb());
        showTableHoaDonCHiTiet(hoaDonChiTietRepository.getAll(repose.getId()));

        repose.setTongTien(showTotalMoney(hoaDonChiTietRepository.getAll(repose.getId())));

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedTongTien = currencyFormatter.format(repose.getTongTien());
        lblTongTq.setText(formattedTongTien); // update tong tien ben phai

        // update vao csdl
        hoaDonChiTietRepository.updateTongTien(repose.getTongTien(), repose.getId());
        showTableHoaDon(repoBh.getAllBanHang(id));

        cboPhieuGiamGiaTq.removeAllItems();
    }

    private void btThanhToanTqActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btThanhToanTqActionPerformed
        int row = tbHoaDon.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn đề thanh toán");
        } else {
            if (tbGioHang.getRowCount() < 1) {
                JOptionPane.showMessageDialog(this, "Không thể thanh toán hàng trống");
            } else {
                int i = tbHoaDon.getSelectedRow();
                String trangThai = tbHoaDon.getValueAt(i, 7).toString();
                HoaDonResponse hd = repoBh.getAllBanHang(id).get(indexHoaDonSelected);
                Date ngayTao = new Date();
                String ma = RandomStringGenerator.generateRandomString("HD");
                if (trangThai.equals("Chưa Thanh Toán")) {
                    inHoaDon();

                    repoBh.updateThanhToan(hd);
                    repoBh.addLichSu(hd.getId(), ma, cboHinhThucThanhToanTq.getSelectedItem().toString(), "", ngayTao, "", 1);
                    showTableHoaDon(repoBh.getAllBanHang(id));
                    DefaultTableModel dtm;
                    dtm = (DefaultTableModel) tbGioHang.getModel();
                    dtm.setRowCount(0);
                    txtSdtTq.setText("");
                    lblNgayTaoTq.setText("");
                    lblMaHdTaiQuay.setText("");
                    lblMaNhanVienTq.setText("");
                    lblTongTq.setText("");
                    JOptionPane.showMessageDialog(this, "Thanh toán thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể thanh toán hóa đơn đặt hàng");
                }
            }
        }
    }

    private void cboHinhThucThanhToanTqActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboHinhThucThanhToanTqActionPerformed
        // TODO add your handling code here:
        // Tiền mặt, Chuyển khoản, Kết Hợp
        HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);

        if (cboHinhThucThanhToanTq.getSelectedItem().equals("Tiền mặt")) {
            txtTienMatTq.setText("0");
            txtTienMatTq.setEnabled(true);
            txtTienChuyenKhoanTq.setEnabled(false);
            repoBh.updateHinhThucHd(1, repose.getId());
        } else if (cboHinhThucThanhToanTq.getSelectedItem().equals("Chuyển khoản")) {
            txtTienMatTq.setText("0");
            txtTienMatTq.setEnabled(false);
            txtTienChuyenKhoanTq.setEnabled(true);
            txtTienChuyenKhoanTq.setText(lblTongTq.getText());
            repoBh.updateHinhThucHd(2, repose.getId());

        } else if (cboHinhThucThanhToanTq.getSelectedItem().equals("Kết Hợp")) {
            txtTienMatTq.setText("0");
            txtTienMatTq.setEnabled(true);
            txtTienChuyenKhoanTq.setEnabled(true);
            repoBh.updateHinhThucHd(3, repose.getId());

        }
    }

    private void cboPhieuGiamGiaTqActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboPhieuGiamGiaTqActionPerformed
        // TODO add your handling code here:
        Object selectedItem = cboPhieuGiamGiaTq.getSelectedItem();
        System.out.println("Selected item: " + selectedItem);
        PhieuGiamGia pgg = new PhieuGiamGia();

        if (selectedItem != null) {
            String selectedItemStr = selectedItem.toString();
            // Biểu thức chính quy để khớp với định dạng giảm giá
            Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)(%|\\s*đ)");
            Matcher matcher = pattern.matcher(selectedItemStr);

            if (matcher.find()) {
                // Lấy ra chuỗi số và loại giảm giá
                String number = matcher.group(1);
                String type = matcher.group(3);
                double giamGia = Double.parseDouble(number);
                double tongTien = 0;

                // Kiểm tra nếu lblTongTq không rỗng
                if (!lblTongTq.getText().isEmpty()) {
                    for (int i = 0; i < tbGioHang.getRowCount(); i++) {
                        String cellValue = tbGioHang.getValueAt(i, 14).toString()
                                .replace(".", "") // Loại bỏ dấu chấm
                                .replace(",", ".") // Thay thế dấu phẩy bằng dấu chấm
                                .replaceAll("[^\\d.]", ""); // Loại bỏ tất cả các ký tự không phải số và dấu chấm

                        if (!cellValue.isEmpty()) {
                            try {
                                tongTien += Double.parseDouble(cellValue);
                                System.out.println("Tổng tiền trước giảm giá: " + tongTien);
                            } catch (NumberFormatException e) {
                                e.printStackTrace(); // Log lỗi nếu cần
                            }
                        }
                    }

                    // Áp dụng giảm giá
                    // Giả sử bạn đã có danh sách tất cả các voucher
                    ArrayList<PhieuGiamGia> allVouchers = phieuGiamGiaRespository.getAllStatusPhieu();

                    // Lấy voucher được chọn từ combobox (cboPhieuGiamGiaTq)
                    Object selectedIte = cboPhieuGiamGiaTq.getSelectedItem();
                    PhieuGiamGia selectedVoucher = null;

                    // Tìm voucher được chọn trong danh sách voucher
                    for (PhieuGiamGia v : allVouchers) {
                        if (v.getTen().equals(selectedItem.toString())) { // So sánh tên voucher
                            selectedVoucher = v;
                            break;
                        }
                    }

                    // Kiểm tra nếu voucher được chọn không null
                    if (selectedVoucher != null) {
                        double giaTriToiDa = selectedVoucher.getGiaTriToiDa();
                        double dieuKien = selectedVoucher.getDieuKien(); // Điều kiện áp dụng voucher

                        if (type.equals("%")) {
                            if (tongTien > dieuKien) {
                                // Nếu tổng tiền lớn hơn điều kiện áp dụng, áp dụng giảm giá với giá trị tối đa
                                tongTien = tongTien - Double.valueOf(giaTriToiDa);
                                System.out.println("Giá trị tối đa áp dụng: " + giaTriToiDa);
                            } else {
                                // Nếu tổng tiền không lớn hơn điều kiện, áp dụng giảm giá theo phần trăm
                                tongTien = tongTien - (tongTien * giamGia / 100);
                            }
                            System.out.println("Tổng tiền sau giảm giá (%): " + tongTien);

                        } else if (type.equals("đ")) {
                            // Nếu loại giảm giá là tiền mặt
                            tongTien = tongTien - giamGia;
                            System.out.println("Tổng tiền sau giảm giá (đ): " + tongTien);

                        } else {
                            System.out.println("Loại giảm giá không hợp lệ.");
                        }

                        // Cập nhật tổng tiền sau giảm giá vào lblTongTq
                        lblTongTq.setText(String.format("%,.0f", tongTien));

                    } else {
                        System.out.println("Voucher không hợp lệ hoặc không được chọn.");
                    }

                    HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);
                    repoBh.updatePhieuHd(selectedVoucher.getId(), repose.getId());

                }
            } else {
                double tongTien = 0;
                for (int i = 0; i < tbGioHang.getRowCount(); i++) {
                    String cellValue = tbGioHang.getValueAt(i, 14).toString()
                            .replace(".", "") // Loại bỏ dấu chấm
                            .replace(",", ".") // Thay thế dấu phẩy bằng dấu chấm
                            .replaceAll("[^\\d.]", ""); // Loại bỏ tất cả các ký tự không phải số và dấu chấm

                    if (!cellValue.isEmpty()) {
                        try {
                            tongTien += Double.parseDouble(cellValue);
                            System.out.println("Tổng tiền không giảm giá: " + tongTien);
                        } catch (NumberFormatException e) {
                            e.printStackTrace(); // Log lỗi nếu cần
                        }
                    }
                }
                lblTongTq.setText(String.format("%,.0f", tongTien));
            }
        }

    }

    private void btChonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btChonActionPerformed
        ChooseKhachHang chooseKhachHang = new ChooseKhachHang();
        chooseKhachHang.setEventFormKhachHang(this);
        chooseKhachHang.setVisible(true);
    }

    private void btChon2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btChon2ActionPerformed
        ChooseNhanVien chooseNhanVien = new ChooseNhanVien();
        chooseNhanVien.setEventFormChoose(this);
        chooseNhanVien.setVisible(true);
    }

    private void cboPhieuGiamGiadhActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboPhieuGiamGiadhActionPerformed
        // TODO add your handling code here:
        Object selectedItem = cboPhieuGiamGiadh.getSelectedItem();
        System.out.println("Selected item: " + selectedItem);

        if (selectedItem != null) {
            String selectedItemStr = selectedItem.toString();
            // Biểu thức chính quy để khớp với định dạng giảm giá
            Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)(%|\\s*đ)");
            Matcher matcher = pattern.matcher(selectedItemStr);

            if (matcher.find()) {
                // Lấy ra chuỗi số và loại giảm giá
                String number = matcher.group(1);
                String type = matcher.group(3);
                double giamGia = Double.parseDouble(number);
                double tongTien = 0;

                // Kiểm tra nếu lblTongTq không rỗng
                if (!lblTongTiendh.getText().isEmpty()) {
                    for (int i = 0; i < tbGioHang.getRowCount(); i++) {
                        String cellValue = tbGioHang.getValueAt(i, 14).toString()
                                .replace(".", "") // Loại bỏ dấu chấm
                                .replace(",", ".") // Thay thế dấu phẩy bằng dấu chấm
                                .replaceAll("[^\\d.]", ""); // Loại bỏ tất cả các ký tự không phải số và dấu chấm

                        if (!cellValue.isEmpty()) {
                            try {
                                tongTien += Double.parseDouble(cellValue);
                                System.out.println("Tổng tiền trước giảm giá: " + tongTien);
                            } catch (NumberFormatException e) {
                                e.printStackTrace(); // Log lỗi nếu cần
                            }
                        }
                    }

                    // Áp dụng giảm giá
                    // Giả sử bạn đã có danh sách tất cả các voucher
                    ArrayList<PhieuGiamGia> allVouchers = phieuGiamGiaRespository.getAllStatusPhieu();

                    // Lấy voucher được chọn từ combobox (cboPhieuGiamGiaTq)
                    Object selectedIte = cboPhieuGiamGiadh.getSelectedItem();
                    PhieuGiamGia selectedVoucher = null;

                    // Tìm voucher được chọn trong danh sách voucher
                    for (PhieuGiamGia v : allVouchers) {
                        if (v.getTen().equals(selectedItem.toString())) { // So sánh tên voucher
                            selectedVoucher = v;
                            break;
                        }
                    }

                    // Kiểm tra nếu voucher được chọn không null
                    if (selectedVoucher != null) {
                        double giaTriToiDa = selectedVoucher.getGiaTriToiDa();
                        double dieuKien = selectedVoucher.getDieuKien(); // Điều kiện áp dụng voucher

                        if (type.equals("%")) {
                            if (tongTien > dieuKien) {
                                // Nếu tổng tiền lớn hơn điều kiện áp dụng, áp dụng giảm giá với giá trị tối đa
                                tongTien = tongTien - Double.valueOf(giaTriToiDa);
                                System.out.println("Giá trị tối đa áp dụng: " + giaTriToiDa);
                            } else {
                                // Nếu tổng tiền không lớn hơn điều kiện, áp dụng giảm giá theo phần trăm
                                tongTien = tongTien - (tongTien * giamGia / 100);
                            }
                            System.out.println("Tổng tiền sau giảm giá (%): " + tongTien);

                        } else if (type.equals("đ")) {
                            // Nếu loại giảm giá là tiền mặt
                            tongTien = tongTien - giamGia;
                            System.out.println("Tổng tiền sau giảm giá (đ): " + tongTien);

                        } else {
                            System.out.println("Loại giảm giá không hợp lệ.");
                        }

                        // Cập nhật tổng tiền sau giảm giá vào lblTongTq
                        lblTongTiendh.setText(String.format("%,.0f", tongTien));

                    } else {
                        System.out.println("Voucher không hợp lệ hoặc không được chọn.");
                    }

                    HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);
                    repoBh.updatePhieuHd(selectedVoucher.getId(), repose.getId());
                }
            } else {
                double tongTien = 0;
                for (int i = 0; i < tbGioHang.getRowCount(); i++) {
                    String cellValue = tbGioHang.getValueAt(i, 14).toString()
                            .replace(".", "") // Loại bỏ dấu chấm
                            .replace(",", ".") // Thay thế dấu phẩy bằng dấu chấm
                            .replaceAll("[^\\d.]", ""); // Loại bỏ tất cả các ký tự không phải số và dấu chấm

                    if (!cellValue.isEmpty()) {
                        try {
                            tongTien += Double.parseDouble(cellValue);
                            System.out.println("Tổng tiền không giảm giá: " + tongTien);
                        } catch (NumberFormatException e) {
                            e.printStackTrace(); // Log lỗi nếu cần
                        }
                    }
                }
                lblTongTiendh.setText(String.format("%,.0f", tongTien));
            }
        }

    }

    private void btnGiaoHangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGiaoHangActionPerformed
        if (lblMaHDDh.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn đề giao hàng");
        } else {
            if (tbGioHang.getRowCount() < 1) {
                JOptionPane.showMessageDialog(this, "Không thể giao đơn hàng trống");
            } else {
                int i = tbHoaDon.getSelectedRow();
                String trangThai = tbHoaDon.getValueAt(i, 7).toString();
                if (trangThai.equals("Chờ Giao Hàng")) {
                    Validate validate = new Validate();
                    validate.khongDuocLaGiaTriNull("Nhân viên vận chuyển", nhanVienVanChuyen);
                    validate.khongDuocTrong("Địa chỉ", txtDiaChiDh.getText());
                    validate.khongDuocTrong("Phí ship", txtPhiShipdh.getText());
                    validate.chiDuocChuaSo("Phí ship", txtPhiShipdh.getText());
                    validate.phaiLonHon0("Phí ship", txtPhiShipdh.getText());
                    if (validate.isChuoiHopLe()) {
                        HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);
                        repoBh.updateTrangThai(repose);
                        showTableHoaDon(repoBh.getAllBanHang(id));
                        JOptionPane.showMessageDialog(this, "Giao hàng thành công");
                    } else {
                        validate.showWarning(this);
                    }
                } else if (trangThai.equals("Đang Giao Hàng")) {
                    JOptionPane.showMessageDialog(this, "Đơn hàng đang được giao");
                } else {
                    JOptionPane.showMessageDialog(this, "Không giao đơn hàng tại quầy");
                }
            }
        }
    }

    private void btnHuyGiaoHangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHuyGiaoHangActionPerformed
        if (lblMaHDDh.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để hủy");
        } else {
            int i = tbHoaDon.getSelectedRow();
            String trangThai = tbHoaDon.getValueAt(i, 7).toString();
            if (trangThai.equals("Đang Giao Hàng")) {
                HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);
                repoBh.updateTrangThaiHuy(repose);
                showTableHoaDon(repoBh.getAllBanHang(id));
                JOptionPane.showMessageDialog(this, "Hủy đơn hàng thành công");
            } else if (trangThai.equals("Chờ Giao Hàng")) {
                JOptionPane.showMessageDialog(this, "Đơn hàng chưa được giao");
            } else {
                JOptionPane.showMessageDialog(this, "Không hủy đơn hàng tại quầy");
            }

        }
    }

    private void cboHinhThucThanhToanDhActionPerformed(java.awt.event.ActionEvent evt) {
        HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);

        if (cboHinhThucThanhToanDh.getSelectedItem().equals("Tiền mặt")) {
            txtTienMatDh.setText("0");
            txtTienMatDh.setEnabled(true);
            txtTienChuyenKhoanDh.setEnabled(false);
            repoBh.updateHinhThucHd(1, repose.getId());

        } else if (cboHinhThucThanhToanDh.getSelectedItem().equals("Chuyển khoản")) {
            txtTienMatDh.setText("0");
            txtTienMatDh.setEnabled(false);
            txtTienChuyenKhoanDh.setEnabled(true);
            txtTienChuyenKhoanDh.setText(lblTongTiendh.getText());
            repoBh.updateHinhThucHd(2, repose.getId());

        } else if (cboHinhThucThanhToanTq.getSelectedItem().equals("Kết Hợp")) {
            txtTienMatDh.setText("0");
            txtTienMatDh.setEnabled(true);
            txtTienChuyenKhoanDh.setEnabled(true);
            repoBh.updateHinhThucHd(3, repose.getId());

        }
    }

    private String safeGetString(Object value) {
        return value == null ? "" : value.toString();
    }

    private void inHoaDon() {
        int selectedRow = indexHoaDonSelected; // Lấy hóa đơn được chọn

        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn Không ?");
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
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(filePath));
            doc.open();

            // 1. Thêm tiêu đề
            Font titleFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph title = new Paragraph("BILL OF SALE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);

            // 2. Thêm thông tin cửa hàng
            doc.add(new Paragraph(" ")); // Thêm khoảng trống
            Paragraph storeInfo = new Paragraph(
                    "GROUP2.com\nNo 00000 Address Line One\nAddress Line 02 SRI LANKA\nwww.facebook.com/SD19310\n+947000000000");
            storeInfo.setAlignment(Element.ALIGN_CENTER);
            doc.add(storeInfo);

            doc.add(new Paragraph(" ")); // Thêm khoảng trống
            doc.add(new Paragraph(
                    "----------------------------------------------------------------------------------------------------------------------------------"));

            // 3. Thêm thông tin hóa đơn và khách hàng
            HoaDonResponse selectedHoaDon = repoBh.getAllBanHang(id).get(selectedRow);

            String maHoaDon = selectedHoaDon.getMaHD();
            String tenKhachHang = selectedHoaDon.getTenKH();
            String tongTien = lblTongTq.getText();
            String sdt = selectedHoaDon.getSdtKH();
            String tenNV = lblMaNhanVienTq.getText();
            String ngayTao = lblNgayTaoTq.getText();

            BaseFont baseFont = BaseFont.createFont("src/com/raven/style/font-times-new-roman/times-new-roman-14.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 12);
            Font boldFont = new Font(baseFont, 12, Font.BOLD);
            // Tạo bảng thông tin khách hàng và hóa đơn
            PdfPTable customerTable = new PdfPTable(4);
            customerTable.setWidthPercentage(100);
            customerTable.setSpacingBefore(10f);

            customerTable.setSpacingAfter(10f);

            float[] customerColumnWidths = {1f, 2f, 1f, 2f};
            customerTable.setWidths(customerColumnWidths);

            PdfPCell cell;

            cell = new PdfPCell(new Paragraph("Mã HĐ", boldFont));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell(new Paragraph(maHoaDon, font));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);

            cell = new PdfPCell(new Paragraph("Thu Ngân", boldFont));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell(new Paragraph(tenNV, font));
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

            cell = new PdfPCell(new Paragraph("SDT", boldFont));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);
            cell = new PdfPCell(new Paragraph(sdt, font));
            cell.setBorder(PdfPCell.NO_BORDER);
            customerTable.addCell(cell);

            doc.add(customerTable);

            // 4. Thêm bảng thông tin chi tiết sản phẩm
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

            for (int i = 0; i < tbGioHang.getRowCount(); i++) {
                String stt = tbGioHang.getValueAt(i, 0).toString();
                String productName = tbGioHang.getValueAt(i, 2).toString();
                String material = tbGioHang.getValueAt(i, 4).toString();
                String size = tbGioHang.getValueAt(i, 5).toString();
                String color = tbGioHang.getValueAt(i, 6).toString();
                String sole = tbGioHang.getValueAt(i, 7).toString();
                String theFirm = tbGioHang.getValueAt(i, 3).toString();
                String shoeCollar = tbGioHang.getValueAt(i, 11).toString();
                String mass = tbGioHang.getValueAt(i, 8).toString();
                String quantity = tbGioHang.getValueAt(i, 13).toString();
                String totalAmount = tbGioHang.getValueAt(i, 14).toString();

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

            // HoaDonResponse selectedHoaDon = currentList.get(selectedRow);
            // Lấy giá trị thành tiền và tiền chiết khấu, kiểm tra nếu chúng là null
            Double thanhTien = selectedHoaDon.getTongTien() != null ? selectedHoaDon.getTongTien() : 0.0;
            Double tienChietKhau = selectedHoaDon.getIdKH() != null ? selectedHoaDon.getIdKH() : 0.0;

            // Lấy giá trị tiền mặt và tiền chuyển khoản, kiểm tra nếu chúng là null
            Double tienMat = selectedHoaDon.getTienMat() != null ? selectedHoaDon.getTienMat() : 0.0;
            Double tienChuyenKhoan = selectedHoaDon.getTienChuyenKhoan() != null ? selectedHoaDon.getTienChuyenKhoan()
                    : 0.0;

            // Tính tổng tiền khách đưa
            Double tienKhachDua = tienMat + tienChuyenKhoan;

            // Lấy phương thức thanh toán và xác định loại thanh toán
            Integer phuongThucThanhToan = selectedHoaDon.getTenHTTT();
            String pttt;
            if (phuongThucThanhToan != null) {
                if (phuongThucThanhToan == 1) {
                    pttt = "Tiền mặt";
                } else if (phuongThucThanhToan == 2) {
                    pttt = "Chuyển khoản";
                } else {
                    pttt = "Kết hợp";
                }
            } else {
                pttt = "Không xác định";
            }

            // Format currency in VND
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            // Tạo thông tin thanh toán và thêm vào tài liệu
            Paragraph paymentInfo = new Paragraph();
            paymentInfo.add(new Paragraph("Thành Tiền: " + currencyFormat.format(thanhTien), font));
            paymentInfo.add(new Paragraph("Tiền Chiết Khấu: " + currencyFormat.format(tienChietKhau), font));
            paymentInfo.add(new Paragraph(
                    "Thành Tiền Sau Khi Giảm: " + currencyFormat.format(thanhTien - tienChietKhau), font));
            paymentInfo.add(new Paragraph("Tiền Khách Đưa: " + currencyFormat.format(tienKhachDua), font));
            paymentInfo.add(new Paragraph("Phương Thức Thanh Toán: " + pttt, font));
            doc.add(paymentInfo);

            doc.add(new Paragraph(" ")); // Thêm khoảng trống
            doc.add(new Paragraph(
                    "----------------------------------------------------------------------------------------------------------------------------------"));
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
            // size qr, number (dạng)
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
        } catch (NumberFormatException e) {
            // Thông báo lỗi nếu có lỗi về định dạng số
            JOptionPane.showMessageDialog(this, "Lỗi định dạng số trong dữ liệu hóa đơn!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            doc.close();
        }

        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT,
                "Export thành công");
    }

    // =========================================================
    public void onQrCodeRead(String qrCodeText) {
        // Lấy sản phẩm dựa trên mã QR
        SanPhamChiTietRepose product = findProductByQRCode(qrCodeText);

        if (product != null) {
            // Hiển thị hộp thoại nhập số lượng
            String quantityStr = JOptionPane.showInputDialog(this, "Số lượng là:", "0");

            if (quantityStr != null && !quantityStr.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityStr);

                    // Kiểm tra số lượng hợp lệ
                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                        return;
                    }

                    if (quantity > product.getSoLuong()) {
                        JOptionPane.showMessageDialog(this, "Số lượng không đủ trong kho!");
                        return;
                    }

                    // Tạo chi tiết hóa đơn
                    HoaDonChiTietResponse hoaDonChiTietResponse = HoaDonChiTietResponse.builder()
                            .hoaDonID(repoBh.getAllBanHang(id).get(indexHoaDonSelected).getId())
                            .ctspID(product.getIdSpct())
                            .maSP(product.getMaSP())
                            .tenSP(product.getTenSp())
                            .soLuong(quantity)
                            .donGia(product.getDonGia())
                            .thanhTien(quantity * product.getDonGia())
                            .ghiChu(product.getGhiChu())
                            .MauSac(product.getMauSac())
                            .hang(product.getHang())
                            .chatLieu(product.getChatLieu())
                            .kichThuoc(product.getKichThuoc())
                            .deGiay(product.getDeGiay())
                            .NSX(product.getNsx())
                            .build();

                    // Thêm vào hóa đơn chi tiết và cập nhật số lượng sản phẩm
                    hoaDonChiTietRepository.add(hoaDonChiTietResponse);
                    product.setSoLuong(product.getSoLuong() - quantity);
                    repo_chitietsanpham.updateSoluong(product);

                    // Cập nhật bảng giỏ hàng
                    showTableGioHang(); // Đảm bảo phương thức này cập nhật đúng

                    // Cập nhật bảng chi tiết hóa đơn
                    showTableSanPham(repo_chitietsanpham.loadSanPhamChiTietFromDb());
                    showTableHoaDonCHiTiet(
                            hoaDonChiTietRepository.getAll(repoBh.getAllBanHang(id).get(indexHoaDonSelected).getId()));

                    // Cập nhật tổng tiền
                    double totalMoney = showTotalMoney(
                            hoaDonChiTietRepository.getAll(repoBh.getAllBanHang(id).get(indexHoaDonSelected).getId()));

                    repoBh.getAllBanHang(id).get(indexHoaDonSelected).setTongTien(totalMoney);
                    lblTongTq.setText(formatCurrency(totalMoney));

                    // Cập nhật vào cơ sở dữ liệu
                    hoaDonChiTietRepository.updateTongTien(totalMoney,
                            repoBh.getAllBanHang(id).get(indexHoaDonSelected).getId());
                    showTableHoaDon(repoBh.getAllBanHang(id));
                    updateVouchers(phieuGiamGiaRespository.getAllStatusPhieu(), totalMoney);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm cho mã QR: " + qrCodeText);
        }
    }

    private SanPhamChiTietRepose findProductByQRCode(String qrCodeText) {
        for (SanPhamChiTietRepose product : repo_chitietsanpham.loadSanPhamChiTietFromDb()) {
            if (product.getMaSP().equals(qrCodeText)) {
                return product;
            }
        }
        return null;
    }

    private void showTableGioHang() {
        // Implement logic to refresh the cart table (tbGioHang)
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btChon;
    private javax.swing.JButton btChon1;
    private javax.swing.JButton btChon2;
    private javax.swing.JButton btLamMoi;
    private javax.swing.JButton btQuetQR;
    private javax.swing.JButton btTaoHoaDon;
    private javax.swing.JButton btThanhToanTq;
    private javax.swing.JButton btnGiaoHang;
    private javax.swing.JButton btnHuyGiaoHang;
    private swing.ButtonCustom btnHuyLoc;
    private javax.swing.JButton btnXoa;
    private combobox.Combobox cboHinhThucThanhToanDh;
    private combobox.Combobox cboHinhThucThanhToanTq;
    private combobox.Combobox cboPhieuGiamGiaTq;
    private combobox.Combobox cboPhieuGiamGiadh;
    private combobox.Combobox cboxChatLieu;
    private combobox.Combobox cboxCoGiay;
    private combobox.Combobox cboxDeGiay;
    private swing.Combobox cboxHang;
    private swing.Combobox cboxKhoiLuong;
    private combobox.Combobox cboxKichThuoc;
    private combobox.Combobox cboxMauSac;
    private combobox.Combobox cboxNhaSanXuat;
    private combobox.Combobox cboxSearch;
    private swing.Combobox cboxXuatXu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblMaHDDh;
    private javax.swing.JLabel lblMaHdTaiQuay;
    private javax.swing.JLabel lblMaNhanVienTq;
    private javax.swing.JLabel lblNgayTaoTq;
    private javax.swing.JLabel lblNhanViendh;
    private javax.swing.JLabel lblTienThuaDh;
    private javax.swing.JLabel lblTienThuaTq;
    private javax.swing.JLabel lblTongTiendh;
    private javax.swing.JLabel lblTongTq;
    private javax.swing.JTable tbDanhSachSanPham;
    private javax.swing.JTable tbGioHang;
    private javax.swing.JTable tbHoaDon;
    private textfield.TextField txtDiaChiDh;
    private textfield.TextField txtPhiShipdh;
    private textfield.TextField txtSDTKhDh;
    private textfield.TextField txtSDTNvDh;
    private textfield.TextField txtSdtTq;
    private swing.TextField txtSearch;
    private textfield.TextField txtTenKhachHang1;
    private textfield.TextField txtTenKhachHangDh;
    private textfield.TextField txtTenNhanVienDh;
    private textfield.TextField txtTienChuyenKhoanDh;
    private textfield.TextField txtTienChuyenKhoanTq;
    private textfield.TextField txtTienMatDh;
    private textfield.TextField txtTienMatTq;
    // End of variables declaration//GEN-END:variables

    @Override
    public void selectedKhachHang(KhachHang kh) {
        if (kh.getHoTen().equalsIgnoreCase("Khách lẻ")) {
            JOptionPane.showMessageDialog(this, "Không được chọn khách lẻ");
        } else {
            txtSDTKhDh.setText(kh.getSDT());
            txtTenKhachHangDh.setText(kh.getHoTen());

            Date ngayTao = new Date();

            // Retrieve the selected invoice from the table or data source
            HoaDonResponse repose = repoBh.getAllBanHang(id).get(indexHoaDonSelected);

            boolean isUpdated = false;

            // Check the invoice status and update accordingly
            if (repose.getTrangThaiHD() == 1) {
                isUpdated = repoBh.updateHoaDon(repose.getMaHD(), Auth.user.getId(), kh.getId(), kh.getSDT(),
                        repose.getTongTien(), ngayTao, 1, kh.getHoTen(), repose);
            } else if (repose.getTrangThaiHD() == 4) {
                isUpdated = repoBh.updateHoaDon(repose.getMaHD(), Auth.user.getId(), kh.getId(), kh.getSDT(),
                        repose.getTongTien(), ngayTao, 4, kh.getHoTen(), repose);
            }

            if (isUpdated) {
                // Refresh the table to show the updated data
                showTableHoaDon(repoBh.getAllBanHang(id));
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update invoice.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void selectedNhanVien(NhanVienResponse nv) {
        txtSDTNvDh.setText(nv.getSdt());
        txtTenNhanVienDh.setText(nv.getHoTenNv());
        nhanVienVanChuyen = nv;
    }
}
