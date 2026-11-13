package dominio;

public class logro {
    private int id_logro;
    private String nombre_logro;
    private String descripcion;

    public logro() {
    }

    public logro(int id_logro, String nombre_logro, String descripcion) {
        this.id_logro = id_logro;
        this.nombre_logro = nombre_logro;
        this.descripcion = descripcion;
    }

    public int getId_logro() {
        return id_logro;
    }

    public void setId_logro(int id_logro) {
        this.id_logro = id_logro;
    }

    public String getNombre_logro() {
        return nombre_logro;
    }

    public void setNombre_logro(String nombre_logro) {
        this.nombre_logro = nombre_logro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
