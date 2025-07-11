package Interfaces;

import java.util.List;

import Modelos.Administrador;
import Modelos.Usuario;

public interface AdministradorRepository {

	   List<Administrador> getAllUsers(); // llama a todos los usuarios de la bdd
	    
	    Administrador getUserById(int id); //llama solo a uno, por su id
	    
	    void addUser(Administrador user); //añade usuarios a la bdd
	    
	    void updateUser(Administrador user); //actualiza los usuarios de la bdd
	    
	    void deleteUser(int id); //eliminar usuarios de la bdd
	}

