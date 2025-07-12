package Vistas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

import Vistas.Admin.ListaCursos;
import Vistas.Admin.ListaUsuarios;
import Vistas.Alumno.MisCursosAlumno;
import Vistas.Alumno.PerfilAlumno;
import Vistas.Profesor.MisCursos;
import Vistas.Profesor.PerfilProfesor;

public class PantallaHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private String nombre;
    private int rol;

    /**
     * Roles:
     * 0 - Admin
     * 1 - Profesor
     * 2 - Alumno
     */
    public PantallaHome(String nombre, int rol) {
        this.nombre = nombre;
        this.rol = rol;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 450);
        setLocationRelativeTo(null); 

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        setContentPane(contentPane);
       
        JLabel lblImagen = new JLabel();
        lblImagen.setBounds(10, 20, 250, 140);  
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/bienvenida.jpg"));
        java.awt.Image scaledImage = originalIcon.getImage().getScaledInstance(250, 140, java.awt.Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        lblImagen.setIcon(icon);
        contentPane.add(lblImagen);

        JLabel lblBienvenida = new JLabel("Bienvenido " + nombre);
        lblBienvenida.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblBienvenida.setBounds(270, 50, 350, 29);
        contentPane.add(lblBienvenida);

        int altoBoton = 50;
        int anchoBoton = 180;
        int espacioEntreBotones = 30;
        int totalBotones = 2;
        int totalAncho = totalBotones * anchoBoton + espacioEntreBotones;
        int xInicio = (getWidth() - totalAncho) / 2;
        int yBotones = 330;

        if (rol == 0) { // Admin
            JButton btnVerUsuarios = new JButton("Ver Usuarios");
            btnVerUsuarios.setBounds(xInicio, yBotones, anchoBoton, altoBoton);
            btnVerUsuarios.addActionListener(e -> {
                ListaUsuarios lista = new ListaUsuarios(nombre, rol);
                lista.setVisible(true);
                dispose(); 
            });

            contentPane.add(btnVerUsuarios); 

            JButton btnVerCursos = new JButton("Ver Cursos");
            btnVerCursos.setBounds(xInicio + anchoBoton + espacioEntreBotones, yBotones, anchoBoton, altoBoton);
            btnVerCursos.addActionListener(e -> {
                ListaCursos lista = new ListaCursos(nombre, rol);
                lista.setVisible(true);
                dispose(); 
            });
            contentPane.add(btnVerCursos);

        } else if (rol == 1) { // Profesor
            JButton btnMisCursos = new JButton("Mis Cursos");
            btnMisCursos.setBounds(xInicio, yBotones, anchoBoton, altoBoton);
            btnMisCursos.addActionListener(e -> {
                MisCursos vista = new MisCursos(nombre, rol);
                vista.setVisible(true);
                dispose();
            });
            contentPane.add(btnMisCursos);

            JButton btnMiPerfil = new JButton("Mi Perfil");
            btnMiPerfil.setBounds(xInicio + anchoBoton + espacioEntreBotones, yBotones, anchoBoton, altoBoton);
            btnMiPerfil.addActionListener(e -> {
            	PerfilProfesor vista = new PerfilProfesor(nombre, rol); // Asegurate de tener esta clase creada
                vista.setVisible(true);
                dispose();
            });
            contentPane.add(btnMiPerfil);
        
        } else if (rol == 2) { // Alumno
            JButton btnMisCursos = new JButton("Mis Cursos");
            btnMisCursos.setBounds(xInicio, yBotones, anchoBoton, altoBoton);
            btnMisCursos.addActionListener(e -> {
                MisCursosAlumno vista = new MisCursosAlumno(nombre, rol);
                vista.setVisible(true);
                dispose();
            });
            contentPane.add(btnMisCursos);

            JButton btnMiPerfil = new JButton("Mi Perfil");
            btnMiPerfil.setBounds(xInicio + anchoBoton + espacioEntreBotones, yBotones, anchoBoton, altoBoton);
            btnMiPerfil.addActionListener(e -> {
                PerfilAlumno vista = new PerfilAlumno(nombre, rol);
                vista.setVisible(true);
                dispose();
            });
            contentPane.add(btnMiPerfil);
        }


        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBounds(284, 125, 132, 35);
        btnCerrarSesion.setBackground(new Color(220, 50, 50));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new PantallaIniciarSesion().setVisible(true);
        });
        contentPane.add(btnCerrarSesion);
    }
}
