package Controladores;

import Modelos.Alumno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Interfaces.AlumnoRepository;

public class AlumnoControlador implements AlumnoRepository {
    private final Connection connection;

    public AlumnoControlador() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Alumno> getAllUsers() {
        List<Alumno> alumnos = new LinkedList<>();
        String sql = "SELECT u.id, u.usuario, u.email, u.pass, u.rol, " +
                     "a.nombre, a.apellido, a.dni " +
                     "FROM usuario u JOIN alumno a ON u.id = a.id_usuario " +
                     "WHERE u.rol = 2"; // asumiendo rol 3 = alumno
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Alumno alumno = new Alumno(
                    rs.getInt("id"),
                    rs.getString("usuario"),
                    rs.getString("email"),
                    rs.getString("pass"),
                    rs.getInt("rol"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni")
                );
                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    @Override
    public Alumno getUserById(int id) {
        Alumno alumno = null;
        String sql = "SELECT u.id, u.usuario, u.email, u.pass, u.rol, " +
                     "a.nombre, a.apellido, a.dni " +
                     "FROM usuario u JOIN alumno a ON u.id = a.id_usuario " +
                     "WHERE u.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    alumno = new Alumno(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("email"),
                        rs.getString("pass"),
                        rs.getInt("rol"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumno;
    }

    @Override
    public void addUser(Alumno alumno) {
        String sqlUser = "INSERT INTO usuario (usuario, email, pass, rol) VALUES (?, ?, ?, ?)";
        String sqlAlumno = "INSERT INTO alumno (id_usuario, nombre, apellido, dni) VALUES (?, ?, ?, ?)";

        try (
            PreparedStatement psUser = connection.prepareStatement(sqlUser, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            // Insertar en usuario
            psUser.setString(1, alumno.getUsuario());
            psUser.setString(2, alumno.getEmail());
            psUser.setString(3, alumno.getPass());
            psUser.setInt(4, alumno.getRol());  // debe ser 3 para alumno
            int affectedRows = psUser.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Crear usuario falló, no se insertaron filas.");
            }

            try (ResultSet generatedKeys = psUser.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int newUserId = generatedKeys.getInt(1);

                    try (PreparedStatement psAlumno = connection.prepareStatement(sqlAlumno)) {
                        // Insertar en alumno con el id_usuario generado
                        psAlumno.setInt(1, newUserId);
                        psAlumno.setString(2, alumno.getNombre());
                        psAlumno.setString(3, alumno.getApellido());
                        psAlumno.setString(4, alumno.getDni());
                        psAlumno.executeUpdate();
                    }
                } else {
                    throw new SQLException("Crear usuario falló, no se obtuvo el ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(Alumno alumno) {
        String sqlUser = "UPDATE usuario SET usuario = ?, email = ?, pass = ?, rol = ? WHERE id = ?";
        String sqlAlumno = "UPDATE alumno SET nombre = ?, apellido = ?, dni = ? WHERE id_usuario = ?";

        try (
            PreparedStatement psUser = connection.prepareStatement(sqlUser);
            PreparedStatement psAlumno = connection.prepareStatement(sqlAlumno);
        ) {
            // Actualizar usuario
            psUser.setString(1, alumno.getUsuario());
            psUser.setString(2, alumno.getEmail());
            psUser.setString(3, alumno.getPass());
            psUser.setInt(4, alumno.getRol()); // debe ser 3
            psUser.setInt(5, alumno.getId());
            psUser.executeUpdate();

            // Actualizar alumno
            psAlumno.setString(1, alumno.getNombre());
            psAlumno.setString(2, alumno.getApellido());
            psAlumno.setString(3, alumno.getDni());
            psAlumno.setInt(4, alumno.getId());
            psAlumno.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        String sqlAlumno = "DELETE FROM alumno WHERE id_usuario = ?";
        String sqlUsuario = "DELETE FROM usuario WHERE id = ?";

        try (
            PreparedStatement psAlumno = connection.prepareStatement(sqlAlumno);
            PreparedStatement psUsuario = connection.prepareStatement(sqlUsuario);
        ) {
            // Primero borrar alumno
            psAlumno.setInt(1, id);
            psAlumno.executeUpdate();

            // Después borrar usuario
            psUsuario.setInt(1, id);
            psUsuario.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
