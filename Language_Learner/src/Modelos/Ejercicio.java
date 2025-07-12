package Modelos;

import java.util.LinkedList;

public class Ejercicio {

    private int id;
    private int idExamen;
    private String contenido;
    private String tipo; 
    private int puntos; // Máximo 10
    private LinkedList<Respuesta> respuestas;

    public Ejercicio() {
        this.respuestas = new LinkedList<>();
    }

    public Ejercicio(int id, int idExamen, String contenido, String tipo, int puntos) {
        this.id = id;
        this.idExamen = idExamen;
        this.contenido = contenido;
        this.tipo = tipo;
        this.puntos = puntos;
        this.respuestas = new LinkedList<>();
    }

    public Ejercicio(String contenido, String tipo, int puntos) {
        this.contenido = contenido;
        this.tipo = tipo;
        this.puntos = puntos;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public LinkedList<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(LinkedList<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    // Métodos auxiliares

    public void agregarRespuesta(String texto, boolean esCorrecta) {
        respuestas.add(new Respuesta(texto, esCorrecta));
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
                ", tipo='" + tipo + '\'' +
                ", puntos=" + puntos +
                ", respuestas=" + respuestas +
                '}';
    }
}
