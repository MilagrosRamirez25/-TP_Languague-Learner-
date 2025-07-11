package Controladores;

import Interfaces.ExamenRepository;
import Modelos.Examen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamenControlador implements ExamenRepository {

    private final Connection connection;

    public ExamenControlador() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Examen> getAllExamenes() {
        List<Examen> examenes = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM examen");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Examen ex = new Examen(
                        rs.getInt("id"),
                        rs.getInt("id_curso"),
                        rs.getString("titulo")
                );
                examenes.add(ex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examenes;
    }

    @Override
    public Examen getExamenById(int id) {
        Examen examen = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM examen WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                examen = new Examen(
                        rs.getInt("id"),
                        rs.getInt("id_curso"),
                        rs.getString("titulo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examen;
    }

    @Override
    public void addExamen(Examen examen) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO examen (id_curso, titulo) VALUES (?, ?)"
            );
            statement.setInt(1, examen.getIdCurso());
            statement.setString(2, examen.getTitulo());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateExamen(Examen examen) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE examen SET id_curso = ?, titulo = ? WHERE id = ?"
            );
            statement.setInt(1, examen.getIdCurso());
            statement.setString(2, examen.getTitulo());
            statement.setInt(3, examen.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteExamen(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM examen WHERE id = ?");
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Examen> getExamenesPorCurso(int idCurso) {
        List<Examen> examenes = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM examen WHERE id_curso = ?");
            statement.setInt(1, idCurso);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Examen ex = new Examen(
                        rs.getInt("id"),
                        rs.getInt("id_curso"),
                        rs.getString("titulo")
                );
                examenes.add(ex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examenes;
    }
}
