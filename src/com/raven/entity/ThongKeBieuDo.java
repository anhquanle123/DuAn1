/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.entity;
import java.math.BigDecimal;
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
/**
 *
 * @author MSII
 */
public class ThongKeBieuDo {
    private Integer idHD;
    
    private Integer idKH;
    
    private Integer idSP;
    
    private Integer idHDCT;
    
    private Date ngayTao;
    
    private int thang;
    
    private BigDecimal thanhTien;

 
    
    
}
