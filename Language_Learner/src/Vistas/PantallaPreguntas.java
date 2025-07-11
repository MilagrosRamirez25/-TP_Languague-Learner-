package Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JOptionPane;

public class PantallaPreguntas extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;

    private String[] preguntas = {
        "Como se traduce esta palabra = CASA ",
        "Como se traduce esta palabra = PERRO ",
        "Indique cual es el significado de LETTER",
        "Indique cual es el significado de GUSTAR",
        "Indique cual es el signficado de GATO",
        "Indique cual es la forma correcta \"She ______ (to be) a teacher",
        "Indique la forma correcta \"They ______ (to go) to the park every Sunday"
    };

    private String[] respuestas = {
        "Home",
        "Dog",
        "Letter",
        "Like",
        "Cat",
        "is",
        "go"
    };

    public PantallaPreguntas() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblPregunta = new JLabel(preguntas[currentQuestionIndex]);
        lblPregunta.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblPregunta.setBounds(20, 30, 400, 30);
        contentPane.add(lblPregunta);

        textField = new JTextField();
        textField.setBounds(20, 80, 400, 30);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnSiguiente = new JButton("Siguiente");
        btnSiguiente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String respuestaUsuario = textField.getText().trim().toLowerCase();
                String respuestaCorrecta = respuestas[currentQuestionIndex].toLowerCase();

                if (respuestaUsuario.equals(respuestaCorrecta)) {
                    correctAnswers++;
                }

                currentQuestionIndex++;

                if (currentQuestionIndex < preguntas.length) {
                    lblPregunta.setText(preguntas[currentQuestionIndex]);
                    textField.setText("");
                } else {
                    double score = ((double) correctAnswers / preguntas.length) * 100;
                    JOptionPane.showMessageDialog(null, "Examen completado.\nTu puntaje: " + score + "%");
                    // Aquí puedes decidir qué hacer después de completar el examen
                    dispose();
                }
            }
        });
        btnSiguiente.setBounds(150, 150, 130, 30);
        contentPane.add(btnSiguiente);
    }
}
