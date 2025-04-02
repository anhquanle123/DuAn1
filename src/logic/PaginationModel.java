/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.logic;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

/**
 *
 * @author Ca1
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter

public class PaginationModel {

    private int currentPage; // Trang hiện tại
    private int itemsPerPage; // Số mục mỗi trang
    private int totalItems; // Tổng số mục
    private JRadioButton rdAll, rdConHang, rdHetHang;

    public PaginationModel(int itemsPerPage, int totalItems) {
        this.itemsPerPage = itemsPerPage;
        this.totalItems = totalItems;
        this.currentPage = 1; // Bắt đầu từ trang đầu tiên
    }
    
    public void setRadio(JRadioButton rdAll, JRadioButton rdConHang, JRadioButton rdHetHang) {
        setRdAll(rdAll);
        setRdConHang(rdConHang);
        setRdHetHang(rdHetHang);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage > 0 && currentPage <= getTotalPages()) {
            this.currentPage = currentPage;
        }
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) totalItems / itemsPerPage); // Tính tổng số trang
    }

    public int getStartIndex() {
        return (currentPage - 1) * itemsPerPage; // Chỉ số bắt đầu của trang hiện tại
    }

    public int getEndIndex() {
        return Math.min(currentPage * itemsPerPage, totalItems); // Chỉ số kết thúc của trang hiện tại
    }

    public JRadioButton getRdAll() {
        return rdAll;
    }

    public void setRdAll(JRadioButton rdAll) {
        this.rdAll = rdAll;
    }

    public JRadioButton getRdConHang() {
        return rdConHang;
    }

    public void setRdConHang(JRadioButton rdConHang) {
        this.rdConHang = rdConHang;
    }

    public JRadioButton getRdHetHang() {
        return rdHetHang;
    }

    public void setRdHetHang(JRadioButton rdHetHang) {
        this.rdHetHang = rdHetHang;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
    
    
}

