public class Pintura extends Obra{
    private TipoPintura tipo;
    private String formato;

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public TipoPintura getTipo() {
        return tipo;
    }

    public void setTipo(TipoPintura tipo) {
        this.tipo = tipo;
    }

    public void imprimirDetalle() {
        System.out.println("------- Pintura -------");
        System.out.println("    - Titulo: " + getTitulo());
        System.out.println("    - Autor: " + getAutor().getNombre());
        System.out.println("    - Tipo de pintura: " + getTipo());
        System.out.println("    - Formato: " + getFormato());
        System.out.println("    - Museo: " + getMuseo().getNombre());
        System.out.println("    - Sala: " + getSala().getNombre());
        System.out.println("");
    }
}
