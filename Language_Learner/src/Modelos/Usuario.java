package Modelos;

import Controladores.UsuarioControlador;

public class Usuario {
    public static final int ROL_ADMIN = 0;
    public static final int ROL_PROFESOR = 1;
    public static final int ROL_ALUMNO = 2;

    private int id;
    private String usuario;  
    private String email;
    private String pass;
    private int rol;


    public Usuario(int id, String usuario, String email, String pass, int rol) {
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.pass = pass;
        this.rol = rol;
    }


    public Usuario(int id, String usuario, String email, int rol) {
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.rol = rol;
    }


    public Usuario(String usuario, String email, String pass, int rol) {
        this.usuario = usuario;
        this.email = email;
        this.pass = pass;
        this.rol = rol;
    }

    public Usuario() {}

    // Getters y setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getRol() {
        return rol;
    }
    public void setRol(int rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", usuario=" + usuario + ", email=" + email + ", rol=" + rol + "]";
    }

    // Método para iniciar sesión usando usuario o email + contraseña
    public static String iniciarSesion(String identificador, String pass) {
        UsuarioControlador controlador = new UsuarioControlador();

        if (controlador.getAllUsers().isEmpty()) {
            return "No hay usuarios registrados";
        }

        for (Usuario u : controlador.getAllUsers()) {
            if ((u.getUsuario().equals(identificador) || u.getEmail().equals(identificador)) &&
                u.getPass().equals(pass)) {
                return "rol:" + u.getRol();
            }
        }
        return "Usuario o contraseña incorrectos";
    }

    // Método para registrarse validando nombre, mail, pass y si ya existe usuario o mail
    public static String registrarse(String usuario, String mail, String pass) {
        UsuarioControlador controlador = new UsuarioControlador();

        if (usuario == null || usuario.length() <= 3) 
            return "Ingrese un nombre de usuario válido (mínimo 4 caracteres)";
        if (mail == null || mail.length() <= 3) 
            return "Ingrese un mail válido (mínimo 4 caracteres)";
        if (pass == null || pass.length() <= 3) 
            return "Ingrese una contraseña válida (mínimo 4 caracteres)";

        for (Usuario u : controlador.getAllUsers()) {
            if (u.getUsuario().equals(usuario) || u.getEmail().equals(mail)) {
                return "Usuario o mail ya existente";
            }
        }

        controlador.addUser(new Usuario(usuario, mail, pass, ROL_ALUMNO)); // Por defecto rol alumno
        return "Ok";
    }
}
