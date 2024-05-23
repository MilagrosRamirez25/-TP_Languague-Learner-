import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		int opcion;
		do {
			String[] menu = { "1-Profesor", "2-Alumno", "3-Administrador", "5-Salir" };
			opcion = JOptionPane.showOptionDialog(null, "Elija la opcion que corresponda", "Elija una opcion", 0, 0,
					null, menu, menu[0]);

			switch (opcion) {
			case 0:
				do {
					String[] menuProf = { "1-Curso", "2-Clase", "3-Examen", "4-Ejercicios", "5-Alumnos Inscriptos",
							"6-volver" };
					switch (opcion) {
					case 0:

						break;
					case 1:

						break;
					case 2:

						break;
					case 3:

						break;
					case 4:

						break;
					case 5:

						break;

					default:
						break;
					}
				} while (opcion !=5);

				break;
			case 1:

				break;
			case 2:

				break;
			case 3:

				break;

			default:
				JOptionPane.showMessageDialog(null, "Opcion no valida, intente de nuevo");
				break;
			}

		} while (opcion != 3);

	}
}
