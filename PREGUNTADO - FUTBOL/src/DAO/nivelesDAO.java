package DAO;

import conexion.conexion;
import dominio.nivel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class nivelesDAO {

    public int guardar(nivel nivel) throws SQLException {
        String sql = "INSERT INTO niveles (id_nivel, nombre_nivel) VALUES (?, ?)";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, nivel.getId_nivel());
            ps.setString(2, nivel.getNombre_nivel());
            

            int filas = ps.executeUpdate();
            if (filas == 0) {
                return -1;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        }
    }

    public List<nivel> listar() throws SQLException {
        String sql = "SELECT id_nivel, nombre_nivel FROM niveles ORDER BY nombre_nivel";
        List<nivel> lista = new ArrayList<>();

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new nivel(
                        rs.getInt("id_nivel"),
                        rs.getString("nombre_nivel")
                ));
            }
        }
        return lista;
    }

    public boolean actualizar(nivel nivel) throws SQLException {
        String sql = "UPDATE niveles SET nombre_nivel = ? WHERE id_nivel = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nivel.getNombre_nivel());
            ps.setInt(2, nivel.getId_nivel());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id_nivel) throws SQLException {
        String sql = "DELETE FROM niveles WHERE id_nivel = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id_nivel);
            return ps.executeUpdate() > 0;
        }
    }
}
