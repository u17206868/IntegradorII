/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAO;

import Entidad.Almacen;
import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlmacenDAO {
    private Conexion conexion;

    public AlmacenDAO(Conexion conexion) {
        this.conexion = conexion;
    }

    public void insertarAlmacen(Almacen almacen) throws SQLException {
        String sql = "INSERT INTO almacenes (nombre, ubicacion, tipo) VALUES (?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, almacen.getNombre());
            ps.setString(2, almacen.getUbicacion());
            ps.setString(3, almacen.getTipo());
            ps.executeUpdate();
        }
    }

    public List<Almacen> listarAlmacenes() throws SQLException {
        List<Almacen> almacenes = new ArrayList<>();
        String sql = "SELECT * FROM almacenes";
        try (Connection conn = conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Almacen almacen = new Almacen();
                almacen.setIdAlmacen(rs.getInt("id_almacen"));
                almacen.setNombre(rs.getString("nombre"));
                almacen.setUbicacion(rs.getString("ubicacion"));
                almacen.setTipo(rs.getString("tipo"));
                almacenes.add(almacen);
            }
        }
        return almacenes;
    }

    public Almacen obtenerAlmacenPorId(int idAlmacen) throws SQLException {
        Almacen almacen = null;
        String sql = "SELECT * FROM almacenes WHERE id_almacen = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAlmacen);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    almacen = new Almacen();
                    almacen.setIdAlmacen(rs.getInt("id_almacen"));
                    almacen.setNombre(rs.getString("nombre"));
                    almacen.setUbicacion(rs.getString("ubicacion"));
                    almacen.setTipo(rs.getString("tipo"));
                }
            }
        }
        return almacen;
    }
    
}
