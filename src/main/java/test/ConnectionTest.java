package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de prueba para diagnosticar problemas de conexión
 */
public class ConnectionTest {
    private static final String URL = "jdbc:mysql://localhost:3306/app_administrativa";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    
    public static void main(String[] args) {
        System.out.println("=== DIAGNÓSTICO DE CONEXIÓN MYSQL ===");
        
        // 1. Verificar driver MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: Driver MySQL no encontrado");
            e.printStackTrace();
            return;
        }
        
        // 2. Intentar conexión
        Connection connection = null;
        try {
            System.out.println("Intentando conectar a: " + URL);
            System.out.println("Usuario: " + USER);
            
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos");
            
            // 3. Verificar base de datos y tabla
            java.sql.Statement stmt = connection.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM usuarios");
            if (rs.next()) {
                System.out.println("✅ Tabla 'usuarios' accesible. Total registros: " + rs.getInt("total"));
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión:");
            System.err.println("Código de error: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("✅ Conexión cerrada correctamente");
                } catch (SQLException e) {
                    System.err.println("Error al cerrar conexión");
                }
            }
        }
    }
}
