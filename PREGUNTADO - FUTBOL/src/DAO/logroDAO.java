package DAO;

import conexion.conexion;
import dominio.logro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class logroDAO {

    public int guardar(logro logro) throws SQLException {
        String sql = "INSERT INTO logros (id_logro, nombre_logro, descripcion) VALUES (?, ?, ?)";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

ps.setString(1, logro.getNombre_logro());
 ps.setString(2, logro.getDescripcion());
 ps.setInt(3, logro.getId_logro());

               
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

    public List<logro> listar() throws SQLException {
        String sql = "SELECT id_logro, nombre_logro, descripcion FROM logros ORDER BY nombre_logro";
        List<logro> lista = new ArrayList<>();

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new logro(
                        rs.getInt("id_logro"),
                        rs.getString("nombre_logro"),
                        rs.getString("descripcion")
                ));
            }
        }
        return lista;
    }

    public boolean actualizar(logro logro) throws SQLException {
        String sql = "UPDATE logros SET nombre_logro = ?, descripcion = ? WHERE id_logro = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, logro.getNombre_logro());
            ps.setString(2, logro.getDescripcion());
            ps.setInt(3, logro.getId_logro());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id_logro) throws SQLException {
        String sql = "DELETE FROM logros WHERE id_logro = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id_logro);
            return ps.executeUpdate() > 0;
        }
    }
}
