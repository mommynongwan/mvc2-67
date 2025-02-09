// คลาสสำหรับจัดการข้อมูลสัตว์เลี้ยงและตรวจสอบเงื่อนไข
package Controller;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class PetController {
    private final String FILE_NAME = "Model/database.csv"; // กำหนดที่อยู่ของไฟล์ CSV
    private int accepted = 0, rejected = 0; // ตัวแปรนับจำนวนสัตว์ที่ผ่านและไม่ผ่านเงื่อนไข

    // เมธอดสำหรับเพิ่มสัตว์ลงในระบบ
    public void addPet(String petId, String type, String date, int vaccines, String specialValue) {
        boolean isValid = validatePet(type, specialValue);

        if (!isValid) {
            rejected++;
            return;
        }

        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(petId + "," + type + "," + date + "," + vaccines + "," + specialValue);
            accepted++;
            JOptionPane.showMessageDialog(null, "✅ Pet ID: " + petId + " - " + type + " has been successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error while saving data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // เมธอดตรวจสอบเงื่อนไขของสัตว์แต่ละประเภท
    private boolean validatePet(String type, String specialValue) {
        try {
            switch (type) {
                case "Phoenix":
                    if (!specialValue.equalsIgnoreCase("true")) {
                        JOptionPane.showMessageDialog(null, "Phoenix must have a fire-proof certificate - Rejected", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    break;
                case "Dragon":
                    if (Double.parseDouble(specialValue) > 70) {
                        JOptionPane.showMessageDialog(null, "Dragon has smoke pollution above 70% - Rejected", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    break;
                case "Owl":
                    if (Double.parseDouble(specialValue) < 100) {
                        JOptionPane.showMessageDialog(null, "Owl can fly less than 100 km without eating - Rejected", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid pet type.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format for " + type + " special value - Rejected", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // เมธอดสำหรับแสดงรายงานสัตว์ที่ถูกบันทึก
    public void showReport(JTextArea reportArea) {
        Map<String, Integer> countMap = new HashMap<>();
        int totalAccepted = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 2) continue; // Avoid errors if the line is incomplete
                String type = data[1];
                countMap.put(type, countMap.getOrDefault(type, 0) + 1);
                totalAccepted++;
            }
        } catch (IOException e) {
            reportArea.append("Unable to read CSV file.\n");
        }

        reportArea.append("Pet Admission Report:\n");
        for (String type : countMap.keySet()) {
            reportArea.append(type + ": " + countMap.get(type) + " pets\n");
        }
        reportArea.append("Accepted: " + accepted + " | Rejected: " + rejected + "\n");
    }
}
