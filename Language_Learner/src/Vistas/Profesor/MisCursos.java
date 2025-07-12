package Vistas.Profesor;

import Controladores.CursoControlador;
import Modelos.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MisCursos extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private CursoControlador cursoControlador;

    public MisCursos(String nombreUsuario, int rol) {
        setTitle("Mis Cursos");
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
        int idProfesor = new Controladores.UsuarioControlador().obtenerIdPorNombreUsuario(nombreUsuario);
        List<Curso> cursos = cursoControlador.obtenerCursosPorProfesor(idProfesor);

        modelo = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(30);

        tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(500);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150);

        for (Curso c : cursos) {
            modelo.addRow(new Object[]{c.getNombreCurso(), c.getDescripcion(), c.getFechaInicio()});
        }

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            new Vistas.PantallaHome(nombreUsuario, rol).setVisible(true);
            dispose();
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnVolver);
        add(panelInferior, BorderLayout.SOUTH);

        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int fila = tabla.getSelectedRow();
                    Curso cursoSeleccionado = cursos.get(fila);
                    new ClasesCurso(cursoSeleccionado, nombreUsuario, rol).setVisible(true);
                    dispose();
                }
            }
        });
    }
}
