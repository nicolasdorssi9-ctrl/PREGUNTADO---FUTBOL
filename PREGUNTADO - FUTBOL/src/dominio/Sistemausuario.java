package dominio;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.usuarioDAO;

public class Sistemausuario {
    private final usuarioDAO usuarioDAO = new usuarioDAO();

    public int registrar(String nombre, String correo, char[] contrasena,
            Timestamp fechaRegistro) {
        try {
            return usuarioDAO.registrar(nombre, correo, contrasena, fechaRegistro);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean autenticar(String correo, char[] contrasena) throws SQLException {
        return usuarioDAO.autenticar(correo, contrasena);
    }

    public usuario autenticarUsuario(String correo, char[] contrasena) throws SQLException {
        return usuarioDAO.autenticarUsuario(correo, contrasena);
    }

    public int guardar(usuario usuario) {
        try {
            return usuarioDAO.guardar(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<usuario> listar() {
        try {
            List<usuario> usuarios = usuarioDAO.listar();
            return new ArrayList<>(usuarios);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(usuario usuario) {
        try {
            return usuarioDAO.actualizar(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try {
            return usuarioDAO.eliminar(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
