public class Obra {
    private String titulo;
    private Autor autor;
    private Museo museo;
    private Sala sala;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Museo getMuseo() {
        return museo;
    }

    public void setMuseo(Museo museo) {
        this.museo = museo;
    }

    public void imprimirDetalle() {
        System.out.println("------- Obra -------");
        System.out.println("    - Titulo: " + getTitulo());
        System.out.println("    - Autor: " + getAutor().getNombre());
        System.out.println("    - Museo: " + getMuseo().getNombre());
        System.out.println("    - Sala: " + getSala().getNombre());
        System.out.println("");
    }
}
