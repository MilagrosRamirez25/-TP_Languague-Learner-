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

    private Map<String, Integer> mapaProfesores;

    public AgregarCurso() {
        controlador = new CursoControlador();

        setTitle("Agregar Curso");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        JLabel lblImagen = new JLabel();
        lblImagen.setBounds(0, 0, 233, 84);
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/logo.png"));
        java.awt.Image scaledImage = originalIcon.getImage().getScaledInstance(250, 140, java.awt.Image.SCALE_SMOOTH);
        lblImagen.setIcon(new ImageIcon(scaledImage));
        contentPane.add(lblImagen);


        JLabel lblTitulo = new JLabel("Agregar Curso");
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 23));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(227, 23, 231, 40);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre del Curso:");
        lblNombre.setBounds(30, 80, 150, 25);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(190, 80, 300, 25);
        contentPane.add(txtNombre);

        // Profesor
        JLabel lblProfesor = new JLabel("Profesor:");
        lblProfesor.setBounds(30, 120, 150, 25);
        contentPane.add(lblProfesor);

        comboProfesores = new JComboBox<>();
        comboProfesores.setBounds(190, 120, 300, 25);
        contentPane.add(comboProfesores);

        mapaProfesores = new HashMap<>();
        for (Profesor p : controlador.obtenerProfesores()) {
            String nombreCompleto = p.getNombre() + " " + p.getApellido();
            String especialidad = p.getEspecialidad();
            String item = nombreCompleto + (especialidad != null && !especialidad.isEmpty() ? " (" + especialidad + ")" : "");
            comboProfesores.addItem(item);
            mapaProfesores.put(item, p.getId());
        }

        // Descripción
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(30, 160, 150, 25);
        contentPane.add(lblDescripcion);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(190, 160, 300, 25);
        contentPane.add(txtDescripcion);

        // Fecha Inicio
        JLabel lblFechaInicio = new JLabel("Fecha Inicio (YYYY-MM-DD):");
        lblFechaInicio.setBounds(30, 200, 170, 25);
        contentPane.add(lblFechaInicio);

        txtFechaInicio = new JTextField();
        txtFechaInicio.setBounds(190, 200, 300, 25);
        contentPane.add(txtFechaInicio);

        // Capacidad
        JLabel lblCapacidad = new JLabel("Capacidad Máxima:");
        lblCapacidad.setBounds(30, 240, 150, 25);
        contentPane.add(lblCapacidad);

        txtCapacidad = new JTextField();
        txtCapacidad.setBounds(190, 240, 300, 25);
        contentPane.add(txtCapacidad);

        // Botones
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(150, 300, 100, 35);
        contentPane.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(300, 300, 100, 35);
        contentPane.add(btnCancelar);

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
