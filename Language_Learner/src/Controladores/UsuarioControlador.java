package Controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import Interfaces.UserRepository;
import Modelos.Alumno;
import Modelos.Profesor;
import Modelos.Usuario;

public class UsuarioControlador implements UserRepository {
	private final Connection connection;

	public UsuarioControlador() {
		this.connection = DatabaseConnection.getInstance().getConnection();
	}

	@Override
	public List<Usuario> getAllUsers() {
		List<Usuario> users = new ArrayList<>();
		String query = "SELECT * FROM usuario";
		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Usuario user = new Usuario(
						resultSet.getInt("id"),
						resultSet.getString("usuario"),
						resultSet.getString("email"),
						resultSet.getString("pass"),
						resultSet.getInt("rol")
						);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public Usuario getUserById(int id) {
		Usuario user = null;
		String query = "SELECT * FROM usuario WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					user = new Usuario(
							resultSet.getInt("id"),
							resultSet.getString("usuario"),
							resultSet.getString("email"),
							resultSet.getString("pass"),
							resultSet.getInt("rol")
							);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean addUser(Usuario usuario) {
		String query = "INSERT INTO usuario (usuario, email, pass, rol) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, usuario.getUsuario());
			statement.setString(2, usuario.getEmail());
			statement.setString(3, usuario.getPass());
			statement.setInt(4, usuario.getRol());

			int rowsInserted = statement.executeUpdate();
			return rowsInserted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean updateUser(Usuario usuario) {
		String query = "UPDATE usuario SET usuario = ?, email = ?, pass = ?, rol = ? WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, usuario.getUsuario());
			statement.setString(2, usuario.getEmail());
			statement.setString(3, usuario.getPass());
			statement.setInt(4, usuario.getRol());
			statement.setInt(5, usuario.getId());

			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Usuario actualizado exitosamente");
				return true;
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error al actualizar usuario");
			return false;
		}
	}

	@Override
	public boolean deleteUser(int id) {
		String query = "DELETE FROM usuario WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Usuario eliminado exitosamente");
				return true;
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addUserWithDetails(Usuario usuario, 
			String nombreProfesor, String apellidoProfesor, String dniProfesor, String especialidad,
			String nombreAlumno, String apellidoAlumno, String dniAlumno) {
		try {
			connection.setAutoCommit(false); // Iniciar transacción

			// Insertar en tabla usuario
			String sqlUser = "INSERT INTO usuario (usuario, email, pass, rol) VALUES (?, ?, ?, ?)";
			try (PreparedStatement psUser = connection.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {
				psUser.setString(1, usuario.getUsuario());
				psUser.setString(2, usuario.getEmail());
				psUser.setString(3, usuario.getPass());
				psUser.setInt(4, usuario.getRol());

				int filas = psUser.executeUpdate();
				if (filas == 0) {
					connection.rollback();
					return false;
				}

				// Obtener id generado del usuario
				ResultSet rs = psUser.getGeneratedKeys();
				int idUsuario = 0;
				if (rs.next()) {
					idUsuario = rs.getInt(1);
				} else {
					connection.rollback();
					return false;
				}

				// Insertar en tabla profesor si rol es 1
				if (usuario.getRol() == 1) {
					String sqlProfesor = "INSERT INTO profesor (id, nombre, apellido, dni, especialidad) VALUES (?, ?, ?, ?, ?)";
					try (PreparedStatement psProfesor = connection.prepareStatement(sqlProfesor)) {
						psProfesor.setInt(1, idUsuario);
						psProfesor.setString(2, nombreProfesor);
						psProfesor.setString(3, apellidoProfesor);
						psProfesor.setString(4, dniProfesor);
						psProfesor.setString(5, especialidad);
						psProfesor.executeUpdate();
					}
				}

				// Insertar en tabla alumno si rol es 2
				else if (usuario.getRol() == 2) {
					String sqlAlumno = "INSERT INTO alumno (id, nombre, apellido, dni) VALUES (?, ?, ?, ?)";
					try (PreparedStatement psAlumno = connection.prepareStatement(sqlAlumno)) {
						psAlumno.setInt(1, idUsuario);
						psAlumno.setString(2, nombreAlumno);
						psAlumno.setString(3, apellidoAlumno);
						psAlumno.setString(4, dniAlumno);
						psAlumno.executeUpdate();
					}
				}
			}

			connection.commit(); // Confirmar transacción
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback(); // Revertir en caso de error
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return false;
		} finally {
			try {
				connection.setAutoCommit(true); // Restaurar autocommit
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public Profesor getProfesorByUserId(int idUsuario) {
	    Profesor prof = null;
	    String query = "SELECT u.id, u.usuario, u.email, u.pass, u.rol, p.nombre, p.apellido, p.dni, p.especialidad " +
	                   "FROM usuario u JOIN profesor p ON u.id = p.id WHERE u.id = ?";
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, idUsuario);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                prof = new Profesor(
	                    rs.getInt("id"),
	                    rs.getString("usuario"),
	                    rs.getString("email"),
	                    rs.getString("pass"),
	                    rs.getInt("rol"),
	                    rs.getString("nombre"),
	                    rs.getString("apellido"),
	                    rs.getString("dni"),
	                    rs.getString("especialidad")
	                );
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return prof;
	}

	public Alumno getAlumnoByUserId(int idUsuario) {
	    Alumno alum = null;
	    String query = "SELECT u.id, u.usuario, u.email, u.pass, u.rol, a.nombre, a.apellido, a.dni " +
	                   "FROM usuario u JOIN alumno a ON u.id = a.id WHERE u.id = ?";
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, idUsuario);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                alum = new Alumno(
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
	    return alum;
	}


	public boolean updateUserWithDetails(Usuario user,
	        String profNombre, String profApellido, String profDni, String profEspecialidad,
	        String alumNombre, String alumApellido, String alumDni) {

	    try {
	        connection.setAutoCommit(false);

	        // Actualizar tabla usuario
	        String queryUser = "UPDATE usuario SET usuario = ?, email = ?, pass = ?, rol = ? WHERE id = ?";
	        try (PreparedStatement stmtUser = connection.prepareStatement(queryUser)) {
	            stmtUser.setString(1, user.getUsuario());
	            stmtUser.setString(2, user.getEmail());
	            stmtUser.setString(3, user.getPass());
	            stmtUser.setInt(4, user.getRol());
	            stmtUser.setInt(5, user.getId());

	            int rows = stmtUser.executeUpdate();
	            if (rows == 0) throw new SQLException("Error actualizando usuario");

	            // Actualizar profesor o alumno según rol
	            if (user.getRol() == 1) { // Profesor
	                String queryProf = "UPDATE profesor SET nombre = ?, apellido = ?, dni = ?, especialidad = ? WHERE id = ?";
	                try (PreparedStatement stmtProf = connection.prepareStatement(queryProf)) {
	                    stmtProf.setString(1, profNombre);
	                    stmtProf.setString(2, profApellido);
	                    stmtProf.setString(3, profDni);
	                    stmtProf.setString(4, profEspecialidad);
	                    stmtProf.setInt(5, user.getId());
	                    stmtProf.executeUpdate();
	                }
	            } else if (user.getRol() == 2) { // Alumno
	                String queryAlum = "UPDATE alumno SET nombre = ?, apellido = ?, dni = ? WHERE id = ?";
	                try (PreparedStatement stmtAlum = connection.prepareStatement(queryAlum)) {
	                    stmtAlum.setString(1, alumNombre);
	                    stmtAlum.setString(2, alumApellido);
	                    stmtAlum.setString(3, alumDni);
	                    stmtAlum.setInt(4, user.getId());
	                    stmtAlum.executeUpdate();
	                }
	            }
	        }

	        connection.commit();
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try { connection.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
	        return false;
	    } finally {
	        try { connection.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
	    }
	}

	public boolean usuarioOEmailExiste(String usuario, String email) {
		String query = "SELECT id FROM usuario WHERE usuario = ? OR email = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, usuario);
			stmt.setString(2, email);
			ResultSet rs = stmt.executeQuery();
			return rs.next(); 
		} catch (SQLException e) {
			e.printStackTrace();
			return true; 
		}
	}

	public int obtenerIdPorNombreUsuario(String nombreUsuario) {
	    String query = "SELECT id FROM usuario WHERE usuario = ?";
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setString(1, nombreUsuario);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; // No encontrado
	}
	public List<Alumno> obtenerAlumnosPorCurso(int idCurso) {
	    List<Alumno> alumnos = new ArrayList<>();
	    String query = """
	        SELECT a.id, a.nombre, a.apellido, a.dni
	        FROM alumno_curso ac
	        JOIN alumno a ON ac.id_alumno = a.id
	        WHERE ac.id_curso = ?
	    """;

	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setInt(1, idCurso);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Alumno a = new Alumno();
	                a.setId(rs.getInt("id"));
	                a.setNombre(rs.getString("nombre"));
	                a.setApellido(rs.getString("apellido"));
	                a.setDni(rs.getString("dni"));
	                alumnos.add(a);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return alumnos;
	}

}
