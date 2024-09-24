import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;

public class Persona {
    //Propiedades y Getter/Setters de Persona
    private String nombre;
    public String getNombre(){
        return this.nombre;
    }
    public void setNombre(String newNombre){
        this.nombre = newNombre;
    }

    private LocalDate fechaNacimiento;
    public LocalDate getFechaNacimiento(){
        return this.fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate newFechaNacimiento){
        if (newFechaNacimiento.isAfter(LocalDate.now()))
            throw new IllegalArgumentException(Literales.Fecha_nacimiento_no_valida);

        this.fechaNacimiento = newFechaNacimiento;
    }

    //Función para obtener la edad de la persona
    public Integer Edad()
    {
        Integer diferencia = Period.between(LocalDate.now(),this.fechaNacimiento).getDays();
        return  diferencia/365;
    }

}
