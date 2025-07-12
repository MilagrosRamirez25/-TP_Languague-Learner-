package Controladores;

import Interfaces.ExamenRepository;
import Modelos.Examen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamenControlador implements ExamenRepository {

    private final Connection conexion;

    public ExamenControlador() {
        this.conexion = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Examen> obtenerTodosLosExamenes() {
        List<Examen> examenes = new ArrayList<>();
        String consulta = "SELECT * FROM examen";
        try (PreparedStatement stmt = conexion.prepareStatement(consulta);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                examenes.add(mapearExamen(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los exámenes: " + e.getMessage());
        }
        return examenes;
    }

    @Override
    public Examen obtenerExamenPorId(int id) {
        Examen examen = null;
        String consulta = "SELECT * FROM examen WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    examen = mapearExamen(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener examen por ID: " + e.getMessage());
        }
        return examen;
    }

    @Override
    public boolean agregarExamen(Examen examen) {
        String consulta = "INSERT INTO examen (idClase, titulo) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
            stmt.setInt(1, examen.getIdClase());
            stmt.setString(2, examen.getTitulo());
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar examen: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizarExamen(Examen examen) {
        String consulta = "UPDATE examen SET idClase = ?, titulo = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
            stmt.setInt(1, examen.getIdClase());
            stmt.setString(2, examen.getTitulo());
            stmt.setInt(3, examen.getId());
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar examen: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminarExamen(int id) {
        String consulta = "DELETE FROM examen WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar examen: " + e.getMessage());
            return false;
        }
    }

    public Examen obtenerExamenPorIdClase(int idClase) {
        Examen examen = null;
        String consulta = "SELECT * FROM examen WHERE idClase = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
            stmt.setInt(1, idClase);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    examen = mapearExamen(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener examen por idClase: " + e.getMessage());
        }
        return examen;
    }

    private Examen mapearExamen(ResultSet rs) throws SQLException {
        return new Examen(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getInt("idClase")
        );
    }

    @Override
    public List<Examen> obtenerExamenesPorCurso(int idCurso) {
        // TODO: Implementar si se necesita obtener exámenes por curso
        return null;
    }

	@Override
	public List<Examen> getExamenesPorCurso(int idCurso) {
		// TODO Auto-generated method stub
		return null;
	}
	public int addExamen(Examen examen) {
	    String consulta = "INSERT INTO examen (idClase, titulo) VALUES (?, ?)";
	    try (PreparedStatement stmt = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS)) {
	        stmt.setInt(1, examen.getIdClase());
	        stmt.setString(2, examen.getTitulo());
	        int filasAfectadas = stmt.executeUpdate();
	        if (filasAfectadas == 0) {
	            return -1; // No se insertó nada
	        }
	        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1); // Retorna el id generado
	            } else {
	                return -1; // No se pudo obtener id generado
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al agregar examen: " + e.getMessage());
	        return -1;
	    }
	}

}
