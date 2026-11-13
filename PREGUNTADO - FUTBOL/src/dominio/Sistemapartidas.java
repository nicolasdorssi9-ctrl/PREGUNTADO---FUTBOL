package dominio;

import java.util.ArrayList;
import java.util.List;

import DAO.partidasDAO;

public class Sistemapartidas {
    private final partidasDAO partidasDAO = new partidasDAO();

    public int guardar(partida partida) {
        try {
            return partidasDAO.guardar(partida);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<partida> listar() {
        try {
            List<partida> partidas = partidasDAO.listar();
            return new ArrayList<>(partidas);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(partida partida) {
        try {
            return partidasDAO.actualizar(partida);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id_partida) {
        try {
            return partidasDAO.eliminar(id_partida);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
