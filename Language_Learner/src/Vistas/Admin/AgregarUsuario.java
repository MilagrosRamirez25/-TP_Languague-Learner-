package Vistas.Admin;

import java.awt.*;
import javax.swing.*;

import Controladores.UsuarioControlador;
import Modelos.Usuario;

public class AgregarUsuario extends JFrame {

    private JPanel contentPane;
    private JTextField txtUsuario, txtEmail, txtNombre, txtApellido, txtDni, txtEspecialidad;
    private JPasswordField txtPass;
    private JComboBox<String> cmbRol;

    private UsuarioControlador controlador;

    private String nombreUsuarioActual;
    private int rolUsuarioActual;

    public AgregarUsuario(String nombreUsuarioActual, int rolUsuarioActual) {
        this.nombreUsuarioActual = nombreUsuarioActual;
        this.rolUsuarioActual = rolUsuarioActual;

        setTitle("Agregar Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 460, 580);
        setLocationRelativeTo(null);

        controlador = new UsuarioControlador();

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(-23, 0, 216, 75);
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(250, 140, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(scaledImage));
        contentPane.add(lblLogo);

        JLabel lblTitulo = new JLabel("Agregar Usuario");
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 23));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(123, 69, 296, 40);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        contentPane.add(lblTitulo);

        // Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblUsuario.setBounds(30, 136, 100, 25);
        contentPane.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(140, 136, 260, 25);
        contentPane.add(txtUsuario);

        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblEmail.setBounds(30, 176, 100, 25);
        contentPane.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(140, 176, 260, 25);
        contentPane.add(txtEmail);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblPass.setBounds(30, 216, 100, 25);
        contentPane.add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(140, 216, 260, 25);
        contentPane.add(txtPass);

        // Rol
        JLabel lblRol = new JLabel("Rol:");
        lblRol.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblRol.setBounds(30, 256, 100, 25);
        contentPane.add(lblRol);

        cmbRol = new JComboBox<>(new String[]{"Admin", "Profesor", "Alumno"});
        cmbRol.setBounds(140, 256, 260, 25);
        contentPane.add(cmbRol);

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNombre.setBounds(30, 296, 100, 25);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 296, 260, 25);
        contentPane.add(txtNombre);

        // Apellido
        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblApellido.setBounds(30, 336, 100, 25);
        contentPane.add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(140, 336, 260, 25);
        contentPane.add(txtApellido);

        // DNI
        JLabel lblDni = new JLabel("DNI:");
        lblDni.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblDni.setBounds(30, 376, 100, 25);
        contentPane.add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(140, 376, 260, 25);
        contentPane.add(txtDni);

        // Especialidad
        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblEspecialidad.setBounds(30, 416, 100, 25);
        contentPane.add(lblEspecialidad);

        txtEspecialidad = new JTextField();
        txtEspecialidad.setBounds(140, 416, 260, 25);
        contentPane.add(txtEspecialidad);

        setCamposEspecificosVisible(false);

        cmbRol.addActionListener(e -> {
            String rolSeleccionado = (String) cmbRol.getSelectedItem();
            if ("Profesor".equals(rolSeleccionado)) {
                setCamposEspecificosVisible(true);
                txtEspecialidad.setEnabled(true);
            } else if ("Alumno".equals(rolSeleccionado)) {
                setCamposEspecificosVisible(true);
                txtEspecialidad.setEnabled(false);
                txtEspecialidad.setText("");
            } else {
                setCamposEspecificosVisible(false);
            }
        });

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnGuardar.setBackground(new Color(255, 255, 0));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setBounds(143, 474, 150, 40);
        btnGuardar.addActionListener(e -> guardarUsuario());
        contentPane.add(btnGuardar);
    }

    private void setCamposEspecificosVisible(boolean visible) {
        txtNombre.setVisible(visible);
        txtApellido.setVisible(visible);
        txtDni.setVisible(visible);
        txtEspecialidad.setVisible(visible);

        for (Component c : contentPane.getComponents()) {
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                String text = label.getText();
                if ("Nombre:".equals(text) || "Apellido:".equals(text) || "DNI:".equals(text) || "Especialidad:".equals(text)) {
                    label.setVisible(visible);
                }
            }
        }
    }

    private void guardarUsuario() {
        String usuario = txtUsuario.getText().trim();
        String email = txtEmail.getText().trim();
        String pass = new String(txtPass.getPassword());
        String rolStr = (String) cmbRol.getSelectedItem();

        if (usuario.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios.");
            return;
        }

        int rol;
        switch (rolStr) {
            case "Admin": rol = 0; break;
            case "Profesor": rol = 1; break;
            case "Alumno": rol = 2; break;
            default: rol = 0;
        }

        Usuario usuarioObj = new Usuario(0, usuario, email, pass, rol);

        boolean exito = controlador.addUserWithDetails(
            usuarioObj,
            rol == 1 ? txtNombre.getText().trim() : null,
            rol == 1 ? txtApellido.getText().trim() : null,
            rol == 1 ? txtDni.getText().trim() : null,
            rol == 1 ? txtEspecialidad.getText().trim() : null,
            rol == 2 ? txtNombre.getText().trim() : null,
            rol == 2 ? txtApellido.getText().trim() : null,
            rol == 2 ? txtDni.getText().trim() : null
        );

        if (exito) {
            JOptionPane.showMessageDialog(this, "Usuario guardado correctamente.");
            new ListaUsuarios(nombreUsuarioActual, rolUsuarioActual).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar usuario.");
        }
    }
}
