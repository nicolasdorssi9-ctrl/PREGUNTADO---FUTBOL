package dominio;

import java.sql.Timestamp;

public class logroJugador {
    private final String nombreJugador;
    private final String correoJugador;
    private final String nombreLogro;
    private final String descripcionLogro;
    private final Timestamp fechaObtencion;

    public logroJugador(String nombreJugador, String correoJugador, String nombreLogro,
            String descripcionLogro, Timestamp fechaObtencion) {
        this.nombreJugador = nombreJugador;
        this.correoJugador = correoJugador;
        this.nombreLogro = nombreLogro;
        this.descripcionLogro = descripcionLogro;
        this.fechaObtencion = fechaObtencion;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public String getCorreoJugador() {
        return correoJugador;
    }

    public String getNombreLogro() {
        return nombreLogro;
    }

    public String getDescripcionLogro() {
        return descripcionLogro;
    }

    public Timestamp getFechaObtencion() {
        return fechaObtencion;
    }
}
