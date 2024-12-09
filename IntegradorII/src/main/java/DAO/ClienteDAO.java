package DAO;

import Entidad.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para registrar un nuevo cliente
    public boolean registrarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, apellido, correo, telefono, direccion, dni) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getApellido());
            preparedStatement.setString(3, cliente.getCorreo());
            preparedStatement.setString(4, cliente.getTelefono());
            preparedStatement.setString(5, cliente.getDireccion());
            preparedStatement.setString(6, cliente.getDni());

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar un cliente
    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre=?, apellido=?, correo=?, telefono=?, direccion=?, dni=? WHERE id_cliente=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getApellido());
            preparedStatement.setString(3, cliente.getCorreo());
            preparedStatement.setString(4, cliente.getTelefono());
            preparedStatement.setString(5, cliente.getDireccion());
            preparedStatement.setString(6, cliente.getDni());
            preparedStatement.setInt(7, cliente.getIdCliente());

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un cliente
    public boolean eliminarCliente(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idCliente);

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener la lista de clientes
   public List<Cliente> obtenerClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM clientes";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(rs.getInt("id_cliente"));
            cliente.setNombre(rs.getString("nombre"));
            cliente.setApellido(rs.getString("apellido"));
            cliente.setDni(rs.getString("dni"));
            cliente.setCorreo(rs.getString("correo"));
            cliente.setTelefono(rs.getString("telefono"));
            cliente.setDireccion(rs.getString("direccion"));
            clientes.add(cliente);
        }
        return clientes;
    }
   public Cliente getCliente(int idCliente) throws SQLException {
    Cliente cliente = null;
    String query = "SELECT * FROM clientes WHERE id_cliente = ?";
    PreparedStatement pstmt = connection.prepareStatement(query);
    pstmt.setInt(1, idCliente);
    ResultSet rs = pstmt.executeQuery();

    if (rs.next()) {
        cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("id_cliente"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellido(rs.getString("apellido"));
        cliente.setDni(rs.getString("dni"));
        cliente.setCorreo(rs.getString("correo"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setDireccion(rs.getString("direccion"));
    }
    return cliente;
}

}
