package Controladores;

import Modelos.Curso;
import Modelos.Profesor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Interfaces.CursoRepository;

public class CursoControlador implements CursoRepository {
    private final Connection connection;

    public CursoControlador() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Curso> obtenerCursos() {
        List<Curso> cursos = new ArrayList<>();
        String query = """
            SELECT c.*, u.usuario AS nombreProfesor
            FROM curso c
            JOIN usuario u ON c.idProfesor = u.id
        """;

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Curso curso = new Curso(
                    rs.getInt("id"),
                    rs.getString("nombreCurso"),
                    rs.getString("descripcion"),
                    rs.getString("fechaInicio"),
                    rs.getInt("idProfesor"),
                    rs.getString("nombreProfesor"), // este es el campo 'usuario'
                    rs.getInt("capacidadMaxima"),
                    rs.getInt("cantidadAlumnosInscritos")
                );
                cursos.add(curso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursos;
    }

    @Override
    public Curso obtenerCursoPorId(int id) {
        Curso curso = null;
        String query = """
            SELECT c.*, u.usuario AS nombreProfesor
            FROM curso c
            JOIN usuario u ON c.idProfesor = u.id
            WHERE c.id = ?
        """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // Aquí no declaramos 'Curso curso' de nuevo, sino que asignamos la variable ya declarada
                    curso = new Curso(
                        rs.getInt("id"),
                        rs.getString("nombreCurso"),
                        rs.getString("descripcion"),
                        rs.getString("fechaInicio"),
                        rs.getInt("idProfesor"),
                        rs.getString("nombreProfesor"),
                        rs.getInt("capacidadMaxima"),
                        rs.getInt("cantidadAlumnosInscritos")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return curso;
    }


    @Override
    public void agregarCurso(Curso curso) {
        String query = """
            INSERT INTO curso (nombreCurso, descripcion, fechaInicio, idProfesor, capacidadMaxima, cantidadAlumnosInscritos)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, curso.getNombreCurso());
            statement.setString(2, curso.getDescripcion());
            statement.setString(3, curso.getFechaInicio());
            statement.setInt(4, curso.getIdProfesor()); // asegurate de tener este campo en el modelo
            statement.setInt(5, curso.getCapacidadMaxima());
            statement.setInt(6, curso.getCantidadAlumnosInscritos());

            statement.executeUpdate();
            System.out.println("Curso agregado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarCurso(Curso curso) {
        String query = """
            UPDATE curso
            SET nombreCurso = ?, descripcion = ?, fechaInicio = ?, idProfesor = ?, capacidadMaxima = ?, cantidadAlumnosInscritos = ?
            WHERE id = ?
        """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, curso.getNombreCurso());
            statement.setString(2, curso.getDescripcion());
            statement.setString(3, curso.getFechaInicio());
            statement.setInt(4, curso.getIdProfesor());
            statement.setInt(5, curso.getCapacidadMaxima());
            statement.setInt(6, curso.getCantidadAlumnosInscritos());
            statement.setInt(7, curso.getId());

            statement.executeUpdate();
            System.out.println("Curso actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarCurso(int id) {
        String query = "DELETE FROM curso WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Curso eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
    public String obtenerNombreProfesorPorId(int idProfesor) {
        String nombreProfesor = "Desconocido";
        String query = "SELECT usuario FROM usuario WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idProfesor);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    nombreProfesor = rs.getString("usuario");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombreProfesor;
    }

    public List<Profesor> obtenerProfesores() {
        List<Profesor> profesores = new ArrayList<>();
        String query = "SELECT id, nombre, apellido, dni, especialidad FROM profesor";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Profesor profesor = new Profesor(
                    rs.getInt("id"),
                    null, // usuario (si no está en esta tabla, o adaptalo si está)
                    null, // email, si no existe acá
                    null, // pass
                    1,    // rol fijo para profesor
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getString("especialidad")
                );
                profesores.add(profesor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profesores;
    }
    public List<Curso> obtenerCursosPorProfesor(int idProfesor) {
        List<Curso> cursos = new ArrayList<>();
        String query = """
            SELECT c.*, u.usuario AS nombreProfesor
            FROM curso c
            JOIN usuario u ON c.idProfesor = u.id
            WHERE c.idProfesor = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idProfesor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Curso curso = new Curso(
                        rs.getInt("id"),
                        rs.getString("nombreCurso"),
                        rs.getString("descripcion"),
                        rs.getString("fechaInicio"),
                        rs.getInt("idProfesor"),
                        rs.getString("nombreProfesor"),
                        rs.getInt("capacidadMaxima"),
                        rs.getInt("cantidadAlumnosInscritos")
                    );
                    cursos.add(curso);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursos;
    }


   
    public String obtenerNombreCompletoProfesorPorId(int idProfesor) {
        String nombreCompleto = "Desconocido";
        String query = "SELECT nombre, apellido FROM profesor WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idProfesor);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombreCompleto;
    }
    public String obtenerEspecialidadProfesorPorId(int idProfesor) {
        String especialidad = "";
        String query = "SELECT especialidad FROM profesor WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idProfesor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    especialidad = rs.getString("especialidad");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidad;
    }

    public List<Curso> obtenerCursosPorAlumno(int idAlumno) {
        List<Curso> cursos = new ArrayList<>();
        String query = """
            SELECT c.*, u.usuario AS nombreProfesor
            FROM curso c
            JOIN usuario u ON c.idProfesor = u.id
            JOIN alumno_curso ac ON c.id = ac.id_curso
            WHERE ac.id_alumno = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idAlumno);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Curso curso = new Curso(
                        rs.getInt("id"),
                        rs.getString("nombreCurso"),
                        rs.getString("descripcion"),
                        rs.getString("fechaInicio"),
                        rs.getInt("idProfesor"),
                        rs.getString("nombreProfesor"),
                        rs.getInt("capacidadMaxima"),
                        rs.getInt("cantidadAlumnosInscritos")
                    );
                    cursos.add(curso);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }
 // Obtener el id del curso a partir del nombre
    public int obtenerIdCursoPorNombre(String nombreCurso) {
        int idCurso = -1;
        String query = "SELECT id FROM curso WHERE nombreCurso = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nombreCurso);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idCurso = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idCurso;
    }

    // Inscribir alumno en curso
    public boolean inscribirAlumnoEnCurso(int idAlumno, int idCurso) {
        String queryCheck = "SELECT * FROM alumno_curso WHERE id_alumno = ? AND id_curso = ?";
        String queryInsert = "INSERT INTO alumno_curso (id_alumno, id_curso, fecha_inscripcion) VALUES (?, ?, CURDATE())";

        try (PreparedStatement stmtCheck = connection.prepareStatement(queryCheck)) {
            stmtCheck.setInt(1, idAlumno);
            stmtCheck.setInt(2, idCurso);
            try (ResultSet rs = stmtCheck.executeQuery()) {
                if (rs.next()) {
                    // Ya está inscripto
                    return false;
                }
            }

            try (PreparedStatement stmtInsert = connection.prepareStatement(queryInsert)) {
                stmtInsert.setInt(1, idAlumno);
                stmtInsert.setInt(2, idCurso);
                int filas = stmtInsert.executeUpdate();
                return filas > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
 // Método para traer todos los cursos con datos del profesor
    public List<Curso> obtenerTodosLosCursos() {
        List<Curso> cursos = new ArrayList<>();
        String query = """
            SELECT c.*, u.usuario AS nombreProfesor
            FROM curso c
            JOIN usuario u ON c.idProfesor = u.id
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Curso curso = new Curso(
                    rs.getInt("id"),
                    rs.getString("nombreCurso"),
                    rs.getString("descripcion"),
                    rs.getString("fechaInicio"),
                    rs.getInt("idProfesor"),
                    rs.getString("nombreProfesor"),
                    rs.getInt("capacidadMaxima"),
                    rs.getInt("cantidadAlumnosInscritos")
                );
                cursos.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }
    public boolean eliminarInscripcionCurso(int idAlumno, int idCurso) {
        String query = "DELETE FROM alumno_curso WHERE id_alumno = ? AND id_curso = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idAlumno);
            stmt.setInt(2, idCurso);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


 

}
