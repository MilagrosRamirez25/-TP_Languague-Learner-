package Modelos;

public class Clase {

    private int id;
    private int idCurso;
    private String titulo;
    private String tema;
    private String contenido;
    private String autor;
    private String fechaCreacion;

    public Clase() {}

    public Clase(int idCurso, String titulo, String tema, String contenido, String autor, String fechaCreacion) {
        this.idCurso = idCurso;
        this.titulo = titulo;
        this.tema = tema;
        this.contenido = contenido;
        this.autor = autor;
        this.fechaCreacion = fechaCreacion;
    }

    public Clase(int id, int idCurso, String titulo, String tema, String contenido, String autor, String fechaCreacion) {
        this.id = id;
        this.idCurso = idCurso;
        this.titulo = titulo;
        this.tema = tema;
        this.contenido = contenido;
        this.autor = autor;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Clase: " + titulo + " (" + tema + "), creada por " + autor + " el " + fechaCreacion;
    }
}
