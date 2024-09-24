import java.util.ArrayList;

public class Alumnos {
    //Propiedades y Getter/Setters Alumnos
    private ArrayList<Alumno> listaAlumno = new ArrayList<Alumno>();

    //Agregar un nuevo alumno a la lista
    public void Agregar(Alumno alumno)
    {
        listaAlumno.add(alumno);
    }

    //Devuelve el alumno que esta en la posicion indicada
    public Alumno Obtener(Integer posicion)
    {
        //Si la posicion es valida devolvemos al alumno.
        if (posicion >= 0 && posicion <= listaAlumno.size()) {
            return listaAlumno.get(posicion);
        }

        //Si la posicion no es valida devolvemos null
        return null;
    }

    //Devuelve la nota media de los alumnos
    public Float Media ()
    {
        if (listaAlumno.size() == 0)
            return 0f;
        else {
            Float total = 0f;

            for (int i = 0; i < listaAlumno.size();i++)
                total += listaAlumno.get(i).getNota();

            return total/listaAlumno.size();
        }
    }
}
