/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repository.sanpham;

import com.raven.config.DBConnect;
import java.util.ArrayList;
import com.raven.model.sanpham_container.SanPhamChiTiet;
import com.raven.respose.SanPhamChiTietRepose;
import java.sql.*;

/**
 *
 * @author Ca1
 */
public class repo_chitietsanpham {

    Connection sConn = DBConnect.getConnection();

    public void addCTSPToDB(SanPhamChiTiet x) {
        String query = "INSERT INTO dbo.ChiTietSanPham\n"
                + "(\n"
                + "    IdSP,\n"
                + "    IdHang,\n"
                + "    IdChatLieu,\n"
                + "    IdKichThuoc,\n"
                + "    IdMauSac,\n"
                + "    IdDeGiay,\n"
                + "    IdCoGiay,\n"
                + "    IdKhoiLuong,\n"
                + "    IdXuatXu,\n"
                + "    IdNSX,\n"
                + "    MaCTSP,\n"
                + "    TenCTSP,\n"
                + "    SoLuong,\n"
                + "    DonGia,\n"
                + "    GhiChu,\n"
                + "    TrangThai,\n"
                + "    urlHinhAnh\n"
                + ")\n"
                + "VALUES\n"
                + "(\n"
                + "    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?\n"
                + ")";
        try {
            PreparedStatement stm = sConn.prepareStatement(query);

            stm.setString(1, x.getSanPham());
            stm.setString(2, x.getHang());
            stm.setString(3, x.getChatLieu());
            stm.setString(4, x.getKichThuoc());
            stm.setString(5, x.getMauSac());
            stm.setString(6, x.getDeGiay());
            stm.setString(7, x.getCoGiay());
            stm.setString(8, x.getKhoiLuong());
            stm.setString(9, x.getXuatXu());
            stm.setString(10, x.getNhaSanXuat());
            stm.setString(11, x.getIdSPCT());
            stm.setString(12, x.getTenCTSP());
            stm.setInt(13, Integer.parseInt(x.getSoLuongTon()));
            stm.setDouble(14, Double.parseDouble(x.getDonGia()));
            stm.setString(15, x.getGhiChu());
            stm.setInt(16, x.getTrangThai());
            stm.setString(17, x.getUrlHinhAnh());

            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCTSPToDb(SanPhamChiTiet x) {
        String query = "UPDATE dbo.ChiTietSanPham\n"
                + "SET\n"
                + "    IdSP = ?,\n"
                + "    IdHang = ?,\n"
                + "    IdChatLieu = ?,\n"
                + "    IdKichThuoc = ?,\n"
                + "    IdMauSac = ?,\n"
                + "    IdDeGiay = ?,\n"
                + "    IdCoGiay = ?,\n"
                + "    IdKhoiLuong = ?,\n"
                + "    IdXuatXu = ?,\n"
                + "    IdNSX = ?,\n"
                + "    MaCTSP = ?,\n"
                + "    TenCTSP = ?,\n"
                + "    SoLuong = ?,\n"
                + "    DonGia = ?,\n"
                + "    GhiChu = ?,\n"
                + "    TrangThai = ?,\n"
                + "    urlHinhAnh = ?\n"
                + "WHERE MaCTSP = ?";

        try {

            PreparedStatement stm = sConn.prepareStatement(query);

            stm.setString(1, "1");
            stm.setString(2, x.getHang());
            stm.setString(3, x.getChatLieu());
            stm.setString(4, x.getKichThuoc());
            stm.setString(5, x.getMauSac());
            stm.setString(6, x.getDeGiay());
            stm.setString(7, x.getCoGiay());
            stm.setString(8, x.getKhoiLuong());
            stm.setString(9, x.getXuatXu());
            stm.setString(10, x.getNhaSanXuat());
            stm.setString(11, x.getIdSPCT());
            stm.setString(12, x.getTenCTSP());
            stm.setInt(13, Integer.parseInt(x.getSoLuongTon()));
            stm.setDouble(14, Double.parseDouble(x.getDonGia()));
            stm.setString(15, x.getGhiChu());
            stm.setInt(16, x.getTrangThai());
            stm.setString(17, x.getUrlHinhAnh());

            // Set the value for the WHERE clause
            stm.setString(18, x.getIdSPCT());

            // Execute the update
            int rowsUpdated = stm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing product was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCTSPFromDb(String maCTSP, int trangThai) {
        String query = "UPDATE dbo.ChiTietSanPham\n"
                + "SET TrangThai = ?\n"
                + "WHERE MaCTSP = ?";
        try {
            PreparedStatement stm = sConn.prepareStatement(query);

            stm.setObject(1, trangThai);
            stm.setString(2, maCTSP);

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SanPhamChiTiet> loadListSanPhamChiTietFromDb() {

        ArrayList<SanPhamChiTiet> sanPhams = new ArrayList<SanPhamChiTiet>();
        String query = "SELECT ctsp.MaCTSP, sp.TenSanPham, h.TenHang, kl.TenKhoiLuong, cl.Loai, kt.Size, \n"
                + "       ms.TenMau, dg.TenDeGiay, xx.NoiXuatXu, nsx.TenNSX, cg.TenCoGiay, \n"
                + "       ctsp.TenCTSP, ctsp.SoLuong, ctsp.DonGia, ctsp.TrangThai, \n"
                + "       ctsp.GhiChu, ctsp.urlHinhAnh\n"
                + "FROM dbo.ChiTietSanPham AS ctsp\n"
                + "INNER JOIN dbo.SanPham AS sp ON sp.Id = ctsp.IdSP\n"
                + "INNER JOIN dbo.ChatLieu AS cl ON cl.Id = ctsp.IdChatLieu\n"
                + "INNER JOIN dbo.CoGiay AS cg ON cg.Id = ctsp.IdCoGiay\n"
                + "INNER JOIN dbo.DeGiay AS dg ON dg.Id = ctsp.IdDeGiay\n"
                + "INNER JOIN dbo.MauSac AS ms ON ms.Id = ctsp.IdMauSac\n"
                + "INNER JOIN dbo.Hang AS h ON h.Id = ctsp.IdHang\n"
                + "INNER JOIN dbo.KhoiLuong AS kl ON kl.Id = ctsp.IdKhoiLuong\n"
                + "INNER JOIN dbo.XuatXu AS xx ON xx.Id = ctsp.IdXuatXu\n"
                + "INNER JOIN dbo.NSX AS nsx ON nsx.Id = ctsp.IdNSX\n"
                + "INNER JOIN dbo.KichThuoc AS kt ON kt.Id = ctsp.IdKichThuoc\n"
                + "ORDER BY ctsp.Id DESC;";

        try {
            Statement stm = sConn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            int count = 0;

            while (rs.next()) {
                SanPhamChiTiet ms = new SanPhamChiTiet();

                ms.setIdSPCT(rs.getString("MaCTSP"));
                ms.setSanPham(rs.getString("TenSanPham"));
                ms.setHang(rs.getString("TenHang"));
                ms.setKhoiLuong(rs.getString("TenKhoiLuong"));
                ms.setChatLieu(rs.getString("Loai"));
                ms.setKichThuoc(rs.getString("Size"));
                ms.setMauSac(rs.getString("TenMau"));
                ms.setDeGiay(rs.getString("TenDeGiay"));
                ms.setXuatXu(rs.getString("NoiXuatXu"));
                ms.setNhaSanXuat(rs.getString("TenNSX"));
                ms.setCoGiay(rs.getString("TenCoGiay"));
                ms.setTenCTSP(rs.getString("TenCTSP"));
                ms.setSoLuongTon(rs.getString("SoLuong"));
                ms.setDonGia(rs.getString("DonGia"));
                ms.setTrangThai(rs.getInt("TrangThai"));
                ms.setGhiChu(rs.getString("GhiChu"));
                ms.setUrlHinhAnh(rs.getString("urlHinhAnh"));

                sanPhams.add(ms);
                count++;

                ms.setIndex(count++);
            }
            System.out.println("Number of records retrieved: " + count);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sanPhams;
    }

    public SanPhamChiTiet getAllIdSPCT(String maSPCT) {
        String query = "SELECT * FROM dbo.ChiTietSanPham WHERE MaCTSP = ?";
        SanPhamChiTiet x = new SanPhamChiTiet();
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, maSPCT);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                x.setChatLieu(rs.getString("IdChatLieu"));
                x.setSanPham(rs.getString("IdSP"));
                x.setHang(rs.getString("IdHang"));
                x.setChatLieu(rs.getString("IdChatLieu"));
                x.setKichThuoc(rs.getString("IdKichThuoc"));
                x.setMauSac(rs.getString("IdMauSac"));
                x.setDeGiay(rs.getString("IdDeGiay"));
                x.setCoGiay(rs.getString("IdCoGiay"));
                x.setKhoiLuong(rs.getString("IdKhoiLuong"));
                x.setXuatXu(rs.getString("IdXuatXu"));
                x.setNhaSanXuat(rs.getString("IdNSX"));
                x.setIdSPCT(rs.getString("MaCTSP"));
                x.setTenCTSP(rs.getString("TenCTSP"));
                x.setSoLuongTon(rs.getString("SoLuong"));
                x.setDonGia(rs.getString("DonGia"));
                x.setGhiChu(rs.getString("GhiChu"));
                x.setTrangThai(rs.getInt("TrangThai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }

    public ArrayList<SanPhamChiTiet> getListSPCTJoinSP(String maSP) {
        String query = "SELECT * FROM dbo.ChiTietSanPham AS ctsp\n"
                + "INNER JOIN dbo.SanPham AS sp\n"
                + "ON sp.Id = ctsp.IdSP\n"
                + "INNER JOIN dbo.ChatLieu AS cl\n"
                + "ON cl.Id = ctsp.IdChatLieu\n"
                + "INNER JOIN dbo.CoGiay AS cg\n"
                + "ON cg.Id = ctsp.IdCoGiay\n"
                + "INNER JOIN dbo.DeGiay AS dg\n"
                + "ON dg.Id = ctsp.IdDeGiay\n"
                + "INNER JOIN dbo.MauSac AS ms\n"
                + "ON ms.Id = ctsp.IdMauSac\n"
                + "INNER JOIN dbo.Hang AS h\n"
                + "ON h.Id = ctsp.IdHang\n"
                + "INNER JOIN dbo.KhoiLuong AS kl\n"
                + "ON kl.Id = ctsp.IdKhoiLuong\n"
                + "INNER JOIN dbo.XuatXu AS xx \n"
                + "ON xx.Id = ctsp.IdXuatXu\n"
                + "INNER JOIN dbo.NSX AS nsx\n"
                + "ON nsx.Id = ctsp.IdNSX\n"
                + "INNER JOIN dbo.KichThuoc AS kt\n"
                + "ON kt.Id = ctsp.IdKichThuoc\n"
                + "WHERE sp.MaSP = ?";

        ArrayList<SanPhamChiTiet> spcts = new ArrayList<>();
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, maSP);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SanPhamChiTiet ms = new SanPhamChiTiet();

                ms.setIdSPCT(rs.getString("MaCTSP"));
                ms.setSanPham(rs.getString("TenSanPham"));
                ms.setHang(rs.getString("TenHang"));
                ms.setKhoiLuong(rs.getString("TenKhoiLuong"));
                ms.setChatLieu(rs.getString("Loai"));
                ms.setKichThuoc(rs.getString("Size"));
                ms.setMauSac(rs.getString("TenMau"));
                ms.setDeGiay(rs.getString("TenDeGiay"));
                ms.setXuatXu(rs.getString("NoiXuatXu"));
                ms.setNhaSanXuat(rs.getString("TenNSX"));
                ms.setCoGiay(rs.getString("TenCoGiay"));
                ms.setTenCTSP(rs.getString("TenCTSP"));
                ms.setSoLuongTon(rs.getString("SoLuong"));
                ms.setDonGia(rs.getString("DonGia"));
                ms.setTrangThai(rs.getInt("TrangThai"));
                ms.setGhiChu(rs.getString("GhiChu"));
                ms.setUrlHinhAnh(rs.getString("urlHinhAnh"));

                spcts.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spcts;
    }

    ;
    public ArrayList<SanPhamChiTietRepose> loadSanPhamChiTietFromDb() {

        ArrayList<SanPhamChiTietRepose> sanPhams = new ArrayList<>();
        String query = """
SELECT    dbo.ChiTietSanPham.Id,
                       			dbo.ChiTietSanPham.MaCTSP, 
                       			dbo.SanPham.TenSanPham, 
                       			dbo.ChiTietSanPham.SoLuong, 
                       			dbo.ChiTietSanPham.DonGia,
                       			dbo.ChatLieu.Loai, 
                       			dbo.CoGiay.TenCoGiay, 
                       			dbo.DeGiay.TenDeGiay, 
                       			dbo.Hang.TenHang,
                       			dbo.KhoiLuong.TenKhoiLuong, 
                       			dbo.KichThuoc.Size, 
                       			dbo.MauSac.TenMau, 
                       			dbo.NSX.TenNSX, 
                       			dbo.XuatXu.NoiXuatXu
                       FROM         dbo.ChiTietSanPham INNER JOIN
                                             dbo.ChatLieu ON dbo.ChiTietSanPham.IdChatLieu = dbo.ChatLieu.Id INNER JOIN
                                             dbo.CoGiay ON dbo.ChiTietSanPham.IdCoGiay = dbo.CoGiay.Id INNER JOIN
                                             dbo.DeGiay ON dbo.ChiTietSanPham.IdDeGiay = dbo.DeGiay.Id INNER JOIN
                                             dbo.Hang ON dbo.ChiTietSanPham.IdHang = dbo.Hang.Id INNER JOIN
                                             dbo.KhoiLuong ON dbo.ChiTietSanPham.IdKhoiLuong = dbo.KhoiLuong.Id INNER JOIN
                                             dbo.KichThuoc ON dbo.ChiTietSanPham.IdKichThuoc = dbo.KichThuoc.Id INNER JOIN
                                             dbo.MauSac ON dbo.ChiTietSanPham.IdMauSac = dbo.MauSac.Id INNER JOIN
                                             dbo.NSX ON dbo.ChiTietSanPham.IdNSX = dbo.NSX.Id INNER JOIN
                                             dbo.XuatXu ON dbo.ChiTietSanPham.IdXuatXu = dbo.XuatXu.Id INNER JOIN
                                             dbo.SanPham ON dbo.ChiTietSanPham.IdSP = dbo.SanPham.Id
                       					  ORDER BY ChiTietSanPham.Id DESC
                       """;

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                SanPhamChiTietRepose repose = SanPhamChiTietRepose.builder()
                        .idSpct(rs.getInt(1))
                        .maSP(rs.getString(2))
                        .tenSp(rs.getString(3))
                        .soLuong(rs.getInt(4))
                        .donGia(rs.getDouble(5))
                        .chatLieu(rs.getString(6))
                        .coGiay(rs.getString(7))
                        .deGiay(rs.getString(8))
                        .hang(rs.getString(9))
                        .khoiLuong(rs.getString(10))
                        .kichThuoc(rs.getInt(11))
                        .mauSac(rs.getString(12))
                        .nsx(rs.getString(13))
                        .xuatXu(rs.getString(14))
                        .build();

                sanPhams.add(repose);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sanPhams;
    }

    public boolean updateSoluong(SanPhamChiTietRepose sp) {
        String sql = """
                    UPDATE [dbo].[ChiTietSanPham]
                        SET [SoLuong] = ?
                      WHERE Id = ? """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, sp.getSoLuong());
            ps.setObject(2, sp.getIdSpct());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
}
