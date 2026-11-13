package DAO;

import conexion.conexion;
import dominio.pregunta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class preguntasDAO {

    public int guardar(pregunta pregunta) throws SQLException {
        String sql = "INSERT INTO preguntas (id_nivel, id_categoria, texto_pregunta) VALUES (?, ?, ?)";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, pregunta.getId_nivel());
            ps.setInt(2, pregunta.getId_categoria());
            ps.setString(3, pregunta.getTexto_pregunta());

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

    public List<pregunta> listar() throws SQLException {
        String sql = "SELECT id_pregunta, id_nivel, id_categoria, texto_pregunta "
                + "FROM preguntas ORDER BY id_pregunta";
        List<pregunta> lista = new ArrayList<>();

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new pregunta(
                        rs.getInt("id_pregunta"),
                        rs.getInt("id_nivel"),
                        rs.getInt("id_categoria"),
                        rs.getString("texto_pregunta")
                ));
            }
        }
        return lista;
    }

    public List<pregunta> listarPorCategoriaYNivel(int idCategoria, int idNivel)
            throws SQLException {
        String sql = "SELECT id_pregunta, id_nivel, id_categoria, texto_pregunta "
                + "FROM preguntas WHERE id_categoria = ? AND id_nivel = ? ORDER BY id_pregunta";
        List<pregunta> lista = new ArrayList<>();

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            ps.setInt(2, idNivel);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new pregunta(
                            rs.getInt("id_pregunta"),
                            rs.getInt("id_nivel"),
                            rs.getInt("id_categoria"),
                            rs.getString("texto_pregunta")
                    ));
                }
            }
        }
        return lista;
    }

    public boolean actualizar(pregunta pregunta) throws SQLException {
        String sql = "UPDATE preguntas SET id_nivel = ?, id_categoria = ?, texto_pregunta = ? "
                + "WHERE id_pregunta = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, pregunta.getId_nivel());
            ps.setInt(2, pregunta.getId_categoria());
            ps.setString(3, pregunta.getTexto_pregunta());
            ps.setInt(4, pregunta.getId_pregunta());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id_pregunta) throws SQLException {
        String sql = "DELETE FROM preguntas WHERE id_pregunta = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id_pregunta);
            return ps.executeUpdate() > 0;
        }
    }
}
