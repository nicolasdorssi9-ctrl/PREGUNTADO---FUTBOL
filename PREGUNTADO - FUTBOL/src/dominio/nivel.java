package dominio;

public class nivel {
    private int id_nivel;
    private String nombre_nivel;

    public nivel() {
    }

    public nivel(int id_nivel, String nombre_nivel) {
        this.id_nivel = id_nivel;
        this.nombre_nivel = nombre_nivel;
    }

    public int getId_nivel() {
        return id_nivel;
    }

    public void setId_nivel(int id_nivel) {
        this.id_nivel = id_nivel;
    }

    public String getNombre_nivel() {
        return nombre_nivel;
    }

    public void setNombre_nivel(String nombre_nivel) {
        this.nombre_nivel = nombre_nivel;
    }
}
