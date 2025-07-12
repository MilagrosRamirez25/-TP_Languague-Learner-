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
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Título examen
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTitulo.add(new JLabel("Título del Examen:"));
        tfTitulo = new JTextField(30);
        panelTitulo.add(tfTitulo);
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);

        // Panel para ejercicios
        panelEjercicios = new JPanel();
        panelEjercicios.setLayout(new BoxLayout(panelEjercicios, BoxLayout.Y_AXIS));
        JScrollPane scrollEjercicios = new JScrollPane(panelEjercicios);
        panelPrincipal.add(scrollEjercicios, BorderLayout.CENTER);

        add(panelPrincipal, BorderLayout.CENTER);

        // Guardar examen completo
        JButton btnGuardar = new JButton("Guardar Examen");
        btnGuardar.addActionListener(e -> guardarExamenCompleto());
        JPanel panelGuardar = new JPanel();
        panelGuardar.add(btnGuardar);
        add(panelGuardar, BorderLayout.SOUTH);

        // Agregar 3 ejercicios por defecto
        for (int i = 0; i < 3; i++) {
            agregarEjercicioPanel();
        }
    }

    private void agregarEjercicioPanel() {
        JPanel panelEj = new JPanel();
        panelEj.setLayout(new BoxLayout(panelEj, BoxLayout.Y_AXIS));
        panelEj.setBorder(BorderFactory.createTitledBorder("Ejercicio"));

        // Contenido
        JPanel pContenido = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pContenido.add(new JLabel("Contenido:"));
        JTextArea taContenido = new JTextArea(3, 60);
        taContenido.setLineWrap(true);
        taContenido.setWrapStyleWord(true);
        pContenido.add(new JScrollPane(taContenido));
        panelEj.add(pContenido);
        camposContenidoEjercicios.add(taContenido);

        // Puntos
        JPanel pPuntos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pPuntos.add(new JLabel("Puntos (máximo 10):"));
        JTextField tfPuntos = new JTextField(5);
        pPuntos.add(tfPuntos);
        camposPuntosEjercicios.add(tfPuntos);
        panelEj.add(pPuntos);

        // Respuestas
        JPanel pRespuestas = new JPanel();
        pRespuestas.setLayout(new BoxLayout(pRespuestas, BoxLayout.Y_AXIS));
        pRespuestas.setBorder(BorderFactory.createTitledBorder("Respuestas"));

        List<JTextField> listaRespuestas = new ArrayList<>();
        List<JCheckBox> listaCorrectas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            JPanel filaResp = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel lbl = new JLabel((char) ('a' + i) + ")");
            JTextField tfResp = new JTextField(40);
            JCheckBox chkCorrecta = new JCheckBox("Correcta");

            filaResp.add(lbl);
            filaResp.add(tfResp);
            filaResp.add(chkCorrecta);

            pRespuestas.add(filaResp);
            listaRespuestas.add(tfResp);
            listaCorrectas.add(chkCorrecta);
        }
        camposRespuestas.add(listaRespuestas);
        camposCorrectas.add(listaCorrectas);

        panelEj.add(pRespuestas);

        panelEjercicios.add(panelEj);
        panelEjercicios.revalidate();
        panelEjercicios.repaint();
    }

    private void guardarExamenCompleto() {
        String tituloExamen = tfTitulo.getText().trim();

        if (tituloExamen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresá un título para el examen.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Examen examen = new Examen();
        examen.setIdClase(clase.getId());
        examen.setTitulo(tituloExamen);

        int idExamenCreado = examenControlador.addExamen(examen);
        if (idExamenCreado == -1) {
            JOptionPane.showMessageDialog(this, "Error al crear el examen.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean todoBien = true;

        for (int i = 0; i < camposContenidoEjercicios.size(); i++) {
            String contenido = camposContenidoEjercicios.get(i).getText().trim();
            String puntosStr = camposPuntosEjercicios.get(i).getText().trim();

            if (contenido.isEmpty() || puntosStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completar todos los campos de ejercicio (contenido y puntos).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int puntos;
            try {
                puntos = Integer.parseInt(puntosStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Puntos debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setIdExamen(idExamenCreado);
            ejercicio.setContenido(contenido);
            ejercicio.setTipo("selección múltiple");
            ejercicio.setPuntos(puntos);

            int idEjercicioCreado = ejercicioControlador.agregarEjercicio(ejercicio);
            if (idEjercicioCreado == -1) {
                todoBien = false;
                continue;
            }

            List<JTextField> respuestasTexto = camposRespuestas.get(i);
            List<JCheckBox> respuestasCorrectas = camposCorrectas.get(i);

            for (int j = 0; j < respuestasTexto.size(); j++) {
                String textoResp = respuestasTexto.get(j).getText().trim();
                boolean esCorrecta = respuestasCorrectas.get(j).isSelected();

                if (textoResp.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Completar texto de todas las respuestas.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Respuesta respuesta = new Respuesta();
                respuesta.setIdEjercicio(idEjercicioCreado);
                respuesta.setRespuesta(textoResp);
                respuesta.setCorrecta(esCorrecta);

                boolean exitoResp = respuestaControlador.agregarRespuesta(respuesta);
                if (!exitoResp) {
                    todoBien = false;
                }
            }
        }

        if (todoBien) {
            JOptionPane.showMessageDialog(this, "Examen, ejercicios y respuestas creados correctamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Hubo errores al guardar algunos ejercicios o respuestas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
