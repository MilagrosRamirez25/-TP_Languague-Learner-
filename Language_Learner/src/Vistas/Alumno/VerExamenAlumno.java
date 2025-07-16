package Vistas.Alumno;

import Controladores.EjercicioControlador;
import Controladores.ExamenControlador;
import Controladores.RespuestaControlador;
import Controladores.ResultadoExamenControlador;
import Modelos.Clase;
import Modelos.Curso;
import Modelos.Ejercicio;
import Modelos.Examen;
import Modelos.Respuesta;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VerExamenAlumno extends JFrame {

    private final List<List<JCheckBox>> opcionesSeleccionadas = new ArrayList<>();
    private List<Ejercicio> ejercicios;
    private Examen examen;
    private final Clase clase;
    private final Curso curso;
    private final String nombreUsuario;
    private final int rol;

    private final ResultadoExamenControlador resultadoControlador = new ResultadoExamenControlador();

    public VerExamenAlumno(Clase clase, Curso curso, String nombreUsuario, int rol) {
        this.clase = clase;
        this.curso = curso;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;

        setTitle("Examen - " + clase.getTitulo());
        setSize(900, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        // Logo
        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(-20, 10, 220, 78);
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image imgEscalada = icono.getImage().getScaledInstance(250, 140, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imgEscalada));
        contentPane.add(lblLogo);

        // Título
        JLabel lblTitulo = new JLabel("Examen: " + clase.getTitulo(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Ebrima", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(183, 31, 600, 40);
        contentPane.add(lblTitulo);

        cargarExamen(contentPane);
    }

    private void cargarExamen(JPanel contentPane) {
        ExamenControlador examenControlador = new ExamenControlador();
        EjercicioControlador ejercicioControlador = new EjercicioControlador();
        RespuestaControlador respuestaControlador = new RespuestaControlador();

        examen = examenControlador.obtenerExamenPorIdClase(clase.getId());

        if (examen == null) {
            JOptionPane.showMessageDialog(this, "Esta clase no tiene examen disponible.");
            new ClasesCursoAlumno(curso, nombreUsuario, rol).setVisible(true);
            dispose();
            return;
        }

        if (resultadoControlador.yaRealizoExamen(nombreUsuario, examen.getId())) {
            int puntosObtenidos = resultadoControlador.obtenerResultado(nombreUsuario, examen.getId());
            JOptionPane.showMessageDialog(this,
                    "Ya realizaste este examen.\nTu puntaje fue: " + puntosObtenidos + " puntos.",
                    "Examen ya realizado",
                    JOptionPane.INFORMATION_MESSAGE);
            new ClasesCursoAlumno(curso, nombreUsuario, rol).setVisible(true);
            dispose();
            return;
        }

        ejercicios = ejercicioControlador.obtenerEjerciciosPorExamen(examen.getId());

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(Color.decode("#F2EEAC"));

        int contador = 1;
        for (Ejercicio ej : ejercicios) {
            JPanel panelEjercicio = new JPanel(new BorderLayout());
            panelEjercicio.setBorder(BorderFactory.createTitledBorder("Ejercicio " + contador + " (" + ej.getTipo() + " - " + ej.getPuntos() + " pts)"));
            panelEjercicio.setBackground(Color.decode("#F2EEAC"));

            JTextArea txtContenido = new JTextArea(ej.getContenido());
            txtContenido.setLineWrap(true);
            txtContenido.setWrapStyleWord(true);
            txtContenido.setEditable(false);
            txtContenido.setFont(new Font("Ebrima", Font.PLAIN, 15));
            txtContenido.setBackground(new Color(240, 240, 240));
            txtContenido.setMargin(new Insets(5, 5, 5, 5));
            panelEjercicio.add(new JScrollPane(txtContenido), BorderLayout.NORTH);

            List<Respuesta> respuestas = respuestaControlador.obtenerRespuestasPorEjercicio(ej.getId());

            JPanel panelOpciones = new JPanel(new GridLayout(respuestas.size(), 1));
            panelOpciones.setBackground(Color.decode("#F2EEAC"));

            List<JCheckBox> opcionesEjercicio = new ArrayList<>();
            char letra = 'a';
            for (Respuesta r : respuestas) {
                JCheckBox chk = new JCheckBox(letra++ + ") " + r.getRespuesta());
                chk.setFont(new Font("Ebrima", Font.PLAIN, 14));
                chk.setBackground(Color.decode("#F2EEAC"));
                opcionesEjercicio.add(chk);
                panelOpciones.add(chk);
            }

            opcionesSeleccionadas.add(opcionesEjercicio);
            panelEjercicio.add(panelOpciones, BorderLayout.CENTER);
            panelCentral.add(panelEjercicio);
            contador++;
        }

        JScrollPane scroll = new JScrollPane(panelCentral);
        scroll.setBounds(30, 90, 820, 530);
        contentPane.add(scroll);

        // Botones
        JButton btnEnviar = new JButton("Enviar respuestas");
        JButton btnVolver = new JButton("Volver");

        btnEnviar.setFont(new Font("Ebrima", Font.PLAIN, 15));
        btnVolver.setFont(new Font("Ebrima", Font.PLAIN, 15));

        btnEnviar.addActionListener(e -> evaluarExamen());
        btnVolver.addActionListener(e -> {
            new ClasesCursoAlumno(curso, nombreUsuario, rol).setVisible(true);
            dispose();
        });

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBounds(30, 630, 820, 50);
        panelBotones.setBackground(Color.decode("#F2EEAC"));
        panelBotones.add(btnEnviar);
        panelBotones.add(btnVolver);

        contentPane.add(panelBotones);
    }

    private void evaluarExamen() {
        RespuestaControlador respuestaControlador = new RespuestaControlador();
        int puntosObtenidos = 0;
        int puntosTotales = 0;

        for (int i = 0; i < ejercicios.size(); i++) {
            Ejercicio ejercicio = ejercicios.get(i);
            List<Respuesta> respuestas = respuestaControlador.obtenerRespuestasPorEjercicio(ejercicio.getId());
            List<JCheckBox> opcionesEjercicio = opcionesSeleccionadas.get(i);

            puntosTotales += ejercicio.getPuntos();

            boolean todasCorrectas = true;

            for (int j = 0; j < respuestas.size(); j++) {
                Respuesta respuesta = respuestas.get(j);
                boolean marcada = opcionesEjercicio.get(j).isSelected();

                if (respuesta.isCorrecta() != marcada) {
                    todasCorrectas = false;
                    break;
                }
            }

            if (todasCorrectas) {
                puntosObtenidos += ejercicio.getPuntos();
            }
        }

        resultadoControlador.guardarResultado(nombreUsuario, examen.getId(), puntosObtenidos);

        JOptionPane.showMessageDialog(this,
                "Has obtenido " + puntosObtenidos + " puntos de " + puntosTotales + " posibles.",
                "Resultado del Examen",
                JOptionPane.INFORMATION_MESSAGE);

        new ClasesCursoAlumno(curso, nombreUsuario, rol).setVisible(true);
        dispose();
    }
}
