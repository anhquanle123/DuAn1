/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repository;

import com.raven.config.DBConnect;
import com.raven.entity.PhieuGiamGia;
import com.raven.util.Helper;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Pham Thu
 */
public class PhieuGiamGiaRespository {

    public ArrayList<PhieuGiamGia> getAll() {
        String sql = """
                     SELECT [Id]
                           ,[MaPhieuGiamGia]
                           ,[TenPhieu]
                           ,[SoLuong]
                           ,[NgayBatDau]
                           ,[NgayKetThuc]
                           ,[DieuKien]
                           ,[Loai]
                           ,[GiaTriToiThieu]
                           ,[GiaTriToiDa]
                           ,[TrangThai]                          
                       FROM [dbo].[PhieuGiamGia]
                        ORDER BY [Id] DESC
                     """;
        ArrayList<PhieuGiamGia> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia pgg = new PhieuGiamGia();
                pgg.setId(rs.getInt(1));
                pgg.setMa(rs.getString(2));
                pgg.setTen(rs.getString(3));
                pgg.setSoLuong(rs.getInt(4));
                pgg.setNgayBatDau(rs.getDate(5));
                pgg.setNgayKetThuc(rs.getDate(6));
                pgg.setDieuKien(rs.getDouble(7));
                pgg.setLoai(rs.getString(8));
                pgg.setGiaTriToiThieu(rs.getDouble(9));
                pgg.setGiaTriToiDa(rs.getDouble(10));
                pgg.setTrangThai(rs.getString(11));
                list.add(pgg);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<PhieuGiamGia> getAllStatusPhieu() {
        String sql = """
                     SELECT [Id]
                    ,[MaPhieuGiamGia]
                    ,[TenPhieu]
                    ,[SoLuong]
                    ,[NgayBatDau]
                    ,[NgayKetThuc]
                    ,[DieuKien]
                    ,[Loai]
                    ,[GiaTriToiThieu]
                    ,[GiaTriToiDa]
                    ,[TrangThai]                          
                FROM [dbo].[PhieuGiamGia]
            where dbo.PhieuGiamGia.TrangThai = N'Đang diễn ra' 
            ORDER BY [Id] DESC                          
                     """;
        ArrayList<PhieuGiamGia> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia pgg = new PhieuGiamGia();
                pgg.setId(rs.getInt(1));
                pgg.setMa(rs.getString(2));
                pgg.setTen(rs.getString(3));
                pgg.setSoLuong(rs.getInt(4));
                pgg.setNgayBatDau(rs.getDate(5));
                pgg.setNgayKetThuc(rs.getDate(6));
                pgg.setDieuKien(rs.getDouble(7));
                pgg.setLoai(rs.getString(8));
                pgg.setGiaTriToiThieu(rs.getDouble(9));
                pgg.setGiaTriToiDa(rs.getDouble(10));
                pgg.setTrangThai(rs.getString(11));
                list.add(pgg);
            }
//（づ￣3￣）づ╭❤️～
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<PhieuGiamGia> getAllStatusPhieuDieuKien(int id) {
        String sql = """
                     SELECT [Id]
                    ,[MaPhieuGiamGia]
                    ,[TenPhieu]
                    ,[SoLuong]
                    ,[NgayBatDau]
                    ,[NgayKetThuc]
                    ,[DieuKien]
                    ,[Loai]
                    ,[GiaTriToiThieu]
                    ,[GiaTriToiDa]
                    ,[TrangThai]                          
                FROM [dbo].[PhieuGiamGia]
            where dbo.PhieuGiamGia.TrangThai = N'Đang diễn ra' and dbo.PhieuGiamGia.Id = ?
            ORDER BY [Id] DESC                          
                     """;
        ArrayList<PhieuGiamGia> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia pgg = new PhieuGiamGia();
                pgg.setId(rs.getInt(1));
                pgg.setMa(rs.getString(2));
                pgg.setTen(rs.getString(3));
                pgg.setSoLuong(rs.getInt(4));
                pgg.setNgayBatDau(rs.getDate(5));
                pgg.setNgayKetThuc(rs.getDate(6));
                pgg.setDieuKien(rs.getDouble(7));
                pgg.setLoai(rs.getString(8));
                pgg.setGiaTriToiThieu(rs.getDouble(9));
                pgg.setGiaTriToiDa(rs.getDouble(10));
                pgg.setTrangThai(rs.getString(11));
                list.add(pgg);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(PhieuGiamGia pgg) {
        String sql = """
                     INSERT INTO [dbo].[PhieuGiamGia]
                                ([MaPhieuGiamGia]
                                ,[TenPhieu]
                                ,[SoLuong]
                                ,[NgayBatDau]
                                ,[NgayKetThuc]
                                ,[DieuKien]
                                ,[Loai]
                                ,[GiaTriToiThieu]
                                ,[GiaTriToiDa]
                                ,[TrangThai])
                          VALUES
                                (?,?,?,?,?,?,?,?,?,?);
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, Helper.generateRandomMaPGG());
            ps.setObject(2, pgg.getTen());
            ps.setObject(3, pgg.getSoLuong());
            ps.setObject(4, pgg.getNgayBatDau());
            ps.setObject(5, pgg.getNgayKetThuc());
            ps.setObject(6, pgg.getDieuKien());
            ps.setObject(7, pgg.getLoai());
            ps.setObject(8, pgg.getGiaTriToiThieu());
            ps.setObject(9, pgg.getGiaTriToiDa());
            ps.setObject(10, pgg.getTrangThai());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public void updateNgayHetHan(int Id) {
        String sql = """
                     UPDATE [dbo].[PhieuGiamGia]
                        SET [TrangThai] = N'Hết hạn'
                        WHERE Id = ?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, Id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean update(PhieuGiamGia newPhieuGiamGia, int id) {
        String sql = """
                     UPDATE [dbo].[PhieuGiamGia]
                                     SET [TenPhieu] = ?
                                        ,[SoLuong] = ?
                                        ,[NgayBatDau] = ?
                                        ,[NgayKetThuc] = ?
                                        ,[DieuKien] = ?
                                        ,[Loai] = ?
                                        ,[GiaTriToiThieu] = ?
                                        ,[GiaTriToiDa] = ?
                                        ,[TrangThai] = ?
                                   WHERE Id = ?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, newPhieuGiamGia.getTen());
            ps.setObject(2, newPhieuGiamGia.getSoLuong());
            ps.setObject(3, newPhieuGiamGia.getNgayBatDau());
            ps.setObject(4, newPhieuGiamGia.getNgayKetThuc());
            ps.setObject(5, newPhieuGiamGia.getDieuKien());
            ps.setObject(6, newPhieuGiamGia.getLoai());
            ps.setObject(7, newPhieuGiamGia.getGiaTriToiThieu());
            ps.setObject(8, newPhieuGiamGia.getGiaTriToiDa());
            ps.setObject(9, newPhieuGiamGia.getTrangThai());
            ps.setObject(10, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public ArrayList<PhieuGiamGia> search(String keyword, String loai, String trangThai) {
        String sql = """
                     SELECT [Id]
                           ,[MaPhieuGiamGia]
                           ,[TenPhieu]
                           ,[SoLuong]
                           ,[NgayBatDau]
                           ,[NgayKetThuc]
                           ,[DieuKien]
                           ,[Loai]
                           ,[GiaTriToiThieu]
                           ,[GiaTriToiDa]
                           ,[TrangThai]                          
                       FROM [dbo].[PhieuGiamGia]                  
                        WHERE (Loai = ? OR ? is null )
                              AND (TrangThai = ? OR ? is null)    
                     """;

//         check neu keyword k nhap gi => k can them gi ca 
//         nhap => moi can cong them vao sql 
        if (keyword.length() > 0) {
            sql += """
                    AND (
                    [MaPhieuGiamGia] LIKE ?
                    OR 
                    [TenPhieu] LIKE ?
                    )
                    """;
        }

        ArrayList<PhieuGiamGia> list = new ArrayList<>();

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            int index = 1;

            ps.setObject(1, loai);
            ps.setObject(2, loai);
            ps.setObject(3, trangThai);
            ps.setObject(4, trangThai);

            if (keyword.length() > 0) {
                String value = "%" + keyword + "%";
                // search 1 o input nhieu truong
                ps.setObject(5, value);
                ps.setObject(6, value);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia pgg = new PhieuGiamGia();
                pgg.setId(rs.getInt(1));
                pgg.setMa(rs.getString(2));
                pgg.setTen(rs.getString(3));
                pgg.setSoLuong(rs.getInt(4));
                pgg.setNgayBatDau(rs.getDate(5));
                pgg.setNgayKetThuc(rs.getDate(6));
                pgg.setDieuKien(rs.getDouble(7));
                pgg.setLoai(rs.getString(8));
                pgg.setGiaTriToiThieu(rs.getDouble(9));
                pgg.setGiaTriToiDa(rs.getDouble(10));
                pgg.setTrangThai(rs.getString(11));
                list.add(pgg);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<PhieuGiamGia> locTheoKhoangThoiGian(Date startDate, Date endDate) {
        String sql = """
                     SELECT [Id]
                           ,[MaPhieuGiamGia]
                           ,[TenPhieu]
                           ,[SoLuong]
                           ,[NgayBatDau]
                           ,[NgayKetThuc]
                           ,[DieuKien]
                           ,[Loai]
                           ,[GiaTriToiThieu]
                           ,[GiaTriToiDa]
                           ,[TrangThai]                          
                       FROM [dbo].[PhieuGiamGia]
                     WHERE  [NgayBatDau] >= ? AND [NgayKetThuc] < ?
                            ORDER BY [NgayBatDau] ASC, [NgayKetThuc] DESC
                     """;
        ArrayList<PhieuGiamGia> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, startDate);
            ps.setObject(2, endDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuGiamGia pgg = new PhieuGiamGia();
                pgg.setId(rs.getInt(1));
                pgg.setMa(rs.getString(2));
                pgg.setTen(rs.getString(3));
                pgg.setSoLuong(rs.getInt(4));
                pgg.setNgayBatDau(rs.getDate(5));
                pgg.setNgayKetThuc(rs.getDate(6));
                pgg.setDieuKien(rs.getDouble(7));
                pgg.setLoai(rs.getString(8));
                pgg.setGiaTriToiThieu(rs.getDouble(9));
                pgg.setGiaTriToiDa(rs.getDouble(10));
                pgg.setTrangThai(rs.getString(11));
                list.add(pgg);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
