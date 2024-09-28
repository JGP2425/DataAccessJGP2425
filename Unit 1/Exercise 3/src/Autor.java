import java.util.List;

public class Autor {
    private String nombre;
    private String nacionalidad;
    private List<Obra> obrasDelAutor;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public List<Obra> getObrasDelAutor() {
        return obrasDelAutor;
    }

    public void setObrasDelAutor(List<Obra> obrasDelAutor) {
        this.obrasDelAutor = obrasDelAutor;
    }
}
