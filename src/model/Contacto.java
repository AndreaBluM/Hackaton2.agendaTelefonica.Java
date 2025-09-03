package model;

public class Contacto extends Persona {
    private String telefono;
    private String email;

    public Contacto() {
    }

    public Contacto(String nombre, String apellido, String telefono, String email) {
        super(nombre, apellido);
        this.telefono = telefono;
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (!telefono.matches("\\d{9}"))
            throw new IllegalArgumentException("El teléfono debe tener 9 dígitos.");
        this.telefono = telefono;
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!email.contains("@") || !email.contains("."))
            throw new IllegalArgumentException("El email debe contener '@' y '.'");
        this.email = email;
    }


    @Override
    public String mostrarInfo() {
        return nombre + " " + apellido + " - " + telefono + " - " + email;
    }
}
