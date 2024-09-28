import java.util.List;

public class Sala {
    private String nombre;
    private List<Obra> obrasDeLaSala;
    private Museo museo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Obra> getObrasDeLaSala() {
        return obrasDeLaSala;
    }

    public void setObrasDeLaSala(List<Obra> obrasDeLaSala) {
        this.obrasDeLaSala = obrasDeLaSala;
    }

    public void anyadirObrasALaSala(Obra newObra) {
        this.obrasDeLaSala.add(newObra);
    }

    public Museo getMuseo() {
        return museo;
    }

    public void setMuseo(Museo museo) {
        this.museo = museo;
    }
}
