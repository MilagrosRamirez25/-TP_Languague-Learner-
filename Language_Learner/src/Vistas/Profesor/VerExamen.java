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
    private List<Ejercicio> ejercicios;

    public VerExamen(Clase clase) {
        setTitle("Editar Examen de la Clase: " + clase.getTitulo());
        setSize(900, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

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

        JPanel contentPane = new JPanel(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image imgOriginal = iconoOriginal.getImage();
        int logoWidth = 140;
        int logoHeight = (imgOriginal.getHeight(null) * logoWidth) / imgOriginal.getWidth(null);
        Image imgEscalada = imgOriginal.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(imgEscalada));
        lblLogo.setBounds(-27, -26, 179, 140);
        contentPane.add(lblLogo);

        JLabel lblTitulo = new JLabel("Editar Examen - " + clase.getTitulo());
        lblTitulo.setFont(new Font("Ebrima", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 83, 166));
        int tituloX = lblLogo.getX() + lblLogo.getWidth() + 25;
        int tituloY = lblLogo.getY() + (lblLogo.getHeight() / 2) - 20;
        lblTitulo.setBounds(221, 69, 700, 40);
        contentPane.add(lblTitulo);

        JPanel panelEjercicios = new JPanel(null);
        panelEjercicios.setBackground(Color.decode("#F2EEAC"));

        int panelWidth = 860;
        int alturaPorEjercicio = 210;
        int panelHeight = ejercicios.size() * alturaPorEjercicio + 20;
        panelEjercicios.setPreferredSize(new Dimension(panelWidth, panelHeight));

        int yBase = 10;
        int ejercicioNum = 1;

        for (Ejercicio ej : ejercicios) {
            JPanel panelEjercicio = new JPanel(null);
            panelEjercicio.setBackground(Color.decode("#FDF9E2"));
            panelEjercicio.setBorder(BorderFactory.createLineBorder(Color.decode("#CCCC99"), 2));
            panelEjercicio.setBounds(10, yBase, panelWidth - 20, alturaPorEjercicio - 10);
            panelEjercicios.add(panelEjercicio);

            JLabel lblEjercicio = new JLabel("Ejercicio " + ejercicioNum + " (" + ej.getTipo() + " - " + ej.getPuntos() + " pts)");
            lblEjercicio.setFont(new Font("Ebrima", Font.BOLD, 16));
            lblEjercicio.setBounds(10, 10, 600, 25);
            panelEjercicio.add(lblEjercicio);

            JTextArea txtContenido = new JTextArea(ej.getContenido());
            txtContenido.setLineWrap(true);
            txtContenido.setWrapStyleWord(true);
            txtContenido.setFont(new Font("Ebrima", Font.PLAIN, 14));
            camposContenido.add(txtContenido);
            JScrollPane spContenido = new JScrollPane(txtContenido);
            spContenido.setBounds(10, 40, panelEjercicio.getWidth() - 20, 60);
            panelEjercicio.add(spContenido);

            List<Respuesta> respuestas = respuestaControlador.obtenerRespuestasPorEjercicio(ej.getId());
            List<JTextField> listaRespuestas = new ArrayList<>();
            List<JCheckBox> listaChecks = new ArrayList<>();

            int respY = 110;
            char letra = 'a';

            for (Respuesta r : respuestas) {
                JLabel lblResp = new JLabel(letra++ + ")");
                lblResp.setFont(new Font("Ebrima", Font.BOLD, 14));
                lblResp.setBounds(10, respY + 5, 20, 25);
                panelEjercicio.add(lblResp);

                JTextField txtResp = new JTextField(r.getRespuesta());
                txtResp.setFont(new Font("Ebrima", Font.PLAIN, 14));
                txtResp.setBounds(30, respY, 520, 30);
                panelEjercicio.add(txtResp);
                listaRespuestas.add(txtResp);

                JCheckBox chkCorrecta = new JCheckBox("Correcta", r.isCorrecta());
                chkCorrecta.setFont(new Font("Ebrima", Font.PLAIN, 13));
                chkCorrecta.setBackground(Color.decode("#FDF9E2"));
                chkCorrecta.setBounds(560, respY + 5, 100, 25);
                panelEjercicio.add(chkCorrecta);
                listaChecks.add(chkCorrecta);

                respY += 35;
            }

            camposRespuestas.add(listaRespuestas);
            camposCorrectas.add(listaChecks);

            yBase += alturaPorEjercicio;
            ejercicioNum++;
        }

        JScrollPane scroll = new JScrollPane(panelEjercicios);
        scroll.setBounds(25, 134, panelWidth, 570);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        contentPane.add(scroll);

        JPanel panelBotones = new JPanel(null);
        panelBotones.setBackground(Color.decode("#F2EEAC"));
        int panelBotonesY = scroll.getY() + scroll.getHeight() + 10;
        panelBotones.setBounds(0, panelBotonesY, getWidth(), 60);
        contentPane.add(panelBotones);

        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setFont(new Font("Ebrima", Font.BOLD, 16));
        btnGuardar.setBackground(Color.WHITE);
        btnGuardar.setForeground(new Color(0, 83, 166));
        btnGuardar.setFocusPainted(false);
        int btnGuardarWidth = 180;
        int btnGuardarHeight = 40;
        btnGuardar.setBounds((getWidth() / 2) - (btnGuardarWidth / 2), 10, btnGuardarWidth, btnGuardarHeight);
        btnGuardar.addActionListener(e -> guardarCambios());
        panelBotones.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Ebrima", Font.PLAIN, 14));
        btnCancelar.setBackground(Color.LIGHT_GRAY);
        btnCancelar.setFocusPainted(false);
        int btnCancelarWidth = 110;
        int btnCancelarHeight = 35;
        btnCancelar.setBounds(btnGuardar.getX() + btnGuardarWidth + 15, 12, btnCancelarWidth, btnCancelarHeight);
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Ebrima", Font.PLAIN, 14));
        btnVolver.setBackground(Color.LIGHT_GRAY);
        btnVolver.setFocusPainted(false);
        int btnVolverWidth = 110;
        int btnVolverHeight = 35;
        btnVolver.setBounds(btnGuardar.getX() - btnVolverWidth - 15, 12, btnVolverWidth, btnVolverHeight);
        btnVolver.addActionListener(e -> {
            dispose();
        });
        panelBotones.add(btnVolver);
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
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Hubo un error al guardar los cambios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
