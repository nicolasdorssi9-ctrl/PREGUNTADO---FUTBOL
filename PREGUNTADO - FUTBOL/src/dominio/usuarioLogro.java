package dominio;

import java.sql.Timestamp;

public class usuarioLogro {
    private int id_usuario;
    private int id_logro;
    private Timestamp fecha_obtencion;

    public usuarioLogro() {
    }

    public usuarioLogro(int id_usuario, int id_logro, Timestamp fecha_obtencion) {
        this.id_usuario = id_usuario;
        this.id_logro = id_logro;
        this.fecha_obtencion = fecha_obtencion;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_logro() {
        return id_logro;
    }

    public void setId_logro(int id_logro) {
        this.id_logro = id_logro;
    }

    public Timestamp getFecha_obtencion() {
        return fecha_obtencion;
    }

    public void setFecha_obtencion(Timestamp fecha_obtencion) {
        this.fecha_obtencion = fecha_obtencion;
    }
}
