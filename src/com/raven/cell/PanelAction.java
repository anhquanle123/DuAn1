/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.cell;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Pham Thu
 */
public class PanelAction extends javax.swing.JPanel {

    private TableActionEvent event;
    private int row;
    private String status;

    /**
     * Creates new form PanelAction
     */
        public void initEvent(TableActionEvent event, int row, String status) {

        this.event = event;
        this.row = row;
        this.status = status;

        // Disable button if status is "Expired"
        updateButtonState();
    }

        public PanelAction() {
        initComponents();
        cmdEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmdEdit.isEnabled()) {
                    editPopup.show(cmdEdit, cmdEdit.getWidth() / 2, cmdEdit.getHeight() / 2);
                }
            }
        });

        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (event != null) {
                    event.onViewDetails(row);
                }
            }
        });

        sapDienRa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCouponStatus("Sắp diễn ra");
            }
        });

        dangDienRa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCouponStatus("Đang diễn ra");
            }
        });

        hetHan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCouponStatus("Hết hạn");
            }
        });
        this.add(cmdEdit);
        this.add(detailsButton);
    }

    private void changeCouponStatus(String newStatus) {
        this.status = newStatus;
        updateButtonState();
        if (event != null) {
            event.onEdit(row, newStatus);
        }
    }

    private void updateButtonState() {
        cmdEdit.setEnabled(!"Hết hạn".equals(status));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editPopup = new javax.swing.JPopupMenu();
        dangDienRa = new javax.swing.JMenuItem();
        sapDienRa = new javax.swing.JMenuItem();
        hetHan = new javax.swing.JMenuItem();
        cmdEdit = new com.raven.cell.ActionButton();
        detailsButton = new com.raven.cell.ActionButton();

        dangDienRa.setText("Đang diễn ra");
        editPopup.add(dangDienRa);

        sapDienRa.setText("Sắp diễn ra");
        editPopup.add(sapDienRa);

        hetHan.setText("Hết hạn");
        editPopup.add(hetHan);

        cmdEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Edit.png"))); // NOI18N

        detailsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/view.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(cmdEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(detailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(detailsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cmdEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.cell.ActionButton cmdEdit;
    private javax.swing.JMenuItem dangDienRa;
    private com.raven.cell.ActionButton detailsButton;
    private javax.swing.JPopupMenu editPopup;
    private javax.swing.JMenuItem hetHan;
    private javax.swing.JMenuItem sapDienRa;
    // End of variables declaration//GEN-END:variables
}
