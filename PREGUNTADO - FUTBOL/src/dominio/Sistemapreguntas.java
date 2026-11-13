package dominio;

import java.util.ArrayList;
import java.util.List;

import DAO.preguntasDAO;

public class Sistemapreguntas {
    private final preguntasDAO preguntasDAO = new preguntasDAO();

    public int guardar(pregunta pregunta) {
        try {
            return preguntasDAO.guardar(pregunta);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<pregunta> listar() {
        try {
            List<pregunta> preguntas = preguntasDAO.listar();
            return new ArrayList<>(preguntas);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<pregunta> listarPorCategoriaYNivel(int idCategoria, int idNivel) {
        try {
            List<pregunta> preguntas =
                    preguntasDAO.listarPorCategoriaYNivel(idCategoria, idNivel);
            return new ArrayList<>(preguntas);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(pregunta pregunta) {
        try {
            return preguntasDAO.actualizar(pregunta);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id_pregunta) {
        try {
            return preguntasDAO.eliminar(id_pregunta);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
