package dominio;

public class pregunta {
    private int id_pregunta;
    private int id_nivel;
    private int id_categoria;
    private String texto_pregunta;

    public pregunta() {
    }

    public pregunta(int id_pregunta, int id_nivel, int id_categoria, String texto_pregunta) {
        this.id_pregunta = id_pregunta;
        this.id_nivel = id_nivel;
        this.id_categoria = id_categoria;
        this.texto_pregunta = texto_pregunta;
    }

    public int getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(int id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public int getId_nivel() {
        return id_nivel;
    }

    public void setId_nivel(int id_nivel) {
        this.id_nivel = id_nivel;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getTexto_pregunta() {
        return texto_pregunta;
    }

    public void setTexto_pregunta(String texto_pregunta) {
        this.texto_pregunta = texto_pregunta;
    }
}
