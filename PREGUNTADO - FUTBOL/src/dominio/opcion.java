package dominio;

public class opcion {
    private int id_opcion;
    private int id_pregunta;
    private String texto_opcion;
    private boolean es_correcta;

    public opcion() {
    }

    public opcion(int id_opcion, int id_pregunta, String texto_opcion, boolean es_correcta) {
        this.id_opcion = id_opcion;
        this.id_pregunta = id_pregunta;
        this.texto_opcion = texto_opcion;
        this.es_correcta = es_correcta;
    }

    public int getId_opcion() {
        return id_opcion;
    }

    public void setId_opcion(int id_opcion) {
        this.id_opcion = id_opcion;
    }

    public int getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(int id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public String getTexto_opcion() {
        return texto_opcion;
    }

    public void setTexto_opcion(String texto_opcion) {
        this.texto_opcion = texto_opcion;
    }

    public boolean isEs_correcta() {
        return es_correcta;
    }

    public void setEs_correcta(boolean es_correcta) {
        this.es_correcta = es_correcta;
    }
}
