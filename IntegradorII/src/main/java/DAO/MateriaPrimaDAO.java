package DAO;

import Entidad.MateriaPrima;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MateriaPrimaDAO {
    private Connection connection;

    public MateriaPrimaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<MateriaPrima> obtenerMateriasPrimas() throws SQLException {
        List<MateriaPrima> materiasPrimas = new ArrayList<>();
        String query = "SELECT * FROM materiasprimas";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            MateriaPrima materia = new MateriaPrima();
            materia.setIdMateriaPrima(rs.getInt("id_materia_prima"));
            materia.setNombre(rs.getString("nombre"));
            materia.setTipo(rs.getString("tipo"));
            materia.setCantidad(rs.getDouble("cantidad"));
            materia.setTipo_materia(rs.getString("tipo_materia")); // Usar el nombre correcto de la columna
            materiasPrimas.add(materia);
        }
        return materiasPrimas;
    }

    public void agregarMateriaPrima(MateriaPrima materia) throws SQLException {
        String query = "INSERT INTO materiasprimas (nombre, tipo, cantidad, tipo_materia) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, materia.getNombre());
            ps.setString(2, materia.getTipo());
            ps.setDouble(3, materia.getCantidad());
            ps.setString(4, materia.getTipo_materia()); // Usar el nombre correcto de la columna
            ps.executeUpdate();
        }
    }

    public void eliminarMateriaPrima(int id) throws SQLException {
    String query = "DELETE FROM materiasprimas WHERE id_materia_prima = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}


    public void actualizarMateriaPrima(MateriaPrima materia) throws SQLException {
        String query = "UPDATE materiasprimas SET nombre = ?, tipo = ?, cantidad = ?, tipo_materia = ? WHERE id_materia_prima = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, materia.getNombre());
            ps.setString(2, materia.getTipo());
            ps.setDouble(3, materia.getCantidad());
            ps.setString(4, materia.getTipo_materia()); // Usar el nombre correcto de la columna
            ps.setInt(5, materia.getIdMateriaPrima());
            ps.executeUpdate();
        }
    }
}



