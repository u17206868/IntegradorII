package DAO;

import Entidad.ProveedorMateriaPrima;
import Entidad.ProveedorMateriaPrimaDTO;
import conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorMateriaPrimaDAO {
    private Connection connection;

    public ProveedorMateriaPrimaDAO(Connection connection) {
        this.connection = connection;
    }
    public void addProveedorMateriaPrima(ProveedorMateriaPrima proveedorMateriaPrima) throws SQLException {
        String query = "INSERT INTO proveedores_materiasprimas (id_proveedor, id_materia_prima, precio, tiempo_entrega) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, proveedorMateriaPrima.getIdProveedor());
            ps.setInt(2, proveedorMateriaPrima.getIdMateriaPrima());
            ps.setDouble(3, proveedorMateriaPrima.getPrecio());
            ps.setInt(4, proveedorMateriaPrima.getTiempoEntrega());
            ps.executeUpdate();
        }
    }

    public void deleteProveedorMateriaPrima(int id) throws SQLException {
        String query = "DELETE FROM proveedores_materiasprimas WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void updateProveedorMateriaPrima(ProveedorMateriaPrima proveedorMateriaPrima) throws SQLException {
        String query = "UPDATE proveedores_materiasprimas SET id_proveedor = ?, id_materia_prima = ?, precio = ?, tiempo_entrega = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, proveedorMateriaPrima.getIdProveedor());
            ps.setInt(2, proveedorMateriaPrima.getIdMateriaPrima());
            ps.setDouble(3, proveedorMateriaPrima.getPrecio());
            ps.setInt(4, proveedorMateriaPrima.getTiempoEntrega());
            ps.setInt(5, proveedorMateriaPrima.getId());
            ps.executeUpdate();
        }
    }

    public List<ProveedorMateriaPrimaDTO> getAllProveedoresMateriasPrimas() throws SQLException {
        String query = "SELECT proveedores.nombre AS NombreProveedor, " +
               "materiasprimas.nombre AS NombreMateriaPrima, " +
               "proveedores_materiasprimas.precio, " +
               "proveedores_materiasprimas.tiempo_entrega " +
               "FROM proveedores_materiasprimas " +
               "JOIN proveedores ON proveedores.idProveedor = proveedores_materiasprimas.idproveedor " +
               "JOIN materiasprimas ON materiasprimas.idMateriaPrima = proveedores_materiasprimas.id_materia_prima";

        List<ProveedorMateriaPrimaDTO> proveedoresMateriasPrimas = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ProveedorMateriaPrimaDTO dto = new ProveedorMateriaPrimaDTO();
                dto.setId(rs.getInt("id"));
                dto.setNombreProveedor(rs.getString("proveedor_nombre"));
                dto.setNombreMateriaPrima(rs.getString("materia_prima_nombre"));
                dto.setPrecio(rs.getDouble("precio"));
                dto.setTiempoEntrega(rs.getInt("tiempo_entrega"));
                proveedoresMateriasPrimas.add(dto);
            }
        }
        return proveedoresMateriasPrimas;
    }
}
