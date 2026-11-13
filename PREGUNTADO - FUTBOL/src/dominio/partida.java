package dominio;

import java.sql.Timestamp;

public class partida {
    private int id_partida;
    private int id_usuario;
    private int puntaje_obtenido;
    private int nivel_jugado;
    private Timestamp fecha_partida;

    public partida() {
    }

    public partida(int id_partida, int id_usuario, int puntaje_obtenido, int nivel_jugado,
            Timestamp fecha_partida) {
        this.id_partida = id_partida;
        this.id_usuario = id_usuario;
        this.puntaje_obtenido = puntaje_obtenido;
        this.nivel_jugado = nivel_jugado;
        this.fecha_partida = fecha_partida;
    }

    public int getId_partida() {
        return id_partida;
    }

    public void setId_partida(int id_partida) {
        this.id_partida = id_partida;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getPuntaje_obtenido() {
        return puntaje_obtenido;
    }

    public void setPuntaje_obtenido(int puntaje_obtenido) {
        this.puntaje_obtenido = puntaje_obtenido;
    }

    public int getNivel_jugado() {
        return nivel_jugado;
    }

    public void setNivel_jugado(int nivel_jugado) {
        this.nivel_jugado = nivel_jugado;
    }

    public Timestamp getFecha_partida() {
        return fecha_partida;
    }

    public void setFecha_partida(Timestamp fecha_partida) {
        this.fecha_partida = fecha_partida;
    }
}
