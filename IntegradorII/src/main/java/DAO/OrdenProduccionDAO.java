package DAO;

import Entidad.OrdenProduccion;
import Entidad.MateriaPrima;
import conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class OrdenProduccionDAO {
    private Conexion conexion;

    public OrdenProduccionDAO(Conexion conexion) {
        this.conexion = conexion;
    }

    public void crearOrden(OrdenProduccion orden) throws SQLException {
        String query = "INSERT INTO OrdenesProduccion (tipo_material, trabajador, tiempo_estimado, fecha, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, orden.getTipoMaterial());
            ps.setInt(2, orden.getTrabajador());
            ps.setInt(3, orden.getTiempoEstimado());
            ps.setDate(4, new java.sql.Date(orden.getFecha().getTime()));
            ps.setString(5, orden.getEstado());
            ps.executeUpdate();
        }
    }

    public void eliminarOrden(int id) throws SQLException {
        String query = "DELETE FROM OrdenesProduccion WHERE id_orden = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void modificarOrden(OrdenProduccion orden) throws SQLException {
        String query = "UPDATE OrdenesProduccion SET tipo_material = ?, trabajador = ?, tiempo_estimado = ?, fecha = ?, estado = ? WHERE id_orden = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, orden.getTipoMaterial());
            ps.setInt(2, orden.getTrabajador());
            ps.setInt(3, orden.getTiempoEstimado());
            ps.setDate(4, new java.sql.Date(orden.getFecha().getTime()));
            ps.setString(5, orden.getEstado());
            ps.setInt(6, orden.getIdOrden());
            ps.executeUpdate();
        }
    }

    public List<OrdenProduccion> obtenerOrdenesProduccion() throws SQLException {
        List<OrdenProduccion> ordenesProduccion = new ArrayList<>();
        String sql = "SELECT op.*, m.nombre as 'nombre_material', \n"
                + "u.nombre as 'nombre_usuario', u.apellido as 'nombre_apellido'\n"
                + " FROM OrdenesProduccion op \n"
                + "inner join usuarios u on op.trabajador = u.id_usuario \n"
                + "inner join materiasprimas m on m.id_materia_prima = op.tipo_material;";
        try (Connection conn = conexion.conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                OrdenProduccion orden = new OrdenProduccion();
                orden.setIdOrden(rs.getInt("id_orden"));
                orden.setTipoMaterial(rs.getInt("tipo_material"));
                orden.setTrabajador(rs.getInt("trabajador"));
                orden.setTiempoEstimado(rs.getInt("tiempo_estimado"));
                orden.setFecha(rs.getDate("fecha"));
                orden.setEstado(rs.getString("estado"));
                orden.setNombreMaterial(rs.getString("nombre_material"));
                orden.setNombreUsuario(rs.getString("nombre_usuario"));
                orden.setApellidoUsuario(rs.getString("nombre_apellido"));
                ordenesProduccion.add(orden);
            }
        }
        return ordenesProduccion;
    }

    public List<OrdenProduccion> obtenerOrdenesProduccionPorTrabajador(int idTrabajador) throws SQLException {
        List<OrdenProduccion> ordenesProduccion = new ArrayList<>();
        String query = "SELECT op.*, m.nombre as 'nombre_material', \n"
                + "u.nombre as 'nombre_usuario', u.apellido as 'nombre_apellido'\n"
                + " FROM OrdenesProduccion op \n"
                + "inner join usuarios u on op.trabajador = u.id_usuario \n"
                + "inner join materiasprimas m on m.id_materia_prima = op.tipo_material"
                + " WHERE op.trabajador = ?";
        try (Connection conn = conexion.conectar();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idTrabajador);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrdenProduccion orden = new OrdenProduccion();
                    orden.setIdOrden(rs.getInt("id_orden"));
                    orden.setTipoMaterial(rs.getInt("tipo_material"));
                    orden.setTrabajador(rs.getInt("trabajador"));
                    orden.setTiempoEstimado(rs.getInt("tiempo_estimado"));
                    orden.setFecha(rs.getDate("fecha"));
                    orden.setEstado(rs.getString("estado"));
                         orden.setNombreMaterial(rs.getString("nombre_material"));
                orden.setNombreUsuario(rs.getString("nombre_usuario"));
                orden.setApellidoUsuario(rs.getString("nombre_apellido"));
                    ordenesProduccion.add(orden);
                }
            }
        }
        return ordenesProduccion;
    }


    public void marcarComoCompletada(int idOrden) throws SQLException {
        String query = "UPDATE OrdenesProduccion SET estado = 'Completada' WHERE id_orden = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idOrden);
            ps.executeUpdate();
        }
    }

    public List<MateriaPrima> obtenerMateriasPrimas() throws SQLException {
        List<MateriaPrima> materiasPrimas = new ArrayList<>();
        String sql = "SELECT * FROM MateriasPrimas";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MateriaPrima materiaPrima = new MateriaPrima();
                materiaPrima.setIdMateriaPrima(rs.getInt("id_materia_prima"));
                materiaPrima.setNombre(rs.getString("nombre"));
                materiaPrima.setTipo(rs.getString("tipo"));
                materiaPrima.setCantidad(rs.getDouble("cantidad"));
                materiaPrima.setTipo_materia(rs.getString("tipo_materia"));
                materiasPrimas.add(materiaPrima);
            }
        }
        return materiasPrimas;
    }

    public OrdenProduccion obtenerOrdenPorId(int idOrden) throws SQLException {
        OrdenProduccion orden = null;
        String query = "SELECT * FROM OrdenesProduccion WHERE id_orden = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idOrden);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    orden = new OrdenProduccion();
                    orden.setIdOrden(rs.getInt("id_orden"));
                    orden.setTipoMaterial(rs.getInt("tipo_material"));
                    orden.setTrabajador(rs.getInt("trabajador"));
                    orden.setTiempoEstimado(rs.getInt("tiempo_estimado"));
                    orden.setFecha(rs.getDate("fecha"));
                    orden.setEstado(rs.getString("estado"));
                }
            }
        }
        return orden;
    }
    public List<OrdenProduccion> obtenerOrdenesProduccionPendientes() throws SQLException {
    List<OrdenProduccion> ordenesProduccion = new ArrayList<>();
    String sql = "SELECT * FROM ordenesproduccion WHERE estado = 'Pendiente'";
    try (Connection conn = conexion.conectar();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
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
    public List<OrdenProduccion> obtenerOrdenesProduccionCompletadas() throws SQLException {
    List<OrdenProduccion> ordenesProduccion = new ArrayList<>();
    String sql = "SELECT * FROM ordenesproduccion WHERE estado = 'Completada'";
    try (Connection conn = conexion.conectar();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
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
public List<OrdenProduccion> obtenerOrdenesPorFecha(String fechaInicio, String fechaFin) throws SQLException {
        List<OrdenProduccion> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM ordenesproduccion WHERE fecha BETWEEN ? AND ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrdenProduccion orden = new OrdenProduccion();
                    orden.setIdOrden(rs.getInt("id_orden"));
                    orden.setTipoMaterial(rs.getInt("tipo_material"));
                    orden.setTrabajador(rs.getInt("trabajador"));
                    orden.setTiempoEstimado(rs.getInt("tiempo_estimado"));
                    orden.setFecha(rs.getDate("fecha"));
                    orden.setEstado(rs.getString("estado"));
                    ordenes.add(orden);
                }
            }
        }
        return ordenes;
    }

}
