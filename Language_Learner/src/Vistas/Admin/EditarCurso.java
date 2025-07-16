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

    private JTextField txtNombre, txtDescripcion, txtFechaInicio, txtCapacidad, txtAlumnosInscritos;
    private JComboBox<String> comboProfesores;
    private Map<String, Integer> mapaProfesores;

    public EditarCurso(int idCurso) {
        this.idCurso = idCurso;
        this.controlador = new CursoControlador();
        this.mapaProfesores = new HashMap<>();

        setTitle("Editar Curso");
        setSize(550, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.decode("#F2EEAC"));
        setContentPane(contentPane);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(-17, 10, 219, 73);
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image imagenEscalada = icono.getImage().getScaledInstance(250, 140, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imagenEscalada));
        contentPane.add(lblLogo);

        JLabel lblTitulo = new JLabel("Editar Curso");
        lblTitulo.setFont(new Font("Eras Bold ITC", Font.BOLD, 23));
        lblTitulo.setForeground(new Color(0, 83, 166));
        lblTitulo.setBounds(160, 93, 250, 30);
        contentPane.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre del Curso:");
        lblNombre.setBounds(30, 170, 150, 25);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(190, 170, 300, 25);
        contentPane.add(txtNombre);

        JLabel lblProfesor = new JLabel("Profesor:");
        lblProfesor.setBounds(30, 210, 150, 25);
        contentPane.add(lblProfesor);

        comboProfesores = new JComboBox<>();
        comboProfesores.setBounds(190, 210, 300, 25);
        contentPane.add(comboProfesores);

        // Descripción
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(30, 250, 150, 25);
        contentPane.add(lblDescripcion);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(190, 250, 300, 25);
        contentPane.add(txtDescripcion);

        // Fecha de inicio
        JLabel lblFecha = new JLabel("Fecha Inicio (YYYY-MM-DD):");
        lblFecha.setBounds(30, 290, 200, 25);
        contentPane.add(lblFecha);

        txtFechaInicio = new JTextField();
        txtFechaInicio.setBounds(230, 290, 260, 25);
        contentPane.add(txtFechaInicio);

        // Capacidad
        JLabel lblCapacidad = new JLabel("Capacidad Máxima:");
        lblCapacidad.setBounds(30, 330, 150, 25);
        contentPane.add(lblCapacidad);

        txtCapacidad = new JTextField();
        txtCapacidad.setBounds(190, 330, 300, 25);
        contentPane.add(txtCapacidad);

        // Alumnos Inscritos
        JLabel lblInscritos = new JLabel("Alumnos Inscritos:");
        lblInscritos.setBounds(30, 370, 150, 25);
        contentPane.add(lblInscritos);

        txtAlumnosInscritos = new JTextField();
        txtAlumnosInscritos.setBounds(190, 370, 300, 25);
        txtAlumnosInscritos.setEditable(false);
        contentPane.add(txtAlumnosInscritos);

        // Botones
        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBackground(new Color(255, 255, 0));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnGuardar.setBounds(150, 420, 170, 35);
        contentPane.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(330, 420, 120, 35);
        contentPane.add(btnCancelar);

        // Eventos
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

        Curso original = controlador.obtenerCursoPorId(idCurso);
        if (original != null) {
            cursoEditado.setCantidadAlumnosInscritos(original.getCantidadAlumnosInscritos());
        }

        controlador.actualizarCurso(cursoEditado);
        JOptionPane.showMessageDialog(this, "Curso actualizado exitosamente.");
        dispose();
    }
}
