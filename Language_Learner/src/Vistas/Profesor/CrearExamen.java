package Vistas.Profesor;

import Controladores.EjercicioControlador;
import Controladores.ExamenControlador;
import Controladores.RespuestaControlador;
import Modelos.Clase;
import Modelos.Ejercicio;
import Modelos.Examen;
import Modelos.Respuesta;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CrearExamen extends JFrame {

    private Clase clase;
    private ExamenControlador examenControlador;
    private EjercicioControlador ejercicioControlador;
    private RespuestaControlador respuestaControlador;

    private JTextField tfTitulo;

    private final List<JTextArea> camposContenidoEjercicios = new ArrayList<>();
    private final List<JTextField> camposPuntosEjercicios = new ArrayList<>();
    private final List<List<JTextField>> camposRespuestas = new ArrayList<>();
    private final List<List<JCheckBox>> camposCorrectas = new ArrayList<>();

    private JPanel panelEjercicios;

    public CrearExamen(Clase clase) {
        this.clase = clase;
        examenControlador = new ExamenControlador();
        ejercicioControlador = new EjercicioControlador();
        respuestaControlador = new RespuestaControlador();

        setTitle("Crear Examen para la Clase: " + clase.getTitulo());
        setSize(1100, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(10, 5, 200, 80);
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image imgEscalada = icono.getImage().getScaledInstance(180, 100, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imgEscalada));
        contentPane.add(lblLogo);

        JLabel lblTitulo = new JLabel("Crear Examen", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(350, 20, 400, 40);
        contentPane.add(lblTitulo);

        JLabel lblInstruccion = new JLabel("Completá el título, los ejercicios y sus respuestas.", SwingConstants.CENTER);
        lblInstruccion.setFont(new Font("Ebrima", Font.ITALIC, 14));
        lblInstruccion.setForeground(Color.DARK_GRAY);
        lblInstruccion.setBounds(300, 60, 500, 20);
        contentPane.add(lblInstruccion);

        JLabel lblCampoTitulo = new JLabel("Título del Examen:");
        lblCampoTitulo.setFont(new Font("Ebrima", Font.PLAIN, 16));
        lblCampoTitulo.setBounds(80, 100, 150, 30);
        contentPane.add(lblCampoTitulo);

        tfTitulo = new JTextField();
        tfTitulo.setBounds(240, 100, 700, 30);
        tfTitulo.setFont(new Font("Ebrima", Font.PLAIN, 15));
        contentPane.add(tfTitulo);

        panelEjercicios = new JPanel();
        panelEjercicios.setLayout(new BoxLayout(panelEjercicios, BoxLayout.Y_AXIS));
        panelEjercicios.setBackground(Color.decode("#F2EEAC"));

        JScrollPane scrollEjercicios = new JScrollPane(panelEjercicios);
        scrollEjercicios.setBounds(50, 150, 980, 520);
        scrollEjercicios.getViewport().setBackground(Color.decode("#F2EEAC"));
        contentPane.add(scrollEjercicios);

        JButton btnAgregarEjercicio = new JButton("Agregar Ejercicio");
        btnAgregarEjercicio.setFont(new Font("Ebrima", Font.PLAIN, 14));
        btnAgregarEjercicio.setBounds(300, 690, 200, 40);
        btnAgregarEjercicio.addActionListener(e -> agregarEjercicioPanel());
        contentPane.add(btnAgregarEjercicio);

        JButton btnGuardar = new JButton("Guardar Examen");
        btnGuardar.setFont(new Font("Ebrima", Font.PLAIN, 14));
        btnGuardar.setBounds(550, 690, 200, 40);
        btnGuardar.addActionListener(e -> guardarExamenCompleto());
        contentPane.add(btnGuardar);

        for (int i = 0; i < 3; i++) {
            agregarEjercicioPanel();
        }
    }

    private void agregarEjercicioPanel() {
        JPanel panelEj = new JPanel();
        panelEj.setLayout(null);
        panelEj.setPreferredSize(new Dimension(940, 280)); // Altura aumentada
        panelEj.setBorder(BorderFactory.createTitledBorder("Ejercicio"));
        panelEj.setBackground(Color.decode("#F2EEAC"));

        JLabel lblContenido = new JLabel("Contenido:");
        lblContenido.setBounds(10, 20, 100, 25);
        lblContenido.setFont(new Font("Ebrima", Font.PLAIN, 14));
        panelEj.add(lblContenido);

        JTextArea taContenido = new JTextArea();
        taContenido.setLineWrap(true);
        taContenido.setWrapStyleWord(true);
        taContenido.setFont(new Font("Ebrima", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(taContenido);
        scroll.setBounds(110, 20, 800, 60);
        panelEj.add(scroll);
        camposContenidoEjercicios.add(taContenido);

        JLabel lblPuntos = new JLabel("Puntos:");
        lblPuntos.setBounds(10, 90, 100, 25);
        lblPuntos.setFont(new Font("Ebrima", Font.PLAIN, 14));
        panelEj.add(lblPuntos);

        JTextField tfPuntos = new JTextField();
        tfPuntos.setBounds(110, 90, 100, 25);
        tfPuntos.setFont(new Font("Ebrima", Font.PLAIN, 14));
        panelEj.add(tfPuntos);
        camposPuntosEjercicios.add(tfPuntos);

        List<JTextField> listaRespuestas = new ArrayList<>();
        List<JCheckBox> listaCorrectas = new ArrayList<>();

        int yBase = 130;
        for (int i = 0; i < 4; i++) {
            JLabel lblResp = new JLabel((char) ('a' + i) + ")");
            lblResp.setFont(new Font("Ebrima", Font.PLAIN, 14));
            lblResp.setBounds(10, yBase + i * 30, 20, 25);
            panelEj.add(lblResp);

            JTextField tfResp = new JTextField();
            tfResp.setFont(new Font("Ebrima", Font.PLAIN, 14));
            tfResp.setBounds(40, yBase + i * 30, 700, 25);
            panelEj.add(tfResp);

            JCheckBox chkCorrecta = new JCheckBox("Correcta");
            chkCorrecta.setFont(new Font("Ebrima", Font.PLAIN, 13));
            chkCorrecta.setBounds(760, yBase + i * 30, 100, 25);
            chkCorrecta.setBackground(Color.decode("#F2EEAC"));
            panelEj.add(chkCorrecta);

            listaRespuestas.add(tfResp);
            listaCorrectas.add(chkCorrecta);
        }

        camposRespuestas.add(listaRespuestas);
        camposCorrectas.add(listaCorrectas);

        panelEjercicios.add(panelEj);
        panelEjercicios.revalidate();
        panelEjercicios.repaint();
    }

    private void guardarExamenCompleto() {
        // Lógica para guardar examen aquí (todavía no implementada)
        JOptionPane.showMessageDialog(this, "Funcionalidad de guardado aún no implementada.");
    }
}
