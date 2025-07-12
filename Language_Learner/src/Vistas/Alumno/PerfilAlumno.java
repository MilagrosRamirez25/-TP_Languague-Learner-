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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Mi Perfil", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        UsuarioControlador usuarioControlador = new UsuarioControlador();
        int idUsuario = usuarioControlador.obtenerIdPorNombreUsuario(nombreUsuario);
        Alumno alumno = usuarioControlador.getAlumnoByUserId(idUsuario);

        JPanel panelDatos = new JPanel(new GridLayout(6, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(new JLabel(alumno.getNombre()));

        panelDatos.add(new JLabel("Apellido:"));
        panelDatos.add(new JLabel(alumno.getApellido()));

        panelDatos.add(new JLabel("DNI:"));
        panelDatos.add(new JLabel(alumno.getDni()));

        panelDatos.add(new JLabel("Email:"));
        panelDatos.add(new JLabel(alumno.getEmail()));

        panelDatos.add(new JLabel("Usuario:"));
        panelDatos.add(new JLabel(alumno.getUsuario()));

        panelDatos.add(new JLabel("Contraseña:"));
        panelDatos.add(new JLabel("********"));  // No mostrar contraseña real

        add(panelDatos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnEditar = new JButton("Editar perfil");
        JButton btnVolver = new JButton("Volver");

        btnEditar.addActionListener(e -> {
            new EditarPerfilAlumno(alumno).setVisible(true);
            dispose();
        });

        btnVolver.addActionListener(e -> {
            new PantallaHome(nombreUsuario, rol).setVisible(true);
            dispose();
        });

        panelBotones.add(btnEditar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }
}
