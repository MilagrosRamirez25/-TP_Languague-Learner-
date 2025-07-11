package Controladores;

import Interfaces.ClaseRepository;
import Modelos.Clase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaseControlador implements ClaseRepository {

    private final Connection connection;

    public ClaseControlador() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Clase> getAllClases() {
        List<Clase> clases = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clase");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Clase c = new Clase(
                        rs.getInt("id"),
                        rs.getInt("id_curso"),
                        rs.getString("titulo"),
                        rs.getString("tema"),
                        rs.getString("contenido"),
                        rs.getString("autor"),
                        rs.getString("fecha_creacion")
                );
                clases.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clases;
    }

    @Override
    public Clase getClaseById(int id) {
        Clase clase = null;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clase WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                clase = new Clase(
                        rs.getInt("id"),
                        rs.getInt("id_curso"),
                        rs.getString("titulo"),
                        rs.getString("tema"),
                        rs.getString("contenido"),
                        rs.getString("autor"),
                        rs.getString("fecha_creacion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clase;
    }

    @Override
    public void addClase(Clase clase) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO clase (id_curso, titulo, tema, contenido, autor, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?)"
            );
            stmt.setInt(1, clase.getIdCurso());
            stmt.setString(2, clase.getTitulo());
            stmt.setString(3, clase.getTema());
            stmt.setString(4, clase.getContenido());
            stmt.setString(5, clase.getAutor());
            stmt.setString(6, clase.getFechaCreacion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateClase(Clase clase) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE clase SET id_curso = ?, titulo = ?, tema = ?, contenido = ?, autor = ?, fecha_creacion = ? WHERE id = ?"
            );
            stmt.setInt(1, clase.getIdCurso());
            stmt.setString(2, clase.getTitulo());
            stmt.setString(3, clase.getTema());
            stmt.setString(4, clase.getContenido());
            stmt.setString(5, clase.getAutor());
            stmt.setString(6, clase.getFechaCreacion());
            stmt.setInt(7, clase.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteClase(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM clase WHERE id = ?");
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Clase> getClasesPorCurso(int idCurso) {
        List<Clase> clases = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clase WHERE id_curso = ?");
            stmt.setInt(1, idCurso);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Clase c = new Clase(
                        rs.getInt("id"),
                        rs.getInt("id_curso"),
                        rs.getString("titulo"),
                        rs.getString("tema"),
                        rs.getString("contenido"),
                        rs.getString("autor"),
                        rs.getString("fecha_creacion")
                );
                clases.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clases;
    }
}
