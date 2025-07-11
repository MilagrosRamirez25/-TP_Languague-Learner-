package Modelos;

public class Curso {
    private int id;
    private String nombreCurso;
    private String descripcion;
    private String fechaInicio;
    private int idProfesor;           // ID del profesor (clave foránea)
    private String nombreProfesor;    // Nombre de usuario del profesor
    private int capacidadMaxima;
    private int cantidadAlumnosInscritos;

    // Constructor completo con ID profesor y nombre
    public Curso(int id, String nombreCurso, String descripcion, String fechaInicio,
                 int idProfesor, String nombreProfesor,
                 int capacidadMaxima, int cantidadAlumnosInscritos) {
        this.id = id;
        this.nombreCurso = nombreCurso;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.idProfesor = idProfesor;
        this.nombreProfesor = nombreProfesor;
        this.capacidadMaxima = capacidadMaxima;
        this.cantidadAlumnosInscritos = cantidadAlumnosInscritos;
    }

    // Constructor sin ID (para crear)
    public Curso(String nombreCurso, String descripcion, String fechaInicio,
                 int idProfesor, int capacidadMaxima, int cantidadAlumnosInscritos) {
        this.nombreCurso = nombreCurso;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.idProfesor = idProfesor;
        this.capacidadMaxima = capacidadMaxima;
        this.cantidadAlumnosInscritos = cantidadAlumnosInscritos;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public int getCantidadAlumnosInscritos() {
        return cantidadAlumnosInscritos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public void setCantidadAlumnosInscritos(int cantidadAlumnosInscritos) {
        this.cantidadAlumnosInscritos = cantidadAlumnosInscritos;
    }
}
