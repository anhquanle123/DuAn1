/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repository;

import com.raven.config.DBConnect;
import com.raven.entity.HoaDon;
import com.raven.entity.HoaDonChiTiet;
import com.raven.entity.KhachHang;
import com.raven.entity.ThongKeBieuDo;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import com.raven.response.HoaDonResponse;
import java.util.Date;

/**
 *
 * @author MSII
 */
public class ThongKeResponstory {

    public ArrayList<HoaDon> getAll() {
        String sql = """
                     SELECT Id, MaHoaDon, NgayTao, TongTien FROM HoaDon
                     """;

        ArrayList<HoaDon> lists = new ArrayList<>();

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon tk = new HoaDon();
                tk.setId(rs.getInt(1));
                tk.setMaHD(rs.getString(2));
                tk.setNgayTao(rs.getDate(3));
                tk.setTongTienKhiGiam(rs.getDouble(4));
                lists.add(tk);
            }
            return lists;

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public ArrayList<ThongKeBieuDo> getList() {
        String sql = """
                SELECT DATEPART(MONTH, NgayTao) AS Thang, SUM(TongTien) AS ThanhTien
                                    FROM HoaDon
                                    WHERE DATEPART(YEAR, NgayTao) = 2024
                                    GROUP BY DATEPART(MONTH, NgayTao)
                                    ORDER BY Thang;
                    """;
        ArrayList<ThongKeBieuDo> lists = new ArrayList<>();

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ThongKeBieuDo tk = new ThongKeBieuDo();
                tk.setThang(rs.getInt("Thang"));
                tk.setThanhTien(rs.getBigDecimal("ThanhTien"));
                lists.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return lists;
    }

    public static String khachHang() {
        String sql = """
                     SELECT COUNT(DISTINCT kh.Id) AS SoLuongKhachHang
                     FROM HoaDon hd
                     INNER JOIN KhachHang kh ON hd.IdKH = kh.Id
                     """;
        String data = "";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                data = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String SoHoaDonHuy() {
        String sql = """
                     SELECT COUNT(LichSuHoaDon.IdHD) FROM HoaDon, LichSuHoaDon 
                     WHERE HoaDon.Id = LichSuHoaDon.IdHD AND LichSuHoaDon.TrangThai = 3
                     """;
        String data = "";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                data = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static double doanhThu() {
        String sql = """
                    SELECT SUM(TongTien) AS TongDoanhThu
                    FROM HoaDon 
                    """;
        double data = 0;

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                data = rs.getDouble(1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String soLuongHoaDon() {
        String sql = """
                     SELECT COUNT(*) FROM HoaDon
                     """;
        String data = "";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                data = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static double locTheoDoanhThu(Date startDate, Date endDate) {
        String sql = """
                     SELECT SUM(TongTien) AS TongDoanhThu
                     FROM HoaDon
                     WHERE NgayTao >= ? AND NgayTao <= ?
                     """;

        double data = 0;

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, startDate);
            ps.setObject(2, endDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                data = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return data;
    }

    public static String locTheoSoHoaDon(Date startDate, Date endDate) {
        String sql = """
                    SELECT COUNT(*) FROM HoaDon WHERE NgayTao >= ? AND NgayTao <= ?
                     """;

        String data = "";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, startDate);
            ps.setObject(2, endDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                data = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return data;
    }

    public static String locTheoSoKhachHang(Date startDate, Date endDate) {
        String sql = """
                    SELECT COUNT(DISTINCT kh.Id) AS SoLuongKhachHang
                                         FROM HoaDon hd
                                         INNER JOIN KhachHang kh ON hd.IdKH = kh.Id
                     WHERE hd.NgayTao >= ? AND hd.NgayTao <= ?
                     """;

        String data = "";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, startDate);
            ps.setObject(2, endDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                data = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return data;
    }

    public static String locTheoSoHDHuy(Date startDate, Date endDate) {
        String sql = """
                 SELECT COUNT(LichSuHoaDon.IdHD) FROM HoaDon, LichSuHoaDon 
                    WHERE HoaDon.Id = LichSuHoaDon.IdHD AND LichSuHoaDon.TrangThai = 3
                     AND HoaDon.NgayTao >=? AND HoaDon.NgayTao <= ?
                     """;

        String data = "";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, startDate);
            ps.setObject(2, endDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                data = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return data;
    }

    public ArrayList<HoaDon> getAllLoc(Date startDate, Date endDate) {
        String sql = """
                     SELECT Id, MaHoaDon, NgayTao, TongTienKhiGiam  FROM HoaDon
                     WHERE NgayTao >= ? AND NgayTao <= ?
                     """;

        ArrayList<HoaDon> lists = new ArrayList<>();

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, startDate);
            ps.setObject(2, endDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon tk = new HoaDon();
                tk.setId(rs.getInt(1));
                tk.setMaHD(rs.getString(2));
                tk.setNgayTao(rs.getDate(3));
                tk.setTongTienKhiGiam(rs.getDouble(4));
                lists.add(tk);
            }
            return lists;

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
}
