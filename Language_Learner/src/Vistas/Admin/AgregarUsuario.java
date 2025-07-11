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

    // Constructor con parámetros para que no te dé error al crear instancia
    public AgregarUsuario(String nombreUsuarioActual, int rolUsuarioActual) {
        this.nombreUsuarioActual = nombreUsuarioActual;
        this.rolUsuarioActual = rolUsuarioActual;

        setTitle("Agregar Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 450);
        setLocationRelativeTo(null);

        controlador = new UsuarioControlador();

        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 20, 100, 25);
        contentPane.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(140, 20, 250, 25);
        contentPane.add(txtUsuario);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 60, 100, 25);
        contentPane.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(140, 60, 250, 25);
        contentPane.add(txtEmail);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(30, 100, 100, 25);
        contentPane.add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(140, 100, 250, 25);
        contentPane.add(txtPass);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(30, 140, 100, 25);
        contentPane.add(lblRol);

        cmbRol = new JComboBox<>(new String[]{"Admin", "Profesor", "Alumno"});
        cmbRol.setBounds(140, 140, 250, 25);
        contentPane.add(cmbRol);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 180, 100, 25);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 180, 250, 25);
        contentPane.add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(30, 220, 100, 25);
        contentPane.add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(140, 220, 250, 25);
        contentPane.add(txtApellido);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setBounds(30, 260, 100, 25);
        contentPane.add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(140, 260, 250, 25);
        contentPane.add(txtDni);

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setBounds(30, 300, 100, 25);
        contentPane.add(lblEspecialidad);

        txtEspecialidad = new JTextField();
        txtEspecialidad.setBounds(140, 300, 250, 25);
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
        btnGuardar.setBounds(140, 350, 150, 40);
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
