package dominio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import DAO.usuarios_logrosDAO;

public class Sistemausuarios_logros {
    private final usuarios_logrosDAO usuariosLogrosDAO = new usuarios_logrosDAO();

    public boolean guardar(usuarioLogro usuarioLogro) {
        try {
            return usuariosLogrosDAO.guardar(usuarioLogro);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean otorgarSiNoExiste(int idUsuario, String nombreLogro)
            throws java.sql.SQLException {
        return usuariosLogrosDAO.otorgarSiNoExiste(idUsuario, nombreLogro);
    }

    public ArrayList<usuarioLogro> listar() {
        try {
            List<usuarioLogro> usuariosLogros = usuariosLogrosDAO.listar();
            return new ArrayList<>(usuariosLogros);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<logroJugador> listarLogrosPorJugador() throws java.sql.SQLException {
        return usuariosLogrosDAO.listarLogrosPorJugador();
    }

    public boolean actualizarFecha(int id_usuario, int id_logro, Timestamp fecha_obtencion) {
        try {
            return usuariosLogrosDAO.actualizarFecha(id_usuario, id_logro, fecha_obtencion);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id_usuario, int id_logro) {
        try {
            return usuariosLogrosDAO.eliminar(id_usuario, id_logro);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
