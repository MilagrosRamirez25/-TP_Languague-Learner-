package Modelos;

import java.util.LinkedList;

public class Alumno extends Usuario {

    private String nombre;
    private String apellido;
    private String dni;
    private LinkedList<Curso> cursosInscritos = new LinkedList<>();

    public Alumno() {
        super();
    }

    public Alumno(int id, String usuario, String email, String pass, int rol,
                  String nombre, String apellido, String dni) {
        super(id, usuario, email, pass, rol);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
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

    public LinkedList<Curso> getCursosInscritos() {
        return cursosInscritos;
    }

    public void setCursosInscritos(LinkedList<Curso> cursosInscritos) {
        this.cursosInscritos = cursosInscritos;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", usuario='" + getUsuario() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
