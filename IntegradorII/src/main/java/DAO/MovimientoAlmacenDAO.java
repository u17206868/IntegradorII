package DAO;

import Entidad.MovimientoAlmacen;
import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientoAlmacenDAO {
    private Conexion conexion;

    public MovimientoAlmacenDAO(Conexion conexion) {
        this.conexion = conexion;
    }

    public void insertarMovimiento(MovimientoAlmacen movimiento) throws SQLException {
        String sql = "INSERT INTO movimientos_almacen (id_producto, cantidad, fecha_movimiento, id_almacen, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, movimiento.getIdProducto());
            ps.setDouble(2, movimiento.getCantidad());
            ps.setString(3, movimiento.getFechaMovimiento());
            ps.setInt(4, movimiento.getIdAlmacen());
            ps.setString(5, movimiento.getEstado());
            ps.executeUpdate();
        }
    }

    public List<MovimientoAlmacen> listarMovimientos() throws SQLException {
        List<MovimientoAlmacen> movimientos = new ArrayList<>();
        String sql = "SELECT * FROM movimientos_almacen";
        try (Connection conn = conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                MovimientoAlmacen movimiento = new MovimientoAlmacen();
                movimiento.setIdMovimiento(rs.getInt("id_movimiento"));
                movimiento.setIdProducto(rs.getInt("id_producto"));
                movimiento.setCantidad(rs.getDouble("cantidad"));
                movimiento.setFechaMovimiento(rs.getString("fecha_movimiento"));
                movimiento.setIdAlmacen(rs.getInt("id_almacen"));
                movimiento.setEstado(rs.getString("estado"));
                movimientos.add(movimiento);
            }
        }
        return movimientos;
    }

    public MovimientoAlmacen obtenerMovimientoPorId(int idMovimiento) throws SQLException {
        MovimientoAlmacen movimiento = null;
        String sql = "SELECT * FROM movimientos_almacen WHERE id_movimiento = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMovimiento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    movimiento = new MovimientoAlmacen();
                    movimiento.setIdMovimiento(rs.getInt("id_movimiento"));
                    movimiento.setIdProducto(rs.getInt("id_producto"));
                    movimiento.setCantidad(rs.getDouble("cantidad"));
                    movimiento.setFechaMovimiento(rs.getString("fecha_movimiento"));
                    movimiento.setIdAlmacen(rs.getInt("id_almacen"));
                    movimiento.setEstado(rs.getString("estado"));
                }
            }
        }
        return movimiento;
    }

    public void modificarMovimiento(MovimientoAlmacen movimiento) throws SQLException {
        String sql = "UPDATE movimientos_almacen SET id_producto = ?, cantidad = ?, fecha_movimiento = ?, id_almacen = ?, estado = ? WHERE id_movimiento = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, movimiento.getIdProducto());
            ps.setDouble(2, movimiento.getCantidad());
            ps.setString(3, movimiento.getFechaMovimiento());
            ps.setInt(4, movimiento.getIdAlmacen());
            ps.setString(5, movimiento.getEstado());
            ps.setInt(6, movimiento.getIdMovimiento());
            ps.executeUpdate();
        }
    }

    public void eliminarMovimiento(int idMovimiento) throws SQLException {
        String sql = "DELETE FROM movimientos_almacen WHERE id_movimiento = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMovimiento);
            ps.executeUpdate();
        }
    }

    public List<MovimientoAlmacen> obtenerMovimientosPorEstado(String estado) throws SQLException {
        List<MovimientoAlmacen> movimientosAlmacen = new ArrayList<>();
        String query = "SELECT * FROM movimientos_almacen WHERE estado = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, estado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MovimientoAlmacen movimiento = new MovimientoAlmacen();
                    movimiento.setIdMovimiento(rs.getInt("id_movimiento"));
                    movimiento.setIdProducto(rs.getInt("id_producto"));
                    movimiento.setCantidad(rs.getDouble("cantidad"));
                    movimiento.setFechaMovimiento(rs.getString("fecha_movimiento"));
                    movimiento.setIdAlmacen(rs.getInt("id_almacen"));
                    movimiento.setEstado(rs.getString("estado"));
                    movimientosAlmacen.add(movimiento);
                }
            }
        }
        return movimientosAlmacen;
    }

    public void cambiarEstadoMovimiento(int idMovimiento, String nuevoEstado) throws SQLException {
        String sql = "UPDATE movimientos_almacen SET estado = ? WHERE id_movimiento = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idMovimiento);
            ps.executeUpdate();
        }
    }

    public List<MovimientoAlmacen> obtenerMovimientosPorFecha(String fechaInicio, String fechaFin) throws SQLException {
        List<MovimientoAlmacen> movimientos = new ArrayList<>();
        String sql = "SELECT * FROM movimientos_almacen WHERE fecha_movimiento BETWEEN ? AND ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MovimientoAlmacen movimiento = new MovimientoAlmacen();
                    movimiento.setIdMovimiento(rs.getInt("id_movimiento"));
                    movimiento.setIdProducto(rs.getInt("id_producto"));
                    movimiento.setCantidad(rs.getDouble("cantidad"));
                    movimiento.setFechaMovimiento(rs.getString("fecha_movimiento"));
                    movimiento.setIdAlmacen(rs.getInt("id_almacen"));
                    movimiento.setEstado(rs.getString("estado"));
                    movimientos.add(movimiento);
                }
            }
        }
        return movimientos;
    }
}

