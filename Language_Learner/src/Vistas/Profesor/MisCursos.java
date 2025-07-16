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
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

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

        JLabel lblTitulo = new JLabel("Mis Cursos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 30));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(300, 20, 400, 40);
        contentPane.add(lblTitulo);

        JLabel lblInstruccion = new JLabel("Hacé doble clic sobre un curso para ver sus clases", SwingConstants.CENTER);
        lblInstruccion.setFont(new Font("Ebrima", Font.ITALIC, 14));
        lblInstruccion.setForeground(Color.DARK_GRAY);
        lblInstruccion.setBounds(250, 60, 500, 20);
        contentPane.add(lblInstruccion);

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
        tabla.setFont(new Font("Ebrima", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Ebrima", Font.BOLD, 14));

        tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(500);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150);

        for (Curso c : cursos) {
            modelo.addRow(new Object[]{c.getNombreCurso(), c.getDescripcion(), c.getFechaInicio()});
        }

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(60, 100, 770, 280);
        contentPane.add(scrollPane);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Ebrima", Font.PLAIN, 15));
        btnVolver.setBounds(370, 400, 140, 35);
        contentPane.add(btnVolver);

        btnVolver.addActionListener(e -> {
            new Vistas.PantallaHome(nombreUsuario, rol).setVisible(true);
            dispose();
        });

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
