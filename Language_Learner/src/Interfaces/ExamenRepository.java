package Interfaces;

import java.util.List;
import Modelos.Examen;

public interface ExamenRepository {
 
    List<Examen> getExamenesPorCurso(int idCurso);
	boolean actualizarExamen(Examen examen);
	List<Examen> obtenerTodosLosExamenes();
	Examen obtenerExamenPorId(int id);
	boolean agregarExamen(Examen examen);
	boolean eliminarExamen(int id);
	List<Examen> obtenerExamenesPorCurso(int idCurso);
}
