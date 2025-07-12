package Vistas.Admin;

import javax.swing.*;
import java.awt.*;
import Controladores.UsuarioControlador;
import Modelos.Usuario;

public class EditarUsuario extends JFrame {

    private JTextField txtUsuario, txtEmail;
    private JPasswordField txtPass;
    private JComboBox<String> comboRol;

    private JTextField txtNombre, txtApellido, txtDni, txtEspecialidad;
    private JCheckBox cbMostrarPass;

    private Usuario usuario;
    private UsuarioControlador controlador;

    private String nombreUsuarioActual;
    private int rolUsuarioActual;

    public EditarUsuario(Usuario usuario, String nombreUsuarioActual, int rolUsuarioActual) {
        this.usuario = usuario;
        this.nombreUsuarioActual = nombreUsuarioActual;
        this.rolUsuarioActual = rolUsuarioActual;
        this.controlador = new UsuarioControlador();

        setTitle("Editar Usuario");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        // Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 20, 100, 25);
        add(lblUsuario);

        txtUsuario = new JTextField(usuario.getUsuario());
        txtUsuario.setBounds(140, 20, 250, 25);
        add(txtUsuario);

        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 60, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField(usuario.getEmail());
        txtEmail.setBounds(140, 60, 250, 25);
        add(txtEmail);

        // Contraseña
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(30, 100, 100, 25);
        add(lblPass);

        txtPass = new JPasswordField(usuario.getPass());
        txtPass.setBounds(140, 100, 250, 25);
        add(txtPass);

        // Mostrar/Ocultar contraseña
        cbMostrarPass = new JCheckBox("Mostrar contraseña");
        cbMostrarPass.setBounds(140, 130, 200, 20);
        cbMostrarPass.addActionListener(e -> {
            txtPass.setEchoChar(cbMostrarPass.isSelected() ? (char) 0 : '•');
        });
        add(cbMostrarPass);

        // Rol
        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(30, 160, 100, 25);
        add(lblRol);

        comboRol = new JComboBox<>(new String[]{"Admin", "Profesor", "Alumno"});
        comboRol.setSelectedIndex(usuario.getRol());
        comboRol.setBounds(140, 160, 250, 25);
        add(comboRol);

        // Campos específicos
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 200, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 200, 250, 25);
        add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(30, 240, 100, 25);
        add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(140, 240, 250, 25);
        add(txtApellido);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setBounds(30, 280, 100, 25);
        add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(140, 280, 250, 25);
        add(txtDni);

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setBounds(30, 320, 100, 25);
        add(lblEspecialidad);

        txtEspecialidad = new JTextField();
        txtEspecialidad.setBounds(140, 320, 250, 25);
        add(txtEspecialidad);

        // Mostrar campos específicos según rol
        actualizarCamposSegunRol(usuario.getRol());
        comboRol.addActionListener(e -> {
            int rol = comboRol.getSelectedIndex();
            actualizarCamposSegunRol(rol);
        });

        // Cargar datos extra
        cargarDatosExtras(usuario);

        // Botón Guardar
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 380, 100, 30);
        btnGuardar.addActionListener(e -> guardarCambios());
        add(btnGuardar);

        // Botón Cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 380, 100, 30);
        btnCancelar.addActionListener(e -> {
            new ListaUsuarios(nombreUsuarioActual, rolUsuarioActual).setVisible(true);
            dispose();
        });
        add(btnCancelar);
    }

    private void actualizarCamposSegunRol(int rol) {
        boolean esProfesor = (rol == 1);
        boolean esAlumno = (rol == 2);

        txtNombre.setVisible(esProfesor || esAlumno);
        txtApellido.setVisible(esProfesor || esAlumno);
        txtDni.setVisible(esProfesor || esAlumno);
        txtEspecialidad.setVisible(esProfesor);

        for (Component c : getContentPane().getComponents()) {
            if (c instanceof JLabel) {
                JLabel l = (JLabel) c;
                switch (l.getText()) {
                    case "Nombre:":
                    case "Apellido:":
                    case "DNI:":
                        l.setVisible(esProfesor || esAlumno);
                        break;
                    case "Especialidad:":
                        l.setVisible(esProfesor);
                        break;
                }
            }
        }
    }

    private void cargarDatosExtras(Usuario usuario) {
        int rol = usuario.getRol();

        if (rol == 1) { // Profesor
            var datosProf = controlador.getProfesorByUserId(usuario.getId());
            if (datosProf != null) {
                txtNombre.setText(datosProf.getNombre());
                txtApellido.setText(datosProf.getApellido());
                txtDni.setText(datosProf.getDni());
                txtEspecialidad.setText(datosProf.getEspecialidad());
            }
        } else if (rol == 2) { // Alumno
            var datosAlum = controlador.getAlumnoByUserId(usuario.getId());
            if (datosAlum != null) {
                txtNombre.setText(datosAlum.getNombre());
                txtApellido.setText(datosAlum.getApellido());
                txtDni.setText(datosAlum.getDni());
            }
        }
    }

    private void guardarCambios() {
        usuario.setUsuario(txtUsuario.getText().trim());
        usuario.setEmail(txtEmail.getText().trim());
        usuario.setPass(new String(txtPass.getPassword()));

        int nuevoRol = comboRol.getSelectedIndex();
        usuario.setRol(nuevoRol);

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String dni = txtDni.getText().trim();
        String especialidad = txtEspecialidad.getText().trim();

        boolean exito = controlador.updateUserWithDetails(
            usuario,
            nuevoRol == 1 ? nombre : null,
            nuevoRol == 1 ? apellido : null,
            nuevoRol == 1 ? dni : null,
            nuevoRol == 1 ? especialidad : null,
            nuevoRol == 2 ? nombre : null,
            nuevoRol == 2 ? apellido : null,
            nuevoRol == 2 ? dni : null
        );

        if (exito) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.");
            new ListaUsuarios(nombreUsuarioActual, rolUsuarioActual).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar usuario.");
        }
    }
}
