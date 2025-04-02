/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import com.raven.model.sanpham_container.SanPhamChiTiet;

/**
 *
 * @author Ca1
 */
public interface EventForm {
    public void addSPCT(SanPhamChiTiet spct);
    public void updateSPCT(SanPhamChiTiet spct);
    public void quitForm();
}
