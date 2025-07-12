package Interfaces;

import Modelos.Clase;
import java.util.List;

public interface ClaseRepository {
    List<Clase> getAllClases();
    Clase getClaseById(int id);
    boolean deleteClase(int id);
    List<Clase> obtenerClasesPorCurso(int idCurso);
	boolean agregarClase(Clase clase);
	boolean actualizarClase(Clase clase);
}
