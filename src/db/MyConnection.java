package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private static final String username = "root";
    private static final String password = "1234";
    private static final String dataConn = "jdbc:mysql://localhost:3306/students_management";

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(dataConn, username, password);
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
        return con;
    }
}
