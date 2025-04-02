/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import com.raven.entity.NhanVien;
import com.raven.respose.NhanVienResponse;
import models.khachhang_container.KhachHang;

/**
 *
 * @author Ca1
 */
public interface EventFormChoose {
    void selectedKhachHang(KhachHang kh);
    void selectedNhanVien(NhanVienResponse nv);
    
}
