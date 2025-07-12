package Modelos;

public class AlumnoCurso {
    private int idAlumno;
    private int idCurso;
    private String fechaInscripcion;

    public AlumnoCurso(int idAlumno, int idCurso, String fechaInscripcion) {
        this.idAlumno = idAlumno;
        this.idCurso = idCurso;
        this.fechaInscripcion = fechaInscripcion;
    }

    public int getIdAlumno() { return idAlumno; }
    public void setIdAlumno(int idAlumno) { this.idAlumno = idAlumno; }

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public String getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(String fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
}
