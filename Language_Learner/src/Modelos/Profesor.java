package Modelos;

import java.util.LinkedList;

public class Profesor extends Usuario {

    private String nombre;
    private String apellido;
    private String dni;
    private String especialidad;
    private LinkedList<Curso> cursos = new LinkedList<>();

    public Profesor() {
        super();
    }

    public Profesor(int id, String usuario, String email, String pass, int rol,
                    String nombre, String apellido, String dni, String especialidad) {
        super(id, usuario, email, pass, rol);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.especialidad = especialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public LinkedList<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(LinkedList<Curso> cursos) {
        this.cursos = cursos;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", usuario='" + getUsuario() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
    
}
