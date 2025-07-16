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

        setTitle("Anotar Curso");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(-13, 10, 219, 78);
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image imgEscalada = icono.getImage().getScaledInstance(250, 140, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imgEscalada));
        contentPane.add(lblLogo);

        JLabel lblTitulo = new JLabel("Cursos Disponibles para Anotarse");
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 25));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(300, 30, 600, 50);
        contentPane.add(lblTitulo);

        modeloCursos = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Profesor"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCursos = new JTable(modeloCursos);
        tablaCursos.setRowHeight(28);
        tablaCursos.setFont(new Font("Ebrima", Font.PLAIN, 14));
        tablaCursos.getTableHeader().setFont(new Font("Ebrima", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(tablaCursos);
        scrollPane.setBounds(20, 120, 850, 250);
        contentPane.add(scrollPane);

        JButton btnAnotar = new JButton("Anotar Curso");
        JButton btnVolver = new JButton("Volver");

        btnAnotar.setFont(new Font("Ebrima", Font.PLAIN, 14));
        btnVolver.setFont(new Font("Ebrima", Font.PLAIN, 14));

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBounds(20, 390, 850, 50);
        panelBotones.setBackground(Color.decode("#F2EEAC"));

        panelBotones.add(btnAnotar);
        panelBotones.add(btnVolver);
        contentPane.add(panelBotones);

        btnAnotar.addActionListener(e -> anotarCursoSeleccionado());
        btnVolver.addActionListener(e -> {
            new PantallaHome(nombreUsuario, rol).setVisible(true);
            dispose();
        });

        cursoControlador = new CursoControlador();
        usuarioControlador = new UsuarioControlador();

        cargarCursosDisponibles();
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
            String profesor = cursoControlador.obtenerNombreCompletoProfesorPorId(c.getIdProfesor());
            modeloCursos.addRow(new Object[]{c.getNombreCurso(), c.getDescripcion(), profesor});
        }
    }

    private void anotarCursoSeleccionado() {
        int fila = tablaCursos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un curso primero.");
            return;
        }

        Curso curso = cursosDisponibles.get(fila);
        boolean exito = cursoControlador.inscribirAlumnoEnCurso(idAlumno, curso.getId());

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Te anotaste al curso correctamente!");
            cargarCursosDisponibles();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo realizar la inscripción.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
