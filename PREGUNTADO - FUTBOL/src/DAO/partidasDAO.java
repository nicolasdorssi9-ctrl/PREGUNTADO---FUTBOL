package DAO;

import conexion.conexion;
import dominio.partida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class partidasDAO {

    public int guardar(partida partida) throws SQLException {
        String sql = "INSERT INTO partidas (id_usuario, puntaje_obtenido, nivel_jugado, fecha_partida, id_partida) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, partida.getId_usuario());
            ps.setInt(2, partida.getPuntaje_obtenido());
            ps.setInt(3, partida.getNivel_jugado());
            ps.setTimestamp(4, partida.getFecha_partida());
             ps.setInt(5, partida.getId_partida());

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

    public List<partida> listar() throws SQLException {
        String sql = "SELECT id_partida, id_usuario, puntaje_obtenido, nivel_jugado, fecha_partida "
                + "FROM partidas ORDER BY fecha_partida DESC";
        List<partida> lista = new ArrayList<>();

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new partida(
                        rs.getInt("id_partida"),
                        rs.getInt("id_usuario"),
                        rs.getInt("puntaje_obtenido"),
                        rs.getInt("nivel_jugado"),
                        rs.getTimestamp("fecha_partida")
                ));
            }
        }
        return lista;
    }

    public boolean actualizar(partida partida) throws SQLException {
        String sql = "UPDATE partidas SET id_usuario = ?, puntaje_obtenido = ?, "
                + "nivel_jugado = ?, fecha_partida = ? WHERE id_partida = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, partida.getId_usuario());
            ps.setInt(2, partida.getPuntaje_obtenido());
            ps.setInt(3, partida.getNivel_jugado());
            ps.setTimestamp(4, partida.getFecha_partida());
            ps.setInt(5, partida.getId_partida());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id_partida) throws SQLException {
        String sql = "DELETE FROM partidas WHERE id_partida = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id_partida);
            return ps.executeUpdate() > 0;
        }
    }
}
