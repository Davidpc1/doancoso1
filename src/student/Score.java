package student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Score {

    private Connection con = MyConnection.getConnection();
    private PreparedStatement ps;

    public int getMax() {
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT MAX(id) FROM score");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public boolean getDetals(int sid, int semesterNo) {
        try {
            ps = con.prepareStatement("select *from course where student_id = ? and semester = ?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Home.jTextField12.setText(String.valueOf(rs.getInt(2)));
                Home.jTextField17.setText(String.valueOf(rs.getInt(3)));
                Home.jTextCouse1.setText(rs.getString(4));
                Home.jTextCouse2.setText(rs.getString(5));
                Home.jTextCouse3.setText(rs.getString(6));
                Home.jTextCouse4.setText(rs.getString(7));
                Home.jTextCouse5.setText(rs.getString(8));

                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Mã sinh viên hoặc số học kỳ không tồn tại!");

            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isidExist(int id) {
        try {
            ps = con.prepareStatement("SELECT * FROM score WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isSidSemesterNoExist(int sid, int semesterNo) {
        try {
            ps = con.prepareStatement("SELECT * FROM score WHERE student id = ? and semester = ?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

public void getScoreValue(JTable table, String searchValue) {
    String sql = "SELECT * FROM score WHERE CONCAT(id, student_id, semester, course_name) LIKE ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, "%" + searchValue + "%");
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear all existing rows in the table

        while (rs.next()) {
            Object[] row = new Object[14]; // Increase the array size to accommodate the new column
            row[0] = rs.getInt(1);
            row[1] = rs.getInt(2);
            row[2] = rs.getInt(3);
            row[3] = rs.getString(4);
            row[4] = rs.getDouble(5);
            row[5] = rs.getString(6);
            row[6] = rs.getDouble(7);
            row[7] = rs.getString(8);
            row[8] = rs.getDouble(9);
            row[9] = rs.getString(10);
            row[10] = rs.getDouble(11);
            row[11] = rs.getString(12);
            row[12] = rs.getDouble(13);
            row[13] = rs.getDouble(14);
         
            model.addRow(row);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
    }
}

//public void insert(int id, int sid, int semester, String course1, String course2, String course3, String course4,
  //          String course5, double score1, double score2, double score3, double score4, double score5, double average) {
        /*String sql = "INSERT INTO score VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, sid);
            ps.setInt(3, semester);

            ps.setString(4, course1);
            ps.setDouble(5, score1);
            ps.setString(6, course2);
            ps.setDouble(7, score2);
            ps.setString(8, course3);
            ps.setDouble(9, score3);
            ps.setString(10, course4);
            ps.setDouble(11, score4);
            ps.setString(12, course5);
            ps.setDouble(13, score5);
            ps.setDouble(14, average);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Score added successfully");

}
        } catch (SQLException ex) {
            Logger.getLogger(Score.class  

.getName()).log(Level.SEVERE, null, ex);
        }*/
        
//}
public void insert(int id, int sid, int semester, String course1, String course2, String course3, String course4,
                   String course5, double score1, double score2, double score3, double score4, double score5, double average) {
    String sql = "INSERT INTO score (id, sid, semester, course1, score1, course2, score2, course3, score3, course4, score4, course5, score5, average) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setInt(2, sid);
        ps.setInt(3, semester);
        ps.setString(4, course1);
        ps.setDouble(5, score1);
        ps.setString(6, course2);
        ps.setDouble(7, score2);
        ps.setString(8, course3);
        ps.setDouble(9, score3);
        ps.setString(10, course4);
        ps.setDouble(11, score4);
        ps.setString(12, course5);
        ps.setDouble(13, score5);
        ps.setDouble(14, average);

        int affectedRows = ps.executeUpdate();
        if (affectedRows > 0.0) {
            JOptionPane.showMessageDialog(null, "Đã thêm điểm thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Không thể thêm điểm!");
        }
    } catch (SQLException ex) {
        Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        ex.printStackTrace(); // In ra chi tiết lỗi
        JOptionPane.showMessageDialog(null, "Lỗi khi thêm điểm!");
    }
}
public void updateScore(int id, double score1, double score2, double score3, double score4, double score5, double average) {
    String sql = "UPDATE score SET score1=?, score2=?, score3=?, score4=?, score5=?, average=? WHERE id=?";
    
    try {
        ps = con.prepareStatement(sql);
        ps.setDouble(1, score1);
        ps.setDouble(2, score2);
        ps.setDouble(3, score3);
        ps.setDouble(4, score4);
        ps.setDouble(5, score5);
        ps.setDouble(6, average);
        ps.setInt(7, id);

        if (ps.executeUpdate() > 0.0) {
            JOptionPane.showMessageDialog(null, "Cập nhật điểm sinh viên thành công");
        } else {
            JOptionPane.showMessageDialog(null, "Không thể cập nhật điểm!");
        }
    } catch (SQLException ex) {
        Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        ex.printStackTrace(); // In ra chi tiết lỗi
        JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật điểm!");
    }
}

    }



