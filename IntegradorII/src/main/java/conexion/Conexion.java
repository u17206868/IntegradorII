package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    static String driver = "com.mysql.cj.jdbc.Driver";
    static String url = "jdbc:mysql://localhost/plasticosdb";
    static String user = "root";
    static String password = "";

    public Conexion() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error en el driver: " + ex.getMessage());
        }
    }
    
    public Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Conexión establecida con éxito a " + url);
            }
        } catch (SQLException ex) {
            System.out.println("Error en la Conexión: " + ex.getMessage());
        }
        return conn;
    }
    
    public void desconectar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Error al desconectar: " + ex.getMessage());
            }
        }
    }
}
