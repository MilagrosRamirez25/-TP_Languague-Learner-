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
        String query = "SELECT * FROM clase";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clases.add(mapearClase(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las clases: " + e.getMessage());
        }
        return clases;
    }

    @Override
    public Clase getClaseById(int id) {
        Clase clase = null;
        String query = "SELECT * FROM clase WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    clase = mapearClase(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la clase por ID: " + e.getMessage());
        }
        return clase;
    }

    @Override
    public boolean agregarClase(Clase clase) {
        String query = "INSERT INTO clase (idCurso, titulo, tema, contenido, autor, fechaCreacion) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clase.getIdCurso());
            stmt.setString(2, clase.getTitulo());
            stmt.setString(3, clase.getTema());
            stmt.setString(4, clase.getContenido());
            stmt.setString(5, clase.getAutor());
            stmt.setString(6, clase.getFechaCreacion());
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar clase: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizarClase(Clase clase) {
        String query = "UPDATE clase SET idCurso = ?, titulo = ?, tema = ?, contenido = ?, autor = ?, fechaCreacion = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clase.getIdCurso());
            stmt.setString(2, clase.getTitulo());
            stmt.setString(3, clase.getTema());
            stmt.setString(4, clase.getContenido());
            stmt.setString(5, clase.getAutor());
            stmt.setString(6, clase.getFechaCreacion());
            stmt.setInt(7, clase.getId());

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar clase: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteClase(int id) {
        String query = "DELETE FROM clase WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar clase: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Clase> obtenerClasesPorCurso(int idCurso) {
        List<Clase> clases = new ArrayList<>();
        String query = "SELECT * FROM clase WHERE idCurso = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idCurso);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clases.add(mapearClase(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clases por curso: " + e.getMessage());
        }
        return clases;
    }

    public Clase obtenerClasePorTituloYCursos(String titulo, int idCurso) {
        Clase clase = null;
        String query = "SELECT * FROM clase WHERE titulo = ? AND idCurso = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, titulo);
            stmt.setInt(2, idCurso);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    clase = mapearClase(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clase por título y curso: " + e.getMessage());
        }
        return clase;
    }

    public boolean eliminarClasePorTituloYCursos(String titulo, int idCurso) {
        String query = "DELETE FROM clase WHERE titulo = ? AND idCurso = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, titulo);
            stmt.setInt(2, idCurso);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar clase por título y curso: " + e.getMessage());
            return false;
        }
    }

    private Clase mapearClase(ResultSet rs) throws SQLException {
        return new Clase(
                rs.getInt("id"),
                rs.getInt("idCurso"),
                rs.getString("titulo"),
                rs.getString("tema"),
                rs.getString("contenido"),
                rs.getString("autor"),
                rs.getString("fechaCreacion")
        );
    }
}
