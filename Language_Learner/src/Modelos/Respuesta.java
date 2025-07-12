package Modelos;

public class Respuesta {

    private int id;
    private int idEjercicio; 
    private String respuesta;
    private boolean esCorrecta;

    public Respuesta() {}

    public Respuesta(int id, int idEjercicio, String respuesta, boolean esCorrecta) {
        this.id = id;
        this.idEjercicio = idEjercicio;
        this.respuesta = respuesta;
        this.esCorrecta = esCorrecta;
    }

    public Respuesta(String respuesta, boolean esCorrecta) {
        this.respuesta = respuesta;
        this.esCorrecta = esCorrecta;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public boolean isCorrecta() {
        return esCorrecta;
    }

    public void setCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "id=" + id +
                ", idEjercicio=" + idEjercicio +
                ", respuesta='" + respuesta + '\'' +
                ", esCorrecta=" + esCorrecta +
                '}';
    }
}
