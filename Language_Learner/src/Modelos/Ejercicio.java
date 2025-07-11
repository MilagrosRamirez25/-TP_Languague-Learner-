package Modelos;

import java.util.LinkedList;

public class Ejercicio {

    private int id;
    private int idExamen; 
    private String contenido;
    private LinkedList<Respuesta> respuestas;

    public Ejercicio() {
        this.respuestas = new LinkedList<>();
    }

    public Ejercicio(int id, int idExamen, String contenido) {
        this.id = id;
        this.idExamen = idExamen;
        this.contenido = contenido;
        this.respuestas = new LinkedList<>();
    }

    public Ejercicio(String contenido) {
        this.contenido = contenido;
        this.respuestas = new LinkedList<>();
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LinkedList<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(LinkedList<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public void agregarRespuesta(String contenido, boolean esCorrecta) {
        respuestas.add(new Respuesta(contenido, esCorrecta));
    }

    public LinkedList<Respuesta> obtenerRespuestasCorrectas() {
        LinkedList<Respuesta> correctas = new LinkedList<>();
        for (Respuesta r : respuestas) {
            if (r.isCorrecta()) {
                correctas.add(r);
            }
        }
        return correctas;
    }

    @Override
    public String toString() {
        return "Ejercicio{" +
                "id=" + id +
                ", idExamen=" + idExamen +
                ", contenido='" + contenido + '\'' +
                ", respuestas=" + respuestas +
                '}';
    }
}
