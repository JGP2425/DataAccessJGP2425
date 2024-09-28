import java.text.MessageFormat;
import java.util.List;

public class Museo {
    private String nombre;
    private String direccion;
    private String ciudad;
    private String pais;
    private List<Obra> obrasDelMuseo;
    private List<Sala> salasDelMuseo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public List<Obra> getObrasDelMuseo() {
        return obrasDelMuseo;
    }

    public void setObrasDelMuseo(List<Obra> obrasDelMuseo) {
        this.obrasDelMuseo = obrasDelMuseo;
    }

    public void anyadirObrasAlMuseo(Obra obra) {
        this.obrasDelMuseo.add(obra);
    }

    public List<Sala> getSalasDelMuseo() {
        return salasDelMuseo;
    }

    public void setSalasDelMuseo(List<Sala> salasDelMuseo) {
        this.salasDelMuseo = salasDelMuseo;
    }

    public void anyadirSalasAlMuseo(Sala sala) {
        this.salasDelMuseo.add(sala);
    }

    public void mostrarObrasYSalas() {
        //Iteramos por cada sala del museo.
        for (Sala sala: salasDelMuseo) {
            //Si la sala tiene obras las pintamos, distinguiendo entre esculturas y pinturas.
            if (sala.getObrasDeLaSala().size() > 0){
                System.out.println("");
                System.out.println("--------------------");
                System.out.println("------- Sala -------");
                System.out.println("--------------------");
                System.out.println(MessageFormat.format("{0} del {1} con las siguientes obras: ", sala.getNombre(), sala.getMuseo().getNombre()));

                //Iteramos por las obras de la sala diferenciando entre esculturas, pinturas y obras.
                for (Obra obra: sala.getObrasDeLaSala()) {
                    if (obra instanceof Pintura) {
                        Pintura pintura = (Pintura) obra;
                        pintura.imprimirDetalle();
                    }
                    else if (obra instanceof Escultura) {
                        Escultura escultura = (Escultura) obra;
                        escultura.imprimirDetalle();
                    }
                    else {
                        obra.imprimirDetalle();
                    }
                }
            }
            //Si no tiene obras lo indicamos.
            else {
                System.out.println(MessageFormat.format("Sala {0} del Museo {1} no tiene ninguna obra.", sala.getNombre(), sala.getMuseo().getNombre()));
            }
        }
    }
}
