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
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("Registro de Alumno");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitulo.setBounds(140, 10, 300, 30);
        contentPane.add(lblTitulo);

        // Campos comunes
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(50, 60, 100, 25);
        contentPane.add(lblUsuario);
        inpUsuario = new JTextField();
        inpUsuario.setBounds(180, 60, 250, 25);
        contentPane.add(inpUsuario);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 100, 100, 25);
        contentPane.add(lblEmail);
        inpEmail = new JTextField();
        inpEmail.setBounds(180, 100, 250, 25);
        contentPane.add(inpEmail);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(50, 140, 100, 25);
        contentPane.add(lblPass);
        inpPass = new JPasswordField();
        inpPass.setBounds(180, 140, 250, 25);
        contentPane.add(inpPass);

        JLabel lblRepetir = new JLabel("Repetir Contraseña:");
        lblRepetir.setBounds(50, 180, 130, 25);
        contentPane.add(lblRepetir);
        inpRepetirPass = new JPasswordField();
        inpRepetirPass.setBounds(180, 180, 250, 25);
        contentPane.add(inpRepetirPass);

        // Datos del alumno
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 220, 100, 25);
        contentPane.add(lblNombre);
        inpNombre = new JTextField();
        inpNombre.setBounds(180, 220, 250, 25);
        contentPane.add(inpNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(50, 260, 100, 25);
        contentPane.add(lblApellido);
        inpApellido = new JTextField();
        inpApellido.setBounds(180, 260, 250, 25);
        contentPane.add(inpApellido);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setBounds(50, 300, 100, 25);
        contentPane.add(lblDni);
        inpDni = new JTextField();
        inpDni.setBounds(180, 300, 250, 25);
        contentPane.add(inpDni);

        // Botón registrar
        JButton btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBounds(180, 350, 150, 40);
        contentPane.add(btnRegistrar);

        // Botón volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(180, 400, 150, 35);
        contentPane.add(btnVolver);

        // Acción registrar
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

                // Primero verificar si usuario o email existen
                if (controlador.usuarioOEmailExiste(usuario, email)) {
                    JOptionPane.showMessageDialog(null, "El usuario o email ya están en uso.");
                    return;
                }

                Usuario nuevo = new Usuario(0, usuario, email, pass, 2); // rol 2 = alumno

                boolean exito = controlador.addUserWithDetails(nuevo, 
                        null, null, null, null, // sin datos de profesor
                        nombre, apellido, dni   // datos de alumno
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


        // Acción volver
        btnVolver.addActionListener(e -> {
            dispose();
            new PantallaIniciarSesion().setVisible(true);
        });
    }
}
