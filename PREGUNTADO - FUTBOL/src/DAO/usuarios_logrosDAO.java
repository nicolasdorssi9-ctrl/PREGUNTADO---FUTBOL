package DAO;

import conexion.conexion;
import dominio.logroJugador;
import dominio.usuarioLogro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class usuarios_logrosDAO {

    public boolean guardar(usuarioLogro usuarioLogro) throws SQLException {
        String sql = "INSERT INTO usuario_logros (id_usuario, id_logro, fecha_obtencion) "
                + "VALUES (?, ?, ?)";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, usuarioLogro.getId_usuario());
            ps.setInt(2, usuarioLogro.getId_logro());
            ps.setTimestamp(3, usuarioLogro.getFecha_obtencion());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean otorgarSiNoExiste(int idUsuario, String nombreLogro) throws SQLException {
        try (Connection cn = conexion.getConexion()) {
            int idLogro;
            try (PreparedStatement ps = cn.prepareStatement(
                    "SELECT id_logro FROM logros WHERE nombre_logro = ?")) {
                ps.setString(1, nombreLogro);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("No existe el logro configurado: " + nombreLogro);
                    }
                    idLogro = rs.getInt("id_logro");
                }
            }

            String sql = "INSERT IGNORE INTO usuario_logros "
                    + "(id_usuario, id_logro, fecha_obtencion) VALUES (?, ?, CURRENT_TIMESTAMP)";
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);
                ps.setInt(2, idLogro);
                return ps.executeUpdate() == 1;
            }
        }
    }

    public List<usuarioLogro> listar() throws SQLException {
        String sql = "SELECT id_usuario, id_logro, fecha_obtencion "
                + "FROM usuario_logros ORDER BY fecha_obtencion DESC";
        List<usuarioLogro> lista = new ArrayList<>();

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new usuarioLogro(
                        rs.getInt("id_usuario"),
                        rs.getInt("id_logro"),
                        rs.getTimestamp("fecha_obtencion")
                ));
            }
        }
        return lista;
    }

    public List<logroJugador> listarLogrosPorJugador() throws SQLException {
        String sql = "SELECT u.nombre_usuario, u.correo, l.nombre_logro, l.descripcion, "
                + "ul.fecha_obtencion "
                + "FROM usuarios u "
                + "LEFT JOIN usuario_logros ul ON ul.id_usuario = u.id_usuario "
                + "LEFT JOIN logros l ON l.id_logro = ul.id_logro "
                + "ORDER BY u.nombre_usuario, ul.fecha_obtencion DESC, l.nombre_logro";
        List<logroJugador> lista = new ArrayList<>();

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new logroJugador(
                        rs.getString("nombre_usuario"),
                        rs.getString("correo"),
                        rs.getString("nombre_logro"),
                        rs.getString("descripcion"),
                        rs.getTimestamp("fecha_obtencion")));
            }
        }
        return lista;
    }

    public boolean actualizarFecha(int id_usuario, int id_logro, Timestamp fecha_obtencion)
            throws SQLException {
        String sql = "UPDATE usuario_logros SET fecha_obtencion = ? "
                + "WHERE id_usuario = ? AND id_logro = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setTimestamp(1, fecha_obtencion);
            ps.setInt(2, id_usuario);
            ps.setInt(3, id_logro);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id_usuario, int id_logro) throws SQLException {
        String sql = "DELETE FROM usuario_logros WHERE id_usuario = ? AND id_logro = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id_usuario);
            ps.setInt(2, id_logro);
            return ps.executeUpdate() > 0;
        }
    }
}
