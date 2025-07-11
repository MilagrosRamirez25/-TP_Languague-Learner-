package Vistas.Admin;

import Modelos.Curso;
import Modelos.Profesor;
import Controladores.CursoControlador;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EditarCurso extends JFrame {

    private CursoControlador controlador;
    private int idCurso;

    private JTextField txtNombre;
    private JComboBox<String> comboProfesores;
    private JTextField txtDescripcion;
    private JTextField txtFechaInicio;
    private JTextField txtCapacidad;
    private JTextField txtAlumnosInscritos;

    private Map<String, Integer> mapaProfesores;

    public EditarCurso(int idCurso) {
        this.idCurso = idCurso;
        this.controlador = new CursoControlador();

        setTitle("Editar Curso");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        mapaProfesores = new HashMap<>();

        // Nombre del curso
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
        add(comboProfesores, gbc);

        // Descripción
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        txtDescripcion = new JTextField(30);
        add(txtDescripcion, gbc);

        // Fecha inicio
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Fecha Inicio (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        txtFechaInicio = new JTextField(30);
        add(txtFechaInicio, gbc);

        // Capacidad máxima
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Capacidad Máxima:"), gbc);
        gbc.gridx = 1;
        txtCapacidad = new JTextField(30);
        add(txtCapacidad, gbc);

        // Alumnos inscritos
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Alumnos Inscritos (no editable):"), gbc);
        gbc.gridx = 1;
        txtAlumnosInscritos = new JTextField(30);
        txtAlumnosInscritos.setEditable(false);
        add(txtAlumnosInscritos, gbc);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar Cambios");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        add(panelBotones, gbc);

        btnGuardar.addActionListener(e -> guardarCambios());
        btnCancelar.addActionListener(e -> dispose());

        cargarProfesoresEnCombo();
        cargarDatosCurso();
    }

    private void cargarProfesoresEnCombo() {
        comboProfesores.removeAllItems();
        mapaProfesores.clear();

        for (Profesor p : controlador.obtenerProfesores()) {
            String nombreCompleto = p.getNombre() + " " + p.getApellido();
            String especialidad = p.getEspecialidad();
            String item = nombreCompleto + (especialidad != null && !especialidad.isEmpty() ? " (" + especialidad + ")" : "");
            comboProfesores.addItem(item);
            mapaProfesores.put(item, p.getId());
        }
    }

    private void cargarDatosCurso() {
        Curso curso = controlador.obtenerCursoPorId(idCurso);
        if (curso == null) {
            JOptionPane.showMessageDialog(this, "Curso no encontrado.");
            dispose();
            return;
        }

        txtNombre.setText(curso.getNombreCurso());

        String nombreCompleto = controlador.obtenerNombreCompletoProfesorPorId(curso.getIdProfesor());
        String especialidad = controlador.obtenerEspecialidadProfesorPorId(curso.getIdProfesor());
        String item = nombreCompleto + (especialidad != null && !especialidad.isEmpty() ? " (" + especialidad + ")" : "");

        comboProfesores.setSelectedItem(item);

        txtDescripcion.setText(curso.getDescripcion());
        txtFechaInicio.setText(curso.getFechaInicio());
        txtCapacidad.setText(String.valueOf(curso.getCapacidadMaxima()));
        txtAlumnosInscritos.setText(String.valueOf(curso.getCantidadAlumnosInscritos()));
    }

    private void guardarCambios() {
        String nombre = txtNombre.getText().trim();
        String seleccion = (String) comboProfesores.getSelectedItem();
        Integer idProfesor = mapaProfesores.get(seleccion);
        String descripcion = txtDescripcion.getText().trim();
        String fechaInicio = txtFechaInicio.getText().trim();
        String capacidadStr = txtCapacidad.getText().trim();

        if (nombre.isEmpty() || descripcion.isEmpty() || fechaInicio.isEmpty() || capacidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        int capacidad;
        try {
            capacidad = Integer.parseInt(capacidadStr);
            if (capacidad < 0) {
                JOptionPane.showMessageDialog(this, "La capacidad no puede ser negativa.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Capacidad inválida.");
            return;
        }

        Curso cursoEditado = new Curso(nombre, descripcion, fechaInicio, idProfesor, capacidad, 0);
        cursoEditado.setId(idCurso);

        // Conservar cantidad de alumnos actual
        Curso original = controlador.obtenerCursoPorId(idCurso);
        if (original != null) {
            cursoEditado.setCantidadAlumnosInscritos(original.getCantidadAlumnosInscritos());
        }

        controlador.actualizarCurso(cursoEditado);
        JOptionPane.showMessageDialog(this, "Curso actualizado exitosamente.");
        dispose();
    }
}
