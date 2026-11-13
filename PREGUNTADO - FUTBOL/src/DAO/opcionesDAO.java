package DAO;

import conexion.conexion;
import dominio.opcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class opcionesDAO {

    public int guardar(opcion opcion) throws SQLException {
        String sql = "INSERT INTO opciones (id_pregunta, texto_opcion, es_correcta) VALUES (?, ?, ?)";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, opcion.getId_pregunta());
            ps.setString(2, opcion.getTexto_opcion());
            ps.setBoolean(3, opcion.isEs_correcta());
ps.setInt(4, opcion.getId_opcion());
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

    public List<opcion> listar() throws SQLException {
        String sql = "SELECT id_opcion, id_pregunta, texto_opcion, es_correcta "
                + "FROM opciones ORDER BY id_opcion";
        List<opcion> lista = new ArrayList<>();

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new opcion(
                        rs.getInt("id_opcion"),
                        rs.getInt("id_pregunta"),
                        rs.getString("texto_opcion"),
                        rs.getBoolean("es_correcta")
                ));
            }
        }
        return lista;
    }

    public boolean actualizar(opcion opcion) throws SQLException {
        String sql = "UPDATE opciones SET id_pregunta = ?, texto_opcion = ?, es_correcta = ? "
                + "WHERE id_opcion = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, opcion.getId_pregunta());
            ps.setString(2, opcion.getTexto_opcion());
            ps.setBoolean(3, opcion.isEs_correcta());
            ps.setInt(4, opcion.getId_opcion());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id_opcion) throws SQLException {
        String sql = "DELETE FROM opciones WHERE id_opcion = ?";

        try (Connection cn = conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id_opcion);
            return ps.executeUpdate() > 0;
        }
    }
}
