package Interfaces;

import Modelos.Ejercicio;
import java.util.List;

public interface EjercicioRepository {
    List<Ejercicio> getAllEjercicios();
    Ejercicio getEjercicioById(int id);
    void addEjercicio(Ejercicio ejercicio);
    boolean updateEjercicio(Ejercicio ejercicio);
    boolean deleteEjercicio(int id);
    List<Ejercicio> getEjerciciosPorExamen(int idExamen);
	List<Ejercicio> obtenerEjerciciosPorExamen(int idExamen);
}
