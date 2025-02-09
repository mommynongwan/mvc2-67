// คลาสสำหรับแสดง GUI ของระบบจัดการสัตว์เลี้ยง
package View;

import Controller.PetController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    private PetController controller;
    
    // คอนสตรักเตอร์สำหรับสร้าง PetController
    public App() {
        controller = new PetController();
    }
    
    // เมธอดสำหรับแสดง GUI หลัก
    public void launchApp() {
        JFrame frame = new JFrame("Pet Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1));

        JLabel titleLabel = new JLabel("Pet Management System", SwingConstants.CENTER);
        frame.add(titleLabel);

        // ปุ่มสำหรับเพิ่มสัตว์, ดูรายงาน และออกจากโปรแกรม
        JButton addPetButton = new JButton("Add a Pet");
        JButton viewReportButton = new JButton("View Report");
        JButton exitButton = new JButton("Exit");

        frame.add(addPetButton);
        frame.add(viewReportButton);
        frame.add(exitButton);

        
        // กำหนดการทำงานเมื่อกดปุ่มเพิ่มสัตว์
        addPetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddPetDialog(frame);
            }
        });

        // กำหนดการทำงานเมื่อกดปุ่มดูรายงาน
        viewReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReportDialog(frame);
            }
        });

        // ปิดโปรแกรมเมื่อกดปุ่มออก
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    // เมธอดสำหรับแสดงหน้าต่างเพิ่มสัตว์
    private void showAddPetDialog(JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JTextField petIdField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField vaccinesField = new JTextField();
        JTextField specialValueField = new JTextField();

        panel.add(new JLabel("Pet ID: "));
        panel.add(petIdField);
        panel.add(new JLabel("Pet Type (Phoenix/Dragon/Owl): "));
        panel.add(typeField);
        panel.add(new JLabel("Last Health Check Date (dd/mm/yyyy): "));
        panel.add(dateField);
        panel.add(new JLabel("Number of Vaccines: "));
        panel.add(vaccinesField);
        panel.add(new JLabel("Special Value: "));
        panel.add(specialValueField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Add a Pet", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String petId = petIdField.getText();
            String type = typeField.getText();
            String date = dateField.getText();
            int vaccines;
            try {
                vaccines = Integer.parseInt(vaccinesField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid number for vaccines.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String specialValue = specialValueField.getText();

            controller.addPet(petId, type, date, vaccines, specialValue);
        }
    }

    // เมธอดสำหรับแสดงรายงานสัตว์ที่บันทึกไว้
    private void showReportDialog(JFrame frame) {
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        controller.showReport(reportArea);
        JOptionPane.showMessageDialog(frame, new JScrollPane(reportArea), "Pet Report", JOptionPane.INFORMATION_MESSAGE);
    }
}