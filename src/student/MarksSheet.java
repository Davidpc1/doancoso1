
package student;

import com.mysql.cj.xdevapi.Statement;
import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MarksSheet {
    
    private Connection con = MyConnection.getConnection();
    private PreparedStatement ps;
     public boolean isidExist(int sid) {
        try {
            ps = con.prepareStatement("SELECT * FROM score WHERE student_id = ?");
            ps.setInt(1, sid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarksSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
public void getScoreValue(JTable table,int sid) {
    String sql = "SELECT * FROM score WHERE student_id = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, sid);
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear all existing rows in the table

        while (rs.next()) {
            Object[] row = new Object[15]; // Increase the array size to accommodate the new column
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
            row[14] = rs.getString(15); // Add the course_name column
            model.addRow(row);
        }
    } catch (SQLException ex) {
        Logger.getLogger(MarksSheet.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public double getCGPA(int sid) {
    double cgpa = 0.0;
    
    try (PreparedStatement ps = con.prepareStatement("SELECT AVG(average) FROM score WHERE student_id = ?")) {
        ps.setInt(1, sid);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                cgpa = rs.getDouble(1);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(MarksSheet.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return cgpa;
}

}
