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
        Image imagenEscalada = icono.getImage().getScaledInstance(250, 140, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imagenEscalada));
        contentPane.add(lblLogo);

        JLabel lblTitulo = new JLabel("Lista de Usuarios");
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 30));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(300, 30, 400, 50);
        contentPane.add(lblTitulo);

        // Ttabla
        String[] columnas = {"ID", "Usuario", "Email", "Rol"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(250);
        tablaUsuarios.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
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
                dispose();
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
        return switch (rol) {
            case 0 -> "Admin";
            case 1 -> "Profesor";
            case 2 -> "Alumno";
            default -> "Desconocido";
        };
    }
}
