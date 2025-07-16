package Vistas.Admin;

import javax.swing.*;
import java.awt.*;

import Controladores.UsuarioControlador;
import Modelos.Usuario;

public class EditarUsuario extends JFrame {

    private JTextField txtUsuario, txtEmail, txtNombre, txtApellido, txtDni, txtEspecialidad;
    private JPasswordField txtPass;
    private JComboBox<String> comboRol;
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
        setSize(646, 593);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(-13, 10, 219, 78);
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image imagenEscalada = icono.getImage().getScaledInstance(250, 140, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imagenEscalada));
        contentPane.add(lblLogo);

        JLabel lblTitulo = new JLabel("Editar Usuario");
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 23));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(167, 94, 250, 30);
        contentPane.add(lblTitulo);

        int yBase = 170;

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, yBase, 100, 25);
        contentPane.add(lblUsuario);

        txtUsuario = new JTextField(usuario.getUsuario());
        txtUsuario.setBounds(140, yBase, 360, 25);
        contentPane.add(txtUsuario);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, yBase + 40, 100, 25);
        contentPane.add(lblEmail);

        txtEmail = new JTextField(usuario.getEmail());
        txtEmail.setBounds(140, yBase + 40, 360, 25);
        contentPane.add(txtEmail);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(30, yBase + 80, 100, 25);
        contentPane.add(lblPass);

        txtPass = new JPasswordField(usuario.getPass());
        txtPass.setBounds(140, yBase + 80, 360, 25);
        contentPane.add(txtPass);

        cbMostrarPass = new JCheckBox("Mostrar contraseña");
        cbMostrarPass.setBounds(140, yBase + 110, 200, 20);
        cbMostrarPass.setBackground(Color.decode("#F2EEAC"));
        cbMostrarPass.addActionListener(e -> txtPass.setEchoChar(cbMostrarPass.isSelected() ? (char) 0 : '•'));
        contentPane.add(cbMostrarPass);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(30, yBase + 140, 100, 25);
        contentPane.add(lblRol);

        comboRol = new JComboBox<>(new String[]{"Admin", "Profesor", "Alumno"});
        comboRol.setSelectedIndex(usuario.getRol());
        comboRol.setBounds(140, yBase + 140, 360, 25);
        contentPane.add(comboRol);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, yBase + 180, 100, 25);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, yBase + 180, 360, 25);
        contentPane.add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(30, yBase + 220, 100, 25);
        contentPane.add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(140, yBase + 220, 360, 25);
        contentPane.add(txtApellido);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setBounds(30, yBase + 260, 100, 25);
        contentPane.add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(140, yBase + 260, 360, 25);
        contentPane.add(txtDni);

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setBounds(30, yBase + 300, 100, 25);
        contentPane.add(lblEspecialidad);

        txtEspecialidad = new JTextField();
        txtEspecialidad.setBounds(140, yBase + 300, 360, 25);
        contentPane.add(txtEspecialidad);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, yBase + 340, 130, 35);
        btnGuardar.setBackground(Color.YELLOW);
        btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(280, yBase + 340, 130, 35);
        contentPane.add(btnCancelar);

        // Eventos
        actualizarCamposSegunRol(usuario.getRol());
        comboRol.addActionListener(e -> actualizarCamposSegunRol(comboRol.getSelectedIndex()));

        btnGuardar.addActionListener(e -> guardarCambios());
        btnCancelar.addActionListener(e -> {
            new ListaUsuarios(nombreUsuarioActual, rolUsuarioActual).setVisible(true);
            dispose();
        });

        cargarDatosExtras(usuario);
    }

    private void actualizarCamposSegunRol(int rol) {
        boolean esProfesor = (rol == 1);
        boolean esAlumno = (rol == 2);

        txtNombre.setVisible(esProfesor || esAlumno);
        txtApellido.setVisible(esProfesor || esAlumno);
        txtDni.setVisible(esProfesor || esAlumno);
        txtEspecialidad.setVisible(esProfesor);

        for (Component c : getContentPane().getComponents()) {
            if (c instanceof JLabel label) {
                switch (label.getText()) {
                    case "Nombre:", "Apellido:", "DNI:" -> label.setVisible(esProfesor || esAlumno);
                    case "Especialidad:" -> label.setVisible(esProfesor);
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
