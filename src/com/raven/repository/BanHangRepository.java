
package com.raven.repository;

import com.raven.config.DBConnect;
import com.raven.response.HoaDonResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class BanHangRepository {

    public ArrayList<HoaDonResponse> getAllBanHang(int id) {
        String sql = """
                SELECT  dbo.HoaDon.Id,
                        dbo.HoaDon.IdPGG,
                        dbo.HoaDon.MaHoaDon, 
                        dbo.HoaDon.TenKhachHang, 
                        dbo.NhanVien.HoTen, 
                        dbo.KhachHang.Id AS Expr1,
                        dbo.NhanVien.Id AS Expr2, 
                        dbo.HoaDon.NgayTao, 
                        dbo.HoaDon.Sdt, 
                        dbo.HoaDon.TongTien, 
                        dbo.HoaDon.TrangThai
                FROM dbo.HoaDon 
                INNER JOIN dbo.KhachHang ON dbo.HoaDon.IdKH = dbo.KhachHang.Id 
                INNER JOIN dbo.NhanVien ON dbo.HoaDon.IdNV = dbo.NhanVien.Id
                WHERE dbo.HoaDon.TrangThai IN (1, 4, 5) AND dbo.NhanVien.Id = ? 
                ORDER BY dbo.HoaDon.Id DESC
                 """;
        ArrayList<HoaDonResponse> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id); // Set the parameter before executing the query
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonResponse response = HoaDonResponse.builder()
                        .id(rs.getInt(1))
                        .idPGG(rs.getInt(2))
                        .maHD(rs.getString(3))
                        .tenKH(rs.getString(4))
                        .tenNV(rs.getString(5))
                        .idKH(rs.getInt(6))
                        .idNV(rs.getInt(7))
                        .ngayTao(rs.getDate(8))
                        .sdtKH(rs.getString(9))
                        .tongTien(rs.getDouble(10))
                        .trangThaiHD(rs.getInt(11))
                        .build();
                lists.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // Log the error
        }
        return lists;
    }

    public boolean add(String maHD, int idNV, int idKh, String sdt, double tongTien, Date ngayTao, int trangThai, String tenKh) {
        String sql = """
                INSERT INTO [dbo].[HoaDon]
                                ([MaHoaDon]
                                ,[IdNV]
                                ,[IdKH]
                                ,[Sdt]
                                ,[TongTien]
                                ,[NgayTao]                    
                                ,[TrangThai]
                                ,[TenKhachHang])
                          VALUES
                                (?,?,?,?,?,?,?,?)
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maHD);
            ps.setObject(2, idNV);
            ps.setObject(3, idKh);
            ps.setObject(4, sdt);
            ps.setObject(5, tongTien);
            ps.setObject(6, ngayTao);
            ps.setObject(7, trangThai);
            ps.setObject(8, tenKh);

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }
    
       public boolean addLichSu(int idHd, String ma, String hd, String lydo, Date ngay, String ghiChu, int trangThai) {
        String sql = """
                INSERT INTO [dbo].[LichSuHoaDon]
                           ([IdHD]
                           ,[Ma]
                           ,[HanhDongNguoiThaoTac]
                           ,[LyDoHuy]
                           ,[NgayCapNhat]
                           ,[GhiChu]
                           ,[TrangThai])
                     VALUES
                           (?,?,?,?,?,?,?)
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, idHd);
            ps.setObject(2, ma);
            ps.setObject(3, hd);
            ps.setObject(4, lydo);
            ps.setObject(5, ngay);
            ps.setObject(6, ghiChu);
            ps.setObject(7, trangThai);

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }


    public boolean updateHoaDon(String maHD, int idNV, int idKh, String sdt, double tongTien, Date ngayTao, int trangThai, String tenKh, HoaDonResponse repose) {
        String sql = """
                UPDATE [dbo].[HoaDon]
                   SET [MaHoaDon] = ?
                		,[IdNV] = ?
                		,[IdKH] = ?
                		,[Sdt] = ?
                		,[TongTien] = ?
                                ,[NgayTao] = ?
                		,[TrangThai] = ?
                                ,[TenKhachHang] = ?
                
                 WHERE Id = ?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maHD);
            ps.setObject(2, idNV);
            ps.setObject(3, idKh);
            ps.setObject(4, sdt);
            ps.setObject(5, tongTien);
            ps.setObject(6, ngayTao);
            ps.setObject(7, trangThai);
            ps.setObject(8, tenKh);
            ps.setObject(9, repose.getId());

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean updateThanhToan(HoaDonResponse response) {
        String sql = """
                    UPDATE [dbo].[HoaDon]
                       SET [TrangThai] = 2
                     WHERE Id = ?
                     """;

        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setObject(1, response.getId());

            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean updateTrangThai(HoaDonResponse response) {
        String sql = """
                    UPDATE [dbo].[HoaDon]
                       SET [TrangThai] = 5
                     WHERE Id = ?
                     """;

        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setObject(1, response.getId());

            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
    
    public boolean updateTrangThaiHuy(HoaDonResponse response) {
        String sql = """
                    UPDATE [dbo].[HoaDon]
                       SET [TrangThai] = 4
                     WHERE Id = ?
                     """;

        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setObject(1, response.getId());

            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
    
        public boolean updatePhieuHd(int idPgg, int idHd) {
        String sql = """
                    UPDATE [dbo].[HoaDon]
                       SET 
                          [IdPGG] =?                        
                     WHERE id = ?
                     """;

        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setObject(1, idPgg);
            ps.setObject(2, idHd);

            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
    
        
        public boolean updateHinhThucHd(int idHttt, int idHd) {
        String sql = """
                   UPDATE [dbo].[HoaDon]
                       SET 
                        [IdHTTT] =?
                     WHERE id = ?
                     """;

        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setObject(1, idHttt);
            ps.setObject(2, idHd);

            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
    
    
    
}
