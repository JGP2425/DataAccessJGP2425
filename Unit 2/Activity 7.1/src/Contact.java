import java.io.*;

public class Contact implements Serializable {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String description;

    public Contact(String _name, String _surname, String _email, String _phone, String _description)
    {
        name = _name;
        surname = _surname;
        email = _email;
        phone = _phone;
        description = _description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullName() {
        return this.name + " " + this.surname;
    }

    public String printContact() {
        return "Name: " + name + " " + surname + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phone + "\n" +
                "Description: " + description + "\n";
    }
}
