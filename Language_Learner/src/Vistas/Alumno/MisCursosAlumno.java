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
        setSize(950, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(-10, 5, 200, 80);
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image imgEscalada = icono.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imgEscalada));
        contentPane.add(lblLogo);

        JLabel lblTitulo = new JLabel("Mis Cursos");
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 30));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(360, 15, 300, 50);
        contentPane.add(lblTitulo);

        JLabel lblInstruccion = new JLabel("Hacé doble clic sobre un curso para ver sus clases");
        lblInstruccion.setFont(new Font("Ebrima", Font.PLAIN, 16));
        lblInstruccion.setForeground(Color.DARK_GRAY);
        lblInstruccion.setBounds(290, 55, 400, 30);
        contentPane.add(lblInstruccion);

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
        tabla.setFont(new Font("Ebrima", Font.PLAIN, 15));
        tabla.getTableHeader().setFont(new Font("Ebrima", Font.BOLD, 15));
        tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(500);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(30, 100, 880, 260);
        contentPane.add(scrollPane);

        JButton btnAnotarCurso = new JButton("Anotar Nuevo Curso");
        JButton btnEliminarCurso = new JButton("Eliminar Curso");
        JButton btnVolver = new JButton("Volver");

        btnAnotarCurso.setFont(new Font("Ebrima", Font.PLAIN, 15));
        btnEliminarCurso.setFont(new Font("Ebrima", Font.PLAIN, 15));
        btnVolver.setFont(new Font("Ebrima", Font.PLAIN, 15));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        panelBotones.setBackground(Color.decode("#F2EEAC"));
        panelBotones.setBounds(30, 380, 880, 50);

        panelBotones.add(btnAnotarCurso);
        panelBotones.add(btnEliminarCurso);
        panelBotones.add(btnVolver);

        contentPane.add(panelBotones);

        cargarCursosEnTabla();

        btnVolver.addActionListener(e -> {
            new PantallaHome(nombreUsuario, rol).setVisible(true);
            dispose();
        });

        btnAnotarCurso.addActionListener(e -> {
            new AnotarCurso(nombreUsuario, rol).setVisible(true);
            dispose();
        });

        btnEliminarCurso.addActionListener(e -> eliminarCursoSeleccionado());

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
