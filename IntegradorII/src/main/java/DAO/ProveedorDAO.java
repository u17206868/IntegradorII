package DAO;

import Entidad.Proveedor;
import conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    // Método para agregar un proveedor
    public boolean agregarProveedor(Proveedor proveedor) {
        Conexion conexion = new Conexion();
        String sql = "INSERT INTO Proveedores (nombre, contacto, telefono, direccion) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, proveedor.getNombre());
            preparedStatement.setString(2, proveedor.getContacto());
            preparedStatement.setString(3, proveedor.getTelefono());
            preparedStatement.setString(4, proveedor.getDireccion());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener todos los proveedores
    public List<Proveedor> obtenerProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        Conexion conexion = new Conexion();
        String sql = "SELECT * FROM Proveedores";
        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setIdProveedor(resultSet.getInt("id_proveedor"));
                proveedor.setNombre(resultSet.getString("nombre"));
                proveedor.setContacto(resultSet.getString("contacto"));
                proveedor.setTelefono(resultSet.getString("telefono"));
                proveedor.setDireccion(resultSet.getString("direccion"));
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedores;
    }
    

    // Método para obtener un proveedor por su ID
    public Proveedor obtenerProveedor(int idProveedor) {
        Proveedor proveedor = null;
        Conexion conexion = new Conexion();
        String sql = "SELECT * FROM Proveedores WHERE id_proveedor = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, idProveedor);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                proveedor = new Proveedor();
                proveedor.setIdProveedor(resultSet.getInt("id_proveedor"));
                proveedor.setNombre(resultSet.getString("nombre"));
                proveedor.setContacto(resultSet.getString("contacto"));
                proveedor.setTelefono(resultSet.getString("telefono"));
                proveedor.setDireccion(resultSet.getString("direccion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedor;
    }

    // Método para actualizar un proveedor
    public boolean actualizarProveedor(Proveedor proveedor) {
        Conexion conexion = new Conexion();
        String sql = "UPDATE Proveedores SET nombre=?, contacto=?, telefono=?, direccion=? WHERE id_proveedor=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, proveedor.getNombre());
            preparedStatement.setString(2, proveedor.getContacto());
            preparedStatement.setString(3, proveedor.getTelefono());
            preparedStatement.setString(4, proveedor.getDireccion());
            preparedStatement.setInt(5, proveedor.getIdProveedor());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un proveedor
    public boolean eliminarProveedor(int idProveedor) {
        Conexion conexion = new Conexion();
        String sql = "DELETE FROM Proveedores WHERE id_proveedor=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, idProveedor);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
