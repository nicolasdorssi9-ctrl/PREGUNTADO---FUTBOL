package dominio;

import java.util.ArrayList;
import java.util.List;

import DAO.opcionesDAO;

public class Sistemaopciones {
    private final opcionesDAO opcionesDAO = new opcionesDAO();

    public int guardar(opcion opcion) {
        try {
            return opcionesDAO.guardar(opcion);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<opcion> listar() {
        try {
            List<opcion> opciones = opcionesDAO.listar();
            return new ArrayList<>(opciones);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(opcion opcion) {
        try {
            return opcionesDAO.actualizar(opcion);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id_opcion) {
        try {
            return opcionesDAO.eliminar(id_opcion);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
