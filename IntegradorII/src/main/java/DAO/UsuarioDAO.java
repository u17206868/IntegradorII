package DAO;

import Entidad.Usuario;
import Entidad.Rol;
import conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Conexion conexion;

    public UsuarioDAO(Conexion conexion) {
        this.conexion = conexion;
    }

    // Método para validar las credenciales del usuario
    public Usuario validarUsuario(String correo, String contrasena) {
        Usuario usuario = null;
        String sql = "SELECT u.*, r.nombre AS rol_nombre FROM Usuarios u JOIN Roles r ON u.id_rol = r.id_rol WHERE u.correo = ? AND u.contrasena = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, correo);
            preparedStatement.setString(2, contrasena);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setTelefono(rs.getString("telefono"));
                    usuario.setIdRol(rs.getInt("id_rol"));
                    usuario.setRol(rs.getString("rol_nombre")); // Obtener nombre del rol
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    // Método para registrar un nuevo usuario
    public boolean registrarUsuario(Usuario usuario) {
        boolean registrado = false;
        String sql = "INSERT INTO Usuarios (nombre, apellido, correo, telefono, contrasena, id_rol) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            // Mensajes de depuración
            System.out.println("Registrando usuario: " + usuario.getNombre() + " " + usuario.getApellido());
            System.out.println("Correo: " + usuario.getCorreo());
            System.out.println("Teléfono: " + usuario.getTelefono());
            System.out.println("Contraseña: " + usuario.getContrasena());
            System.out.println("ID Rol: " + usuario.getIdRol());

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApellido());
            preparedStatement.setString(3, usuario.getCorreo());
            preparedStatement.setString(4, usuario.getTelefono());
            preparedStatement.setString(5, usuario.getContrasena());
            preparedStatement.setInt(6, usuario.getIdRol());

            int result = preparedStatement.executeUpdate();
            System.out.println("Resultado de la inserción: " + result);

            registrado = result > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
        return registrado;
    }

    // Método para obtener la lista de usuarios
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre AS rol_nombre FROM Usuarios u JOIN Roles r ON u.id_rol = r.id_rol";

        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(resultSet.getInt("id_usuario"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setApellido(resultSet.getString("apellido"));
                usuario.setCorreo(resultSet.getString("correo"));
                usuario.setTelefono(resultSet.getString("telefono"));
                usuario.setRol(resultSet.getString("rol_nombre")); // Obtener nombre del rol
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Método para actualizar un usuario
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE Usuarios SET nombre=?, apellido=?, correo=?, telefono=?, id_rol=? WHERE id_usuario=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApellido());
            preparedStatement.setString(3, usuario.getCorreo());
            preparedStatement.setString(4, usuario.getTelefono());
            preparedStatement.setInt(5, usuario.getIdRol());
            preparedStatement.setInt(6, usuario.getIdUsuario());

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un usuario
    public boolean eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM Usuarios WHERE id_usuario=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, idUsuario);

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener la lista de roles
    public List<Rol> obtenerRoles() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles";

        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Rol rol = new Rol();
                rol.setIdRol(resultSet.getInt("id_rol"));
                rol.setNombre(resultSet.getString("nombre"));
                roles.add(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public List<Usuario> obtenerTrabajadores() {
        List<Usuario> trabajadores = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE id_rol = 2";

        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Usuario trabajador = new Usuario();
                trabajador.setIdUsuario(resultSet.getInt("id_usuario"));
                trabajador.setNombre(resultSet.getString("nombre"));
                trabajador.setApellido(resultSet.getString("apellido"));
                trabajador.setCorreo(resultSet.getString("correo"));
                trabajador.setTelefono(resultSet.getString("telefono"));
                trabajador.setIdRol(resultSet.getInt("id_rol"));
                trabajador.setContrasena(resultSet.getString("contrasena"));
                trabajadores.add(trabajador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trabajadores;
    }
}
