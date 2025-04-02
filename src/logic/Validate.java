/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.logic;

import interfaces.Checker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import swing.Notification;
import swing.TextField;

/**
 *
 * @author Ca1
 */
public class Validate {

    private boolean chuoiHopLe = true;
    Map<String, String> listWarning = new LinkedHashMap<>();

    public Validate() {
    }

    public void khongDuocTrong(TextField... textFields) {
        for (TextField i : textFields) {
            if (i.getText().trim().isEmpty()) {
                listWarning.put(i.getLabelText(), i.getLabelText() + " không được để trống");
                chuoiHopLe = false;
            }
        }
    }

    public void khongDuocTrong(String label, String input) {
        if (input.trim().isEmpty()) {
            listWarning.put(label, label + " không được để trống");
            chuoiHopLe = false;
        }
    }

    public void chiDuocChuaSo(TextField... textFields) {
        for (TextField i : textFields) {
            String number = i.getText();
            try {
                Double.parseDouble(number);
            } catch (Exception e) {
                if (!number.isEmpty()) {
                    listWarning.put(i.getLabelText(), i.getLabelText() + " chỉ được chứa số");
                }
                chuoiHopLe = false;
            }
        }
    }
    
    public void khongDuocLaGiaTriNull(String label, Object x) {
        if ( x == null ) {
            chuoiHopLe = false;
            listWarning.put(label, label + " không được để trống");
        }
    }

    public void chiDuocChuaSo(String label, String input) {
        try {
            Double.parseDouble(input);
        } catch (Exception e) {
            if (!input.isEmpty()) {
                listWarning.put(label, label + " chỉ được chứa số");
            }
            chuoiHopLe = false;
        }
    }

    public void phaiLonHon0(TextField... textFields) {
        for (TextField i : textFields) {
            String number = i.getText();
            try {
                if (Double.parseDouble(number) <= 0) {
                    listWarning.put(i.getLabelText(), i.getLabelText() + " phải lớn hơn 0");
                    chuoiHopLe = false;
                }
            } catch (Exception e) {
            }
        }
    }

    public void phaiLonHon0(String label, String input) {
        try {
            if (Double.parseDouble(input) <= 0) {
                listWarning.put(label, label + " phải lớn hơn 0");
                chuoiHopLe = false;
            }
        } catch (Exception e) {
            // Handle exception if needed
        }
    }

    public void phaiNamTrongKhoang(Double first, Double end, String label, String input) {
        try {
            if (Double.parseDouble(input) <= first || Double.parseDouble(input) >= end) {
                listWarning.put(label, label + " phải nằm trong khoảng " + first + " - " + end);
                chuoiHopLe = false;
            }
        } catch (Exception e) {
            // Handle exception if needed
        }
    }

    public void khongChuaSoVaKiTuDacBiet(TextField... textFields) {
        Pattern pattern = Pattern.compile("[aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆ\n"
                + "fFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTu\n"
                + "UùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ]");
        for (TextField i : textFields) {
            if (!i.getText().isEmpty()) {
                String text = i.getText();
                Matcher matcher = pattern.matcher(text);
                if (!matcher.find()) {
                    listWarning.put(i.getLabelText(), i.getLabelText() + " không được chứa kí tự đặc biệt hoặc số");
                    chuoiHopLe = false;
                }
            }
        }
    }

    public <T> void khongDuocTrung(ArrayList<T> list, TextField textField, Checker checker) {
        for (T i : list) {
            if (!textField.getText().isEmpty() && checker.check(i, textField.getText())) {
                listWarning.put(textField.getLabelText(), textField.getLabelText() + " đã có sẵn");
                chuoiHopLe = false;
            }
        }
    }

    public void soThuNhatPhaiNhoHonSoThuHai(TextField textField1, TextField textField2) {
        try {
            double n1 = Double.parseDouble(textField1.getText());
            double n2 = Double.parseDouble(textField2.getText());
            if (n1 < n2) {
                listWarning.put("So lon hon", textField1.getLabelText() + " phải lớn hơn " + textField2.getLabelText());
                chuoiHopLe = false;
            }
        } catch (Exception e) {
        }
    }

    public boolean isChuoiHopLe() {
        return chuoiHopLe;
    }

    public void setChuoiHopLe(boolean chuoiHopLe) {
        this.chuoiHopLe = chuoiHopLe;
    }

    public void showWarning(JPanel panel) {
        int time = 0;
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);

        for (Map.Entry<String, String> entry : listWarning.entrySet()) {
            Timer timer = new Timer(time, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Notification notii = new Notification(parentFrame, Notification.Type.WARNING,
                            Notification.Location.TOP_RIGHT, entry.getValue());
                    notii.showNotification();
                }
            });
            timer.setRepeats(false);
            timer.start();
            time += 3000;
        }
    }
}
