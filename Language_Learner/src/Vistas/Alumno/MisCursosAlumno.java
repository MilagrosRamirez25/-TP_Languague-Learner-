package Vistas.Alumno;

import Controladores.CursoControlador;
import Controladores.UsuarioControlador;
import Modelos.Curso;
import Vistas.PantallaHome;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MisCursosAlumno extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private CursoControlador cursoControlador;
    private List<Curso> cursos;
    private String nombreUsuario;
    private int rol;
    private int idAlumno;

    public MisCursosAlumno(String nombreUsuario, int rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;

        setTitle("Mis Cursos - Alumno: " + nombreUsuario);
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Mis Cursos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel lblInstruccion = new JLabel("Hacé doble clic sobre un curso para ver sus clases", SwingConstants.CENTER);
        lblInstruccion.setFont(new Font("Arial", Font.ITALIC, 14));
        lblInstruccion.setForeground(Color.DARK_GRAY);

        JPanel panelTitulo = new JPanel(new GridLayout(2, 1));
        panelTitulo.add(lblTitulo);
        panelTitulo.add(lblInstruccion);
        add(panelTitulo, BorderLayout.NORTH);

        cursoControlador = new CursoControlador();
        UsuarioControlador usuarioControlador = new UsuarioControlador();

        idAlumno = usuarioControlador.obtenerIdPorNombreUsuario(nombreUsuario);
        cursos = cursoControlador.obtenerCursosPorAlumno(idAlumno);

        modelo = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Profesor"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(500);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200);

        cargarCursosEnTabla();

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botón volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            new PantallaHome(nombreUsuario, rol).setVisible(true);
            dispose();
        });

        // Botón anotar nuevo curso
        JButton btnAnotarCurso = new JButton("Anotar Nuevo Curso");
        btnAnotarCurso.addActionListener(e -> {
            new AnotarCurso(nombreUsuario, rol).setVisible(true);
            dispose();
        });

        // Botón eliminar curso
        JButton btnEliminarCurso = new JButton("Eliminar Curso");
        btnEliminarCurso.addActionListener(e -> eliminarCursoSeleccionado());

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnAnotarCurso);
        panelInferior.add(btnEliminarCurso);
        panelInferior.add(btnVolver);
        add(panelInferior, BorderLayout.SOUTH);

        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int fila = tabla.getSelectedRow();
                    Curso cursoSeleccionado = cursos.get(fila);
                    new ClasesCursoAlumno(cursoSeleccionado, nombreUsuario, rol).setVisible(true);
                    dispose();
                }
            }
        });
    }

    private void cargarCursosEnTabla() {
        modelo.setRowCount(0);
        for (Curso c : cursos) {
            String nombreCompletoProfesor = cursoControlador.obtenerNombreCompletoProfesorPorId(c.getIdProfesor());
            modelo.addRow(new Object[]{
                    c.getNombreCurso(),
                    c.getDescripcion(),
                    nombreCompletoProfesor
            });
        }
    }

    private void eliminarCursoSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un curso para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Curso cursoSeleccionado = cursos.get(filaSeleccionada);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro que querés eliminar tu inscripción en el curso \"" + cursoSeleccionado.getNombreCurso() + "\"?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean exito = cursoControlador.eliminarInscripcionCurso(idAlumno, cursoSeleccionado.getId());
            if (exito) {
                JOptionPane.showMessageDialog(this, "Inscripción eliminada correctamente.");
                cursos.remove(filaSeleccionada);
                cargarCursosEnTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar inscripción.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
