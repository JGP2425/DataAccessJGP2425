public class Escultura extends Obra {
    private Materiales material;
    private Estilos sitio;

    public Materiales getMaterial() {
        return material;
    }

    public void setMaterial(Materiales material) {
        this.material = material;
    }

    public Estilos getSitio() {
        return sitio;
    }

    public void setSitio(Estilos sitio) {
        this.sitio = sitio;
    }

    public void imprimirDetalle() {
        System.out.println("------- Escultura -------");
        System.out.println("    - Titulo: " + getTitulo());
        System.out.println("    - Autor: " + getAutor().getNombre());
        System.out.println("    - Material: " + getMaterial());
        System.out.println("    - Sitio: " + getSitio());
        System.out.println("    - Museo: " + getMuseo().getNombre());
        System.out.println("    - Sala: " + getSala().getNombre());
        System.out.println("");
    }
}
