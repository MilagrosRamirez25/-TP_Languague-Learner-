package Vistas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

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
        contentPane.setBackground(Color.decode("#F2EEAC")); 
        setContentPane(contentPane);

        JLabel lblImagen = new JLabel();
        lblImagen.setBounds(0, 0, 233, 84);
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/logo.png"));
        java.awt.Image scaledImage = originalIcon.getImage().getScaledInstance(250, 140, java.awt.Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        lblImagen.setIcon(icon);
        contentPane.add(lblImagen);

        JLabel lblBienvenida = new JLabel("Bienvenido " + nombre);
        lblBienvenida.setForeground(new Color(37, 68, 175));
        lblBienvenida.setFont(new Font("Eras Bold ITC", Font.BOLD, 22));
        lblBienvenida.setBounds(204, 77, 286, 50);
        contentPane.add(lblBienvenida);

        int anchoBoton = 180;
        int altoBoton = 50;

        // Coordenadas base para alinear botones en dos columnas
        int xIzquierda = 100;
        int xDerecha = 370;
        int yArriba = 160;
        int yAbajo = 230;

        // Botón "Mi Perfil"
        JButton btnMiPerfil = new JButton("Mi Perfil");
        btnMiPerfil.setBounds(xIzquierda, yArriba, anchoBoton, altoBoton);
        btnMiPerfil.setBackground(new Color(255, 255, 0));
        btnMiPerfil.setFont(new Font("Ebrima", Font.PLAIN, 12));
        contentPane.add(btnMiPerfil);

        // Botón "Ver Cursos"
        JButton btnVerCursos = new JButton("Ver Cursos");
        btnVerCursos.setBounds(xDerecha, yArriba, anchoBoton, altoBoton);
        btnVerCursos.setBackground(new Color(255, 255, 0));
        btnVerCursos.setFont(new Font("Ebrima", Font.PLAIN, 12));
        contentPane.add(btnVerCursos);

        // Botón "Ver Usuarios"
        JButton btnVerUsuarios = new JButton("Ver Usuarios");
        btnVerUsuarios.setBounds(xIzquierda, yAbajo, anchoBoton, altoBoton);
        btnVerUsuarios.setBackground(new Color(255, 255, 0));
        btnVerUsuarios.setFont(new Font("Ebrima", Font.PLAIN, 12));
        contentPane.add(btnVerUsuarios);

        // Botón "Mis Cursos"
        JButton btnMisCursos = new JButton("Mis Cursos");
        btnMisCursos.setBounds(xDerecha, yAbajo, anchoBoton, altoBoton);
        btnMisCursos.setBackground(new Color(255, 255, 0));
        btnMisCursos.setFont(new Font("Ebrima", Font.PLAIN, 12));
        contentPane.add(btnMisCursos);

        // Lógica según rol
        if (rol == 0) { // Admin
            btnMisCursos.setVisible(false);
            btnMiPerfil.setVisible(false);

            btnVerUsuarios.addActionListener(e -> {
                ListaUsuarios lista = new ListaUsuarios(nombre, rol);
                lista.setVisible(true);
                dispose();
            });

            btnVerCursos.addActionListener(e -> {
                ListaCursos lista = new ListaCursos(nombre, rol);
                lista.setVisible(true);
                dispose();
            });

        } else if (rol == 1) { // Profesor
            btnVerUsuarios.setVisible(false);
            btnVerCursos.setVisible(false);

            btnMisCursos.addActionListener(e -> {
                MisCursos vista = new MisCursos(nombre, rol);
                vista.setVisible(true);
                dispose();
            });

            btnMiPerfil.addActionListener(e -> {
                PerfilProfesor vista = new PerfilProfesor(nombre, rol);
                vista.setVisible(true);
                dispose();
            });

        } else if (rol == 2) { // Alumno
            btnVerUsuarios.setVisible(false);
            btnVerCursos.setVisible(false);

            btnMisCursos.addActionListener(e -> {
                MisCursosAlumno vista = new MisCursosAlumno(nombre, rol);
                vista.setVisible(true);
                dispose();
            });

            btnMiPerfil.addActionListener(e -> {
                PerfilAlumno vista = new PerfilAlumno(nombre, rol);
                vista.setVisible(true);
                dispose();
            });
        }

        // Botón "Salir"
        JButton btnCerrarSesion = new JButton("Salir");
        btnCerrarSesion.setBounds(241, 330, 132, 35);
        btnCerrarSesion.setBackground(new Color(220, 50, 50));
        btnCerrarSesion.setForeground(new Color(255, 255, 255));
        btnCerrarSesion.setFont(new Font("Ebrima", Font.BOLD, 12));
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new PantallaIniciarSesion().setVisible(true);
        });
        contentPane.add(btnCerrarSesion);
    }
}
