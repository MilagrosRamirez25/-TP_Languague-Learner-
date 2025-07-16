package Vistas;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

import Modelos.Usuario;

public class PantallaIniciarSesion extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField inpUsuario;
    private JPasswordField inpContraseña;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PantallaIniciarSesion frame = new PantallaIniciarSesion();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PantallaIniciarSesion() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(661, 483);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC")); // Fondo amarillo claro
        setContentPane(contentPane);

        JLabel lblImagen = new JLabel();
        lblImagen.setBounds(0, 0, 233, 84);
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/logo.png"));
        java.awt.Image scaledImage = originalIcon.getImage().getScaledInstance(250, 140, java.awt.Image.SCALE_SMOOTH);
        lblImagen.setIcon(new ImageIcon(scaledImage));
        contentPane.add(lblImagen);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setForeground(new Color(37, 68, 175));
        lblUsuario.setFont(new Font("Eras Bold ITC", Font.BOLD, 22));
        lblUsuario.setBounds(207, 70, 215, 38);
        lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblUsuario);

        inpUsuario = new JTextField();
        inpUsuario.setBounds(207, 110, 215, 38);
        inpUsuario.setFont(new Font("Ebrima", Font.PLAIN, 16));
        contentPane.add(inpUsuario);
        inpUsuario.setColumns(10);

        JLabel lblContraseña = new JLabel("Contraseña");
        lblContraseña.setForeground(new Color(37, 68, 175));
        lblContraseña.setFont(new Font("Eras Bold ITC", Font.BOLD, 22));
        lblContraseña.setBounds(207, 165, 215, 28);
        lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblContraseña);

        inpContraseña = new JPasswordField();
        inpContraseña.setBounds(207, 200, 215, 38);
        inpContraseña.setFont(new Font("Ebrima", Font.PLAIN, 16));
        contentPane.add(inpContraseña);

        JLabel lblError = new JLabel("");
        lblError.setBounds(207, 245, 215, 20);
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Ebrima", Font.PLAIN, 14));
        contentPane.add(lblError);
        lblError.setVisible(false);

        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Ebrima", Font.PLAIN, 16));
        btnIngresar.setBackground(new Color(255, 255, 0)); // Amarillo
        btnIngresar.setBounds(228, 275, 176, 40);
        btnIngresar.addActionListener(e -> {
            String usuario = inpUsuario.getText().trim();
            String pass = new String(inpContraseña.getPassword());

            String respuesta = Usuario.iniciarSesion(usuario, pass);

            if (respuesta.startsWith("rol:")) {
                int rol = Integer.parseInt(respuesta.substring(4));
                PantallaHome home = new PantallaHome(usuario, rol);
                home.setVisible(true);
                dispose();
            } else {
                lblError.setText(respuesta);
                lblError.setVisible(true);
            }
        });
        contentPane.add(btnIngresar);

        JButton btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setFont(new Font("Ebrima", Font.PLAIN, 16));
        btnRegistrarse.setBackground(new Color(255, 255, 0)); // Amarillo
        btnRegistrarse.setBounds(228, 325, 176, 42);
        btnRegistrarse.addActionListener(e -> {
            Registro registro = new Registro();
            registro.setVisible(true);
            dispose();
        });
        contentPane.add(btnRegistrarse);
    }
}
