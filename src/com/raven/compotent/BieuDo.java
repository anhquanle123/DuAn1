/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.compotent;

import com.raven.repository.ThongKeResponstory;
import com.raven.entity.ThongKeBieuDo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author MSII
 */
public class BieuDo {

    private ThongKeResponstory repo = new ThongKeResponstory();

    public void setDataToChart(JPanel jpnItem) {
        ArrayList<ThongKeBieuDo> listItem = repo.getList();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (listItem != null) {
            for (ThongKeBieuDo item : listItem) {
                String thang = "Tháng " + item.getThang();
                dataset.addValue(item.getThanhTien(), "Tổng Tiền", thang);
            }
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu đồ thống kê Doanh Thu theo tháng",
                "Doanh Thu Năm 2024", "Số Tiền",
                dataset, PlotOrientation.VERTICAL, false, true, false
        );
        
        CategoryPlot plot = barChart.getCategoryPlot();
             plot.setRangeGridlinePaint(Color.BLACK);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 10));

        ChartPanel chartPanel = new ChartPanel(barChart);
        jpnItem.removeAll();
        jpnItem.setLayout(new BorderLayout());
        jpnItem.add(chartPanel, BorderLayout.CENTER);
        jpnItem.validate();
    }
}
