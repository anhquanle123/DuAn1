/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.logic;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Ca1
 */
public class RenderQRCode {

   public static String renderQRCode(String maSPCT) {
        String filePath = null;
        try {
            // Tạo thư mục nếu chưa tồn tại
            File directory = new File("src/img/qrcode");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Đặt tên file theo mã SPCT
            String fileName = maSPCT + ".png";
            filePath = new File(directory, fileName).getAbsolutePath();

            String charset = StandardCharsets.UTF_8.name();
            Map<EncodeHintType, Object> hintMap = new Hashtable<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            BitMatrix matrix = new MultiFormatWriter().encode(new String(maSPCT.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, (Hashtable) hintMap);

            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);
            
            System.out.println("QR code has been generated at: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }
    
    public static void main(String[] args) {
        
    }
}
