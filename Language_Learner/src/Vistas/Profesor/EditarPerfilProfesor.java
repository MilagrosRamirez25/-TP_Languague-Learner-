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

        setTitle("Editar Perfil de Profesor");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(-10, 5, 200, 80);
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image imgEscalada = icono.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imgEscalada));
        contentPane.add(lblLogo);

        JLabel lblTitulo = new JLabel("Editar Perfil");
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 30));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(230, 20, 400, 50);
        contentPane.add(lblTitulo);

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(7, 2, 10, 10));
        panelForm.setBounds(80, 100, 440, 250);
        panelForm.setBackground(Color.decode("#F2EEAC"));

        Font labelFont = new Font("Ebrima", Font.PLAIN, 16);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(labelFont);
        panelForm.add(lblNombre);
        txtNombre = new JTextField(profesor.getNombre());
        txtNombre.setFont(labelFont);
        panelForm.add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(labelFont);
        panelForm.add(lblApellido);
        txtApellido = new JTextField(profesor.getApellido());
        txtApellido.setFont(labelFont);
        panelForm.add(txtApellido);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setFont(labelFont);
        panelForm.add(lblDni);
        txtDni = new JTextField(profesor.getDni());
        txtDni.setFont(labelFont);
        panelForm.add(txtDni);

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setFont(labelFont);
        panelForm.add(lblEspecialidad);
        txtEspecialidad = new JTextField(profesor.getEspecialidad());
        txtEspecialidad.setFont(labelFont);
        panelForm.add(txtEspecialidad);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(labelFont);
        panelForm.add(lblUsuario);
        txtUsuario = new JTextField(profesor.getUsuario());
        txtUsuario.setFont(labelFont);
        panelForm.add(txtUsuario);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(labelFont);
        panelForm.add(lblPass);
        pfPass = new JPasswordField(profesor.getPass());
        pfPass.setFont(labelFont);
        panelForm.add(pfPass);

        cbMostrarPass = new JCheckBox("Mostrar contraseña");
        cbMostrarPass.setFont(new Font("Ebrima", Font.PLAIN, 14));
        cbMostrarPass.setBackground(Color.decode("#F2EEAC"));
        cbMostrarPass.addActionListener(e -> {
            pfPass.setEchoChar(cbMostrarPass.isSelected() ? (char) 0 : '•');
        });

        panelForm.add(new JLabel()); 
        panelForm.add(cbMostrarPass);

        contentPane.add(panelForm);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelBotones.setBounds(80, 370, 440, 50);
        panelBotones.setBackground(Color.decode("#F2EEAC"));

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(labelFont);
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(labelFont);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        contentPane.add(panelBotones);

        btnGuardar.addActionListener(e -> {
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
    }
}
