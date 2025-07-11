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
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Nombre", "Profesor", "Descripción", "Fecha Inicio", "Capacidad Máxima", "Alumnos Inscritos"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCursos = new JTable(modeloTabla);
        // Ajuste de anchos:
        tablaCursos.getColumnModel().getColumn(0).setPreferredWidth(30);  // ID
        tablaCursos.getColumnModel().getColumn(1).setPreferredWidth(140); // Nombre
        tablaCursos.getColumnModel().getColumn(2).setPreferredWidth(140); // Profesor (más ancho para nombre + apellido)
        tablaCursos.getColumnModel().getColumn(3).setPreferredWidth(320); // Descripción
        tablaCursos.getColumnModel().getColumn(4).setPreferredWidth(90);  // Fecha Inicio
        tablaCursos.getColumnModel().getColumn(5).setPreferredWidth(140); // Capacidad Máxima
        tablaCursos.getColumnModel().getColumn(6).setPreferredWidth(130); // Alumnos Inscritos
        JScrollPane scrollPane = new JScrollPane(tablaCursos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
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

        add(panelBotones, BorderLayout.SOUTH);

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
            // Aquí llamamos al método que concatena nombre + apellido del profesor
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
