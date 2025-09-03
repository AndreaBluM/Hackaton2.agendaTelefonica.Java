package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Persona {
    protected String nombre;
    protected String apellido;


    public Persona() {
    }

    public Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }


    @Override
    public int hashCode() {
        return (nombre.toLowerCase() + apellido.toLowerCase()).hashCode();
    }

    // metodo si los nombres son iguales en contenido
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Persona)) return false;

        Persona p = (Persona) obj;
        return nombre.equalsIgnoreCase(p.nombre) &&
                apellido.equalsIgnoreCase(p.apellido);
    }
    public String toString(){
        return nombre + " " + apellido;
    }

    public abstract String
    mostrarInfo();
}

