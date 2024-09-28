import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        // Crear autores
        Autor autor1 = new Autor();
        autor1.setNombre("Pablo Picasso");
        autor1.setNacionalidad("Español");

        Autor autor2 = new Autor();
        autor2.setNombre("Miguel Ángel");
        autor2.setNacionalidad("Italiano");

        Autor autor3 = new Autor();
        autor3.setNombre("Claude Monet");
        autor3.setNacionalidad("Francés");

        // Crear pinturas
        Pintura pintura1 = new Pintura();
        pintura1.setTitulo("Guernica");
        pintura1.setAutor(autor1);
        pintura1.setTipo(TipoPintura.Oleo);
        pintura1.setFormato("Lienzo");

        Pintura pintura2 = new Pintura();
        pintura2.setTitulo("Impresión, sol naciente");
        pintura2.setAutor(autor3);
        pintura2.setTipo(TipoPintura.Acuarela);
        pintura2.setFormato("Papel");

        // Crear esculturas
        Escultura escultura1 = new Escultura();
        escultura1.setTitulo("David");
        escultura1.setAutor(autor2);
        escultura1.setMaterial(Materiales.Marmol);
        escultura1.setSitio(Estilos.Grecorromano);

        Escultura escultura2 = new Escultura();
        escultura2.setTitulo("El Pensador");
        escultura2.setAutor(autor2);
        escultura2.setMaterial(Materiales.Bronce);
        escultura2.setSitio(Estilos.Neoclasico);

        // Crear salas
        Sala sala1 = new Sala();
        sala1.setNombre("Sala de Pinturas Modernas");
        sala1.setObrasDeLaSala(new ArrayList<>(Arrays.asList(pintura1, pintura2)));

        Sala sala2 = new Sala();
        sala2.setNombre("Sala de Esculturas Clásicas");
        sala2.setObrasDeLaSala(new ArrayList<>(Arrays.asList(escultura1, escultura2)));

        // Crear museo y asignar salas
        Museo museo = new Museo();
        museo.setNombre("Museo de Arte Moderno");
        museo.setDireccion("Calle Falsa 123");
        museo.setCiudad("Madrid");
        museo.setPais("España");
        museo.setSalasDelMuseo(new ArrayList<>(Arrays.asList(sala1, sala2)));

        // Establecer museo en cada sala
        sala1.setMuseo(museo);
        sala2.setMuseo(museo);

        // Establecer sala y museo en cada obra
        pintura1.setSala(sala1);
        pintura1.setMuseo(museo);

        pintura2.setSala(sala1);
        pintura2.setMuseo(museo);

        escultura1.setSala(sala2);
        escultura1.setMuseo(museo);

        escultura2.setSala(sala2);
        escultura2.setMuseo(museo);

        // Mostrar las obras y salas
        museo.mostrarObrasYSalas();
    }
}
