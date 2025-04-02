package com.raven.respose;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class NhanVienResponse {

    private int id;

    private String maNv;

    private String hoTenNv;

    private boolean gioiTinh;

    private Date ngaySinh;

    private String email;

    private String sdt;

    private int trangThai;

    private Double luong;

    private boolean chucVu;

    private String diachi;

    private String matKhau;

    public NhanVienResponse(String matKhau) {
        this.matKhau = matKhau;
    }
}
