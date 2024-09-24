public class Alumno {
    //Propiedades y Getter/Setters Alumno
    private String nombre;
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String newNombre) {
        this.nombre = newNombre;
    }

    private Integer nota;
    public Integer getNota() {
        return this.nota;
    }
    public void setNota(Integer newNota) {
        if (newNota >= 0 && newNota <= 10)
            this.nota = newNota;
    }

    //Funciones Alumno
    public Boolean Aprobado()
    {
        Integer nota = getNota();

        if (nota >= 5)
            return true;
        else
            return false;
    }
}
