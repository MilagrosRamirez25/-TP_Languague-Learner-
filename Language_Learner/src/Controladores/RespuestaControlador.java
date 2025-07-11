package Controladores;

import Modelos.Respuesta;
import Interfaces.RespuestaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM respuesta");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Respuesta r = new Respuesta(
                        rs.getInt("id"),
                        rs.getInt("id_ejercicio"),
                        rs.getString("respuesta"),
                        rs.getBoolean("es_correcta")
                );
                respuestas.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return respuestas;
    }

    @Override
    public Respuesta getRespuestaById(int id) {
        Respuesta respuesta = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM respuesta WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                respuesta = new Respuesta(
                        rs.getInt("id"),
                        rs.getInt("id_ejercicio"),
                        rs.getString("respuesta"),
                        rs.getBoolean("es_correcta")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    @Override
    public void addRespuesta(Respuesta respuesta) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO respuesta (id_ejercicio, respuesta, es_correcta) VALUES (?, ?, ?)"
            );
            statement.setInt(1, respuesta.getIdEjercicio());
            statement.setString(2, respuesta.getRespuesta());
            statement.setBoolean(3, respuesta.isCorrecta());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateRespuesta(Respuesta respuesta) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE respuesta SET id_ejercicio = ?, respuesta = ?, es_correcta = ? WHERE id = ?"
            );
            statement.setInt(1, respuesta.getIdEjercicio());
            statement.setString(2, respuesta.getRespuesta());
            statement.setBoolean(3, respuesta.isCorrecta());
            statement.setInt(4, respuesta.getId());

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteRespuesta(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM respuesta WHERE id = ?"
            );
            statement.setInt(1, id);
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Respuesta> getRespuestasPorEjercicio(int idEjercicio) {
        List<Respuesta> respuestas = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM respuesta WHERE id_ejercicio = ?"
            );
            statement.setInt(1, idEjercicio);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Respuesta r = new Respuesta(
                        rs.getInt("id"),
                        rs.getInt("id_ejercicio"),
                        rs.getString("respuesta"),
                        rs.getBoolean("es_correcta")
                );
                respuestas.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return respuestas;
    }
}
