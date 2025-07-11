package Interfaces;

import Modelos.Clase;
import java.util.List;

public interface ClaseRepository {
    List<Clase> getAllClases();
    Clase getClaseById(int id);
    void addClase(Clase clase);
    boolean updateClase(Clase clase);
    boolean deleteClase(int id);
    List<Clase> getClasesPorCurso(int idCurso);
}
