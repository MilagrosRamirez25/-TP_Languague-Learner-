package Controladores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/lenguaje_learner";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private static DatabaseConnection instance;
    private Connection connection;
    
    private DatabaseConnection() {
        try {
            // Establece la conexión a la base de datos
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida a la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo establecer la conexión a la base de datos.");
        }
    }
    
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public Connection getConnection() {
        return connection;
    }
}
