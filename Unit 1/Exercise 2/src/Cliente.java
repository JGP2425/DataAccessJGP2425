import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente extends Persona {
    //Propiedaes y Getters/Setters de Cliente
    private String telefono;
    public String getTelefono() {
        return this.telefono;
    }
    public void setTelefono(String newTelefono) {
        String patronTelefono = "^((\\+|00)\\d{2,3})?\\d{9}$";
        Pattern pattern = Pattern.compile(patronTelefono);
        Matcher matcher = pattern.matcher(newTelefono);

        if (matcher.find())
            this.telefono = newTelefono;
        else
            throw new IllegalArgumentException(Literales.Fecha_nacimiento_no_valida);
    }

    private HashSet<Empresa> esClienteDe = new HashSet<Empresa>();


}
