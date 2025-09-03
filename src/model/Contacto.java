package model;

import java.util.ArrayList;
import java.util.List;

public class Contacto extends Persona {
    private String telefono;
    private String email;

    public Contacto() {
    }

    public Contacto(String nombre, String apellido, String telefono, String email) {
        List<String> errores = new ArrayList<>();

        // Validaciones nombre y apellido
        if (nombre == null || nombre.isEmpty() || !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
            errores.add("El nombre no puede estar vacío ni contener números o símbolos.");
        }
        if (apellido == null || apellido.isEmpty() || !apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
            errores.add("El apellido no puede estar vacío ni contener números o símbolos.");
        }

        // Validaciones teléfono y email
        if (telefono == null || !telefono.matches("\\d{10}")) {
            errores.add("El teléfono debe tener exactamente 10 dígitos numéricos.");
        }
        if (email == null || !email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            errores.add("El email no es válido. Debe contener '@' y un dominio correcto.");
        }

        // Si hay errores, lanzamos todos juntos
        if (!errores.isEmpty()) {
            throw new IllegalArgumentException(String.join(" , ", errores));
        }

        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.matches("\\d{10}")) {
            throw new IllegalArgumentException("El teléfono debe tener exactamente 10 dígitos numéricos.");
        }
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }




    @Override
    public String mostrarInfo() {
        return nombre + " " + apellido + " - " + telefono + " - " + email;
    }
}


