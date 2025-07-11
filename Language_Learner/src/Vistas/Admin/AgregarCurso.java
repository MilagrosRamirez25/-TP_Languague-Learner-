package Vistas.Admin;

import Modelos.Curso;
import Modelos.Profesor;
import Controladores.CursoControlador;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class AgregarCurso extends JFrame {

    private CursoControlador controlador;

    private JTextField txtNombre;
    private JComboBox<String> comboProfesores;
    private JTextField txtDescripcion;
    private JTextField txtFechaInicio;
    private JTextField txtCapacidad;

    private Map<String, Integer> mapaProfesores; // "Nombre Apellido (Especialidad)" -> idProfesor

    public AgregarCurso() {
        controlador = new CursoControlador();

        setTitle("Agregar Curso");
        setSize(550, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre del Curso
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nombre del Curso:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(30);
        add(txtNombre, gbc);

        // Profesor
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Profesor:"), gbc);
        gbc.gridx = 1;
        comboProfesores = new JComboBox<>();
        mapaProfesores = new HashMap<>();

        for (Profesor p : controlador.obtenerProfesores()) {
            String nombreCompleto = p.getNombre() + " " + p.getApellido();
            String especialidad = p.getEspecialidad();
            String item = nombreCompleto + (especialidad != null && !especialidad.isEmpty() ? " (" + especialidad + ")" : "");
            comboProfesores.addItem(item);
            mapaProfesores.put(item, p.getId());
        }

        add(comboProfesores, gbc);

        // Descripción
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        txtDescripcion = new JTextField(30);
        add(txtDescripcion, gbc);

        // Fecha de inicio
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Fecha Inicio (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        txtFechaInicio = new JTextField(30);
        add(txtFechaInicio, gbc);

        // Capacidad
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Capacidad Máxima:"), gbc);
        gbc.gridx = 1;
        txtCapacidad = new JTextField(30);
        add(txtCapacidad, gbc);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        add(panelBotones, gbc);

        btnGuardar.addActionListener(e -> guardarCurso());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarCurso() {
        String nombre = txtNombre.getText().trim();
        String profesorSeleccionado = (String) comboProfesores.getSelectedItem();
        Integer idProfesor = mapaProfesores.get(profesorSeleccionado);
        String descripcion = txtDescripcion.getText().trim();
        String fechaInicio = txtFechaInicio.getText().trim();
        String capacidadStr = txtCapacidad.getText().trim();

        if (nombre.isEmpty() || descripcion.isEmpty() || fechaInicio.isEmpty() || capacidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        try {
            LocalDate.parse(fechaInicio); // valida formato YYYY-MM-DD
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Fecha inválida. Use formato YYYY-MM-DD.");
            return;
        }

        int capacidad;
        try {
            capacidad = Integer.parseInt(capacidadStr);
            if (capacidad <= 0) {
                JOptionPane.showMessageDialog(this, "Capacidad debe ser mayor a 0.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Capacidad inválida.");
            return;
        }

        Curso nuevoCurso = new Curso(nombre, descripcion, fechaInicio, idProfesor, capacidad, 0);

        controlador.agregarCurso(nuevoCurso);

        JOptionPane.showMessageDialog(this, "Curso agregado exitosamente.");
        dispose();
    }
}
