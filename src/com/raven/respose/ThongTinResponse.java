
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

public class ThongTinResponse {
    private int idHd;
    
    private String maHd;
    
    private String maNv;
    
    private String tenKhachHang;
    
    private Date ngayTao;
    
    private String sdt;
    
    private Double tongTien;
    
    private String hinhThuc;
    
    private Double tienMat;
    
    private Double tienCk;
    
    private Double tienThua;
    
}
