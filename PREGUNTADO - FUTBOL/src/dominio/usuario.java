package dominio;

import java.sql.Timestamp;

public class usuario {
    private int id_usuario;
    private String nombre;
    private String correo;
    private int puntos_totales;
    private Timestamp fecha_registro;

    public usuario() {
    }

    public usuario(int id_usuario, String nombre, String correo, int puntos_totales, Timestamp fecha_registro) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.puntos_totales = puntos_totales;
        this.fecha_registro = fecha_registro;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getPuntos_totales() {
        return puntos_totales;
    }

    public void setPuntos_totales(int puntos_totales) {
        this.puntos_totales = puntos_totales;
    }

    public Timestamp getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Timestamp fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
}
