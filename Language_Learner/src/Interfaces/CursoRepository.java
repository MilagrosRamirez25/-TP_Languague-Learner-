package Interfaces;

import Modelos.Curso;
import java.util.List;

public interface CursoRepository {
    List<Curso> obtenerCursos();
    Curso obtenerCursoPorId(int id);
    void agregarCurso(Curso curso);
    void actualizarCurso(Curso curso);
    void eliminarCurso(int id);
}
