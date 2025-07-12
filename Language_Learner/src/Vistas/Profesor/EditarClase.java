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
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Título:"), gbc);
        tfTitulo = new JTextField(clase.getTitulo(), 30);
        gbc.gridx = 1;
        panelFormulario.add(tfTitulo, gbc);

        // Tema
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Tema:"), gbc);
        tfTema = new JTextField(clase.getTema(), 30);
        gbc.gridx = 1;
        panelFormulario.add(tfTema, gbc);

        // Contenido
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        panelFormulario.add(new JLabel("Contenido:"), gbc);
        taContenido = new JTextArea(clase.getContenido(), 8, 30);
        gbc.gridx = 1;
        JScrollPane spContenido = new JScrollPane(taContenido);
        panelFormulario.add(spContenido, gbc);

        // Autor
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(new JLabel("Autor:"), gbc);
        tfAutor = new JTextField(clase.getAutor(), 30);
        gbc.gridx = 1;
        panelFormulario.add(tfAutor, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardarCambios());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
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
