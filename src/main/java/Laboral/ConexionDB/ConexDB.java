package Laboral.ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexDB {
    final String USER = "root";
    final String PASS = "12345678";
    final String DB_NAME = "nominas";
    final String CONN_URL = "jdbc:mariadb://localhost:3306/nominas";
    Connection conn = null;

    public void conexion() {
        try {
            conn = DriverManager.getConnection(CONN_URL, USER, PASS);
            System.out.println("Conexión válida: " + conn.isValid(10));
            System.out.println("Estado del autocommit: " + conn.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
