import java.util.HashSet;

public class Empresa {
    //Propiedaes y Getters/Setters de Empresa
    private String nombre;
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String newNombre) {
        this.nombre = newNombre;
    }

    private HashSet<Empleado> plantilla = new HashSet<Empleado>();
    private HashSet<Cliente> carteraDeClientes = new HashSet<Cliente>();

    //Función para obtener la cantidad de Clientes totales
    public Integer ClientesTotales() {
        return this.plantilla.size();
    }

    //Función para obtener la cantidad de Empleados totales
    public Integer EmpleadosTotales() {
        return this.carteraDeClientes.size();
    }
}
