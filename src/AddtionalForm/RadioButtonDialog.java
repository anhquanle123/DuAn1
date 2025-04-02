package AddtionalForm;

import com.raven.logic.ShowMessageCustom;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RadioButtonDialog extends JDialog {
    private JRadioButton option1;
    private JRadioButton option2;
    private ButtonGroup group;
    private int selectedOption = -1;

    public RadioButtonDialog(JPanel parentPanel) {
        // Lấy Window chứa JPanel làm parent cho JDialog
        super(SwingUtilities.getWindowAncestor(parentPanel), "Chọn trạng thái", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());

        // Tạo các radio button
        option1 = new JRadioButton("Còn hàng");
        option2 = new JRadioButton("Hết hàng");

        // Nhóm các radio button lại với nhau
        group = new ButtonGroup();
        group.add(option1);
        group.add(option2);

        // Panel để chứa các radio button
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(option1);
        panel.add(option2);

        // Panel cho nút xác nhận
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lưu lựa chọn khi nhấn nút OK
                if (option1.isSelected()) {
                    selectedOption = 1;
                } else if (option2.isSelected()) {
                    selectedOption = 0;
                }
                if ( selectedOption != -1 ) {
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn trạng thái");
                }
            }
        });
        buttonPanel.add(okButton);

        // Thêm các panel vào dialog
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Cài đặt kích thước và hiển thị dialog
        pack();
        setLocationRelativeTo(parentPanel);
        setSize(200, 120);
        setVisible(true);
    }

    // Phương thức getter để trả về lựa chọn
    public int getSelectedOption() {
        return selectedOption;
    }

    public static void main(String[] args) {
        // Tạo frame chính để hiển thị dialog
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Tạo một JPanel để truyền vào dialog
        JPanel panel = new JPanel();
        frame.add(panel);
        frame.setVisible(true);

        // Hiển thị dialog khi ứng dụng chạy
        RadioButtonDialog dialog = new RadioButtonDialog(panel);
        System.out.println("Selected Option: " + dialog.getSelectedOption());
    }
}
