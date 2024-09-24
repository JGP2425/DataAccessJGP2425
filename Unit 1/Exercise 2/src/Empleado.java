public class Empleado extends Persona{
    //Propiedaes y Getters/Setters de Empleado
    private Double sueldoBruto;
    public Double getSueldoBruto() {
        return this.sueldoBruto;
    }
    public void setSueldoBruto(Double newSueldoBruto) {
        if (newSueldoBruto < 0)
            throw new IllegalArgumentException(Literales.Sueldo_no_valido);

        this.sueldoBruto = newSueldoBruto;
    }
}
