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
        setSize(1000, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título
        JLabel lblTitulo = new JLabel("Clases del Curso: " + curso.getNombreCurso(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel lblSubtitulo = new JLabel("Hacé doble clic para ver el examen", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        lblSubtitulo.setForeground(Color.DARK_GRAY);

        JPanel panelTitulo = new JPanel(new GridLayout(2, 1));
        panelTitulo.add(lblTitulo);
        panelTitulo.add(lblSubtitulo);
        add(panelTitulo, BorderLayout.NORTH);

        claseControlador = new ClaseControlador();

        // Agregamos columna Estado
        modelo = new DefaultTableModel(new Object[]{"Título", "Descripción", "Fecha", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(450);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120);

        cargarClases();

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            new MisCursosAlumno(nombreUsuario, rol).setVisible(true);
            dispose();
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnVolver);
        add(panelInferior, BorderLayout.SOUTH);

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
