package Modelos;

public class Examen {

    private int id;
    private String titulo;
    private int idClase;

    public Examen() {}

    public Examen(int id, String titulo, int idClase) {
        this.id = id;
        this.titulo = titulo;
        this.idClase = idClase;
    }

    public Examen(String titulo, int idClase) {
        this.titulo = titulo;
        this.idClase = idClase;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    @Override
    public String toString() {
        return "Examen{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idClase=" + idClase +
                '}';
    }
}
