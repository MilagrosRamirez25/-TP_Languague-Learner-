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

    private List<List<JCheckBox>> opcionesSeleccionadas = new ArrayList<>();
    private List<Ejercicio> ejercicios;
    private Examen examen;
    private Clase clase;
    private Curso curso;
    private String nombreUsuario;
    private int rol;

    private ResultadoExamenControlador resultadoControlador = new ResultadoExamenControlador();

    public VerExamenAlumno(Clase clase, Curso curso, String nombreUsuario, int rol) {
        this.clase = clase;
        this.curso = curso;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;

        setTitle("Examen - " + clase.getTitulo());
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        cargarExamen();
    }

    private void cargarExamen() {
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

        int contador = 1;
        for (Ejercicio ej : ejercicios) {
            JPanel panelEjercicio = new JPanel(new BorderLayout());
            panelEjercicio.setBorder(BorderFactory.createTitledBorder("Ejercicio " + contador + " (" + ej.getTipo() + " - " + ej.getPuntos() + " pts)"));

            JTextArea txtContenido = new JTextArea(ej.getContenido());
            txtContenido.setLineWrap(true);
            txtContenido.setWrapStyleWord(true);
            txtContenido.setEditable(false);
            txtContenido.setBackground(new Color(240, 240, 240));
            panelEjercicio.add(new JScrollPane(txtContenido), BorderLayout.NORTH);

            List<Respuesta> respuestas = respuestaControlador.obtenerRespuestasPorEjercicio(ej.getId());

            JPanel panelOpciones = new JPanel(new GridLayout(respuestas.size(), 1));
            List<JCheckBox> opcionesEjercicio = new ArrayList<>();
            char letra = 'a';
            for (Respuesta r : respuestas) {
                JCheckBox chk = new JCheckBox(letra++ + ") " + r.getRespuesta());
                opcionesEjercicio.add(chk);
                panelOpciones.add(chk);
            }
            opcionesSeleccionadas.add(opcionesEjercicio);

            panelEjercicio.add(panelOpciones, BorderLayout.CENTER);
            panelCentral.add(panelEjercicio);
            contador++;
        }

        JScrollPane scroll = new JScrollPane(panelCentral);
        add(scroll, BorderLayout.CENTER);

        JButton btnEnviar = new JButton("Enviar respuestas");
        btnEnviar.addActionListener(e -> evaluarExamen());

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            new ClasesCursoAlumno(curso, nombreUsuario, rol).setVisible(true);
            dispose();
        });

        JPanel panelSur = new JPanel();
        panelSur.add(btnEnviar);
        panelSur.add(btnVolver);
        add(panelSur, BorderLayout.SOUTH);
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
