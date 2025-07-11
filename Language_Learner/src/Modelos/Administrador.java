package Modelos;

import javax.swing.JOptionPane;
import java.util.LinkedList;

public class Administrador extends Usuario {

    private int idCurso;

    public Administrador(int id, String nombre, String email, String contra, int rol, int idCurso) {
        super(id, nombre, email, contra, rol);
        this.idCurso = idCurso;
    }

    public Administrador() {}

    // CREAR PROFESOR
    public void crearProfesor(LinkedList<Profesor> profesores, String nombre, String apellido, String dni, String especialidad) {
        for (Profesor profesor : profesores) {
            if (profesor.getDni().equals(dni)) {
                JOptionPane.showMessageDialog(null, "Ya existe un profesor con el mismo DNI.");
                return;
            }
        }

        Profesor nuevoProfesor = new Profesor();
        nuevoProfesor.setNombre(nombre);
        nuevoProfesor.setApellido(apellido);
        nuevoProfesor.setDni(dni);
        nuevoProfesor.setEspecialidad(especialidad);

        profesores.add(nuevoProfesor);
        JOptionPane.showMessageDialog(null, "¡Profesor creado exitosamente!");
    }

    // CREAR CURSO
    public void crearCurso(LinkedList<Curso> cursos, String nombre, String descripcion, String fechaInicio, int capacidadMaxima) {
        Curso nuevoCurso = new Curso(nombre);
        nuevoCurso.setDescripcion(descripcion);
        nuevoCurso.setFechaInicio(fechaInicio);
        nuevoCurso.setCapacidadMaxima(capacidadMaxima);

        cursos.add(nuevoCurso);
        JOptionPane.showMessageDialog(null, "¡Curso creado exitosamente!");
    }

    // ELIMINAR PROFESOR
    public void eliminarProfesor(LinkedList<Profesor> profesores, String idProfesorString) {
        try {
            int id = Integer.parseInt(idProfesorString);
            Profesor profesorAEliminar = null;

            for (Profesor p : profesores) {
                if (p.getId() == id) {
                    if (!p.getCursos().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No se puede eliminar: el profesor tiene cursos asignados.");
                        return;
                    }
                    profesorAEliminar = p;
                    break;
                }
            }

            if (profesorAEliminar != null) {
                profesores.remove(profesorAEliminar);
                JOptionPane.showMessageDialog(null, "¡Profesor eliminado exitosamente!");
            } else {
                JOptionPane.showMessageDialog(null, "Profesor no encontrado.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    // ELIMINAR CURSO
    public void eliminarCurso(LinkedList<Curso> cursos, String idCursoString) {
        try {
            int id = Integer.parseInt(idCursoString);
            Curso cursoAEliminar = null;

            for (Curso c : cursos) {
                if (c.getId() == id) {
                    if (c.getCantidadAlumnosInscritos() > 0) {
                        JOptionPane.showMessageDialog(null, "No se puede eliminar: el curso tiene alumnos inscritos.");
                        return;
                    }
                    cursoAEliminar = c;
                    break;
                }
            }

            if (cursoAEliminar != null) {
                cursos.remove(cursoAEliminar);
                JOptionPane.showMessageDialog(null, "¡Curso eliminado exitosamente!");
            } else {
                JOptionPane.showMessageDialog(null, "Curso no encontrado.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    // MODIFICAR PROFESOR
    public void modificarProfesor(LinkedList<Profesor> profesores, String idProfesorString, String nuevoNombre,
                                   String nuevoApellido, String nuevaEspecialidad) {
        try {
            int id = Integer.parseInt(idProfesorString);
            for (Profesor p : profesores) {
                if (p.getId() == id) {
                    p.setNombre(nuevoNombre);
                    p.setApellido(nuevoApellido);
                    p.setEspecialidad(nuevaEspecialidad);
                    JOptionPane.showMessageDialog(null, "¡Profesor modificado exitosamente!");
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Profesor no encontrado.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    // MODIFICAR CURSO
    public void modificarCurso(LinkedList<Curso> cursos, String idCursoString, String nuevoNombre,
                                String nuevaDescripcion, String nuevaFechaInicio, int nuevaCapacidadMaxima) {
        try {
            int id = Integer.parseInt(idCursoString);
            for (Curso c : cursos) {
                if (c.getId() == id) {
                    c.setNombreCurso(nuevoNombre);
                    c.setDescripcion(nuevaDescripcion);
                    c.setFechaInicio(nuevaFechaInicio);
                    c.setCapacidadMaxima(nuevaCapacidadMaxima);
                    JOptionPane.showMessageDialog(null, "¡Curso modificado exitosamente!");
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Curso no encontrado.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }
}
