package Vistas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controladores.UsuarioControlador;
import Modelos.Usuario;

public class Registro extends JFrame {

    private JPanel contentPane;
    private JTextField inpUsuario, inpEmail, inpNombre, inpApellido, inpDni;
    private JPasswordField inpPass, inpRepetirPass;

    public Registro() {
        setTitle("Registrarse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 500);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC")); 
        setContentPane(contentPane);

        JLabel lblImagen = new JLabel();
        lblImagen.setBounds(0, 0, 223, 68);
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/logo.png"));
        java.awt.Image scaledImage = originalIcon.getImage().getScaledInstance(250, 140, java.awt.Image.SCALE_SMOOTH);
        lblImagen.setIcon(new ImageIcon(scaledImage));
        contentPane.add(lblImagen);


        JLabel lblTitulo = new JLabel("Registro de Alumno");
        lblTitulo.setForeground(new Color(37, 68, 175));
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 22));
        lblTitulo.setBounds(226, 49, 300, 30);
        contentPane.add(lblTitulo);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Ebrima", Font.PLAIN, 14));
        lblUsuario.setBounds(50, 100, 100, 25);
        contentPane.add(lblUsuario);

        inpUsuario = new JTextField();
        inpUsuario.setFont(new Font("Ebrima", Font.PLAIN, 14));
        inpUsuario.setBounds(180, 100, 250, 25);
        contentPane.add(inpUsuario);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Ebrima", Font.PLAIN, 14));
        lblEmail.setBounds(50, 130, 100, 25);
        contentPane.add(lblEmail);

        inpEmail = new JTextField();
        inpEmail.setFont(new Font("Ebrima", Font.PLAIN, 14));
        inpEmail.setBounds(180, 130, 250, 25);
        contentPane.add(inpEmail);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Ebrima", Font.PLAIN, 14));
        lblPass.setBounds(50, 160, 100, 25);
        contentPane.add(lblPass);

        inpPass = new JPasswordField();
        inpPass.setFont(new Font("Ebrima", Font.PLAIN, 14));
        inpPass.setBounds(180, 160, 250, 25);
        contentPane.add(inpPass);

        JLabel lblRepetir = new JLabel("Repetir Contraseña:");
        lblRepetir.setFont(new Font("Ebrima", Font.PLAIN, 14));
        lblRepetir.setBounds(50, 190, 130, 25);
        contentPane.add(lblRepetir);

        inpRepetirPass = new JPasswordField();
        inpRepetirPass.setFont(new Font("Ebrima", Font.PLAIN, 14));
        inpRepetirPass.setBounds(180, 190, 250, 25);
        contentPane.add(inpRepetirPass);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Ebrima", Font.PLAIN, 14));
        lblNombre.setBounds(50, 220, 100, 25);
        contentPane.add(lblNombre);

        inpNombre = new JTextField();
        inpNombre.setFont(new Font("Ebrima", Font.PLAIN, 14));
        inpNombre.setBounds(180, 220, 250, 25);
        contentPane.add(inpNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(new Font("Ebrima", Font.PLAIN, 14));
        lblApellido.setBounds(50, 250, 100, 25);
        contentPane.add(lblApellido);

        inpApellido = new JTextField();
        inpApellido.setFont(new Font("Ebrima", Font.PLAIN, 14));
        inpApellido.setBounds(180, 250, 250, 25);
        contentPane.add(inpApellido);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setFont(new Font("Ebrima", Font.PLAIN, 14));
        lblDni.setBounds(50, 280, 100, 25);
        contentPane.add(lblDni);

        inpDni = new JTextField();
        inpDni.setFont(new Font("Ebrima", Font.PLAIN, 14));
        inpDni.setBounds(180, 280, 250, 25);
        contentPane.add(inpDni);

        JButton btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setFont(new Font("Ebrima", Font.PLAIN, 16));
        btnRegistrar.setBackground(new Color(255, 255, 0));
        btnRegistrar.setBounds(180, 330, 150, 40);
        contentPane.add(btnRegistrar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Ebrima", Font.PLAIN, 16));
        btnVolver.setBackground(new Color(255, 255, 0));
        btnVolver.setBounds(180, 385, 150, 35);
        contentPane.add(btnVolver);

        // registrar
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = inpUsuario.getText().trim();
                String email = inpEmail.getText().trim();
                String pass = new String(inpPass.getPassword());
                String repetir = new String(inpRepetirPass.getPassword());
                String nombre = inpNombre.getText().trim();
                String apellido = inpApellido.getText().trim();
                String dni = inpDni.getText().trim();

                if (usuario.isEmpty() || email.isEmpty() || pass.isEmpty() || repetir.isEmpty()
                        || nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete todos los campos.");
                    return;
                }

                if (!pass.equals(repetir)) {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
                    return;
                }

                UsuarioControlador controlador = new UsuarioControlador();

                if (controlador.usuarioOEmailExiste(usuario, email)) {
                    JOptionPane.showMessageDialog(null, "El usuario o email ya están en uso.");
                    return;
                }

                Usuario nuevo = new Usuario(0, usuario, email, pass, 2);

                boolean exito = controlador.addUserWithDetails(nuevo,
                        null, null, null, null,
                        nombre, apellido, dni
                );

                if (exito) {
                    JOptionPane.showMessageDialog(null, "Registro exitoso. Inicie sesión.");
                    dispose();
                    new PantallaIniciarSesion().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar. Intente nuevamente.");
                }
            }
        });

        btnVolver.addActionListener(e -> {
            dispose();
            new PantallaIniciarSesion().setVisible(true);
        });
    }
}
