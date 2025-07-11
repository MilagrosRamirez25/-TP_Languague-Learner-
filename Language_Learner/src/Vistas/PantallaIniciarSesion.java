package Vistas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Modelos.Usuario;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class PantallaIniciarSesion extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField inpUsuario;
    private JPasswordField inpContraseña;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PantallaIniciarSesion frame = new PantallaIniciarSesion();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PantallaIniciarSesion() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(661, 483);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblUsuario.setBounds(207, 51, 215, 38);
        lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblUsuario);

        inpUsuario = new JTextField();
        inpUsuario.setBounds(207, 100, 215, 38);
        contentPane.add(inpUsuario);
        inpUsuario.setColumns(10);

        JLabel lblContraseña = new JLabel("Contraseña");
        lblContraseña.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblContraseña.setBounds(207, 149, 215, 28);
        lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblContraseña);

        inpContraseña = new JPasswordField();
        inpContraseña.setBounds(207, 188, 215, 38);
        contentPane.add(inpContraseña);

        JLabel lblError = new JLabel("");
        lblError.setBounds(207, 237, 215, 20);
        contentPane.add(lblError);
        lblError.setVisible(false);

        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Tahoma", Font.PLAIN, 24));
        btnIngresar.setBounds(228, 268, 176, 40);
        btnIngresar.addActionListener(e -> {
            String usuario = inpUsuario.getText().trim();
            String pass = new String(inpContraseña.getPassword());

            String respuesta = Usuario.iniciarSesion(usuario, pass);

            if (respuesta.startsWith("rol:")) {
                int rol = Integer.parseInt(respuesta.substring(4));
                PantallaHome home = new PantallaHome(usuario, rol);
                home.setVisible(true);
                dispose();
            } else {
                lblError.setText(respuesta);
                lblError.setVisible(true);
            }
        });

        contentPane.add(btnIngresar);

        JButton btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setFont(new Font("Tahoma", Font.PLAIN, 24));
        btnRegistrarse.setBounds(228, 319, 176, 42);
        btnRegistrarse.addActionListener(e -> {
            Registro registro = new Registro();
            registro.setVisible(true);
            dispose();
        });
        contentPane.add(btnRegistrarse);
    }
}
