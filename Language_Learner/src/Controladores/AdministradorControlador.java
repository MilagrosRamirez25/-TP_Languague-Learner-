package Controladores;

import Modelos.Administrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Interfaces.AdministradorRepository;

public class AdministradorControlador implements AdministradorRepository {
    private final Connection connection;

    public AdministradorControlador() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Administrador> getAllUsers() {
        List<Administrador> admins = new LinkedList<>();
        String sql = "SELECT * FROM usuario WHERE rol = 0";  
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Administrador admin = new Administrador(
                    rs.getInt("id"),
                    rs.getString("usuario"),
                    rs.getString("email"),
                    rs.getString("pass"),
                    rs.getInt("rol"),
                    0  // idCurso, o poner un valor default o eliminar si no aplica
                );
                admins.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    @Override
    public Administrador getUserById(int id) {
        Administrador admin = null;
        String sql = "SELECT * FROM usuario WHERE id = ? AND rol = 0";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    admin = new Administrador(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("email"),
                        rs.getString("pass"),
                        rs.getInt("rol"),
                        0
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    @Override
    public void addUser(Administrador admin) {
        String sql = "INSERT INTO usuario (usuario, email, pass, rol) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, admin.getUsuario());
            ps.setString(2, admin.getEmail());
            ps.setString(3, admin.getPass());
            ps.setInt(4, 1); // rol administrador fijo
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(Administrador admin) {
        String sql = "UPDATE usuario SET usuario = ?, email = ?, pass = ? WHERE id = ? AND rol = 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, admin.getUsuario());
            ps.setString(2, admin.getEmail());
            ps.setString(3, admin.getPass());
            ps.setInt(4, admin.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM usuario WHERE id = ? AND rol = 0";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
