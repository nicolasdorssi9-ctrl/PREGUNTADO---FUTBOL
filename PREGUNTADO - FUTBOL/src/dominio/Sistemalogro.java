package dominio;

import java.util.ArrayList;
import java.util.List;

import DAO.logroDAO;

public class Sistemalogro {
    private final logroDAO logroDAO = new logroDAO();

    public int guardar(logro logro) {
        try {
            return logroDAO.guardar(logro);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<logro> listar() {
        try {
            List<logro> logros = logroDAO.listar();
            return new ArrayList<>(logros);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(logro logro) {
        try {
            return logroDAO.actualizar(logro);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id_logro) {
        try {
            return logroDAO.eliminar(id_logro);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
