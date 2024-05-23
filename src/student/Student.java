package student;

import db.MyConnection;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Student {

    private Connection con = MyConnection.getConnection();
    private PreparedStatement ps;
    private JTextField idField;
    private JTextField nameField;
    private JTextField dateField;
    private JTextField genderField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField fatherField;
    private JTextField motherField;
    private JTextField address1Field;
    private JTextField address2Field;
    private JTextField imagePathField;

    public int getMax() {
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT MAX(id) FROM student");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public void insert(int id, String sname, String date, String gender, String email, String phone, String father,
            String mother, String address1, String address2, String imagePath) {
        String sql = "INSERT INTO student VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, sname);
            ps.setString(3, date);
            ps.setString(4, gender);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setString(7, father);
            ps.setString(8, mother);
            ps.setString(9, address1);
            ps.setString(10, address2);
            ps.setString(11, imagePath);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thêm sinh viên mới thành công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isEmailExist(String email) {
        try {
            ps = con.prepareStatement("SELECT * FROM student WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isidExist(int id) {
        try {
            ps = con.prepareStatement("SELECT * FROM student WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isPhoneExist(String phone) {
        try {
            ps = con.prepareStatement("SELECT * FROM student WHERE phone = ?");
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void getStudentValue(JTable table, String searchValue) {
        String sql = "SELECT * FROM student WHERE CONCAT(id, name, email, phone) LIKE ? ORDER BY id";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Xóa tất cả các hàng cũ trong bảng
            while (rs.next()) {
                Object[] row = {
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getString(11)
                };
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(int id, String sname, String date, String gender, String email, String phone, String father,
            String mother, String address1, String address2, String imagePath) {
        String sql = "UPDATE student SET name=?, date_of_birth=?, gender=?, email=?, phone=?, father_name=?, mother_name=?, address1=?, address2=?, image_path=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, sname);
            ps.setString(2, date);
            ps.setString(3, gender);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, father);
            ps.setString(7, mother);
            ps.setString(8, address1);
            ps.setString(9, address2);
            ps.setString(10, imagePath);
            ps.setInt(11, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thông tin sinh viên thành công");
                clearFields(); // Triệu gọi phương thức clearFields() để xóa nội dung trong các trường dữ liệu
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearFields() {
        // Xóa nội dung trong các trường dữ liệu (với kiểm tra null)
        if (idField != null) {
            idField.setText("");
        }
        if (nameField != null) {
            nameField.setText("");
        }
        if (dateField != null) {
            dateField.setText("");
        }
        if (genderField != null) {
            genderField.setText("");
        }
        if (emailField != null) {
            emailField.setText("");
        }
        if (phoneField != null) {
            phoneField.setText("");
        }
        if (fatherField != null) {
            fatherField.setText("");
        }
        if (motherField != null) {
            motherField.setText("");
        }
        if (address1Field != null) {
            address1Field.setText("");
        }
        if (address2Field != null) {
            address2Field.setText("");
        }
        if (imagePathField != null) {
            imagePathField.setText("");
        }
    }

   public void delete(int id) {
    int yesOrNo = JOptionPane.showConfirmDialog(null, "Hồ sơ khóa học và điểm số cũng sẽ bị xóa", "Sinh viên xóa", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
    if (yesOrNo == JOptionPane.OK_OPTION) {
        try {
            ps = con.prepareStatement("DELETE FROM student WHERE id = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Sinh viên xóa thành công!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(); // In ra chi tiết lỗi
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa sinh viên!");
        }
    }
}

}
