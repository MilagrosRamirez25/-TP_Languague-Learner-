package Interfaces;

import java.util.List;

import Modelos.Profesor;
import Modelos.Usuario;

public interface ProfesorRepository {
	   List<Profesor> getAllUsers(); // llama a todos los usuarios de la bdd
	    
	   Profesor getUserById(int id); //llama solo a uno, por su id
	    
	    void addUser(Profesor user); //añade usuarios a la bdd
	    
	    void updateUser(Profesor user); //actualiza los usuarios de la bdd
	    
	    void deleteUser(int id); //eliminar usuarios de la bdd
	}
