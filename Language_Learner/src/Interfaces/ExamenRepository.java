package Interfaces;

import java.util.List;
import Modelos.Examen;

public interface ExamenRepository {
    List<Examen> getAllExamenes();
    Examen getExamenById(int id);
    void addExamen(Examen examen);
    boolean updateExamen(Examen examen);
    boolean deleteExamen(int id);
    List<Examen> getExamenesPorCurso(int idCurso);
}
