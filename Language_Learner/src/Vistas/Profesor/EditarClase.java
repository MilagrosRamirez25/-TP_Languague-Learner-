package Vistas.Profesor;

import Controladores.ClaseControlador;
import Modelos.Clase;

import javax.swing.*;
import java.awt.*;

public class EditarClase extends JFrame {
    private JTextField tfTitulo;
    private JTextField tfTema;
    private JTextArea taContenido;
    private JTextField tfAutor;
    private Clase clase;
    private ClaseControlador claseControlador;

    public EditarClase(Clase clase) {
        this.clase = clase;
        claseControlador = new ClaseControlador();

        setTitle("Editar Clase - " + clase.getTitulo());
        setSize(600, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(-20, -5, 220, 80);
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image imgEscalada = icono.getImage().getScaledInstance(230, 130, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imgEscalada));
        contentPane.add(lblLogo);

        JLabel lblTituloPantalla = new JLabel("Editar Clase", SwingConstants.CENTER);
        lblTituloPantalla.setFont(new Font("Ebrima", Font.BOLD, 24));
        lblTituloPantalla.setForeground(new Color(0, 83, 166));
        lblTituloPantalla.setBounds(180, 30, 300, 40);
        contentPane.add(lblTituloPantalla);

        Font fuente = new Font("Ebrima", Font.PLAIN, 15);

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setFont(fuente);
        lblTitulo.setBounds(60, 100, 100, 25);
        contentPane.add(lblTitulo);

        tfTitulo = new JTextField(clase.getTitulo());
        tfTitulo.setFont(fuente);
        tfTitulo.setBounds(160, 100, 360, 25);
        contentPane.add(tfTitulo);

        JLabel lblTema = new JLabel("Tema:");
        lblTema.setFont(fuente);
        lblTema.setBounds(60, 140, 100, 25);
        contentPane.add(lblTema);

        tfTema = new JTextField(clase.getTema());
        tfTema.setFont(fuente);
        tfTema.setBounds(160, 140, 360, 25);
        contentPane.add(tfTema);

        JLabel lblContenido = new JLabel("Contenido:");
        lblContenido.setFont(fuente);
        lblContenido.setBounds(60, 180, 100, 25);
        contentPane.add(lblContenido);

        taContenido = new JTextArea(clase.getContenido());
        taContenido.setFont(fuente);
        taContenido.setLineWrap(true);
        taContenido.setWrapStyleWord(true);

        JScrollPane spContenido = new JScrollPane(taContenido);
        spContenido.setBounds(160, 180, 360, 120);
        contentPane.add(spContenido);

        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setFont(fuente);
        lblAutor.setBounds(60, 310, 100, 25);
        contentPane.add(lblAutor);

        tfAutor = new JTextField(clase.getAutor());
        tfAutor.setFont(fuente);
        tfAutor.setBounds(160, 310, 360, 25);
        contentPane.add(tfAutor);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(fuente);
        btnGuardar.setBounds(170, 380, 110, 35);
        btnGuardar.addActionListener(e -> guardarCambios());
        contentPane.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(fuente);
        btnCancelar.setBounds(320, 380, 110, 35);
        btnCancelar.addActionListener(e -> dispose());
        contentPane.add(btnCancelar);
    }

    private void guardarCambios() {
        String titulo = tfTitulo.getText().trim();
        String tema = tfTema.getText().trim();
        String contenido = taContenido.getText().trim();
        String autor = tfAutor.getText().trim();

        if (titulo.isEmpty() || tema.isEmpty() || contenido.isEmpty() || autor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completá todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        clase.setTitulo(titulo);
        clase.setTema(tema);
        clase.setContenido(contenido);
        clase.setAutor(autor);

        boolean exito = claseControlador.actualizarClase(clase);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Clase actualizada correctamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar la clase.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
