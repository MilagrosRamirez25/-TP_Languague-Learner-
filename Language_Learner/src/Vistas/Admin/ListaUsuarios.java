package Vistas.Admin;

import Modelos.Usuario;
import Controladores.UsuarioControlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import Vistas.PantallaHome;

public class ListaUsuarios extends JFrame {

    private UsuarioControlador controlador;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    private String nombreUsuario;
    private int rolUsuario;

    public ListaUsuarios(String nombreUsuario, int rolUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.rolUsuario = rolUsuario;

        controlador = new UsuarioControlador();

        setTitle("Lista de Usuarios");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Usuario", "Email", "Rol"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
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

        cargarUsuarios();

        btnAgregar.addActionListener(e -> {
            AgregarUsuario agregar = new AgregarUsuario(nombreUsuario, rolUsuario);
            agregar.setVisible(true);
            agregar.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    cargarUsuarios();
                }
            });
        });

        btnEditar.addActionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un usuario para editar.");
                return;
            }
            int idUsuario = (int) modeloTabla.getValueAt(fila, 0);
            Usuario usuarioEditar = controlador.getUserById(idUsuario);
            if (usuarioEditar != null) {
                EditarUsuario editar = new EditarUsuario(usuarioEditar, nombreUsuario, rolUsuario);
                editar.setVisible(true);
                editar.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        cargarUsuarios();
                    }
                });
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al cargar usuario.");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar.");
                return;
            }
            int idUsuario = (int) modeloTabla.getValueAt(fila, 0);
            int confirmar = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el usuario seleccionado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                boolean eliminado = controlador.deleteUser(idUsuario);
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar usuario.");
                }
                cargarUsuarios();
            }
        });

        btnActualizar.addActionListener(e -> cargarUsuarios());

        btnVolver.addActionListener(e -> {
            new PantallaHome(nombreUsuario, rolUsuario).setVisible(true);
            dispose();
        });
    }

    private void cargarUsuarios() {
        List<Usuario> usuarios = controlador.getAllUsers();
        modeloTabla.setRowCount(0);

        for (Usuario u : usuarios) {
            Object[] fila = {
                    u.getId(),
                    u.getUsuario(),
                    u.getEmail(),
                    rolToString(u.getRol())
            };
            modeloTabla.addRow(fila);
        }
    }

    private String rolToString(int rol) {
        switch (rol) {
            case 0: return "Admin";
            case 1: return "Profesor";
            case 2: return "Alumno";
            default: return "Desconocido";
        }
    }

}
