
package DAO;

import conexion.conexion;
import dominio.usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class usuarioDAO {

    private static final int ITERACIONES_HASH = 120_000;

    public int registrar(String nombre, String correo, char[] contrasena,
            java.sql.Timestamp fechaRegistro) throws SQLException {
        String sql = "INSERT INTO usuarios "
                + "(nombre_usuario, correo, contrasena, puntos_totales, fecha_registro) "
                + "VALUES (?, ?, ?, 0, ?)";
        String hash = hashContrasena(contrasena);

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, hash);
            ps.setTimestamp(4, fechaRegistro);

            if (ps.executeUpdate() == 0) {
                return -1;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    public boolean autenticar(String correo, char[] contrasena) throws SQLException {
        return autenticarUsuario(correo, contrasena) != null;
    }

    public usuario autenticarUsuario(String correo, char[] contrasena) throws SQLException {
        String sql = "SELECT id_usuario, nombre_usuario, correo, puntos_totales, "
                + "fecha_registro, contrasena FROM usuarios WHERE correo = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                if (!verificarContrasena(contrasena, rs.getString("contrasena"))) {
                    return null;
                }
                return new usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("correo"),
                        rs.getInt("puntos_totales"),
                        rs.getTimestamp("fecha_registro"));
            }
        }
    }

    private boolean verificarContrasena(char[] contrasena, String hashGuardado)
            throws SQLException {
        if (hashGuardado == null) {
            return false;
        }

        String[] partes = hashGuardado.split("\\$", -1);
        if (partes.length != 4 || !"pbkdf2".equals(partes[0])) {
            return false;
        }

        try {
            int iteraciones = Integer.parseInt(partes[1]);
            if (iteraciones <= 0) {
                return false;
            }

            byte[] sal = Base64.getDecoder().decode(partes[2]);
            byte[] hashEsperado = Base64.getDecoder().decode(partes[3]);
            PBEKeySpec spec = new PBEKeySpec(contrasena, sal, iteraciones,
                    hashEsperado.length * 8);
            try {
                byte[] hashCalculado = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                        .generateSecret(spec)
                        .getEncoded();
                return MessageDigest.isEqual(hashEsperado, hashCalculado);
            } catch (GeneralSecurityException ex) {
                throw new SQLException("No se pudo verificar la contraseña.", ex);
            } finally {
                spec.clearPassword();
            }
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private String hashContrasena(char[] contrasena) throws SQLException {
        byte[] sal = new byte[16];
        new SecureRandom().nextBytes(sal);
        PBEKeySpec spec = new PBEKeySpec(contrasena, sal, ITERACIONES_HASH, 256);
        try {
            byte[] hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec)
                    .getEncoded();
            return "pbkdf2$" + ITERACIONES_HASH + "$"
                    + Base64.getEncoder().encodeToString(sal) + "$"
                    + Base64.getEncoder().encodeToString(hash);
        } catch (GeneralSecurityException ex) {
            throw new SQLException("No se pudo proteger la contraseña.", ex);
        } finally {
            spec.clearPassword();
        }
    }

    public int guardar(usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (id_usuario, nombre_usuario, correo, puntos_totales, fecha_registro) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

               ps.setInt(1, usuario.getId_usuario());
                ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getCorreo());
            ps.setInt(4, usuario.getPuntos_totales());
            ps.setTimestamp(5, usuario.getFecha_registro());

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

    public List<usuario> listar() throws SQLException {
        String sql = "SELECT id_usuario, nombre_usuario AS nombre, correo, puntos_totales, fecha_registro "
                + "FROM usuarios ORDER BY nombre_usuario";
        List<usuario> lista = new ArrayList<>();

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getInt("puntos_totales"),
                        rs.getTimestamp("fecha_registro")
                ));
            }
        }
        return lista;
    }

    public boolean actualizar(usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre_usuario = ?, correo = ?, puntos_totales = ? "
                + "WHERE id_usuario = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setInt(3, usuario.getPuntos_totales());
            ps.setInt(4, usuario.getId_usuario());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
