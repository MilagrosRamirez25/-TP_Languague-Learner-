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

        setTitle("Clases del Curso");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(-26, -8, 235, 98);
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image imgEscalada = icono.getImage().getScaledInstance(250, 140, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imgEscalada));
        contentPane.add(lblLogo);

        JLabel lblTitulo = new JLabel("Clases del Curso: " + curso.getNombreCurso());
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(300, 20, 700, 40);
        contentPane.add(lblTitulo);

        JLabel lblInstruccion = new JLabel("Hacé doble clic sobre una clase para ver o crear el examen");
        lblInstruccion.setFont(new Font("Ebrima", Font.ITALIC, 16));
        lblInstruccion.setForeground(Color.DARK_GRAY);
        lblInstruccion.setBounds(300, 60, 600, 30);
        contentPane.add(lblInstruccion);

        claseControlador = new ClaseControlador();
        usuarioControlador = new UsuarioControlador();

        modeloClases = new DefaultTableModel(new Object[]{"Título", "Tema", "Fecha Creación", "Contenido"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaClases = new JTable(modeloClases);
        tablaClases.setRowHeight(30);
        tablaClases.setFont(new Font("Ebrima", Font.PLAIN, 14));
        tablaClases.getTableHeader().setFont(new Font("Ebrima", Font.BOLD, 14));
        cargarClases();

        JScrollPane scrollPane = new JScrollPane(tablaClases);
        scrollPane.setBounds(50, 110, 890, 300);
        contentPane.add(scrollPane);

        Font fuenteBoton = new Font("Ebrima", Font.PLAIN, 16);

        JButton btnAgregar = new JButton("Agregar Clase");
        btnAgregar.setFont(fuenteBoton);
        btnAgregar.setBounds(100, 430, 170, 35);
        contentPane.add(btnAgregar);

        JButton btnEditar = new JButton("Editar Clase");
        btnEditar.setFont(fuenteBoton);
        btnEditar.setBounds(290, 430, 170, 35);
        contentPane.add(btnEditar);

        JButton btnEliminar = new JButton("Eliminar Clase");
        btnEliminar.setFont(fuenteBoton);
        btnEliminar.setBounds(480, 430, 170, 35);
        contentPane.add(btnEliminar);

        JButton btnVerAlumnos = new JButton("Ver Alumnos");
        btnVerAlumnos.setFont(fuenteBoton);
        btnVerAlumnos.setBounds(670, 430, 170, 35);
        contentPane.add(btnVerAlumnos);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(fuenteBoton);
        btnVolver.setBounds(400, 500, 200, 40);
        contentPane.add(btnVolver);

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
                                new CrearExamen(claseSeleccionada).setVisible(true);
                            }
                        }
                    }
                }
            }
        });

        btnAgregar.addActionListener(e -> {
            AgregarClase agregarClase = new AgregarClase(curso);
            agregarClase.setVisible(true);
            agregarClase.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosed(java.awt.event.WindowEvent e2) {
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
                        public void windowClosed(java.awt.event.WindowEvent e2) {
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
    }

    private void cargarClases() {
        modeloClases.setRowCount(0);
        List<Clase> clases = claseControlador.obtenerClasesPorCurso(curso.getId());
        for (Clase c : clases) {
            modeloClases.addRow(new Object[]{c.getTitulo(), c.getTema(), c.getFechaCreacion(), c.getContenido()});
        }
        tablaClases.getColumnModel().getColumn(3).setPreferredWidth(400);
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
