package Vistas.Profesor;

import Controladores.ClaseControlador;
import Controladores.ExamenControlador;
import Controladores.UsuarioControlador;
import Modelos.Alumno;
import Modelos.Clase;
import Modelos.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ClasesCurso extends JFrame {
    private JTable tablaClases;
    private DefaultTableModel modeloClases;
    private ClaseControlador claseControlador;
    private UsuarioControlador usuarioControlador;
    private Curso curso;
    private String nombreUsuario;
    private int rol;

    public ClasesCurso(Curso curso, String nombreUsuario, int rol) {
        this.curso = curso;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;

        setTitle("Clases del Curso: " + curso.getNombreCurso());
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Títulos
        JLabel lblTitulo = new JLabel("Clases del Curso: " + curso.getNombreCurso(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel lblInstruccion = new JLabel("Hacé doble clic sobre una clase para ver el examen asociado", SwingConstants.CENTER);
        lblInstruccion.setFont(new Font("Arial", Font.ITALIC, 14));
        lblInstruccion.setForeground(Color.DARK_GRAY);

        JPanel panelTitulo = new JPanel(new GridLayout(2, 1));
        panelTitulo.add(lblTitulo);
        panelTitulo.add(lblInstruccion);
        add(panelTitulo, BorderLayout.NORTH);

        claseControlador = new ClaseControlador();
        usuarioControlador = new UsuarioControlador();

        modeloClases = new DefaultTableModel(new Object[]{"Título", "Tema", "Fecha Creación", "Contenido"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaClases = new JTable(modeloClases);
        tablaClases.setRowHeight(30);
        cargarClases();

        // Doble clic para ver examen o crear si no existe
        tablaClases.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tablaClases.getSelectedRow();
                    if (fila >= 0) {
                        String titulo = (String) modeloClases.getValueAt(fila, 0);
                        Clase claseSeleccionada = claseControlador.obtenerClasePorTituloYCursos(titulo, curso.getId());
                        if (claseSeleccionada != null) {
                            ExamenControlador examenControlador = new ExamenControlador();
                            if (examenControlador.obtenerExamenPorIdClase(claseSeleccionada.getId()) != null) {
                                new VerExamen(claseSeleccionada).setVisible(true);
                            } else {
                                CrearExamen crearExamen = new CrearExamen(claseSeleccionada);
                                crearExamen.setVisible(true);
                            }
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaClases);
        add(scrollPane, BorderLayout.CENTER);

        // Botones
        JButton btnAgregar = new JButton("Agregar Clase");
        JButton btnEditar = new JButton("Editar Clase");
        JButton btnEliminar = new JButton("Eliminar Clase");
        JButton btnVerAlumnos = new JButton("Ver Alumnos");
        JButton btnVolver = new JButton("Volver");

        Dimension btnSize = new Dimension(130, 30);
        btnAgregar.setPreferredSize(btnSize);
        btnEditar.setPreferredSize(btnSize);
        btnEliminar.setPreferredSize(btnSize);
        btnVerAlumnos.setPreferredSize(btnSize);
        btnVolver.setPreferredSize(btnSize);

        btnAgregar.addActionListener(e -> {
            AgregarClase agregarClase = new AgregarClase(curso);
            agregarClase.setVisible(true);
            agregarClase.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosed(java.awt.event.WindowEvent e) {
                    cargarClases();
                }
            });
        });

        btnEditar.addActionListener(e -> {
            int fila = tablaClases.getSelectedRow();
            if (fila >= 0) {
                String titulo = (String) modeloClases.getValueAt(fila, 0);
                Clase claseSeleccionada = claseControlador.obtenerClasePorTituloYCursos(titulo, curso.getId());
                if (claseSeleccionada != null) {
                    EditarClase editarClase = new EditarClase(claseSeleccionada);
                    editarClase.setVisible(true);
                    editarClase.addWindowListener(new java.awt.event.WindowAdapter() {
                        public void windowClosed(java.awt.event.WindowEvent e) {
                            cargarClases();
                        }
                    });
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaClases.getSelectedRow();
            if (fila >= 0) {
                String titulo = (String) modeloClases.getValueAt(fila, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar clase: " + titulo + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean exito = claseControlador.eliminarClasePorTituloYCursos(titulo, curso.getId());
                    if (exito) {
                        cargarClases();
                        JOptionPane.showMessageDialog(this, "Clase eliminada correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar la clase.");
                    }
                }
            }
        });

        btnVerAlumnos.addActionListener(e -> mostrarAlumnosInscriptos());
        btnVolver.addActionListener(e -> {
            new MisCursos(nombreUsuario, rol).setVisible(true);
            dispose();
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVerAlumnos);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarClases() {
        modeloClases.setRowCount(0);
        List<Clase> clases = claseControlador.obtenerClasesPorCurso(curso.getId());
        for (Clase c : clases) {
            modeloClases.addRow(new Object[]{c.getTitulo(), c.getTema(), c.getFechaCreacion(), c.getContenido()});
        }
        tablaClases.getColumnModel().getColumn(3).setPreferredWidth(400); // contenido
    }

    private void mostrarAlumnosInscriptos() {
        List<Alumno> alumnos = usuarioControlador.obtenerAlumnosPorCurso(curso.getId());
        if (alumnos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay alumnos inscriptos.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Alumno a : alumnos) {
            sb.append(a.getNombre()).append(" ").append(a.getApellido()).append(" - DNI: ").append(a.getDni()).append("\n");
        }

        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        JScrollPane scroll = new JScrollPane(ta);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scroll, "Alumnos Inscriptos", JOptionPane.INFORMATION_MESSAGE);
    }
}
