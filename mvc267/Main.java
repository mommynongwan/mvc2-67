// คลาสหลักสำหรับเริ่มต้นโปรแกรม
// ใช้สำหรับเรียกใช้งานคลาส App ซึ่งเป็น GUI หลักของโปรแกรม
public class Main {
    public static void main(String[] args) {
        View.App app = new View.App(); // สร้างอินสแตนซ์ของ App
        app.launchApp(); // เรียกใช้งาน GUI
    }
}
