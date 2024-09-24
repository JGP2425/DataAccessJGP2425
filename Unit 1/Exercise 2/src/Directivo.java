import java.util.HashSet;

public class Directivo extends Empleado{
    //Propiedaes y Getters/Setters de Directivo
    private String categoria;
    public String getCategoria(){
        return this.categoria;
    }
    public void setCategoria(String newCategoria){
        this.categoria = newCategoria;
    }

    private HashSet<Empleado> supervisa = new HashSet<Empleado>();

    //Función para recuperar la cantidad de subordinados
    public Integer Subordinados()
    {
        return supervisa.size();
    }
}
