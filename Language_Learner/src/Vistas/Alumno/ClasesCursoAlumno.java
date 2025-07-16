package Vistas.Alumno;

import Controladores.ClaseControlador;
import Controladores.ExamenControlador;
import Controladores.ResultadoExamenControlador;
import Modelos.Clase;
import Modelos.Curso;
import Modelos.Examen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClasesCursoAlumno extends JFrame {

    private Curso curso;
    private String nombreUsuario;
    private int rol;

    private JTable tabla;
    private DefaultTableModel modelo;
    private ClaseControlador claseControlador;
    private List<Clase> clases;

    public ClasesCursoAlumno(Curso curso, String nombreUsuario, int rol) {
        this.curso = curso;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;

        setTitle("Clases del Curso");
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

        JLabel lblTitulo = new JLabel("Clases del Curso: " + curso.getNombreCurso());
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(250, 20, 600, 40);
        contentPane.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Hacé doble clic sobre una clase para ver su examen");
        lblSubtitulo.setFont(new Font("Ebrima", Font.ITALIC, 16));
        lblSubtitulo.setForeground(Color.DARK_GRAY);
        lblSubtitulo.setBounds(250, 60, 600, 20);
        contentPane.add(lblSubtitulo);

        modelo = new DefaultTableModel(new Object[]{"Título", "Descripción", "Fecha", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Ebrima", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Ebrima", Font.BOLD, 14));

        tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(400);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(20, 100, 850, 280);
        contentPane.add(scrollPane);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Ebrima", Font.PLAIN, 14));
        btnVolver.setBounds(390, 400, 100, 30);
        contentPane.add(btnVolver);

        btnVolver.addActionListener(e -> {
            new MisCursosAlumno(nombreUsuario, rol).setVisible(true);
            dispose();
        });

        claseControlador = new ClaseControlador();
        cargarClases();

        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int fila = tabla.getSelectedRow();
                    if (fila >= 0 && fila < clases.size()) {
                        Clase claseSeleccionada = clases.get(fila);

                        ExamenControlador examenControlador = new ExamenControlador();
                        ResultadoExamenControlador resultadoControlador = new ResultadoExamenControlador();

                        Examen examen = examenControlador.obtenerExamenPorIdClase(claseSeleccionada.getId());
                        if (examen == null) {
                            JOptionPane.showMessageDialog(null, "Esta clase no tiene examen disponible.");
                            return;
                        }

                        int idExamen = examen.getId();

                        if (resultadoControlador.yaRealizoExamen(nombreUsuario, idExamen)) {
                            int puntos = resultadoControlador.obtenerResultado(nombreUsuario, idExamen);
                            JOptionPane.showMessageDialog(null,
                                    "Ya realizaste este examen.\nTu puntaje fue: " + puntos + " puntos.",
                                    "Examen ya realizado",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            new VerExamenAlumno(claseSeleccionada, curso, nombreUsuario, rol).setVisible(true);
                            dispose();
                        }
                    }
                }
            }
        });
    }

    private void cargarClases() {
        modelo.setRowCount(0);
        clases = claseControlador.obtenerClasesPorCurso(curso.getId());

        ResultadoExamenControlador resultadoControlador = new ResultadoExamenControlador();
        ExamenControlador examenControlador = new ExamenControlador();

        for (Clase clase : clases) {
            String estado = "Pendiente";
            Examen examen = examenControlador.obtenerExamenPorIdClase(clase.getId());
            if (examen != null) {
                int idExamen = examen.getId();
                if (resultadoControlador.yaRealizoExamen(nombreUsuario, idExamen)) {
                    estado = "Realizado";
                }
            }

            modelo.addRow(new Object[]{
                    clase.getTitulo(),
                    clase.getTema(),
                    clase.getFechaCreacion(),
                    estado
            });
        }
    }
}
