package Controladores;

import Modelos.Profesor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Interfaces.ProfesorRepository;

public class ProfesorControlador implements ProfesorRepository {
    private final Connection connection;

    public ProfesorControlador() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Profesor> getAllUsers() {
        List<Profesor> profesores = new ArrayList<>();
        String query = "SELECT u.id, u.usuario, u.email, u.pass, u.rol, " +
                       "p.nombre, p.apellido, p.dni, p.especialidad " +
                       "FROM user u " +
                       "JOIN profesor p ON u.id = p.id " +
                       "WHERE u.rol = 1";  // Asumiendo rol=2 es profesor
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Profesor profesor = new Profesor(
                    resultSet.getInt("id"),
                    resultSet.getString("usuario"),
                    resultSet.getString("email"),
                    resultSet.getString("pass"),
                    resultSet.getInt("rol"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellido"),
                    resultSet.getString("dni"),
                    resultSet.getString("especialidad")
                );
                profesores.add(profesor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profesores;
    }

    @Override
    public Profesor getUserById(int id) {
        Profesor profesor = null;
        String query = "SELECT u.id, u.usuario, u.email, u.pass, u.rol, " +
                       "p.nombre, p.apellido, p.dni, p.especialidad " +
                       "FROM user u " +
                       "JOIN profesor p ON u.id = p.id " +
                       "WHERE u.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    profesor = new Profesor(
                        resultSet.getInt("id"),
                        resultSet.getString("usuario"),
                        resultSet.getString("email"),
                        resultSet.getString("pass"),
                        resultSet.getInt("rol"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("dni"),
                        resultSet.getString("especialidad")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profesor;
    }

    @Override
    public void addUser(Profesor profesor) {
        String insertUser = "INSERT INTO user (usuario, email, pass, rol) VALUES (?, ?, ?, ?)";
        String insertProfesor = "INSERT INTO profesor (id, nombre, apellido, dni, especialidad) VALUES (?, ?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false);

            // Insertar en tabla user
            try (PreparedStatement statementUser = connection.prepareStatement(insertUser, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statementUser.setString(1, profesor.getUsuario());
                statementUser.setString(2, profesor.getEmail());
                statementUser.setString(3, profesor.getPass());
                statementUser.setInt(4, profesor.getRol()); // debe ser 2 para profesor

                int affectedRows = statementUser.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Crear usuario falló, no se insertó ninguna fila.");
                }

                // Obtener el id generado automáticamente
                try (ResultSet generatedKeys = statementUser.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);

                        // Insertar en tabla profesor con ese id
                        try (PreparedStatement statementProfesor = connection.prepareStatement(insertProfesor)) {
                            statementProfesor.setInt(1, userId);
                            statementProfesor.setString(2, profesor.getNombre());
                            statementProfesor.setString(3, profesor.getApellido());
                            statementProfesor.setString(4, profesor.getDni());
                            statementProfesor.setString(5, profesor.getEspecialidad());
                            statementProfesor.executeUpdate();
                        }
                    } else {
                        throw new SQLException("Crear usuario falló, no se pudo obtener el ID.");
                    }
                }
            }

            connection.commit();
            System.out.println("Profesor agregado exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateUser(Profesor profesor) {
        String updateUser = "UPDATE user SET usuario = ?, email = ?, pass = ? WHERE id = ?";
        String updateProfesor = "UPDATE profesor SET nombre = ?, apellido = ?, dni = ?, especialidad = ? WHERE id = ?";
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement statementUser = connection.prepareStatement(updateUser)) {
                statementUser.setString(1, profesor.getUsuario());
                statementUser.setString(2, profesor.getEmail());
                statementUser.setString(3, profesor.getPass());
                statementUser.setInt(4, profesor.getId());
                statementUser.executeUpdate();
            }

            try (PreparedStatement statementProfesor = connection.prepareStatement(updateProfesor)) {
                statementProfesor.setString(1, profesor.getNombre());
                statementProfesor.setString(2, profesor.getApellido());
                statementProfesor.setString(3, profesor.getDni());
                statementProfesor.setString(4, profesor.getEspecialidad());
                statementProfesor.setInt(5, profesor.getId());
                statementProfesor.executeUpdate();
            }

            connection.commit();
            System.out.println("Profesor actualizado exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteUser(int id) {
        String deleteProfesor = "DELETE FROM profesor WHERE id = ?";
        String deleteUser = "DELETE FROM user WHERE id = ?";
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement statementProfesor = connection.prepareStatement(deleteProfesor)) {
                statementProfesor.setInt(1, id);
                statementProfesor.executeUpdate();
            }

            try (PreparedStatement statementUser = connection.prepareStatement(deleteUser)) {
                statementUser.setInt(1, id);
                statementUser.executeUpdate();
            }

            connection.commit();
            System.out.println("Profesor eliminado exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
