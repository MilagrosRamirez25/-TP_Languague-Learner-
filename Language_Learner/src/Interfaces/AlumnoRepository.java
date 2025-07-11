package Interfaces;

import java.util.List;

import Modelos.Alumno;
import Modelos.Usuario;

public interface AlumnoRepository {

	   List< Alumno> getAllUsers(); // llama a todos los usuarios de la bdd
	    
	 Alumno getUserById(int id); //llama solo a uno, por su id
	    
	    void addUser( Alumno user); //añade usuarios a la bdd
	    
	    void updateUser( Alumno user); //actualiza los usuarios de la bdd
	    
	    void deleteUser(int id); //eliminar usuarios de la bdd
	}
