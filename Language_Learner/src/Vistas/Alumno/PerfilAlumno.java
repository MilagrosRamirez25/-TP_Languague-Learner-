package Vistas.Alumno;

import javax.swing.*;
import java.awt.*;

import Controladores.UsuarioControlador;
import Modelos.Alumno;
import Vistas.PantallaHome;

public class PerfilAlumno extends JFrame {
    private final String nombreUsuario;
    private final int rol;

    public PerfilAlumno(String nombreUsuario, int rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;

        setTitle("Perfil del Alumno");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

                JLabel lblTitulo = new JLabel("Mi Perfil");
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 30));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(210, 20, 300, 50);
        contentPane.add(lblTitulo);

        UsuarioControlador usuarioControlador = new UsuarioControlador();
        int idUsuario = usuarioControlador.obtenerIdPorNombreUsuario(nombreUsuario);
        Alumno alumno = usuarioControlador.getAlumnoByUserId(idUsuario);

        JPanel panelDatos = new JPanel(new GridLayout(6, 2, 10, 10));
        panelDatos.setBounds(70, 100, 400, 160);
        panelDatos.setOpaque(false);
        Font fuente = new Font("Ebrima", Font.PLAIN, 16);

        JLabel[] etiquetas = {
            new JLabel("Nombre:"), new JLabel(alumno.getNombre()),
            new JLabel("Apellido:"), new JLabel(alumno.getApellido()),
            new JLabel("DNI:"), new JLabel(alumno.getDni()),
            new JLabel("Email:"), new JLabel(alumno.getEmail()),
            new JLabel("Usuario:"), new JLabel(alumno.getUsuario()),
            new JLabel("Contraseña:"), new JLabel("********")
        };

        for (JLabel label : etiquetas) {
            label.setFont(fuente);
            panelDatos.add(label);
        }

        contentPane.add(panelDatos);

        JButton btnEditar = new JButton("Editar perfil");
        JButton btnVolver = new JButton("Volver");

        btnEditar.setFont(new Font("Ebrima", Font.PLAIN, 15));
        btnVolver.setFont(new Font("Ebrima", Font.PLAIN, 15));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelBotones.setBounds(70, 280, 400, 50);
        panelBotones.setBackground(Color.decode("#F2EEAC"));
        panelBotones.add(btnEditar);
        panelBotones.add(btnVolver);

        contentPane.add(panelBotones);

        btnEditar.addActionListener(e -> {
            new EditarPerfilAlumno(alumno).setVisible(true);
            dispose();
        });

        btnVolver.addActionListener(e -> {
            new PantallaHome(nombreUsuario, rol).setVisible(true);
            dispose();
        });
    }
}
