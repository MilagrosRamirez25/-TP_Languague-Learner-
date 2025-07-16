package Vistas.Profesor;

import javax.swing.*;
import java.awt.*;

import Controladores.UsuarioControlador;
import Modelos.Profesor;
import Vistas.PantallaHome;

public class PerfilProfesor extends JFrame {
    private final String nombreUsuario;
    private final int rol;

    public PerfilProfesor(String nombreUsuario, int rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;

        setTitle("Perfil del Profesor");
        setSize(480, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image imgOriginal = icono.getImage();
        int maxWidth = 120;
        int ancho = imgOriginal.getWidth(null);
        int alto = imgOriginal.getHeight(null);
        int altoEscalado = (alto * maxWidth) / ancho;
        Image imgEscalada = imgOriginal.getScaledInstance(maxWidth, altoEscalado, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(imgEscalada));
        lblLogo.setBounds(10, -33, maxWidth, altoEscalado);
        contentPane.add(lblLogo);

        JLabel lblTitulo = new JLabel("Mi Perfil");
        lblTitulo.setFont(new Font("Ebrima", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(115, -16, 200, 40);
        contentPane.add(lblTitulo);

        UsuarioControlador usuarioControlador = new UsuarioControlador();
        int idUsuario = usuarioControlador.obtenerIdPorNombreUsuario(nombreUsuario);
        Profesor profesor = usuarioControlador.getProfesorByUserId(idUsuario);

        Font fuenteEtiqueta = new Font("Ebrima", Font.BOLD, 16);
        Font fuenteValor = new Font("Ebrima", Font.PLAIN, 16);

        int etiquetaX = 30;
        int valorX = 160;
        int yBase = 80;
        int espacioY = 35;

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(fuenteEtiqueta);
        lblNombre.setBounds(etiquetaX, yBase, 120, 25);
        contentPane.add(lblNombre);

        JLabel valNombre = new JLabel(profesor.getNombre());
        valNombre.setFont(fuenteValor);
        valNombre.setBounds(valorX, yBase, 250, 25);
        contentPane.add(valNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(fuenteEtiqueta);
        lblApellido.setBounds(etiquetaX, yBase + espacioY, 120, 25);
        contentPane.add(lblApellido);

        JLabel valApellido = new JLabel(profesor.getApellido());
        valApellido.setFont(fuenteValor);
        valApellido.setBounds(valorX, yBase + espacioY, 250, 25);
        contentPane.add(valApellido);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setFont(fuenteEtiqueta);
        lblDni.setBounds(etiquetaX, yBase + 2 * espacioY, 120, 25);
        contentPane.add(lblDni);

        JLabel valDni = new JLabel(profesor.getDni());
        valDni.setFont(fuenteValor);
        valDni.setBounds(valorX, yBase + 2 * espacioY, 250, 25);
        contentPane.add(valDni);

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setFont(fuenteEtiqueta);
        lblEspecialidad.setBounds(etiquetaX, yBase + 3 * espacioY, 120, 25);
        contentPane.add(lblEspecialidad);

        JLabel valEspecialidad = new JLabel(profesor.getEspecialidad());
        valEspecialidad.setFont(fuenteValor);
        valEspecialidad.setBounds(valorX, yBase + 3 * espacioY, 250, 25);
        contentPane.add(valEspecialidad);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(fuenteEtiqueta);
        lblUsuario.setBounds(etiquetaX, yBase + 4 * espacioY, 120, 25);
        contentPane.add(lblUsuario);

        JLabel valUsuario = new JLabel(profesor.getUsuario());
        valUsuario.setFont(fuenteValor);
        valUsuario.setBounds(valorX, yBase + 4 * espacioY, 250, 25);
        contentPane.add(valUsuario);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(fuenteEtiqueta);
        lblPass.setBounds(etiquetaX, yBase + 5 * espacioY, 120, 25);
        contentPane.add(lblPass);

        JLabel valPass = new JLabel("********");
        valPass.setFont(fuenteValor);
        valPass.setBounds(valorX, yBase + 5 * espacioY, 250, 25);
        contentPane.add(valPass);

        JButton btnEditar = new JButton("Editar perfil");
        btnEditar.setFont(new Font("Ebrima", Font.BOLD, 16));
        btnEditar.setBackground(Color.WHITE);
        btnEditar.setForeground(new Color(0, 83, 166));
        btnEditar.setFocusPainted(false);
        btnEditar.setBounds(100, yBase + 7 * espacioY, 130, 40);
        btnEditar.addActionListener(e -> {
            new EditarPerfilProfesor(profesor).setVisible(true);
            dispose();
        });
        contentPane.add(btnEditar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Ebrima", Font.BOLD, 16));
        btnVolver.setBackground(Color.WHITE);
        btnVolver.setForeground(new Color(0, 83, 166));
        btnVolver.setFocusPainted(false);
        btnVolver.setBounds(260, yBase + 7 * espacioY, 130, 40);
        btnVolver.addActionListener(e -> {
            new PantallaHome(nombreUsuario, rol).setVisible(true);
            dispose();
        });
        contentPane.add(btnVolver);
    }
}
