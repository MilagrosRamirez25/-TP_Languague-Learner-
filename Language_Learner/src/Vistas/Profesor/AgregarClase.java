package Vistas.Profesor;

import Controladores.ClaseControlador;
import Modelos.Clase;
import Modelos.Curso;

import javax.swing.*;
import java.awt.*;

public class AgregarClase extends JFrame {

    private JTextField tfTitulo;
    private JTextField tfTema;
    private JTextArea taContenido;
    private JTextField tfAutor;
    private Curso curso;
    private ClaseControlador claseControlador;

    public AgregarClase(Curso curso) {
        this.curso = curso;
        this.claseControlador = new ClaseControlador();

        setTitle("Agregar Clase");
        setSize(650, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(10, 5, 220, 80);
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image imgEscalada = icono.getImage().getScaledInstance(180, 100, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imgEscalada));
        contentPane.add(lblLogo);

        JLabel lblTituloPantalla = new JLabel("Agregar Clase al Curso: " + curso.getNombreCurso());
        lblTituloPantalla.setFont(new Font("Eras Bold ITC", Font.BOLD, 22));
        lblTituloPantalla.setForeground(new Color(0, 83, 166));
        lblTituloPantalla.setBounds(182, 55, 506, 30);
        contentPane.add(lblTituloPantalla);

        Font fuenteLabel = new Font("Ebrima", Font.BOLD, 16);
        Font fuenteCampo = new Font("Ebrima", Font.PLAIN, 15);

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setFont(fuenteLabel);
        lblTitulo.setBounds(80, 100, 100, 25);
        contentPane.add(lblTitulo);

        tfTitulo = new JTextField();
        tfTitulo.setFont(fuenteCampo);
        tfTitulo.setBounds(180, 100, 370, 30);
        contentPane.add(tfTitulo);

        JLabel lblTema = new JLabel("Tema:");
        lblTema.setFont(fuenteLabel);
        lblTema.setBounds(80, 150, 100, 25);
        contentPane.add(lblTema);

        tfTema = new JTextField();
        tfTema.setFont(fuenteCampo);
        tfTema.setBounds(180, 150, 370, 30);
        contentPane.add(tfTema);

        JLabel lblContenido = new JLabel("Contenido:");
        lblContenido.setFont(fuenteLabel);
        lblContenido.setBounds(80, 200, 100, 25);
        contentPane.add(lblContenido);

        taContenido = new JTextArea();
        taContenido.setFont(fuenteCampo);
        taContenido.setLineWrap(true);
        taContenido.setWrapStyleWord(true);

        JScrollPane scrollContenido = new JScrollPane(taContenido);
        scrollContenido.setBounds(180, 200, 370, 120);
        contentPane.add(scrollContenido);

        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setFont(fuenteLabel);
        lblAutor.setBounds(80, 340, 100, 25);
        contentPane.add(lblAutor);

        tfAutor = new JTextField();
        tfAutor.setFont(fuenteCampo);
        tfAutor.setBounds(180, 340, 370, 30);
        contentPane.add(tfAutor);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.setFont(fuenteCampo);
        btnCancelar.setFont(fuenteCampo);

        btnGuardar.setBounds(180, 420, 150, 40);
        btnCancelar.setBounds(400, 420, 150, 40);

        contentPane.add(btnGuardar);
        contentPane.add(btnCancelar);

        btnGuardar.addActionListener(e -> guardarClase());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarClase() {
        String titulo = tfTitulo.getText().trim();
        String tema = tfTema.getText().trim();
        String contenido = taContenido.getText().trim();
        String autor = tfAutor.getText().trim();

        if (titulo.isEmpty() || tema.isEmpty() || contenido.isEmpty() || autor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completá todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Clase nuevaClase = new Clase(
                curso.getId(),
                titulo,
                tema,
                contenido,
                autor,
                java.time.LocalDate.now().toString()
        );

        boolean exito = claseControlador.agregarClase(nuevaClase);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Clase agregada correctamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar la clase.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
