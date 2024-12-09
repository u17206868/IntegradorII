package DAO;

import Entidad.Producto;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private Conexion conexion;

    public ProductoDAO(Conexion conexion) {
        this.conexion = conexion;
    }

    public List<Producto> obtenerProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM productos";
        try (Connection conn = conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setTipo(rs.getString("tipo"));
                producto.setCantidad(rs.getDouble("cantidad"));
                producto.setAlmacen(rs.getInt("almacen"));
                productos.add(producto);
            }
        }
        return productos;
    }

    public void crearProducto(Producto producto) throws SQLException {
        String query = "INSERT INTO productos (nombre, descripcion, tipo, cantidad, almacen) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setString(3, producto.getTipo());
            ps.setDouble(4, producto.getCantidad());
            ps.setInt(5, producto.getAlmacen());
            ps.executeUpdate();

            // Obtener el ID generado automáticamente
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    producto.setIdProducto(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Error al obtener el ID generado del producto.");
                }
            }
        }
    }

    public void modificarProducto(Producto producto) throws SQLException {
        String query = "UPDATE productos SET nombre = ?, descripcion = ?, tipo = ?, cantidad = ?, almacen = ? WHERE id_producto = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setString(3, producto.getTipo());
            ps.setDouble(4, producto.getCantidad());
            ps.setInt(5, producto.getAlmacen());
            ps.setInt(6, producto.getIdProducto());
            ps.executeUpdate();
        }
    }

    public void eliminarProducto(int idProducto) throws SQLException {
        String query = "DELETE FROM productos WHERE id_producto = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idProducto);
            ps.executeUpdate();
        }
    }

    public Producto getProducto(int idProducto) throws SQLException {
        Producto producto = null;
        String query = "SELECT * FROM productos WHERE id_producto = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idProducto);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto();
                    producto.setIdProducto(rs.getInt("id_producto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setTipo(rs.getString("tipo"));
                    producto.setCantidad(rs.getDouble("cantidad"));
                    producto.setAlmacen(rs.getInt("almacen"));
                }
            }
        }
        return producto;
    }
}
