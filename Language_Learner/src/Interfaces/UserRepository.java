package Interfaces;

import java.util.List;

import Modelos.Usuario;

public interface UserRepository {
	//prototipos de metodos 
    List<Usuario> getAllUsers(); // llama a todos los usuarios de la bdd
    
    Usuario getUserById(int id); //llama solo a uno, por su id
    
    boolean addUser(Usuario user); //añade usuarios a la bdd
    
    boolean updateUser(Usuario user); //actualiza los usuarios de la bdd
    
    boolean deleteUser(int id); //eliminar usuarios de la bdd
}
