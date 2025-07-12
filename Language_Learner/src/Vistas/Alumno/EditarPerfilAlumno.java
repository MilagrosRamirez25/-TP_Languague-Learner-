package Vistas.Alumno;

import javax.swing.*;
import java.awt.*;

import Controladores.UsuarioControlador;
import Modelos.Alumno;

public class EditarPerfilAlumno extends JFrame {
    private final Alumno alumno;

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDni;
    private JTextField txtEmail;
    private JTextField txtUsuario;
    private JPasswordField pfPass;
    private JCheckBox cbMostrarPass;

    public EditarPerfilAlumno(Alumno alumno) {
        this.alumno = alumno;

        setTitle("Editar Perfil");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(8, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(alumno.getNombre());
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Apellido:"));
        txtApellido = new JTextField(alumno.getApellido());
        panelForm.add(txtApellido);

        panelForm.add(new JLabel("DNI:"));
        txtDni = new JTextField(alumno.getDni());
        panelForm.add(txtDni);

        panelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField(alumno.getEmail());
        panelForm.add(txtEmail);

        panelForm.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField(alumno.getUsuario());
        panelForm.add(txtUsuario);

        panelForm.add(new JLabel("Contraseña:"));
        pfPass = new JPasswordField(alumno.getPass());
        panelForm.add(pfPass);

        cbMostrarPass = new JCheckBox("Mostrar contraseña");
        cbMostrarPass.addActionListener(e -> {
            if (cbMostrarPass.isSelected()) {
                pfPass.setEchoChar((char) 0);
            } else {
                pfPass.setEchoChar('•'); // o '*'
            }
        });
        panelForm.add(new JLabel()); // espacio vacío
        panelForm.add(cbMostrarPass);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            // Actualizar datos en objeto alumno
            alumno.setNombre(txtNombre.getText());
            alumno.setApellido(txtApellido.getText());
            alumno.setDni(txtDni.getText());
            alumno.setEmail(txtEmail.getText());
            alumno.setUsuario(txtUsuario.getText());
            alumno.setPass(new String(pfPass.getPassword()));

            UsuarioControlador controlador = new UsuarioControlador();
            boolean exito = controlador.updateUserWithDetails(
                alumno,
                null, null, null, null,
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getDni()
            );

            if (exito) {
                JOptionPane.showMessageDialog(this, "Perfil actualizado correctamente.");
                new PerfilAlumno(alumno.getUsuario(), alumno.getRol()).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el perfil.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> {
            new PerfilAlumno(alumno.getUsuario(), alumno.getRol()).setVisible(true);
            dispose();
        });

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }
}
