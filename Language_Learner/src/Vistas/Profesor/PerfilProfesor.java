package Vistas.Profesor;

import javax.swing.*;
import java.awt.*;

import Controladores.UsuarioControlador;
import Modelos.Profesor;
import Vistas.PantallaHome;

public class PerfilProfesor extends JFrame {
    private final String nombreUsuario;
    private final int rol;

    public PerfilProfesor(String nombreUsuario, int rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;

        setTitle("Perfil del Profesor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Mi Perfil", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        UsuarioControlador usuarioControlador = new UsuarioControlador();
        int idUsuario = usuarioControlador.obtenerIdPorNombreUsuario(nombreUsuario);
        Profesor profesor = usuarioControlador.getProfesorByUserId(idUsuario);

        JPanel panelDatos = new JPanel(new GridLayout(6, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(new JLabel(profesor.getNombre()));

        panelDatos.add(new JLabel("Apellido:"));
        panelDatos.add(new JLabel(profesor.getApellido()));

        panelDatos.add(new JLabel("DNI:"));
        panelDatos.add(new JLabel(profesor.getDni()));

        panelDatos.add(new JLabel("Especialidad:"));
        panelDatos.add(new JLabel(profesor.getEspecialidad()));

        panelDatos.add(new JLabel("Usuario:"));
        panelDatos.add(new JLabel(profesor.getUsuario()));

        panelDatos.add(new JLabel("Contraseña:"));
        panelDatos.add(new JLabel("********")); 

        add(panelDatos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnEditar = new JButton("Editar perfil");
        JButton btnVolver = new JButton("Volver");

        btnEditar.addActionListener(e -> {
            new EditarPerfilProfesor(profesor).setVisible(true);
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
