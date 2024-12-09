package DAO;

import Entidad.OrdenProduccion;
import Entidad.OrdenSellado;
import Entidad.Producto;
import conexion.Conexion;
import java.sql.*;
import java.util.*;
 import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrdenSelladoDAO {
    private Conexion conexion;

    public OrdenSelladoDAO(Conexion conexion) {
        this.conexion = conexion;
    }

    public void insertarOrdenSellado(OrdenSellado ordenSellado) throws SQLException {
        String query = "INSERT INTO ordenessellado (id_usuario, medida_bolsa, cantidad_a_sellar, fecha_orden, estado, tiempo_estimado, id_orden) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = conexion.conectar();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ordenSellado.getIdUsuario());
            ps.setString(2, ordenSellado.getMedidaBolsa());
            ps.setInt(3, ordenSellado.getCantidadASellar());
            ps.setString(4, ordenSellado.getFechaOrden());
            ps.setString(5, ordenSellado.getEstado());
            ps.setInt(6, ordenSellado.getTiempoEstimado());
            ps.setInt(7, ordenSellado.getIdOrden());
            ps.executeUpdate();
        }
    }

    public List<OrdenSellado> listarOrdenesSellado() throws SQLException {
        List<OrdenSellado> listaOrdenesSellado = new ArrayList<>();
        String sql = "SELECT * FROM ordenessellado";
        try (Connection connection = conexion.conectar();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                OrdenSellado ordenSellado = new OrdenSellado();
                ordenSellado.setIdOrdenSellado(rs.getInt("id_orden_sellado"));
                ordenSellado.setIdUsuario(rs.getInt("id_usuario"));
                ordenSellado.setMedidaBolsa(rs.getString("medida_bolsa"));
                ordenSellado.setCantidadASellar(rs.getInt("cantidad_a_sellar"));
                ordenSellado.setFechaOrden(rs.getString("fecha_orden"));
                ordenSellado.setEstado(rs.getString("estado"));
                ordenSellado.setTiempoEstimado(rs.getInt("tiempo_estimado"));
                ordenSellado.setIdOrden(rs.getInt("id_orden"));
                listaOrdenesSellado.add(ordenSellado);
            }
        }
        return listaOrdenesSellado;
    }

    public void actualizarOrdenSellado(OrdenSellado ordenSellado) throws SQLException {
        String sql = "UPDATE ordenessellado SET id_usuario = ?, medida_bolsa = ?, cantidad_a_sellar = ?, fecha_orden = ?, estado = ?, tiempo_estimado = ?, id_orden = ? WHERE id_orden_sellado = ?";
        try (Connection connection = conexion.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ordenSellado.getIdUsuario());
            stmt.setString(2, ordenSellado.getMedidaBolsa());
            stmt.setInt(3, ordenSellado.getCantidadASellar());
            stmt.setString(4, ordenSellado.getFechaOrden());
            stmt.setString(5, ordenSellado.getEstado());
            stmt.setInt(6, ordenSellado.getTiempoEstimado());
            stmt.setInt(7, ordenSellado.getIdOrden());
            stmt.setInt(8, ordenSellado.getIdOrdenSellado());
            stmt.executeUpdate();
        }
    }

    public void eliminarOrdenSellado(int idOrdenSellado) throws SQLException {
        String sql = "DELETE FROM ordenessellado WHERE id_orden_sellado = ?";
        try (Connection connection = conexion.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOrdenSellado);
            stmt.executeUpdate();
        }
    }



    public List<OrdenSellado> obtenerOrdenesSelladoPorTrabajador(int idTrabajador) throws SQLException {
    List<OrdenSellado> ordenesSellado = new ArrayList<>();
    String sql = "SELECT * FROM ordenessellado WHERE id_usuario = ?";
    try (Connection conn = conexion.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idTrabajador);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrdenSellado orden = new OrdenSellado();
                orden.setIdOrdenSellado(rs.getInt("id_orden_sellado"));
                orden.setIdUsuario(rs.getInt("id_usuario"));
                orden.setMedidaBolsa(rs.getString("medida_bolsa"));
                orden.setCantidadASellar(rs.getInt("cantidad_a_sellar"));
                orden.setFechaOrden(rs.getString("fecha_orden"));
                orden.setEstado(rs.getString("estado"));
                orden.setTiempoEstimado(rs.getInt("tiempo_estimado"));
                orden.setIdOrden(rs.getInt("id_orden"));
                ordenesSellado.add(orden);
                System.out.println("Orden Sellado recuperada: " + orden.getIdOrdenSellado());
            }
        }
    }
    System.out.println("Total de órdenes de sellado recuperadas: " + ordenesSellado.size());
    return ordenesSellado;
}



    public void marcarComoCompletada(int idOrden) throws SQLException {
        String sql = "UPDATE ordenessellado SET estado = 'Completada' WHERE id_orden = ?";
        try (Connection connection = conexion.conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOrden);
            stmt.executeUpdate();
        }
    }

   
public OrdenSellado obtenerOrdenPorId(int idOrdenSellado) throws SQLException, ParseException {
    OrdenSellado orden = null;
    String sql = "SELECT * FROM ordenessellado WHERE id_orden_sellado = ?";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    try (Connection conn = conexion.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idOrdenSellado);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                orden = new OrdenSellado();
                orden.setIdOrdenSellado(rs.getInt("id_orden_sellado"));
                orden.setIdUsuario(rs.getInt("id_usuario"));
                orden.setMedidaBolsa(rs.getString("medida_bolsa"));
                orden.setCantidadASellar(rs.getInt("cantidad_a_sellar"));
                Date fechaOrden = sdf.parse(rs.getString("fecha_orden"));
                orden.setFechaOrden(sdf.format(fechaOrden));
                orden.setEstado(rs.getString("estado"));
                orden.setTiempoEstimado(rs.getInt("tiempo_estimado"));
                orden.setIdOrden(rs.getInt("id_orden"));
            }
        }
    }
    return orden;
}


    public List<OrdenProduccion> obtenerOrdenesProduccion() throws SQLException {
        List<OrdenProduccion> ordenesProduccion = new ArrayList<>();
        String sql = "SELECT * FROM OrdenesProduccion";
        try (Connection connection = conexion.conectar();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                OrdenProduccion orden = new OrdenProduccion();
                orden.setIdOrden(rs.getInt("id_orden"));
                orden.setTipoMaterial(rs.getInt("tipo_material"));
                orden.setTrabajador(rs.getInt("trabajador"));
                orden.setTiempoEstimado(rs.getInt("tiempo_estimado"));
                orden.setFecha(rs.getDate("fecha"));
                orden.setEstado(rs.getString("estado"));
                ordenesProduccion.add(orden);
            }
        }
        return ordenesProduccion;
    }
    public List<OrdenSellado> obtenerOrdenesPorFecha(String fechaInicio, String fechaFin) throws SQLException {
        List<OrdenSellado> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM ordenessellado WHERE STR_TO_DATE(fecha_Orden, '%Y-%m-%d') BETWEEN STR_TO_DATE(?, '%Y-%m-%d') AND STR_TO_DATE(?, '%Y-%m-%d')";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrdenSellado orden = new OrdenSellado();
                    orden.setIdOrdenSellado(rs.getInt("id_orden_Sellado"));
                    orden.setIdUsuario(rs.getInt("id_usuario"));
                    orden.setMedidaBolsa(rs.getString("medida_Bolsa"));
                    orden.setCantidadASellar(rs.getInt("cantidad_a_Sellar"));
                    orden.setFechaOrden(rs.getString("fecha_Orden"));
                    orden.setEstado(rs.getString("estado"));
                    orden.setTiempoEstimado(rs.getInt("tiempo_Estimado"));
                    orden.setIdOrden(rs.getInt("id_orden"));
                    ordenes.add(orden);
                }
            }
        }
        return ordenes;
    }
}

