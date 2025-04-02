package com.raven.repository;

import com.raven.config.DBConnect;
import com.raven.entity.NhanVien;
import com.raven.respose.NhanVienResponse;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NhanVienRepository {

    public ArrayList<NhanVienResponse> getAll() {
        String sql = """
                        SELECT 
                            [Id]
                          ,[MaNhanVien]
                          ,[HoTen]
                          ,[GioiTinh]
                          ,[NgaySinh]
                          ,[Email]
                          ,[SDT]
                          ,[MatKhau]
                          ,[Luong]
                          ,[DiaChi]
                          ,[ChucVu]
                          ,[TrangThai]
                      FROM [dbo].[NhanVien]
                     where TrangThai = 1
                     """;
        ArrayList<NhanVienResponse> lists = new ArrayList<>();

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                NhanVienResponse response
                        = NhanVienResponse.builder()
                                .id(rs.getInt(1))
                                .maNv(rs.getString(2))
                                .hoTenNv(rs.getString(3))
                                .gioiTinh(rs.getBoolean(4))
                                .ngaySinh(rs.getDate(5))
                                .email(rs.getString(6))
                                .sdt(rs.getString(7))
                                .matKhau(rs.getString(8))
                                .luong(rs.getDouble(9))
                                .diachi(rs.getString(10))
                                .chucVu(rs.getBoolean(11))
                                .trangThai(rs.getInt(12))
                                .build();

                lists.add(response);

            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return lists;
    }

    public ArrayList<NhanVienResponse> getAllStatus() {
        String sql = """
                    SELECT [Id]
                          ,[MaNhanVien]
                          ,[HoTen]
                          ,[GioiTinh]
                          ,[NgaySinh]
                          ,[Email]
                          ,[SDT]
                          ,[MatKhau]
                          ,[Luong]
                          ,[DiaChi]
                          ,[ChucVu]
                          ,[TrangThai]
                      FROM [dbo].[NhanVien]
                     where TrangThai = 0
                     """;
        ArrayList<NhanVienResponse> lists = new ArrayList<>();

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                NhanVienResponse response
                        = NhanVienResponse.builder()
                                .id(rs.getInt(1))
                                .maNv(rs.getString(2))
                                .hoTenNv(rs.getString(3))
                                .gioiTinh(rs.getBoolean(4))
                                .ngaySinh(rs.getDate(5))
                                .email(rs.getString(6))
                                .sdt(rs.getString(7))
                                .matKhau(rs.getString(8))
                                .luong(rs.getDouble(9))
                                .diachi(rs.getString(10))
                                .chucVu(rs.getBoolean(11))
                                .trangThai(rs.getInt(12))
                                .build();

                lists.add(response);

            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return lists;
    }

    public boolean add(NhanVienResponse nhanVien) {
        String sql = """
                        INSERT INTO [dbo].[NhanVien]
                                ([MaNhanVien]
                                ,[HoTen]
                                ,[GioiTinh]
                                ,[NgaySinh]
                                ,[Email]
                                ,[SDT]
                                ,[MatKhau]
                                ,[Luong]
                                ,[DiaChi]
                                ,[ChucVu]
                                ,[TrangThai])
                          VALUES
                                (?,?,?,?,?,?,?,?,?,?,?)
                     """;

        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setObject(1, nhanVien.getMaNv());
            ps.setObject(2, nhanVien.getHoTenNv());
            ps.setObject(3, nhanVien.isGioiTinh());
            ps.setObject(4, nhanVien.getNgaySinh());
            ps.setObject(5, nhanVien.getEmail());
            ps.setObject(6, nhanVien.getSdt());
            ps.setObject(7, nhanVien.getMatKhau());
            ps.setObject(8, nhanVien.getLuong());
            ps.setObject(9, nhanVien.getDiachi());
            ps.setObject(10, nhanVien.isChucVu());
            ps.setObject(11, nhanVien.getTrangThai());

            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean update(int id, NhanVienResponse nv) {
        String sql = """
                    UPDATE [dbo].[NhanVien]
                        SET [MaNhanVien] = ?
                           ,[HoTen] = ?
                           ,[GioiTinh] = ?
                           ,[NgaySinh] = ?
                           ,[Email] = ?
                           ,[SDT] = ?
                           ,[MatKhau] = ?
                           ,[Luong] = ?
                           ,[DiaChi] = ?
                           ,[ChucVu] = ?
                      WHERE id =?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, nv.getMaNv());
            ps.setObject(2, nv.getHoTenNv());
            ps.setObject(3, nv.isGioiTinh());
            ps.setObject(4, nv.getNgaySinh());
            ps.setObject(5, nv.getEmail());
            ps.setObject(6, nv.getSdt());
            ps.setObject(7, nv.getMatKhau());
            ps.setObject(8, nv.getLuong());
            ps.setObject(9, nv.getDiachi());
            ps.setObject(10, nv.isChucVu());
            ps.setObject(11, id);

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean delete(int id) {
        String sql = """
                     UPDATE NhanVien set TrangThai = 0 where id =?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean deleteNghi(int id) {
        String sql = """
                     UPDATE NhanVien set TrangThai = 1 where id =?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public ArrayList<NhanVienResponse> search(String keyword, Integer trangThai, boolean gioiTinh) {
        ArrayList<NhanVienResponse> lists = new ArrayList<>();

        String sql = """
                        SELECT [Id]
                          ,[MaNhanVien]
                          ,[HoTen]
                          ,[GioiTinh]
                          ,[NgaySinh]
                          ,[Email]
                          ,[SDT]
                          ,[MatKhau]
                          ,[Luong]
                          ,[DiaChi]
                          ,[ChucVu]
                          ,[TrangThai]
                      FROM [dbo].[NhanVien]
                     where TrangThai = ?
                     and GioiTinh = ?
                     """;
        if (keyword.length() > 0) {
            sql += """
                  and
                  	(
                  	MaNhanVien like ?
                  	or
                  	HoTen like ?
                  	or
                  	NgaySinh like ?
                  	or
                  	Email like ?
                  	or
                  	SDT like ?
                  	or
                  	Luong like ?
                  	or
                  	DiaChi like ?
                  	)
                  """;
        }

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            int index = 1;
            ps.setObject(index++, trangThai);
            ps.setObject(index++, gioiTinh ? 1 : 0);
            if (keyword.length() > 0) {
                String value = "%" + keyword + "%";
                ps.setObject(index++, value);
                ps.setObject(index++, value);
                ps.setObject(index++, value);
                ps.setObject(index++, value);
                ps.setObject(index++, value);
                ps.setObject(index++, value);
                ps.setObject(index++, value);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                NhanVienResponse response
                        = NhanVienResponse.builder()
                                .id(rs.getInt(1))
                                .maNv(rs.getString(2))
                                .hoTenNv(rs.getString(3))
                                .gioiTinh(rs.getBoolean(4))
                                .ngaySinh(rs.getDate(5))
                                .email(rs.getString(6))
                                .sdt(rs.getString(7))
                                .matKhau(rs.getString(8))
                                .luong(rs.getDouble(9))
                                .diachi(rs.getString(10))
                                .chucVu(rs.getBoolean(11))
                                .trangThai(rs.getInt(12))
                                .build();

                lists.add(response);

            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return lists;
    }

    public boolean updateMatKhau(NhanVienResponse response, int id) {
        String sql = """
                    UPDATE [dbo].[NhanVien]
                        SET [MatKhau] = ?
                        WHERE Id = ?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, response.getMatKhau());
            ps.setObject(2, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean checkEmailTonTai(String email) {
        String sql = "SELECT COUNT(*) FROM [dbo].[NhanVien] WHERE Email = ?";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // Email tồn tại
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Email không tồn tại
    }

    public int getIdByEmail(String email) {
        String sql = "SELECT Id FROM [dbo].[NhanVien] WHERE Email = ?";
        int id = -1;

        try (Connection conn = DBConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public NhanVienResponse login(String email, String pass) {
        String sql = """
                        SELECT 
                            [Id]
                          ,[MaNhanVien]
                          ,[HoTen]
                          ,[GioiTinh]
                          ,[NgaySinh]
                          ,[Email]
                          ,[SDT]
                          ,[MatKhau]
                          ,[Luong]
                          ,[DiaChi]
                          ,[ChucVu]
                          ,[TrangThai]
                      FROM [dbo].[NhanVien]
                     where TrangThai = 1 and [Email] = ? and [MatKhau] = ?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, email);
            ps.setObject(2, pass);
            ResultSet rs = ps.executeQuery();
            NhanVienResponse response = null;
            while (rs.next()) {
                response = new NhanVienResponse();
                response.setId(rs.getInt(1));
                response.setMaNv(rs.getString(2));
                response.setHoTenNv(rs.getString(3));
                response.setGioiTinh(rs.getBoolean(4));
                response.setNgaySinh(rs.getDate(5));
                response.setEmail(rs.getString(6));
                response.setSdt(rs.getString(7));
                response.setMatKhau(rs.getString(8));
                response.setLuong(rs.getDouble(9));
                response.setDiachi(rs.getString(10));
                response.setChucVu(rs.getBoolean(11));
                response.setTrangThai(rs.getInt(12));
            }
            if (response == null) {
                return null;
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new NhanVienRepository().getAll());
        System.out.println(new NhanVienRepository().getAll());
    }
}
