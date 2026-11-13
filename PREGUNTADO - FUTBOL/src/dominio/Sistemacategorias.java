package dominio;

import java.util.ArrayList;
import java.util.List;

import DAO.categoriasDAO;

public class Sistemacategorias {
    private final categoriasDAO categoriasDAO = new categoriasDAO();

    public int guardar(categorias categoria) {
        try {
            return categoriasDAO.guardar(categoria);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<categorias> listar() {
        try {
            List<categorias> categorias = categoriasDAO.listar();
            return new ArrayList<>(categorias);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(categorias categoria) {
        try {
            return categoriasDAO.actualizar(categoria);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id_categoria) {
        try {
            return categoriasDAO.eliminar(id_categoria);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
