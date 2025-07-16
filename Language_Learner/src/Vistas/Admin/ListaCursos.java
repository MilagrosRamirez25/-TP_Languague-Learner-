package Vistas.Admin;

import Modelos.Curso;
import Controladores.CursoControlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import Vistas.PantallaHome;

public class ListaCursos extends JFrame {

    private CursoControlador controlador;
    private JTable tablaCursos;
    private DefaultTableModel modeloTabla;

    private String nombreUsuario;
    private int rolUsuario;

    public ListaCursos(String nombreUsuario, int rolUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.rolUsuario = rolUsuario;
        controlador = new CursoControlador();

        setTitle("Lista de Cursos");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(-17, 10, 219, 73);
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image imagenEscalada = icono.getImage().getScaledInstance(250, 140, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imagenEscalada));
        contentPane.add(lblLogo);


        JLabel lblTitulo = new JLabel("Lista de Cursos");
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 30));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(300, 30, 400, 50);
        contentPane.add(lblTitulo);

        // tabala
        String[] columnas = {"ID", "Nombre", "Profesor", "Descripción", "Fecha Inicio", "Capacidad Máxima", "Alumnos Inscritos"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCursos = new JTable(modeloTabla);
        tablaCursos.getColumnModel().getColumn(0).setPreferredWidth(30);   // ID
        tablaCursos.getColumnModel().getColumn(1).setPreferredWidth(120);  // Nombre
        tablaCursos.getColumnModel().getColumn(2).setPreferredWidth(140);  // Profesor
        tablaCursos.getColumnModel().getColumn(3).setPreferredWidth(250);  // Descripción
        tablaCursos.getColumnModel().getColumn(4).setPreferredWidth(100);  // Fecha
        tablaCursos.getColumnModel().getColumn(5).setPreferredWidth(110);  // Capacidad
        tablaCursos.getColumnModel().getColumn(6).setPreferredWidth(130);  // Inscritos

        JScrollPane scrollPane = new JScrollPane(tablaCursos);
        scrollPane.setBounds(20, 120, 850, 250);
        contentPane.add(scrollPane);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBounds(20, 390, 850, 50);
        panelBotones.setBackground(Color.decode("#F2EEAC"));

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnVolver = new JButton("Volver");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnVolver);

        contentPane.add(panelBotones);

        cargarCursos();

        btnAgregar.addActionListener(e -> {
            AgregarCurso agregar = new AgregarCurso();
            agregar.setVisible(true);
            agregar.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    cargarCursos();
                }
            });
        });

        btnEditar.addActionListener(e -> {
            int fila = tablaCursos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un curso para editar.");
                return;
            }
            int idCurso = (int) modeloTabla.getValueAt(fila, 0);
            EditarCurso editar = new EditarCurso(idCurso);
            editar.setVisible(true);
            editar.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    cargarCursos();
                }
            });
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaCursos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un curso para eliminar.");
                return;
            }
            int idCurso = (int) modeloTabla.getValueAt(fila, 0);
            int confirmar = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el curso seleccionado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                controlador.eliminarCurso(idCurso);
                cargarCursos();
            }
        });

        btnActualizar.addActionListener(e -> cargarCursos());

        btnVolver.addActionListener(e -> {
            new PantallaHome(nombreUsuario, rolUsuario).setVisible(true);
            dispose();
        });
    }

    private void cargarCursos() {
        List<Curso> cursos = controlador.obtenerCursos();
        modeloTabla.setRowCount(0);

        for (Curso c : cursos) {
            String nombreProfesor = controlador.obtenerNombreCompletoProfesorPorId(c.getIdProfesor());
            Object[] fila = {
                c.getId(),
                c.getNombreCurso(),
                nombreProfesor,
                c.getDescripcion(),
                c.getFechaInicio(),
                c.getCapacidadMaxima(),
                c.getCantidadAlumnosInscritos()
            };
            modeloTabla.addRow(fila);
        }
    }
}
