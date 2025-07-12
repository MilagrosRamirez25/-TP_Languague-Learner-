package Controladores;

import Interfaces.RespuestaRepository;
import Modelos.Respuesta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RespuestaControlador implements RespuestaRepository {

    private final Connection connection;

    public RespuestaControlador() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Respuesta> getAllRespuestas() {
        List<Respuesta> respuestas = new ArrayList<>();
        String query = "SELECT * FROM respuesta";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                respuestas.add(mapearRespuesta(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las respuestas: " + e.getMessage());
        }
        return respuestas;
    }

    @Override
    public Respuesta getRespuestaById(int id) {
        Respuesta respuesta = null;
        String query = "SELECT * FROM respuesta WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    respuesta = mapearRespuesta(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener respuesta por ID: " + e.getMessage());
        }
        return respuesta;
    }

   
 
    

    @Override
    public boolean updateRespuesta(Respuesta respuesta) {
        String query = "UPDATE respuesta SET idEjercicio = ?, respuesta = ?, esCorrecta = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, respuesta.getIdEjercicio());
            stmt.setString(2, respuesta.getRespuesta());
            stmt.setBoolean(3, respuesta.isCorrecta());
            stmt.setInt(4, respuesta.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar respuesta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteRespuesta(int id) {
        String query = "DELETE FROM respuesta WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar respuesta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Respuesta> obtenerRespuestasPorEjercicio(int idEjercicio) {
        List<Respuesta> respuestas = new ArrayList<>();
        String query = "SELECT * FROM respuesta WHERE idEjercicio = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idEjercicio);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    respuestas.add(mapearRespuesta(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener respuestas por ejercicio: " + e.getMessage());
        }
        return respuestas;
    }

    private Respuesta mapearRespuesta(ResultSet rs) throws SQLException {
        return new Respuesta(
                rs.getInt("id"),
                rs.getInt("idEjercicio"),
                rs.getString("respuesta"),
                rs.getBoolean("esCorrecta")
        );
    }

	@Override
	public List<Respuesta> getRespuestasPorEjercicio(int idEjercicio) {
		// TODO Auto-generated method stub
		return null;
	}
	 @Override
	public boolean agregarRespuesta(Respuesta respuesta) {
	    String query = "INSERT INTO respuesta (idEjercicio, respuesta, esCorrecta) VALUES (?, ?, ?)";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setInt(1, respuesta.getIdEjercicio());
	        stmt.setString(2, respuesta.getRespuesta());
	        stmt.setBoolean(3, respuesta.isCorrecta());
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        System.err.println("Error al agregar respuesta: " + e.getMessage());
	        return false;
	    }
	}

	

	
}
