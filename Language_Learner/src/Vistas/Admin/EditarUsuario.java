package Vistas.Admin;

import javax.swing.*;
import java.awt.*;
import Controladores.UsuarioControlador;
import Modelos.Usuario;

public class EditarUsuario extends JFrame {

    private JTextField txtUsuario, txtEmail;
    private JPasswordField txtPass; // Opcional para cambiar contraseña
    private JComboBox<String> comboRol;

    // Campos específicos
    private JTextField txtNombre, txtApellido, txtDni, txtEspecialidad;

    private Usuario usuario;
    private UsuarioControlador controlador;

    // Datos del usuario actual para pasar a ListaUsuarios
    private String nombreUsuarioActual;
    private int rolUsuarioActual;

    public EditarUsuario(Usuario usuario, String nombreUsuarioActual, int rolUsuarioActual) {
        this.usuario = usuario;
        this.nombreUsuarioActual = nombreUsuarioActual;
        this.rolUsuarioActual = rolUsuarioActual;
        this.controlador = new UsuarioControlador();

        setTitle("Editar Usuario");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setLayout(null);

        // Campos básicos
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 20, 100, 25);
        add(lblUsuario);

        txtUsuario = new JTextField(usuario.getUsuario());
        txtUsuario.setBounds(140, 20, 250, 25);
        add(txtUsuario);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 60, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField(usuario.getEmail());
        txtEmail.setBounds(140, 60, 250, 25);
        add(txtEmail);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(30, 100, 100, 25);
        add(lblPass);

        txtPass = new JPasswordField(usuario.getPass());
        txtPass.setBounds(140, 100, 250, 25);
        add(txtPass);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(30, 140, 100, 25);
        add(lblRol);

        comboRol = new JComboBox<>(new String[]{"Admin", "Profesor", "Alumno"});
        comboRol.setSelectedIndex(usuario.getRol());
        comboRol.setBounds(140, 140, 250, 25);
        add(comboRol);

        // Campos específicos
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 180, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 180, 250, 25);
        add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(30, 220, 100, 25);
        add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(140, 220, 250, 25);
        add(txtApellido);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setBounds(30, 260, 100, 25);
        add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(140, 260, 250, 25);
        add(txtDni);

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setBounds(30, 300, 100, 25);
        add(lblEspecialidad);

        txtEspecialidad = new JTextField();
        txtEspecialidad.setBounds(140, 300, 250, 25);
        add(txtEspecialidad);

        // Mostrar u ocultar campos según rol inicial
        actualizarCamposSegunRol(usuario.getRol());

        // Cambiar visibilidad si cambia rol en combo
        comboRol.addActionListener(e -> {
            int rol = comboRol.getSelectedIndex();
            actualizarCamposSegunRol(rol);
        });

        // Cargar datos específicos según rol
        cargarDatosExtras(usuario);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 350, 100, 30);
        btnGuardar.addActionListener(e -> guardarCambios());
        add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 350, 100, 30);
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

        // También ocultar/mostrar labels (buscamos por posición)
        // Simplificación: asumimos labels están en el orden fijo:
        Component[] comps = getContentPane().getComponents();
        for (Component c : comps) {
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
        usuario.setRol(comboRol.getSelectedIndex());

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String dni = txtDni.getText().trim();
        String especialidad = txtEspecialidad.getText().trim();

        boolean exito = controlador.updateUserWithDetails(usuario,
                usuario.getRol() == 1 ? nombre : null,
                usuario.getRol() == 1 ? apellido : null,
                usuario.getRol() == 1 ? dni : null,
                usuario.getRol() == 1 ? especialidad : null,
                usuario.getRol() == 2 ? nombre : null,
                usuario.getRol() == 2 ? apellido : null,
                usuario.getRol() == 2 ? dni : null);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.");
            new ListaUsuarios(nombreUsuarioActual, rolUsuarioActual).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar usuario.");
        }
    }
}
