package dominio;

import java.util.ArrayList;
import java.util.List;

import DAO.nivelesDAO;

public class Sistemaniveles {
    private final nivelesDAO nivelesDAO = new nivelesDAO();

    public int guardar(nivel nivel) {
        try {
            return nivelesDAO.guardar(nivel);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<nivel> listar() {
        try {
            List<nivel> niveles = nivelesDAO.listar();
            return new ArrayList<>(niveles);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(nivel nivel) {
        try {
            return nivelesDAO.actualizar(nivel);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id_nivel) {
        try {
            return nivelesDAO.eliminar(id_nivel);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
