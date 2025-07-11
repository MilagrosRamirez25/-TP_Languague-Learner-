package Modelos;

import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;

import Controladores.UsuarioControlador;
import Modelos.Usuario;

public class Main {
	public static void main(String[] args) {
			
		UsuarioControlador controlador = new UsuarioControlador();
		
		
		String[] menu = { 
				"Agregar usuario","Ver usuarios","Buscar usuario por id","Editar usuario","Eliminar","Salir"
		};
		int opcionMenu = 0;
		do {
			
		opcionMenu = JOptionPane.showOptionDialog(null, "Elija una opcion", null, 0, 0, null, menu, menu[0]);
		
		
		switch (opcionMenu) {
		case 0:
			String nombre = JOptionPane.showInputDialog("Ingreso el nombre ");
			String mail = JOptionPane.showInputDialog("Ingreso el mail ");
			controlador.addUser(new Usuario(opcionMenu, nombre,mail, opcionMenu));
			
			break;
		case 1:
			
			JOptionPane.showMessageDialog(null, controlador.getAllUsers());
			break;
		case 2:
				JOptionPane.showMessageDialog(null, Seleccionarusuario(controlador));
			break;
		case 3:
		  Usuario nuevo = Seleccionarusuario(controlador);
		  String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nombre a cambiar por: " + nuevo.getNombre());
		  String nuevoEmail = JOptionPane.showInputDialog("Ingrese el email a cambiar por: " + nuevo.getEmail());
		  nuevo.setEmail(nuevoEmail);
		  nuevo.setNombre(nuevoNombre);
		  controlador.updateUser(nuevo);
		break;
		case 4: 
			 Usuario otro = Seleccionarusuario(controlador);
			controlador.deleteUser(otro.getId());
			break;
		case 5: 
			 JOptionPane.showMessageDialog(null, "Finalizar");
			break;
		default:
			break;
		}
		
		
		} while (opcionMenu!=5);
	
	}
	public static Usuario Seleccionarusuario(UsuarioControlador controlador ) {
		String[] lista = new String[controlador.getAllUsers().size()];
		
		for (int i = 0; i < lista.length; i++) {
			lista[i] = Integer.toString( controlador.getAllUsers().get(i).getId());
		}
		String elegido = (String)JOptionPane.showInputDialog(null, "Elija un usuario", null, 0, null, lista, lista[0]);
		
		Usuario seleccionado =  controlador.getUserById(Integer.parseInt(elegido));
		 return seleccionado;
	}
}