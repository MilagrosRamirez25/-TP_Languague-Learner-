package Vistas.Profesor;

import javax.swing.*;
import java.awt.*;

import Controladores.UsuarioControlador;
import Modelos.Profesor;

public class EditarPerfilProfesor extends JFrame {
    private final Profesor profesor;

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDni;
    private JTextField txtEspecialidad;
    private JTextField txtUsuario;
    private JPasswordField pfPass;
    private JCheckBox cbMostrarPass;

    public EditarPerfilProfesor(Profesor profesor) {
        this.profesor = profesor;

        setTitle("Editar Perfil");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(7, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(profesor.getNombre());
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Apellido:"));
        txtApellido = new JTextField(profesor.getApellido());
        panelForm.add(txtApellido);

        panelForm.add(new JLabel("DNI:"));
        txtDni = new JTextField(profesor.getDni());
        panelForm.add(txtDni);

        panelForm.add(new JLabel("Especialidad:"));
        txtEspecialidad = new JTextField(profesor.getEspecialidad());
        panelForm.add(txtEspecialidad);

        panelForm.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField(profesor.getUsuario());
        panelForm.add(txtUsuario);

        panelForm.add(new JLabel("Contraseña:"));
        pfPass = new JPasswordField(profesor.getPass());
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
            // Actualizar datos en objeto profesor
            profesor.setNombre(txtNombre.getText());
            profesor.setApellido(txtApellido.getText());
            profesor.setDni(txtDni.getText());
            profesor.setEspecialidad(txtEspecialidad.getText());
            profesor.setUsuario(txtUsuario.getText());
            profesor.setPass(new String(pfPass.getPassword()));

            UsuarioControlador controlador = new UsuarioControlador();
            boolean exito = controlador.updateUserWithDetails(
                profesor,
                profesor.getNombre(),
                profesor.getApellido(),
                profesor.getDni(),
                profesor.getEspecialidad(),
                null, null, null
            );

            if (exito) {
                JOptionPane.showMessageDialog(this, "Perfil actualizado correctamente.");
                new PerfilProfesor(profesor.getUsuario(), profesor.getRol()).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el perfil.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> {
            new PerfilProfesor(profesor.getUsuario(), profesor.getRol()).setVisible(true);
            dispose();
        });

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }
}
