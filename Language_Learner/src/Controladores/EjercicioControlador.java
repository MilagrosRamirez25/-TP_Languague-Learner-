package Controladores;

import Interfaces.EjercicioRepository;
import Modelos.Ejercicio;

import java.sql.*;
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
        String query = "SELECT * FROM ejercicio";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ejercicios.add(mapearEjercicio(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los ejercicios: " + e.getMessage());
        }
        return ejercicios;
    }

    @Override
    public List<Ejercicio> obtenerEjerciciosPorExamen(int idExamen) {
        List<Ejercicio> ejercicios = new ArrayList<>();
        String query = "SELECT * FROM ejercicio WHERE idExamen = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idExamen);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ejercicios.add(mapearEjercicio(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ejercicios por examen: " + e.getMessage());
        }
        return ejercicios;
    }

    @Override
    public Ejercicio getEjercicioById(int id) {
        Ejercicio ejercicio = null;
        String query = "SELECT * FROM ejercicio WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ejercicio = mapearEjercicio(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ejercicio por ID: " + e.getMessage());
        }
        return ejercicio;
    }

    @Override
    public void addEjercicio(Ejercicio ejercicio) {
        String query = "INSERT INTO ejercicio (idExamen, contenido, tipo, puntos) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ejercicio.getIdExamen());
            stmt.setString(2, ejercicio.getContenido());
            stmt.setString(3, ejercicio.getTipo());
            stmt.setInt(4, ejercicio.getPuntos());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar ejercicio: " + e.getMessage());
        }
    }

    @Override
    public boolean updateEjercicio(Ejercicio ejercicio) {
        String query = "UPDATE ejercicio SET idExamen = ?, contenido = ?, tipo = ?, puntos = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ejercicio.getIdExamen());
            stmt.setString(2, ejercicio.getContenido());
            stmt.setString(3, ejercicio.getTipo());
            stmt.setInt(4, ejercicio.getPuntos());
            stmt.setInt(5, ejercicio.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar ejercicio: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteEjercicio(int id) {
        String query = "DELETE FROM ejercicio WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar ejercicio: " + e.getMessage());
            return false;
        }
    }

    // Método privado para mapear un ResultSet a un objeto Ejercicio
    private Ejercicio mapearEjercicio(ResultSet rs) throws SQLException {
        return new Ejercicio(
                rs.getInt("id"),
                rs.getInt("idExamen"),
                rs.getString("contenido"),
                rs.getString("tipo"),
                rs.getInt("puntos")
        );
    }

	@Override
	public List<Ejercicio> getEjerciciosPorExamen(int idExamen) {
		// TODO Auto-generated method stub
		return null;
	}
	public int agregarEjercicio(Ejercicio ejercicio) {
	    String query = "INSERT INTO ejercicio (idExamen, contenido, tipo, puntos) VALUES (?, ?, ?, ?)";
	    try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	        stmt.setInt(1, ejercicio.getIdExamen());
	        stmt.setString(2, ejercicio.getContenido());
	        stmt.setString(3, ejercicio.getTipo());
	        stmt.setInt(4, ejercicio.getPuntos());
	        int filas = stmt.executeUpdate();
	        if (filas == 0) {
	            return -1;
	        }
	        try (ResultSet keys = stmt.getGeneratedKeys()) {
	            if (keys.next()) {
	                return keys.getInt(1); // ID generado
	            } else {
	                return -1;
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al agregar ejercicio: " + e.getMessage());
	        return -1;
	    }
	}

}
