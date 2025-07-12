package Interfaces;

import java.util.List;
import Modelos.Respuesta;

public interface RespuestaRepository {

    List<Respuesta> getAllRespuestas();

    Respuesta getRespuestaById(int id);

    boolean updateRespuesta(Respuesta respuesta);

    boolean deleteRespuesta(int id);

    List<Respuesta> getRespuestasPorEjercicio(int idEjercicio);

	List<Respuesta> obtenerRespuestasPorEjercicio(int idEjercicio);

	boolean agregarRespuesta(Respuesta respuesta);
}
