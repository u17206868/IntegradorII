package DAO;

import Entidad.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {
    private Connection connection;

    public VentaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Venta> obtenerVentas() throws SQLException {
        List<Venta> ventas = new ArrayList<>();
        String query = "SELECT * FROM ventas";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            Venta venta = new Venta();
            venta.setIdVenta(rs.getInt("id_venta"));
            venta.setIdCliente(rs.getInt("id_cliente"));
            venta.setDniCliente(rs.getString("dni_cliente"));
            venta.setFechaVenta(rs.getString("fecha_venta"));
            venta.setTotal(rs.getDouble("total"));
            venta.setSubtotal(rs.getDouble("subtotal"));
            venta.setIgv(rs.getDouble("igv"));
            venta.setFechaRegistro(rs.getDate("fecha_registro"));
            venta.setEstado(rs.getString("estado")); // Nuevo campo
            ventas.add(venta);
        }
        return ventas;
    }

    public Venta getVenta(int idVenta) throws SQLException {
        Venta venta = null;
        String query = "SELECT * FROM ventas WHERE id_venta = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, idVenta);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            venta = new Venta();
            venta.setIdVenta(rs.getInt("id_venta"));
            venta.setIdCliente(rs.getInt("id_cliente"));
            venta.setDniCliente(rs.getString("dni_cliente"));
            venta.setFechaVenta(rs.getString("fecha_venta"));
            venta.setTotal(rs.getDouble("total"));
            venta.setSubtotal(rs.getDouble("subtotal"));
            venta.setIgv(rs.getDouble("igv"));
            venta.setFechaRegistro(rs.getDate("fecha_registro"));
            venta.setEstado(rs.getString("estado")); // Nuevo campo
        }
        return venta;
    }

    public void crearVenta(Venta venta) throws SQLException {
        String query = "INSERT INTO ventas (id_cliente, dni_cliente, fecha_venta, total, subtotal, igv, fecha_registro, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, venta.getIdCliente());
        pstmt.setString(2, venta.getDniCliente());
        pstmt.setString(3, venta.getFechaVenta());
        pstmt.setDouble(4, venta.getTotal());
        pstmt.setDouble(5, venta.getSubtotal());
        pstmt.setDouble(6, venta.getIgv());
        pstmt.setDate(7, new java.sql.Date(venta.getFechaRegistro().getTime()));
        pstmt.setString(8, venta.getEstado()); // Nuevo campo
        pstmt.executeUpdate();

        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            venta.setIdVenta(rs.getInt(1));
        }
    }

    public void modificarVenta(Venta venta) throws SQLException {
        String query = "UPDATE ventas SET id_cliente = ?, dni_cliente = ?, fecha_venta = ?, total = ?, subtotal = ?, igv = ?, fecha_registro = ?, estado = ? WHERE id_venta = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, venta.getIdCliente());
        pstmt.setString(2, venta.getDniCliente());
        pstmt.setString(3, venta.getFechaVenta());
        pstmt.setDouble(4, venta.getTotal());
        pstmt.setDouble(5, venta.getSubtotal());
        pstmt.setDouble(6, venta.getIgv());
        pstmt.setDate(7, new java.sql.Date(venta.getFechaRegistro().getTime()));
        pstmt.setString(8, venta.getEstado()); // Nuevo campo
        pstmt.setInt(9, venta.getIdVenta());
        pstmt.executeUpdate();
    }

    public void actualizarEstadoVenta(int idVenta, String estado) throws SQLException {
        String sql = "UPDATE ventas SET estado = ? WHERE id_venta = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idVenta);
            ps.executeUpdate();
        }
    }
    
}
