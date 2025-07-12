package Controladores;

import java.util.HashMap;
import java.util.Map;

public class ResultadoExamenControlador {
    private static Map<String, Integer> resultados = new HashMap<>();

    public boolean guardarResultado(String nombreUsuario, int idExamen, int puntos) {
        String key = nombreUsuario + "-" + idExamen;
        resultados.put(key, puntos);
        return true;
    }

    public Integer obtenerResultado(String nombreUsuario, int idExamen) {
        String key = nombreUsuario + "-" + idExamen;
        return resultados.get(key);
    }

    public boolean yaRealizoExamen(String nombreUsuario, int idExamen) {
        return resultados.containsKey(nombreUsuario + "-" + idExamen);
    }
}
