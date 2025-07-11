package Controladores;

import Modelos.Ejercicio;
import Interfaces.EjercicioRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EjercicioControlador implements EjercicioRepository {

    private final Connection connection;

    public EjercicioControlador() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Ejercicio> getAllEjercicios() {
        List<Ejercicio> ejercicios = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ejercicio");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ejercicio e = new Ejercicio(
                    rs.getInt("id"),
                    rs.getInt("id_examen"),
                    rs.getString("contenido")
                );
                ejercicios.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ejercicios;
    }

    @Override
    public Ejercicio getEjercicioById(int id) {
        Ejercicio ejercicio = null;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ejercicio WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ejercicio = new Ejercicio(
                    rs.getInt("id"),
                    rs.getInt("id_examen"),
                    rs.getString("contenido")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ejercicio;
    }

    @Override
    public void addEjercicio(Ejercicio ejercicio) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO ejercicio (id_examen, contenido) VALUES (?, ?)"
            );
            stmt.setInt(1, ejercicio.getIdExamen());
            stmt.setString(2, ejercicio.getContenido());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateEjercicio(Ejercicio ejercicio) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "UPDATE ejercicio SET id_examen = ?, contenido = ? WHERE id = ?"
            );
            stmt.setInt(1, ejercicio.getIdExamen());
            stmt.setString(2, ejercicio.getContenido());
            stmt.setInt(3, ejercicio.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEjercicio(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM ejercicio WHERE id = ?");
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Ejercicio> getEjerciciosPorExamen(int idExamen) {
        List<Ejercicio> ejercicios = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM ejercicio WHERE id_examen = ?"
            );
            stmt.setInt(1, idExamen);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ejercicio e = new Ejercicio(
                    rs.getInt("id"),
                    rs.getInt("id_examen"),
                    rs.getString("contenido")
                );
                ejercicios.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ejercicios;
    }
}
