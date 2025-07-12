package Vistas.Alumno;

import Controladores.CursoControlador;
import Controladores.UsuarioControlador;
import Modelos.Curso;
import Vistas.PantallaHome;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AnotarCurso extends JFrame {

    private String nombreUsuario;
    private int rol;
    private JTable tablaCursos;
    private DefaultTableModel modeloCursos;
    private CursoControlador cursoControlador;
    private UsuarioControlador usuarioControlador;
    private List<Curso> cursosDisponibles;
    private int idAlumno;

    public AnotarCurso(String nombreUsuario, int rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;

        setTitle("Cursos Disponibles para Anotar - Alumno: " + nombreUsuario);
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        cursoControlador = new CursoControlador();
        usuarioControlador = new UsuarioControlador();

        modeloCursos = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Profesor"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCursos = new JTable(modeloCursos);
        tablaCursos.setRowHeight(30);

        cargarCursosDisponibles();

        add(new JScrollPane(tablaCursos), BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            new MisCursosAlumno(nombreUsuario, rol).setVisible(true);
            dispose();
        });

        JButton btnAnotar = new JButton("Anotar Curso");
        btnAnotar.addActionListener(e -> anotarCursoSeleccionado());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAnotar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarCursosDisponibles() {
        modeloCursos.setRowCount(0);

        idAlumno = usuarioControlador.obtenerIdPorNombreUsuario(nombreUsuario);
        if (idAlumno == -1) {
            JOptionPane.showMessageDialog(this, "Error: No se encontró el alumno.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Curso> cursosAnotados = cursoControlador.obtenerCursosPorAlumno(idAlumno);
        List<Curso> todosLosCursos = cursoControlador.obtenerTodosLosCursos();

        cursosDisponibles = todosLosCursos.stream()
                .filter(c -> cursosAnotados.stream().noneMatch(ca -> ca.getId() == c.getId()))
                .toList();

        for (Curso c : cursosDisponibles) {
            String nombreProfesorCompleto = cursoControlador.obtenerNombreCompletoProfesorPorId(c.getIdProfesor());
            modeloCursos.addRow(new Object[]{c.getNombreCurso(), c.getDescripcion(), nombreProfesorCompleto});
        }
    }

    private void anotarCursoSeleccionado() {
        int filaSeleccionada = tablaCursos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un curso para anotarte.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Curso cursoSeleccionado = cursosDisponibles.get(filaSeleccionada);

        boolean exito = cursoControlador.inscribirAlumnoEnCurso(idAlumno, cursoSeleccionado.getId());

        if (exito) {
            JOptionPane.showMessageDialog(this, "Te anotaste correctamente en el curso.");
            cargarCursosDisponibles();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo anotar en el curso (quizás ya estás anotado).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
