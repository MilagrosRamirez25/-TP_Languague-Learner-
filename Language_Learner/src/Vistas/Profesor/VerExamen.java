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

public class VerExamen extends JFrame {

    private final ExamenControlador examenControlador;
    private final EjercicioControlador ejercicioControlador;
    private final RespuestaControlador respuestaControlador;

    private final List<JTextArea> camposContenido = new ArrayList<>();
    private final List<List<JTextField>> camposRespuestas = new ArrayList<>();
    private final List<List<JCheckBox>> camposCorrectas = new ArrayList<>();
    private List<Ejercicio> ejercicios;  // <- NO final para evitar errores de inicialización

 

    // Constructor principal
    public VerExamen(Clase clase) {
        setTitle("Editar Examen de la Clase: " + clase.getTitulo());
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        examenControlador = new ExamenControlador();
        ejercicioControlador = new EjercicioControlador();
        respuestaControlador = new RespuestaControlador();

        Examen examen = examenControlador.obtenerExamenPorIdClase(clase.getId());
        if (examen == null) {
            JOptionPane.showMessageDialog(this, "Esta clase no tiene un examen asociado.");
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

            JTextArea txtContenido = new JTextArea(ej.getContenido(), 3, 50);
            txtContenido.setLineWrap(true);
            txtContenido.setWrapStyleWord(true);
            camposContenido.add(txtContenido);
            panelEjercicio.add(new JScrollPane(txtContenido), BorderLayout.NORTH);

            List<Respuesta> respuestas = respuestaControlador.obtenerRespuestasPorEjercicio(ej.getId());
            List<JTextField> camposResp = new ArrayList<>();
            List<JCheckBox> camposCheck = new ArrayList<>();

            JPanel panelResp = new JPanel(new GridLayout(respuestas.size(), 1));
            char letra = 'a';
            for (Respuesta r : respuestas) {
                JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel lbl = new JLabel(letra++ + ")");
                JTextField txtResp = new JTextField(r.getRespuesta(), 40);
                JCheckBox chkCorrecta = new JCheckBox("Correcta", r.isCorrecta());

                camposResp.add(txtResp);
                camposCheck.add(chkCorrecta);

                fila.add(lbl);
                fila.add(txtResp);
                fila.add(chkCorrecta);
                panelResp.add(fila);
            }

            camposRespuestas.add(camposResp);
            camposCorrectas.add(camposCheck);
            panelEjercicio.add(panelResp, BorderLayout.CENTER);

            panelCentral.add(panelEjercicio);
            contador++;
        }

        JScrollPane scroll = new JScrollPane(panelCentral);
        add(scroll, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.addActionListener(e -> guardarCambios());

        JPanel panelSur = new JPanel();
        panelSur.add(btnGuardar);
        add(panelSur, BorderLayout.SOUTH);
    }

    private void guardarCambios() {
        boolean todoBien = true;

        for (int i = 0; i < ejercicios.size(); i++) {
            Ejercicio ej = ejercicios.get(i);
            String nuevoContenido = camposContenido.get(i).getText();
            ej.setContenido(nuevoContenido);

            if (!ejercicioControlador.updateEjercicio(ej)) {
                todoBien = false;
            }

            List<JTextField> respuestasTexto = camposRespuestas.get(i);
            List<JCheckBox> respuestasCorrectas = camposCorrectas.get(i);
            List<Respuesta> respuestasBD = respuestaControlador.obtenerRespuestasPorEjercicio(ej.getId());

            for (int j = 0; j < respuestasTexto.size(); j++) {
                Respuesta r = respuestasBD.get(j);
                r.setRespuesta(respuestasTexto.get(j).getText());
                r.setCorrecta(respuestasCorrectas.get(j).isSelected());
                if (!respuestaControlador.updateRespuesta(r)) {
                    todoBien = false;
                }
            }
        }

        if (todoBien) {
            JOptionPane.showMessageDialog(this, "Cambios guardados exitosamente.");
            dispose();  // <-- Cierra la ventana
        } else {
            JOptionPane.showMessageDialog(this, "Hubo un error al guardar los cambios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
