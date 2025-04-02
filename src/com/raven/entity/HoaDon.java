/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.entity;

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
public class HoaDon {
     private Integer id;

    private Integer idKH;

    private Integer idNV;

    private Integer idPGG;

    private Integer idHTTT;

    private Integer idLSHD;

    private String maHD;

    private String maKH;

    private String maNV;

    private String tenKH;

    private String tenNV;

    private Integer tenHTTT;

    private Date ngayTao;

    private Date NgayThanhToan;

    private String sdtKH;

    private String diaChiKH;

    private Double tongTien;

    private Double tongTienKhiGiam;

    private Integer trangThaiHD;
}
