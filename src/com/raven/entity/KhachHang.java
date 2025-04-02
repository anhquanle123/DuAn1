/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.entity;

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
/**
 *
 * @author MSII
 */
public class KhachHang {
    private int id;
    
    private String maKH;
    
    private String hoTen;
    
    private boolean gioiTinh;
    
    private String SDT;
    
    private String diaChi;
    
}
