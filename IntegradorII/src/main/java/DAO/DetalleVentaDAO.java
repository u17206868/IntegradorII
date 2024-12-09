package DAO;
import Entidad.DetalleVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDAO {
    private Connection connection;

    public DetalleVentaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<DetalleVenta> obtenerDetalleVentas(int idVenta) throws SQLException {
        List<DetalleVenta> detallesVentas = new ArrayList<>();
        String query = "SELECT * FROM detallesventas WHERE id_venta = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, idVenta);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setIdDetalle(rs.getInt("id_detalle"));
            detalleVenta.setIdVenta(rs.getInt("id_venta"));
            detalleVenta.setIdProducto(rs.getInt("id_producto"));
            detalleVenta.setProducto(rs.getString("producto"));
            detalleVenta.setCantidad(rs.getInt("cantidad"));
            detalleVenta.setTiempo(rs.getInt("tiempo"));
            detalleVenta.setPrecio(rs.getDouble("precio"));
            detalleVenta.setEstado(rs.getString("estado")); // Nuevo campo
            detallesVentas.add(detalleVenta);
        }
        return detallesVentas;
    }

    public List<DetalleVenta> obtenerDetallesPendientes() throws SQLException {
        List<DetalleVenta> detallesVentas = new ArrayList<>();
        String query = "SELECT * FROM detallesventas WHERE estado = 'Pendiente'";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setIdDetalle(rs.getInt("id_detalle"));
                detalleVenta.setIdVenta(rs.getInt("id_venta"));
                detalleVenta.setIdProducto(rs.getInt("id_producto"));
                detalleVenta.setProducto(rs.getString("producto"));
                detalleVenta.setCantidad(rs.getInt("cantidad"));
                detalleVenta.setTiempo(rs.getInt("tiempo"));
                detalleVenta.setPrecio(rs.getDouble("precio"));
                detalleVenta.setEstado(rs.getString("estado"));
                detallesVentas.add(detalleVenta);
            }
        }
        return detallesVentas;
    }

    public void crearDetalleVenta(DetalleVenta detalle) throws SQLException {
        String query = "INSERT INTO detallesventas (id_venta, id_producto, producto, cantidad, tiempo, precio, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, detalle.getIdVenta());
            ps.setInt(2, detalle.getIdProducto());
            ps.setString(3, detalle.getProducto());
            ps.setInt(4, detalle.getCantidad());
            ps.setInt(5, detalle.getTiempo());
            ps.setDouble(6, detalle.getPrecio());
            ps.setString(7, detalle.getEstado()); // Nuevo campo
            ps.executeUpdate();
        }
    }

    public void modificarDetalleVenta(DetalleVenta detalle) throws SQLException {
        String query = "UPDATE detallesventas SET id_producto = ?, producto = ?, cantidad = ?, tiempo = ?, precio = ?, estado = ? WHERE id_detalle = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, detalle.getIdProducto());
            ps.setString(2, detalle.getProducto());
            ps.setInt(3, detalle.getCantidad());
            ps.setInt(4, detalle.getTiempo());
            ps.setDouble(5, detalle.getPrecio());
            ps.setString(6, detalle.getEstado()); // Nuevo campo
            ps.setInt(7, detalle.getIdDetalle());
            ps.executeUpdate();
        }
    }

    public void eliminarDetalleVenta(int idDetalle) throws SQLException {
        String query = "DELETE FROM detallesventas WHERE id_detalle = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idDetalle);
            ps.executeUpdate();
        }
    }

    public void actualizarEstadoDetalleVenta(int idDetalle, String estado) throws SQLException {
        String sql = "UPDATE detallesventas SET estado = ? WHERE id_detalle = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idDetalle);
            ps.executeUpdate();
        }
    }

    public void actualizarEstadoVenta(int idVenta, String estado) throws SQLException {
        String sql = "UPDATE ventas SET estado = ? WHERE id_venta = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, idVenta);
            ps.executeUpdate();
        }
    }

    public int obtenerIdVentaPorDetalle(int idDetalle) throws SQLException {
        String sql = "SELECT id_venta FROM detallesventas WHERE id_detalle = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idDetalle);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_venta");
            } else {
                throw new SQLException("No se encontró idVenta para el idDetalle: " + idDetalle);
            }
        }
        
    }
    // Nuevo método para sincronizar el estado de todos los detalles de una venta
    public void sincronizarEstadosConVenta(int idVenta, String estadoVenta) throws SQLException {
        String estadoDetalle = "Pendiente".equals(estadoVenta) ? "Pendiente" : "Terminado";
        String sql = "UPDATE detallesventas SET estado = ? WHERE id_venta = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estadoDetalle);
            ps.setInt(2, idVenta);
            ps.executeUpdate();
        }
    }
}

