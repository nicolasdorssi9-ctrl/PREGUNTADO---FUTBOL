package DAO;

import conexion.conexion;
import dominio.categorias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class categoriasDAO {

    public int guardar(categorias categoria) throws SQLException {
        String sql = "INSERT INTO categorias (nombre_categoria) VALUES (?)";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, categoria.getNombre_categoria());
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

    public List<categorias> listar() throws SQLException {
        String sql = "SELECT id_categoria, nombre_categoria FROM categorias ORDER BY nombre_categoria";
        List<categorias> lista = new ArrayList<>();

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new categorias(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_categoria")
                ));
            }
        }
        return lista;
    }

    public boolean actualizar(categorias categoria) throws SQLException {
        String sql = "UPDATE categorias SET nombre_categoria = ? WHERE id_categoria = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombre_categoria());
            ps.setInt(2, categoria.getId_categoria());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id_categoria) throws SQLException {
        String sql = "DELETE FROM categorias WHERE id_categoria = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id_categoria);
            return ps.executeUpdate() > 0;
        }
    }
}
